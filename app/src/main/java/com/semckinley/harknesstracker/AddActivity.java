package com.semckinley.harknesstracker;

import android.app.ActivityOptions;
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
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;

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
    private static final String AD_CODE = "ca-app-pub-5879971744303882~6870610705";
    private static final String NO_AD = "No ad ready";
    private static final String TAG = "Tag";
    private static final String INTERSTITIAL = "ca-app-pub-5879971744303882/7058312966";
    private static final String NO_NAME = "No name entered";
    private static final String DISTRACTED = "Distracted";
    private static final String WELL_PREP= "Well prepared";

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addclass_info);
        String teacherID = getIntent().getStringExtra("ID");
        MobileAds.initialize(this, AD_CODE );
        mAd = new InterstitialAd(this);
        mAd.setAdUnitId(INTERSTITIAL);
        mAd.loadAd(new AdRequest.Builder().build());

        

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
                    cv.put(StudentContract.StudentEntry.COLUMN_ATTENTION, DISTRACTED);
                    cv.put(StudentContract.StudentEntry.COLUMN_COMMENT1, WELL_PREP);
                    cv.put(StudentContract.StudentEntry.COLUMN_COMMENT2, WELL_PREP);
                    cv.put(StudentContract.StudentEntry.COLUMN_COMMENT3, WELL_PREP);


                        mDb.insert(StudentContract.StudentEntry.TABLE_NAME, null, cv);


                    mStudentName.setText("");}
                    else{mStudentName.setText(NO_NAME);}
                }});
        //final ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(this);
            mSubmitButton = (Button) findViewById(R.id.submit_button);
           mSubmitButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                   if (mAd.isLoaded()) {
                        mAd.show();
                    } else {
                        Log.d(TAG, NO_AD);
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

        ImageView imageView = (ImageView) findViewById(R.id.iv_picasso);
        Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(imageView);
    }


}
