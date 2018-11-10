package com.semckinley.harknesstracker;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;


public class StudentDetail extends AppCompatActivity {
    public static String STUDENT_NAME = "student_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail);
        Intent intent = getIntent();
        String student_name = intent.getStringExtra(STUDENT_NAME);
        Bundle b = new Bundle();
        b.putString(STUDENT_NAME, student_name);

        StudentDetailFragment sdFragment = new StudentDetailFragment();
        sdFragment.setArguments(b);
        FragmentManager fragManager = getSupportFragmentManager();
        fragManager.beginTransaction()
                .add(R.id.frag_stud_detail, sdFragment).commit();

    }
}
