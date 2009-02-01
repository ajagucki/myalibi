package com.neutralspace.alibi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.util.Log;


/**
 * Database access object for a User Event in Alibi.
 * 
 * This is the interface to the SQLite database we use for persisting user events
 * when the application is not running.
 */
public class UserEventDAO {

    public static final String KEY_LOC_LAT = "latitude";
    public static final String KEY_LOC_LON = "longitude";
    public static final String KEY_CATEGORY = "category";
    public static final String KEY_START_TIME = "startTime";
    public static final String KEY_END_TIME = "endTime";
    public static final String KEY_USER_NOTES = "userNotes";
    public static final String KEY_ROWID = "_id";
    public static final String KEY_CURRENT_ID = "currentId";

    private static final String TAG = "UserEventDAO";
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    
    /**
     * Database creation SQL statement
     */
    private static final String DB_CREATE_ALIBIS_TABLE =
            "create table alibis (_id integer primary key autoincrement, "
                    + "latitude real not null, "
                    + "longitude real not null, "
                    + "category text not null, "
                    + "startTime integer not null, "
                    + "endTime integer not null, "
                    + "userNotes text);";
    private static final String DB_CREATE_CURRENT_TABLE = "create table current (currentId integer not null);";

    private static final String DB_NAME = "my_alibi.db";
    private static final String DB_TABLE_ALIBIS = "alibis";
    private static final String DB_TABLE_CURRENT = "current";
    private static final int DB_VERSION = 1;

    private final Context context;

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DB_CREATE_ALIBIS_TABLE);
            db.execSQL(DB_CREATE_CURRENT_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_ALIBIS);
            db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_CURRENT);
            onCreate(db);
        }
    }

    /**
     * Constructor - takes the context to allow the database to be
     * opened/created
     * 
     * @param context the Context within which to work
     */
    public UserEventDAO(Context context) {
        this.context = context;
    }

    /**
     * Open the alibis database. If it cannot be opened, try to create a new
     * instance of the database. If it cannot be created, throw an exception to
     * signal the failure
     * 
     * @return this (self reference, allowing this to be chained in an
     *         initialization call)
     * @throws SQLException if the database could be neither opened or created
     */
    public UserEventDAO open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
        return this;
    }
    
    public void close() {
        dbHelper.close();
    }


    /**
     * Create a new user event. If the user event is
     * successfully created return the new rowId for that event, otherwise return
     * a -1 to indicate failure.
     * 
     * @param userEvent the UserEvent object
     * @return rowId or -1 if failed
     */
    public long createUserEvent(UserEvent userEvent) {
    	if (userEvent == null) {
    		Log.e(TAG, "Tried to store a null userEvent in the db.");
    		return -1;
    	}
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_LOC_LAT, userEvent.getLocation().getLatitude());
        initialValues.put(KEY_LOC_LON, userEvent.getLocation().getLongitude());
        initialValues.put(KEY_CATEGORY, userEvent.getCategory().getTitle());
        initialValues.put(KEY_START_TIME, userEvent.getStartTime());
        initialValues.put(KEY_END_TIME, userEvent.getEndTime());
        if (userEvent.getUserNotes() != null && userEvent.getUserNotes().length() > 0) {
        	initialValues.put(KEY_USER_NOTES, userEvent.getUserNotes());
        }

        return db.insert(DB_TABLE_ALIBIS, null, initialValues);
    }

    /**
     * Delete the user event with the given rowId
     * 
     * @param rowId id of user event to delete
     * @return true if deleted, false otherwise
     */
    public boolean deleteUserEvent(long rowId) {

        return db.delete(DB_TABLE_ALIBIS, KEY_ROWID + "=" + rowId, null) > 0;
    }

    /**
     * Return the user event stored at the given row id.
     * 
     * @param rowId id of user event to retrieve
     * @return user event stored at the given row id, if found
     * @throws SQLException if user event could not be found/retrieved
     */
    public UserEvent fetchUserEvent(long rowId) throws SQLException {
        String[] columns = { KEY_ROWID, KEY_LOC_LAT, KEY_LOC_LON, KEY_CATEGORY,
				KEY_START_TIME, KEY_END_TIME, KEY_USER_NOTES };
        Cursor cursor =
                db.query(true, DB_TABLE_ALIBIS, columns, KEY_ROWID + "=" + rowId, null,
                        null, null, null, null);
        if (cursor == null || cursor.moveToFirst() == false) {
        	throw new SQLException();
        }
        
        double latitude  = cursor.getDouble(cursor.getColumnIndex(KEY_LOC_LAT));
        double longitude = cursor.getDouble(cursor.getColumnIndex(KEY_LOC_LON));
        // TODO: Get the correct Location provider to construct this location
        Location location = new Location("gps");
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        UserEvent.Category category = UserEvent.Category.getCategory(cursor
				.getString(cursor.getColumnIndex(KEY_CATEGORY)));
        long startTime = cursor.getLong(cursor.getColumnIndex(KEY_START_TIME));
        long endTime = cursor.getLong(cursor.getColumnIndex(KEY_END_TIME));
        String userNotes = cursor.getString(cursor.getColumnIndex(KEY_USER_NOTES));
        UserEvent userEvent = new UserEvent(location, category, startTime);
        userEvent.setEndTime(endTime);
        if (userNotes != null) {
        	userEvent.setUserNotes(userNotes);
        }
        return userEvent;
    }

    /**
     * Update the user event using the details provided. The user event to be updated is
     * specified using the rowId, and it is altered to use the data from the user event
     * that is passed in.
     * 
     * @param rowId id of user event to update
     * @param userEvent contains the data used to update the stored user event 
     * @return true if the user event was successfully updated, false otherwise
     */
    public boolean updateUserEvent(long rowId, UserEvent userEvent) {
    	if (userEvent == null) {
    		Log.e(TAG, "Cannot update stored user event to a null user event.");
    		return false;
    	}
        ContentValues args = new ContentValues();
        args.put(KEY_LOC_LAT, userEvent.getLocation().getLatitude());
        args.put(KEY_LOC_LON, userEvent.getLocation().getLongitude());
        args.put(KEY_CATEGORY, userEvent.getCategory().getTitle());
        args.put(KEY_START_TIME, userEvent.getStartTime());
        args.put(KEY_END_TIME, userEvent.getEndTime());
        if (userEvent.getUserNotes() == null) {
        	args.put(KEY_USER_NOTES, "");
        }
        else {
        	args.put(KEY_USER_NOTES, userEvent.getUserNotes());
        }

        return db.update(DB_TABLE_ALIBIS, args, KEY_ROWID + "=" + rowId, null) > 0;
    }
    
    /**
     * Returns the id of the current user event.
     * 
     * @return the id of the current user event, or -1 if not set.
     */
    public long fetchCurrentId() {
    	Cursor cursor = db.query(DB_TABLE_CURRENT, null, null, null, null, null, null);
    	if (cursor == null || cursor.moveToFirst() == false) {
    		return -1;
    	}
    	return cursor.getLong(cursor.getColumnIndex(KEY_CURRENT_ID));
    }
    
    /**
     * Updates the current user event id.
     * 
     * If the current user event id has never been set, this method will
     * perform an insert on the database, instead.
     * 
     * @param rowId the id of the user event to make current
     * @return true if the current event id was updated, false otherwise
     */
    public boolean updateCurrentId(long rowId) {
    	ContentValues args = new ContentValues();
    	args.put(KEY_CURRENT_ID, rowId);
    	
    	if (db.update(DB_TABLE_CURRENT, args, null, null) == 0) {
    		return db.insert(DB_TABLE_CURRENT, null, args) >= 0;
    	}
    	return true;
    }

}
