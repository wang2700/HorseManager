package jw.horsemanager.Activities;

import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

import jw.horsemanager.Dialog.ChooseRepeatDialogFragment;
import jw.horsemanager.Misc.Constants;
import jw.horsemanager.Misc.SystemSetting;
import jw.horsemanager.R;

public class AddEditFeedingSchedule extends AppCompatActivity implements ChooseRepeatDialogFragment.ChooseRepeatDialogListener {

    private static final String TAG = "AddEditFeedingSchedule";

    private int editMode;
    private int feedingHour;
    private int feedingMinute;
    private Button feedingTimeBtn, repeatBtn;
    private EditText feedNameEditText, amountEditText;
    private boolean[] feedRepeat = new boolean[8];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_feeding_schedule);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        int editMode = (int) extras.get("mode");

        if (editMode == Constants.ADD) {
            getSupportActionBar().setTitle(getString(R.string.new_text) + " " + getString(R.string.feeding_schedule));
        } else if (editMode == Constants.EDIT) {
            getSupportActionBar().setTitle(getString(R.string.edit) + " " + getString(R.string.feeding_schedule));
        }

        feedingTimeBtn = (Button) findViewById(R.id.feed_time_btn);
        repeatBtn = (Button) findViewById(R.id.repeat_btn);
        feedNameEditText = (EditText) findViewById(R.id.feed_name_edit_text);
        amountEditText = (EditText) findViewById(R.id.amount_edit_text);

        //time picker for feeding time
        feedingTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        feedingHour = hourOfDay;
                        feedingMinute = minute;
                        String time;
                        int hour;
                        if (SystemSetting.is24HourFormat())
                            if (minute >= 10)
                                time = Integer.toString(hourOfDay) + ":" + Integer.toString(minute);
                            else
                                time = Integer.toString(hourOfDay) + ":0" + Integer.toString(minute);
                        else {
                            if (hourOfDay >= 12) {
                                hour = hourOfDay - 12;
                                if (minute >= 10)
                                    time = Integer.toString(hour) + ":" + Integer.toString(minute) + " PM";
                                else
                                    time = Integer.toString(hour) + ":0" + Integer.toString(minute) + " AM";
                            } else {
                                hour = hourOfDay;
                                if (hour == 0)
                                    hour = 12;
                                if (minute >= 10)
                                    time = Integer.toString(hour) + ":" + Integer.toString(minute) + " PM";
                                else
                                    time = Integer.toString(hour) + ":0" + Integer.toString(minute) + " AM";
                            }
                        }
                        feedingTimeBtn.setText(time);
                    }
                };

                Calendar calendar = Calendar.getInstance(Locale.getDefault());

                new TimePickerDialog(AddEditFeedingSchedule.this, onTimeSetListener,
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        SystemSetting.is24HourFormat()).show();
            }
        });

        //chooses repeat type
        final DialogFragment repeatDialog = new ChooseRepeatDialogFragment();
        repeatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                repeatDialog.show(getFragmentManager(), "ChooseRepeatDialogFragment");
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_feeding_schedule, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

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

    @Override
    public void onDialogPositiveClick(ChooseRepeatDialogFragment dialog) {
        feedRepeat = Arrays.copyOf(dialog.getChoicesAfter(), dialog.getChoicesAfter().length);
        Log.i(TAG, Arrays.toString(feedRepeat));
        dialog.setChoicesBefore(dialog.getChoicesAfter());
        String repeatText = "Repeat: ";
        if (feedRepeat[0]) {
            repeatText = repeatText + getString(R.string.mon_repeat) + " ";
        }

        if (feedRepeat[1]) {
            repeatText = repeatText + getString(R.string.tue_repeat) + " ";
        }

        if (feedRepeat[2]) {
            repeatText = repeatText + getString(R.string.wed_repeat) + " ";
        }

        if (feedRepeat[3]) {
            repeatText = repeatText + getString(R.string.thur_repeat) + " ";
        }

        if (feedRepeat[4]) {
            repeatText = repeatText + getString(R.string.fri_repeat) + " ";
        }

        if (feedRepeat[5]) {
            repeatText = repeatText + getString(R.string.sat_repeat) + " ";
        }
        if (feedRepeat[6]) {
            repeatText = repeatText + getString(R.string.sun_repeat) + " ";
        }
        repeatText = repeatText.substring(0, repeatText.length() - 2);
        if (checkEveryday(feedRepeat)){
            repeatText = getString(R.string.repeat_everyday);
        }
        repeatBtn.setText(repeatText);
    }


    @Override
    public void onDialogNegativeClick(ChooseRepeatDialogFragment dialog) {
        dialog.setChoicesAfter(dialog.getChoicesBefore());
    }

    private boolean checkEveryday (boolean[] choices){
        for (int i = 0; i < choices.length - 1; i++) {
            if (!choices[i])
                return false;
        }
        return true;
    }
}
