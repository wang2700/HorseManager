package jw.horsemanager.Objects;

/**
 * Created by jerry on 8/24/2017.
 * Description:
 */

public class FeedingSchedule {

    private String feedName;
    private boolean[] repeatWeekday; // this array has 7 item starting on Monday to Sunday, if it is true, then repeat on that day
    private int timeHour;
    private int timeMinute;
    private double amount; // amount of feed
    private String unit;

    public FeedingSchedule(String feedName, boolean[] repeatWeekday, int timeHour, int timeMinute, boolean isAM, double amount, String unit) {
        this.feedName = feedName;
        this.repeatWeekday = repeatWeekday;
        this.timeHour = timeHour;
        this.timeMinute = timeMinute;
        this.amount = amount;
        this.unit = unit;
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
}
