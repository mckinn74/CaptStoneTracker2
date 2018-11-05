package com.semckinley.harknesstracker;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
import com.semckinley.harknesstracker.data.StudentContract;
import com.semckinley.harknesstracker.data.StudentDbHelper;
import com.semckinley.harknesstracker.data.StudentInfo;
import com.semckinley.harknesstracker.data.SubjectPeriod;

import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stephen.mckinley on 1/26/18.
 */

public class AddActivity extends AppCompatActivity {

    private EditText mStudentName;
    private Button mAddButton;
    private Button mSubmitButton;
    private Button mClearButton;
    private SQLiteDatabase mDb;
  //  private FirebaseDatabase mDatabase;
   // private DatabaseReference mDatabaseReference;
    private InterstitialAd mAd;



    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addclass_info);
        String teacherID = getIntent().getStringExtra("ID");
        MobileAds.initialize(this,"ca-app-pub-5879971744303882~6870610705" );
        mAd = new InterstitialAd(this);
        mAd.setAdUnitId("ca-app-pub-5879971744303882/7058312966");
        mAd.loadAd(new AdRequest.Builder().build());
       /*mDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mDatabase.getReference("User").child(teacherID);
        List<StudentInfo> studentList = new ArrayList<StudentInfo>();
        StudentInfo studentInfo = new StudentInfo("Jill", 1, 2.5);
        StudentInfo studentInfo1 = new StudentInfo("Bob", 1, 3.0);
        studentList.add(studentInfo);
        studentList.add(studentInfo);
        studentList.add(studentInfo1);
        SubjectPeriod subjectPeriod = new SubjectPeriod("First Hour", studentList);
        mDatabaseReference.push().setValue(subjectPeriod);*/
        

        FirebaseAuth.getInstance().signOut();
        //create the student database helper and the db on first run
        StudentDbHelper dbHelper = new StudentDbHelper(this);

        //get a writeable database as we will be adding students...this may change as inputing data from a file and creating readable databases instead
        mDb = dbHelper.getWritableDatabase();
        //create the cursor to read from the database
        Cursor cursor; //more is needed here but not sure how it fits with my code yet

        mStudentName = (EditText) findViewById(R.id.et_student_name);

        mAddButton = (Button) findViewById(R.id.add_button);
        mAddButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                    if(mStudentName != null){
                    ContentValues cv = new ContentValues();
                    cv.put(StudentContract.StudentEntry.COLUMN_STUDENT_NAME,  mStudentName.getText().toString());
                    cv.put(StudentContract.StudentEntry.COLUMN_COUNT, 0);
                    cv.put(StudentContract.StudentEntry.COLUMN_MINUTES, 0);
                    cv.put(StudentContract.StudentEntry.COLUMN_SECONDS, 0);
                    cv.put(StudentContract.StudentEntry.COLUMN_MILLISECONDS, 0);
                    cv.put(StudentContract.StudentEntry.COLUMN_ATTENTION, "Distracted");
                    cv.put(StudentContract.StudentEntry.COLUMN_COMMENT1, "Well Prepared");
                    cv.put(StudentContract.StudentEntry.COLUMN_COMMENT2, "Relevant Questions");
                    cv.put(StudentContract.StudentEntry.COLUMN_COMMENT3, "Saved Discussion");


                        mDb.insert(StudentContract.StudentEntry.TABLE_NAME, null, cv);


                    mStudentName.setText("");}
                    else{mStudentName.setText("No name Entered");}
                }});

            mSubmitButton = (Button) findViewById(R.id.submit_button);
           mSubmitButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                   if (mAd.isLoaded()) {
                        mAd.show();
                    } else {
                        Log.d("TAG", "There isn't an ad ready.");
                    }

                    Intent intent = new Intent(AddActivity.this, MainActivity.class);

                    startActivity(intent);}

        });
           mClearButton = (Button) findViewById(R.id.clear_button);
           mClearButton.setOnClickListener(new View.OnClickListener(){
               @Override
               public void onClick(View v){


                   mDb.delete(StudentContract.StudentEntry.TABLE_NAME, null, null);
               }
           });

    }


}
