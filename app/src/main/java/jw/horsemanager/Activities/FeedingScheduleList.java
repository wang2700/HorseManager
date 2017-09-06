package jw.horsemanager.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import jw.horsemanager.Adapter.FeedingScheduleAdapter;
import jw.horsemanager.Misc.Constants;
import jw.horsemanager.Objects.FeedingSchedule;
import jw.horsemanager.R;

public class FeedingScheduleList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeding_schedule_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if (HomeScreen.getFeedingScheduleList() != null) {
            FeedingScheduleAdapter feedingScheduleAdapter = new FeedingScheduleAdapter(this, R.layout.list_feeding_schedule, HomeScreen.getFeedingScheduleList());
            ListView listView = (ListView) findViewById(R.id.feeding_schedule_list_view);
            listView.setAdapter(feedingScheduleAdapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_feeding_schedule_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.add_action_feeding_list){
            Intent addFeedingSchedule = new Intent(FeedingScheduleList.this, AddEditFeedingSchedule.class);
            addFeedingSchedule.putExtra("mode", Constants.ADD);
            startActivity(addFeedingSchedule);
        }

        //when back button is pressed
        if (id == android.R.id.home) {
            /*
              TODO: add what happened when the back is pressed
              if any field is changed show dialog for confirm returning to previous screen
             */

            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
