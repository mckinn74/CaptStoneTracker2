 package com.semckinley.harknesstracker;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.common.base.Stopwatch;
import com.semckinley.harknesstracker.data.StudentContract;
import com.semckinley.harknesstracker.data.StudentDbHelper;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements HarkAdapter.HarkStudentClickListener, HarkAdapter.OnStudentSelection {

    private Boolean phoneFormat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(findViewById(R.id.student_detail_container) == null)
        {
            phoneFormat = true;
        }
        else{phoneFormat = false;}
        Bundle b = new Bundle();
        b.putBoolean("phone", phoneFormat);

        MainFragment mainFragment = new MainFragment();
        mainFragment.setArguments(b);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.class_fragment_container, mainFragment).commit();

        if(findViewById(R.id.student_detail_container) !=null){
            StudentDetailFragment studFragment = new StudentDetailFragment();
            fragmentManager.beginTransaction().add(R.id.student_detail_container, studFragment).commit();
        }

    }


    @Override
    public void onStudentClick(int clickedStudentIndex) {

    }
    @NonNull
    @Override
    public void onStudentSelected(String name) {
       if(findViewById(R.id.student_detail_container) != null) {
           StudentDetailFragment sdFragment = new StudentDetailFragment();
           FragmentManager fragmentManager = getSupportFragmentManager();
           Bundle b = new Bundle();
           b.putString("student_name", name);
           sdFragment.setArguments(b);
           fragmentManager.beginTransaction().replace(R.id.student_detail_container, sdFragment).commit();
       }
       else{

       }
    }
}
