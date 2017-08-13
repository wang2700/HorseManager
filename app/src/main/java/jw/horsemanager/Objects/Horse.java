package jw.horsemanager.Objects;

import java.util.ArrayList;
import java.util.Date;

/**
 * This horse object will store all the info for each horse in the spp
 */

public class Horse {
    private String name;
    private String picturePath;
    private Date birthday;
    private String Breed;
    //private FeedingSchedule
    private ArrayList<String> docPath;

    public Horse(String name, String picturePath, Date birthday, String breed, ArrayList<String> docPath) {
        this.name = name;
        this.picturePath = picturePath;
        this.birthday = birthday;
        Breed = breed;
        this.docPath = docPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
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

    public ArrayList<String> getDocPath() {
        return docPath;
    }

    public void setDocPath(ArrayList<String> docPath) {
        this.docPath = docPath;
    }
}
