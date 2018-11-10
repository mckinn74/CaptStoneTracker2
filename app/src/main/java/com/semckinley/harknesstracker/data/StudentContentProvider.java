package com.semckinley.harknesstracker.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.content.Context;
import com.semckinley.harknesstracker.data.StudentContract;

import static com.semckinley.harknesstracker.data.StudentContract.StudentEntry.TABLE_NAME;

public class StudentContentProvider extends ContentProvider {
    StudentDbHelper mStudentDbHelper;
    public final static int CLASS_NAME = 100;
    public final static int STUDENT_NAME = 101;
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    public static final String UNKNOWN = "Unknown uri: ";

    public static UriMatcher buildUriMatcher(){
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(StudentContract.AUTHORITY, StudentContract.STUDENT_INFORMATION, CLASS_NAME);
        uriMatcher.addURI(StudentContract.AUTHORITY, StudentContract.STUDENT_INFORMATION, STUDENT_NAME);


        return uriMatcher;
    }
    @Override
    public boolean onCreate() {
        Context context = getContext();
        mStudentDbHelper = new StudentDbHelper(context);

        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        final SQLiteDatabase db = mStudentDbHelper.getReadableDatabase();
        int match = sUriMatcher.match(uri);
        Cursor cursor;
        switch (match){
            case CLASS_NAME:
                cursor = db.query(TABLE_NAME,
                        strings,
                        s,
                        strings1, null, null,
                        s1);
                break;

            default:
                throw new UnsupportedOperationException(UNKNOWN  + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;



    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        final SQLiteDatabase db = mStudentDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);

        int classesDeleted; // starts as 0


        switch (match) {
            case CLASS_NAME:

                String id = uri.getPathSegments().get(1);
                classesDeleted = db.delete(TABLE_NAME, "id_number=?", new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (classesDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Return the number of tasks deleted
        return classesDeleted;





    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
