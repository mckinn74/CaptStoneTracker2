package com.semckinley.harknesstracker;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import com.semckinley.harknesstracker.data.StudentContract;
import com.semckinley.harknesstracker.data.StudentDbHelper;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class ClassInformationService extends IntentService {


    public static final String ACTION_UPDATE_CLASS = "com.semckinley.harknesstracker.action.UPDATE_CLASS";
    private static Context mContext;


    public ClassInformationService() {
        super("ClassInformationService");
    }


    public static void startActionUpdateClass(Context context) {
        mContext = context;
        Intent intent = new Intent(context,ClassInformationService.class);
        intent.setAction(ACTION_UPDATE_CLASS);
        context.startService(intent);



        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if(ACTION_UPDATE_CLASS.equals(action)){
                handleActionUpdateClass();
            }

        }



    }


    private void handleActionUpdateClass(){

        StudentDbHelper studentDbHelper = new StudentDbHelper((mContext));
        SQLiteDatabase dB = studentDbHelper.getReadableDatabase();
        Uri uri = Uri.parse(StudentContract.StudentEntry.TABLE_NAME);
        Cursor  cursor = getContentResolver().query(uri,
                null,
                null,
                null,
                null,
                null);
        int count = cursor.getCount();


        StringBuilder sb = new StringBuilder();

        for (int i =0; i < count; i++)

        {        cursor.moveToPosition(i);

            String name = cursor.getString(cursor.getColumnIndex(StudentContract.StudentEntry.COLUMN_STUDENT_NAME));

            String num_speak = Integer.toString(cursor.getInt(cursor.getColumnIndex(StudentContract.StudentEntry.COLUMN_COUNT)));


            sb.append(name + ": " + num_speak);

            sb.append("\n");

        }
        String finalList = sb.toString();
       // LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent.putExtra("ClassList", finalList));
        //PreferenceManager.getDefaultSharedPreferences(this).edit().putString("ClassList", finalList);

    }
}
