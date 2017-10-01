package jw.horsemanager.Objects;

import java.io.Serializable;
import java.text.DecimalFormat;

import jw.horsemanager.R;

/**
 * Created by jerry on 8/24/2017.
 * Description:
 */

public class FeedingSchedule implements Serializable{

    private String feedName;
    private String feedType;
    private boolean[] repeatWeekday; // this array has 7 item starting on Monday to Sunday, if it is true, then repeat on that day
    private int timeHour;
    private int timeMinute;
    private double amount; // amount of feed
    private String unit;
    private String fileName;


    public FeedingSchedule(String feedName, boolean[] repeatWeekday, int timeHour, int timeMinute, double amount, String unit, String feedType) {
        this.feedName = feedName;
        this.repeatWeekday = repeatWeekday;
        this.timeHour = timeHour;
        this.timeMinute = timeMinute;
        this.amount = amount;
        this.unit = unit;
        this.feedType = feedType;

        this.fileName = feedName + "_" + Integer.toString(timeHour) + "_" + Integer.toString(timeMinute);
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


    public String getFeedType() {
        return feedType;
    }

    public void setFeedType(String feedType) {
        this.feedType = feedType;
    }

    public String getFeedName() {
        return feedName;
    }

    public void setFeedName(String feedName) {
        this.feedName = feedName;
    }

    public boolean[] getRepeatWeekday() {
        return repeatWeekday;
    }

    public void setRepeatWeekday(boolean[] repeatWeekday) {
        this.repeatWeekday = repeatWeekday;
    }

    public int getTimeHour() {
        return timeHour;
    }

    public void setTimeHour(int timeHour) {
        this.timeHour = timeHour;
    }

    public int getTimeMinute() {
        return timeMinute;
    }

    public void setTimeMinute(int timeMinute) {
        this.timeMinute = timeMinute;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getRepeatString(){
        String repeatText = "Repeat: ";
        if (repeatWeekday[0]) {
            repeatText = repeatText + "Mon, ";
        }

        if (repeatWeekday[1]) {
            repeatText = repeatText + "Tue, ";
        }

        if (repeatWeekday[2]) {
            repeatText = repeatText + "Wed, ";
        }

        if (repeatWeekday[3]) {
            repeatText = repeatText + "Thur, ";
        }

        if (repeatWeekday[4]) {
            repeatText = repeatText + "Fri, ";
        }

        if (repeatWeekday[5]) {
            repeatText = repeatText + "Sat, ";
        }
        if (repeatWeekday[6]) {
            repeatText = repeatText + "Sun, ";
        }
        repeatText = repeatText.substring(0, repeatText.length() - 2);
        if (isEveryday(repeatWeekday)){
            repeatText = "Repeat: Everyday";
        } else if (isNotRepeat(repeatWeekday)){
            repeatText = "Does not repeat";
        }

        return repeatText;
    }

    public String getAmountString() {
        DecimalFormat df = new DecimalFormat("#.00");
        String amountString = df.format(amount);
        return "Amount: " + amountString + " " + unit;
    }

    private boolean isEveryday(boolean[] choices){
        for (boolean choice : choices) {
            if (!choice)
                return false;
        }
        return true;
    }

    private boolean isNotRepeat (boolean[] choices){
        for (boolean choice : choices) {
            if (choice)
                return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return feedName + "\n" + getRepeatString() + "\n" + getAmountString();
    }
}
