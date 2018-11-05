package com.semckinley.harknesstracker.data;

/**
 * Created by Mordor on 1/21/2018.
 */

public class StudentInfo {
     String name; //Student's name
     int count; //How many times student spoke
     double time;//The amount of time the student for which the student spoke

    public StudentInfo(){}

    public StudentInfo(String name, int count, double time){
        this.name = name;
        this.count = count;
        this.time = time;
    }

    public void setName(String name){
        this.name = name;
    }
    public void setCount(int count){
        this.count = count;
    }
    public void setTime(float time){
        this.time = time;
    }

    public String getName()
    {

        return this.name;
    }
    public int getCount(){
        return this.count;
    }
    public double getTime(){
        return this.time;
    }
}
