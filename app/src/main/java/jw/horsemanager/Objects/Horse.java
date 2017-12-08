package jw.horsemanager.Objects;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * This horse object will store all the info for each horse in the spp
 */

public class Horse implements Serializable{
    private String name;
    private SerializedBitmap picture;
    private Date birthday;
    private String Breed;
    private FeedingSchedule[] feedingSchedules;
    //private ArrayList<String> docPath;

    public Horse(String name, SerializedBitmap picture, Date birthday, String breed, ArrayList<String> docPath) {
        this.name = name;
        this.picture = picture;
        this.birthday = birthday;
        Breed = breed;
        //this.docPath = docPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SerializedBitmap getPicture() {
        return picture;
    }

    public void setPicture(SerializedBitmap picture) {
        this.picture = picture;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getBreed() {
        return Breed;
    }

    public void setBreed(String breed) {
        Breed = breed;
    }

//    public ArrayList<String> getDocPath() {
//        return docPath;
//    }

//    public void setDocPath(ArrayList<String> docPath) {
//        this.docPath = docPath;
//    }
}
