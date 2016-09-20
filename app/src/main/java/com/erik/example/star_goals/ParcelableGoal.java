package com.erik.example.star_goals;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by erik on 9/5/16.
 */
public class ParcelableGoal implements Parcelable{
    private String dbID;
    private String goal_name;
    private String category_name;
    private String created_date;

    public ParcelableGoal(String dbID, String goal_name, String category_name, String created_date){
        this.dbID = dbID;
        this.goal_name = goal_name;
        this.category_name = category_name;
        this.created_date = created_date;
    }
    public ParcelableGoal(Parcel in){
        this.dbID = in.readString();
        this.goal_name = in.readString();
        this.category_name = in.readString();
        this.created_date = in.readString();
    }

    public String getID(){
        return this.dbID;
    }
    public String getName(){
        return this.goal_name;
    }
    public String getCategory(){
        return this.category_name;
    }
    public String getDate(){
        return this.created_date;
    }


    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(dbID);
        dest.writeString(goal_name);
        dest.writeString(category_name);
        dest.writeString(created_date);
    }

    @Override
    public String toString(){
        return goal_name;
    }

    public static final Parcelable.Creator<ParcelableGoal> CREATOR = new Parcelable.Creator<ParcelableGoal>(){
        public ParcelableGoal createFromParcel(Parcel in){
            return new ParcelableGoal(in);
        }

        public ParcelableGoal[] newArray(int size){
            return new ParcelableGoal[size];
        }
    };
}
