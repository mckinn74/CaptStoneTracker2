package com.semckinley.harknesstracker.data;

import android.provider.BaseColumns;

/**
 * Created by Mordor on 1/27/2018.
 * This Class is being used to replace StudentInfo class
 * Moving from using arraylist of StudentInfo to a more persistent data type
 * the SQLiteDatabase so that the data for the class remains once the app is closed
 * This hopefully will allow for the creation of mulitple class files that may be referenced in the future
 */

public class StudentContract {
    public static final String AUTHORITY = "com.semckinley.harknesstracker";
    public static final String STUDENT_INFORMATION = "studentinformation";
    public static final class StudentEntry implements BaseColumns{

        public static final String TABLE_NAME = "studentinformation";
        //Used internally as the name of the database table
        public static final String COLUMN_STUDENT_NAME = "studentName";
        //Used internally as the name of the column containing the student name
        public static final String COLUMN_COUNT = "speakCount";
        //Used internally as the name of the column containing the number of times the student has spoken during the discussion
        //public static final String COLUMN_TIME = "timeSpoke";
        //Used internally as the name of the column containing the amount of time the student spoke during the discussion
        public static final String COLUMN_MINUTES = "minutesSpoke";
        public static final String COLUMN_SECONDS = "secondsSpoke";
        public static final String COLUMN_MILLISECONDS= "milliSecondsSpoke";
        public static final String COLUMN_ATTENTION = "attentionLevel";
        public static final String COLUMN_COMMENT1 = "comment1";
        public static final String COLUMN_COMMENT2 = "comment2";
        public static final String COLUMN_COMMENT3 = "comment3";


    }
}
