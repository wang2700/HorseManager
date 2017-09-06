package jw.horsemanager.Adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import jw.horsemanager.Objects.FeedingSchedule;
import jw.horsemanager.R;

/**
 * Created by jerry on 9/5/2017.
 * Description:
 */

public class FeedingScheduleAdapter extends ArrayAdapter<FeedingSchedule> {

    public FeedingScheduleAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<FeedingSchedule> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_feeding_schedule, parent, false);
        }

        FeedingSchedule currentFeedingSchedule = getItem(position);

        TextView feedNameTv = (TextView) listItemView.findViewById(R.id.feed_name_list);
        TextView feedRepeatTv = (TextView) listItemView.findViewById(R.id.feed_repeat_list);
        TextView feedAmountTv = (TextView) listItemView.findViewById(R.id.feed_amount_list);

        feedNameTv.setText(currentFeedingSchedule.getFeedName() + " (" + currentFeedingSchedule.getFeedType() + ")");
        feedRepeatTv.setText(currentFeedingSchedule.getRepeatString());
        feedAmountTv.setText(currentFeedingSchedule.getAmountString());

        return listItemView;
    }
}
