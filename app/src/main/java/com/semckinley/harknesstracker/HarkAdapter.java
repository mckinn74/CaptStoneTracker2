package com.semckinley.harknesstracker;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.semckinley.harknesstracker.data.StudentContract;
import com.semckinley.harknesstracker.data.StudentDbHelper;
import com.semckinley.harknesstracker.data.StudentInfo;

import java.util.ArrayList;

/**
 * Created by Mordor on 1/20/2018.
 * See license statement in activity_main.xml
 */

public class HarkAdapter extends RecyclerView.Adapter<HarkAdapter.StudentViewHolder>{
    private static final String TAG = HarkAdapter.class.getSimpleName();
    private int mStudentItems;
    private  String[] mStudentNames;
    SQLiteDatabase mDb;
    static StudentDbHelper mStudentDbHelper;
    Cursor mCursor;
    static Context mContext;

    final private HarkStudentClickListener mOnClickListener;
    OnStudentSelection mCallBacks;

    ArrayList<StudentInfo> mStudentInfoList;

    public interface HarkStudentClickListener  {
        void onStudentClick(int clickedStudentIndex);
    }

    public HarkAdapter(Cursor cursor, HarkStudentClickListener listener, Context context){
        mCursor = cursor;
        //mStudentInfoList = new ArrayList<StudentInfo>();
        mContext = context;

        mOnClickListener = listener;
        mCallBacks = (OnStudentSelection) context;


        }




    //This is called to create the viewholders


    @Override
    public HarkAdapter.StudentViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();

        int layoutIdForStudentItem = R.layout.student_list;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForStudentItem, viewGroup, shouldAttachToParentImmediately);
        StudentViewHolder viewHolder = new StudentViewHolder(view);
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(HarkAdapter.StudentViewHolder holder, int position) {
        if(!mCursor.moveToPosition(position))
            return;

        String name = mCursor.getString(mCursor.getColumnIndex(StudentContract.StudentEntry.COLUMN_STUDENT_NAME));

        int count = mCursor.getInt(mCursor.getColumnIndex(StudentContract.StudentEntry.COLUMN_COUNT));
       int minuteTime = mCursor.getInt(mCursor.getColumnIndex(StudentContract.StudentEntry.COLUMN_MINUTES));
       int secondTime = mCursor.getInt(mCursor.getColumnIndex(StudentContract.StudentEntry.COLUMN_SECONDS));
       int milliSecondTime = mCursor.getInt(mCursor.getColumnIndex(StudentContract.StudentEntry.COLUMN_MILLISECONDS));


        holder.listStudentView.setText(name
                + "\n" + count + "\n" + minuteTime + ":" + String.format("%02d", secondTime) );
    }
    @Override
    public int getItemCount() {
       return mCursor.getCount();
       // return 15;
    }

    class StudentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView listStudentView;

        public StudentViewHolder(View itemView){
            super(itemView);

            listStudentView = itemView.findViewById(R.id.tv_item_student);
            itemView.setOnClickListener(this);

        }



        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            mCursor.moveToPosition(adapterPosition);
            String student_name  = mCursor.getString(mCursor.getColumnIndex(StudentContract.StudentEntry.COLUMN_STUDENT_NAME));


            mOnClickListener.onStudentClick(adapterPosition);
            mCallBacks.onStudentSelected(student_name);

        }
    }
public void setCursor (Cursor mCursor){this.mCursor = mCursor;}

    public interface OnStudentSelection{

        void onStudentSelected(String name);
    }
}


