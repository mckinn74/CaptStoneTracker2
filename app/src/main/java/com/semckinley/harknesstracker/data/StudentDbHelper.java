package com.semckinley.harknesstracker.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Mordor on 1/27/2018.
 * This will manage the local database for the student information
 * Hopefully I will be able to use this to create multiple classes
 * for use in the classroom.
 */

public class StudentDbHelper extends SQLiteOpenHelper{

    /*To do this I need to extend SQliteOpenHelper and to extend that I need to override onCreate and onUpgrade

     */

    private static final String DATABASE_NAME = "studentinfo.db";
    private static final int DATABASE_VERSION = 3;
    //initializing database version for future incrementation to upgrade database after changes
    //Create the constructor
    public StudentDbHelper (Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
    //Here I make the string that will create the database that will house the student information
        final String SQL_CREATE_STUDENT_TABLE =
                "CREATE TABLE " + StudentContract.StudentEntry.TABLE_NAME + " (" +
                        StudentContract.StudentEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        StudentContract.StudentEntry.COLUMN_STUDENT_NAME + " TEXT NOT NULL, " +
                        StudentContract.StudentEntry.COLUMN_COUNT + " INTEGER NOT NULL, "
                + StudentContract.StudentEntry.COLUMN_MINUTES + " INTEGER NOT NULL," +
                        StudentContract.StudentEntry.COLUMN_SECONDS + " INTEGER NOT NULL," +
                        StudentContract.StudentEntry.COLUMN_MILLISECONDS + " INTEGER NOT NULL," +
                        StudentContract.StudentEntry.COLUMN_ATTENTION + " TEXT NOT NULL,"+
                        StudentContract.StudentEntry.COLUMN_COMMENT1 + " TEXT NOT NULL," +
                        StudentContract.StudentEntry.COLUMN_COMMENT2 + " TEXT NOT NULL," +
                        StudentContract.StudentEntry.COLUMN_COMMENT3 + " TEXT NOT NULL" + "); ";
        db.execSQL(SQL_CREATE_STUDENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //The example I'm following in creating drops the table if there is a version change and creates a new one...
        db.execSQL("DROP TABLE IF EXISTS " + StudentContract.StudentEntry.TABLE_NAME);//TODO Study SQLiteDatabases
        onCreate(db);

    }
}


/*If everything is going correctly, this and StudentContract will replace StudentInfo
* I will now begin to make the changes to addActivity that will create the database entries instead of an arraylist
 */