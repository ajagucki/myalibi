#!/usr/bin/perl

system("adb -s `adb get-serialno` shell \"cd /data/data/com.android.providers.calendar/databases; sqlite3 calendar.db 'delete from events'\"");

