/*
 * Copyright (C) 2008 The Android Open Source Project
 * Modified by Armando Jagucki
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.neutralspace.alibi.widget;

import com.neutralspace.alibi.android.annotation.Widget;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.neutralspace.alibi.R;
import com.neutralspace.alibi.android.widget.NumberPicker;
import com.neutralspace.alibi.android.widget.NumberPicker.OnChangedListener;


/**
 * A view for selecting a delay in days / hours / minutes.
 */
@Widget
public class DelayPicker extends FrameLayout {
    private static final String[] displayedDays = new String[5];
    private static final String[] displayedHours = new String[25];
    private static final String[] displayedMinutes = new String[60];
    
    static {
    	for (int i = 0; i < 5; i++) {
    		displayedDays[i] = i + " d";
    	}
    	for (int i = 0; i < 24; i++) {
    		displayedHours[i] = i + " h";
    	}
    	for (int i = 0; i < 60; i++) {
    		displayedMinutes[i] = i + " m";
    	}
    }
    
    /* UI Components */
    private final NumberPicker daysPicker;
    private final NumberPicker hoursPicker;
    private final NumberPicker minutesPicker;
    
    /**
     * How we notify users the delay has changed.
     */
    private OnDelayChangedListener onDelayChangedListener;

    private int days;
    private int hours;
    private int minutes;

    /**
     * The callback used to indicate the user changes the delay.
     */
    public interface OnDelayChangedListener {

        /**
         * @param view The view associated with this listener.
         * @param minutes The minutes that were set.
         * @param days The days that were set.
         * @param hours The hours that were set.
         */
    	void onDelayChanged(DelayPicker view, int minutes, int days, int hours);
    }

    public DelayPicker(Context context) {
        this(context, null);
    }
    
    public DelayPicker(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DelayPicker(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.delay_picker,
            this, // we are the parent
            true);
        
        hoursPicker = (NumberPicker) findViewById(R.id.hours);
        hoursPicker.setFormatter(NumberPicker.TWO_DIGIT_FORMATTER);
        hoursPicker.setRange(0, 23, displayedHours);
        hoursPicker.setSpeed(100);
        hoursPicker.setOnChangeListener(new OnChangedListener() {
            public void onChanged(NumberPicker picker, int oldVal, int newVal) {
                hours = newVal;
                if (onDelayChangedListener != null) {
                	onDelayChangedListener.onDelayChanged(DelayPicker.this, minutes, days, hours);
                }
                updateMinutes();
            }
        });
        daysPicker = (NumberPicker) findViewById(R.id.days);
        daysPicker.setFormatter(NumberPicker.TWO_DIGIT_FORMATTER);
        daysPicker.setRange(0, 4, displayedDays);
        daysPicker.setSpeed(100);
        daysPicker.setOnChangeListener(new OnChangedListener() {
            public void onChanged(NumberPicker picker, int oldVal, int newVal) {
                days = newVal;
                if (onDelayChangedListener != null) {
                	onDelayChangedListener.onDelayChanged(DelayPicker.this, minutes, days, hours);
                }
                updateMinutes();
            }
        });
        minutesPicker = (NumberPicker) findViewById(R.id.minutes);
        minutesPicker.setRange(0, 59, displayedMinutes);
        minutesPicker.setSpeed(100);
        minutesPicker.setCurrent(30);
        minutesPicker.setOnChangeListener(new OnChangedListener() {
            public void onChanged(NumberPicker picker, int oldVal, int newVal) {
                minutes = newVal;
                if (onDelayChangedListener != null) {
                	onDelayChangedListener.onDelayChanged(DelayPicker.this, minutes, days, hours);
                }
                updateMinutes();
            }
        });
        
        if (!isEnabled()) {
            setEnabled(false);
        }
    }
    
    protected void updateMinutes() {
        if (daysPicker.getCurrent() == 0 && hoursPicker.getCurrent() == 0
                && minutesPicker.getCurrent() == 0) {
            minutes = 1;
            minutesPicker.setCurrent(1);
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        daysPicker.setEnabled(enabled);
        hoursPicker.setEnabled(enabled);
        minutesPicker.setEnabled(enabled);
    }

    public void updateDelay(int days, int hours, int minutes) {
        this.days = days;
        daysPicker.setCurrent(this.days);
        this.hours = hours;
        hoursPicker.setCurrent(this.hours);
        this.minutes = minutes;
        minutesPicker.setCurrent(this.minutes);
    }

    private static class SavedState extends BaseSavedState {

        private final int minutes;
        private final int days;
        private final int hours;

        /**
         * Constructor called from {@link DelayPicker#onSaveInstanceState()}
         */
        private SavedState(Parcelable superState, int days, int hours, int minutes) {
            super(superState);
            this.days = days;
            this.hours = hours;
            this.minutes = minutes;
        }
        
        /**
         * Constructor called from {@link #CREATOR}
         */
        private SavedState(Parcel in) {
            super(in);
            minutes = in.readInt();
            days = in.readInt();
            hours = in.readInt();
        }

        public int getMinutes() {
            return minutes;
        }

        public int getDays() {
            return days;
        }

        public int getHours() {
            return hours;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(minutes);
            dest.writeInt(days);
            dest.writeInt(hours);
        }

        public static final Parcelable.Creator<SavedState> CREATOR =
                new Creator<SavedState>() {

                    public SavedState createFromParcel(Parcel in) {
                        return new SavedState(in);
                    }

                    public SavedState[] newArray(int size) {
                        return new SavedState[size];
                    }
                };
    }


    /**
     * Override so we are in complete control of save / restore for this widget.
     */
    @Override
    protected void dispatchRestoreInstanceState(SparseArray<Parcelable> container) {
        dispatchThawSelfOnly(container);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        
        return new SavedState(superState, days, hours, minutes);
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        days = ss.getDays();
        hours = ss.getHours();
        minutes = ss.getMinutes();
    }

    /**
     * Initialize the state.
     * @param days The initial days.
     * @param hours The initial hours.
     * @param minutes The initial minutes.
     * @param onDateChangedListener How user is notified delay is changed by user, can be null.
     */
    public void init(int days, int hours, int minutes,
            OnDelayChangedListener onDelayChangedListener) {
        this.days = days;
        this.hours = hours;
        this.minutes = minutes;
        this.onDelayChangedListener = onDelayChangedListener;
    }

	public int getDays() {
		return days;
	}

	public int getHours() {
		return hours;
	}

	public int getMinutes() {
		return minutes;
	}
}

