package com.semckinley.harknesstracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.semckinley.harknesstracker.data.StudentContract;
import com.semckinley.harknesstracker.data.StudentDbHelper;


public class  StudentDetailFragment extends Fragment implements AdapterView.OnItemSelectedListener{
    private TextView mStudentName;
    private TextView mCountView;
    private TextView mTimeView;
    private RadioGroup mRadioGroup;
    private RadioButton mAttentive;
    private RadioButton mDistracted;
    private RadioButton mDisruptive;
    private Spinner mSpinner1;
    private Spinner mSpinner2;
    private Spinner mSpinner3;
    Context mContext;
            //OnStudentSelection mListener;
    SQLiteDatabase mDB;
    StudentDbHelper mDBHelper;
    Cursor mCursor;
    String mID;
    int count = 0;
    Boolean isFirstItemSelected0 = true;
    Boolean isFirstItemSelected1 = true;
    Boolean isFirstItemSelected2 = true;
    private static final String NOTHING = "nothing";
    private static final String STUDENT_NAME = "student_name";
    private static final String TIME = "Time: ";
    private static final String COUNT = "Count: ";
    private static final String SELECTED = "You selected: ";
    private static final String QUESTION = " =? ";
    public StudentDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mContext = getContext();
    mDBHelper = new StudentDbHelper(mContext);
       mDB = mDBHelper.getWritableDatabase();

        View rootView = inflater.inflate(R.layout.fragment_student_detail, container, false);
        mTimeView = (TextView) rootView.findViewById(R.id.tv_detail_time);
        mStudentName = (TextView) rootView.findViewById(R.id.tv_detail_student_name);
        mCountView = (TextView) rootView.findViewById(R.id.tv_detail_count);
        /*mRadioGroup = (RadioGroup) rootView.findViewById(R.id.rd_group_detail);
        mAttentive = (RadioButton) rootView.findViewById(R.id.rd_bt_attentive);
        mDisruptive = (RadioButton) rootView.findViewById(R.id.rd_bt_disruptive);
        mDistracted = (RadioButton) rootView.findViewById(R.id.rd_bt_distracted);*/
        mSpinner1 = (Spinner) rootView.findViewById(R.id.sp_comment1);
        mSpinner2 = (Spinner) rootView.findViewById(R.id.sp_comment2);
        mSpinner3 = (Spinner) rootView.findViewById(R.id.sp_comment3);
        mContext = getContext();

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(mContext,
                R.array.Comments, android.R.layout.simple_spinner_item);

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSpinner1.setAdapter(adapter1);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(mContext,
                R.array.Comments, android.R.layout.simple_spinner_item);

    mSpinner1.setOnItemSelectedListener(this);

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSpinner2.setAdapter(adapter2);
        mSpinner2.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(mContext,
                R.array.Comments, android.R.layout.simple_spinner_item);

        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSpinner3.setAdapter(adapter3);
        mSpinner3.setOnItemSelectedListener(this);
        if(mDB != null && getArguments() != null ){
            String student_name = getArguments().getString(STUDENT_NAME);
            String [] studentName = {"" + student_name};
            mStudentName.setText(student_name);

            String sColumns = "" +StudentContract.StudentEntry.COLUMN_STUDENT_NAME + QUESTION ;
            mCursor = mDB.query(StudentContract.StudentEntry.TABLE_NAME, null , sColumns, studentName,
                    null, null, null,
                    null);
            mCursor.moveToFirst();
            mID = mCursor.getString(mCursor.getColumnIndex(StudentContract.StudentEntry._ID));
            String comment1 = mCursor.getString(mCursor.getColumnIndex(StudentContract.StudentEntry.COLUMN_COMMENT1));
            mSpinner1.setSelection(adapter1.getPosition(comment1));
            String comment2 = mCursor.getString(mCursor.getColumnIndex(StudentContract.StudentEntry.COLUMN_COMMENT2));
            mSpinner2.setSelection(adapter2.getPosition(comment2));

            String comment3 = mCursor.getString(mCursor.getColumnIndex(StudentContract.StudentEntry.COLUMN_COMMENT3));
            mSpinner3.setSelection(adapter3.getPosition(comment3));
            String time = TIME + mCursor.getInt(mCursor.getColumnIndex(StudentContract.StudentEntry.COLUMN_MINUTES)) +":"
                    + mCursor.getInt(mCursor.getColumnIndex(StudentContract.StudentEntry.COLUMN_SECONDS));
            mTimeView.setText(time );

            String count = COUNT + mCursor.getInt(mCursor.getColumnIndex(StudentContract.StudentEntry.COLUMN_COUNT));
                    mCountView.setText(count);
            mCursor.close();
        }
        return rootView;
    }




    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(isFirstItemSelected0 || isFirstItemSelected1 || isFirstItemSelected2){
            switch(count) {

                case 0:    isFirstItemSelected0 = false;
                count++;
                break;
                case 1: isFirstItemSelected1 = false;
                count++;
                break;
                case 2: isFirstItemSelected2 = false;
                count++;
                    break;
                default: count++;
                break;

            }
        }
       else{
            ContentValues cv = new ContentValues();

            String selected =NOTHING;
        if(adapterView.equals(mSpinner1)) {
            selected = mSpinner1.getItemAtPosition(i).toString();
            cv.put(StudentContract.StudentEntry.COLUMN_COMMENT1, selected);
        }
        else if(adapterView.equals(mSpinner2)){
                selected = mSpinner2.getItemAtPosition(i).toString();
                 cv.put(StudentContract.StudentEntry.COLUMN_COMMENT2, selected);

        }
        else if(adapterView.equals(mSpinner3)) {
            selected = mSpinner3.getItemAtPosition(i).toString();
            cv.put(StudentContract.StudentEntry.COLUMN_COMMENT3, selected);


            String selection ="" + StudentContract.StudentEntry._ID + QUESTION ;
            String [] selectionArgs = {mID};
            mDB.update(StudentContract.StudentEntry.TABLE_NAME, cv, selection, selectionArgs);

      Toast.makeText(mContext, SELECTED + selected, Toast.LENGTH_LONG).show();}
    }}

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}
