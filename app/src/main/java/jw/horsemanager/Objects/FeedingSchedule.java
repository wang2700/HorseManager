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
    private boolean isAM; // if the time is AM then it is true.
    private double amount; // amount of feed
    private String unit;

}
