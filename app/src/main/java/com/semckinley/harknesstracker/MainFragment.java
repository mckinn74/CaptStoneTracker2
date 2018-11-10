package com.semckinley.harknesstracker;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.common.base.Stopwatch;
import com.semckinley.harknesstracker.data.StudentContract;
import com.semckinley.harknesstracker.data.StudentDbHelper;

import java.util.concurrent.TimeUnit;


public class MainFragment extends Fragment implements HarkAdapter.HarkStudentClickListener{


    private static final int NUM_STUDENTS = 15;

    private HarkAdapter mAdapter;

    private RecyclerView mStudentList;

    StudentDbHelper mStudentDbHelper;
    SQLiteDatabase mDb;
    static Cursor cursor;
    public long minuteTime;
    public long  secondTime;
    public long milliSecondTime;
    public long startTime;
    public boolean startClick = false;
    public long updateTime;
    Button awkwardPause;
    public int mPrevClick;
    public long prevTime;
    Stopwatch stopwatch ;
    Handler mHandler;
    TextView pauseTime;
    public long timeBuff, pauseMillis = 0L;
    FloatingActionButton mFab;
    private Boolean phoneFormat = false;
    private static final String PHONE = "phone";
    private static final String STUDENT_NAME = "student_name";
    private static final String PAUSED_FOR = "Paused for: ";


    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_class_detail, container, false);
        mFab = rootView.findViewById(R.id.floatingActionButton2);
        if(getArguments() != null)
        { phoneFormat = getArguments().getBoolean(PHONE);}
       if(phoneFormat){
             mFab.setVisibility(View.VISIBLE);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), StudentDetail.class);
                String student_name = cursor.getString(cursor.getColumnIndex(StudentContract.StudentEntry.COLUMN_STUDENT_NAME));
                intent.putExtra(STUDENT_NAME, student_name);
                startActivity(intent);
            }
        });
        } else{
            mFab.setVisibility(View.GONE);

       }

        startTime = System.currentTimeMillis();

        mHandler = new Handler();
        awkwardPause = (Button) rootView.findViewById(R.id.bt_awkPause);
        pauseTime = (TextView) rootView.findViewById(R.id.pauseTime);
        awkwardPause.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                stopwatch.stop();
                updateTime = stopwatch.elapsed(TimeUnit.MILLISECONDS) + milliSecondTime;
                secondTime = (updateTime/1000)%60;
                minuteTime = (updateTime/1000)/60;
                ContentValues cv3 = new ContentValues();
                cv3.put(StudentContract.StudentEntry.COLUMN_MINUTES, minuteTime);
                cv3.put(StudentContract.StudentEntry.COLUMN_SECONDS, secondTime);
                cv3.put(StudentContract.StudentEntry.COLUMN_MILLISECONDS,updateTime);
                int updateQuantiy = mDb.update(StudentContract.StudentEntry.TABLE_NAME, cv3, StudentContract.StudentEntry._ID + "=" + mPrevClick, null);
                Cursor cursor = mDb.query(StudentContract.StudentEntry.TABLE_NAME, null, null, null, null, null,
                        null);
                mAdapter.setCursor(cursor);
                mAdapter.notifyDataSetChanged();

                mHandler.removeCallbacks(runnable);
                startTime = SystemClock.uptimeMillis();
                timeBuff += pauseMillis;
                stopwatch = Stopwatch.createStarted();
                mHandler.postDelayed(runnable, 0);


            }
        });
        mPrevClick = 1000;
        Context context = getContext();
        mStudentDbHelper = new StudentDbHelper(context);
        //The above pretty much has to happen in all 'main activities' to get things rolling. At least as far as I know at this time

        mStudentList = (RecyclerView) rootView.findViewById(R.id.rv_students); //this creates the link between the java variable mStudentList and the Recyclerview in the layout xml file

        //below is the linearlayout manager set up that will help construct the recycler view?

        GridLayoutManager layoutManager = new GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false);
        mStudentList.setLayoutManager(layoutManager);
        //This links the recyclerview to the layoutmanager I've chosen at this time. Initially linear.


        mStudentList.setHasFixedSize(false);
        //going with true at first for simplicity sake, as it was in the example. However, classes can vary in size so may make this set to false in future
        mDb = mStudentDbHelper.getReadableDatabase();
        cursor = mDb.query(StudentContract.StudentEntry.TABLE_NAME, null, null, null, null, null,
                null);
        mAdapter = new HarkAdapter(cursor, this, context);
        mStudentList.setAdapter(mAdapter);



        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cursor.close();
    }

   // @Override
    public void onStudentClick(int clickedStudentIndex) {
        Context context = getContext();
        mHandler.removeCallbacks(runnable);

        if (!cursor.moveToPosition( clickedStudentIndex)) return;
        if(startClick){
            stopwatch.stop();
            updateTime = stopwatch.elapsed(TimeUnit.MILLISECONDS) + milliSecondTime;
            secondTime = (updateTime/1000)%60;
            minuteTime = (updateTime/1000)/60;
        }
        mStudentDbHelper = new StudentDbHelper(context);
        mDb = mStudentDbHelper.getWritableDatabase();
        int count = cursor.getInt(cursor.getColumnIndex(StudentContract.StudentEntry.COLUMN_COUNT));
        count++;
        milliSecondTime= cursor.getLong(cursor.getColumnIndex(StudentContract.StudentEntry.COLUMN_MILLISECONDS));




        int clickedDbIndex = cursor.getInt(cursor.getColumnIndex(StudentContract.StudentEntry._ID));

        ContentValues cv = new ContentValues();
        ContentValues cv2 = new ContentValues();

        cv.put(StudentContract.StudentEntry.COLUMN_COUNT, count);
        cv2.put(StudentContract.StudentEntry.COLUMN_MINUTES, minuteTime);
        cv2.put(StudentContract.StudentEntry.COLUMN_SECONDS, secondTime);
        cv2.put(StudentContract.StudentEntry.COLUMN_MILLISECONDS,updateTime);
        int updateQuantiy = mDb.update(StudentContract.StudentEntry.TABLE_NAME, cv, StudentContract.StudentEntry._ID + "=" + clickedDbIndex, null);
        if(mPrevClick != 1000) {
            int updateQuantity = mDb.update(StudentContract.StudentEntry.TABLE_NAME, cv2, StudentContract.StudentEntry._ID + "=" + mPrevClick, null);
        }
        cursor = mDb.query(StudentContract.StudentEntry.TABLE_NAME, null, null, null, null, null,
                null);
        mPrevClick = clickedDbIndex;
        startClick = true;
        stopwatch = Stopwatch.createStarted();
        mAdapter.setCursor(cursor);
        mAdapter.notifyDataSetChanged();

    }
    //
    public Runnable runnable = new Runnable(){

        public void run(){
            startClick= false;

            pauseTime.setText( PAUSED_FOR+ String.format("%02d", stopwatch.elapsed(TimeUnit.MILLISECONDS)/1000/60) + ":" +
                    String.format("%02d", stopwatch.elapsed(TimeUnit.MILLISECONDS)/1000%60) );

            mHandler.postDelayed(runnable, 0);
        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }




    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
