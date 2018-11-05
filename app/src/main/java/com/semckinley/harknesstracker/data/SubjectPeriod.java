package com.semckinley.harknesstracker.data;

import java.util.List;

public class SubjectPeriod {
    private String mClassName;
    private List<StudentInfo> mStudentInfo;


    public SubjectPeriod(){}

   public SubjectPeriod(String className, List<StudentInfo> studentInfo){
        this.mClassName = className;
        this.mStudentInfo = studentInfo;

   }


    public String getClassName() {
        return mClassName;
    }

    public void setClassName(String className) {
        mClassName = className;
    }


    public List<StudentInfo> getStudentInfo() {
        return mStudentInfo;
    }

    public void setStudentInfo(List<StudentInfo> studentInfo) {
        mStudentInfo = studentInfo;
    }


}
