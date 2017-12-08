package jw.horsemanager.Activities;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

import jw.horsemanager.Misc.Constants;
import jw.horsemanager.Misc.SystemSetting;
import jw.horsemanager.Objects.FeedingSchedule;
import jw.horsemanager.R;

public class AddEditFeedingSchedule extends AppCompatActivity {

    private static final String TAG = "AddEditFeedingSchedule";

    private int editMode;
    private int feedingHour;
    private int feedingMinute;
    private Button feedingTimeBtn, repeatBtn;
    private EditText feedNameEditText, amountEditText;
    private boolean[] feedRepeat = new boolean[7];
    private boolean isFirstRepeat = true;
    private Spinner unitSpinner, feedTypeSpinner;
    private TextInputLayout feedNameTIL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_feeding_schedule);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        editMode = (int) extras.get("mode");

        if (editMode == Constants.ADD) {
            getSupportActionBar().setTitle(getString(R.string.new_text) + " " + getString(R.string.feeding_schedule));
        } else if (editMode == Constants.EDIT) {
            getSupportActionBar().setTitle(getString(R.string.edit) + " " + getString(R.string.feeding_schedule));
        }

        Arrays.fill(feedRepeat, true);

        feedingTimeBtn = (Button) findViewById(R.id.feed_time_btn);
        repeatBtn = (Button) findViewById(R.id.repeat_btn);
        feedNameEditText = (EditText) findViewById(R.id.feed_name_edit_text);
        amountEditText = (EditText) findViewById(R.id.amount_edit_text);
        unitSpinner = (Spinner) findViewById(R.id.unit_spinner);
        feedTypeSpinner = (Spinner) findViewById(R.id.feed_type_spinner);
        feedNameTIL = (TextInputLayout) findViewById(R.id.feed_name_text_input_layout);

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
        repeatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chooseRepeatIntent = new Intent(AddEditFeedingSchedule.this, ChooseRepeat.class);
                chooseRepeatIntent.putExtra("currentSelection", feedRepeat);
                chooseRepeatIntent.putExtra("first", isFirstRepeat);
                startActivityForResult(chooseRepeatIntent, Constants.REQUEST_CHOOSE_REPEAT);
            }
        });

        //configure feed type spinner
        ArrayAdapter<String> feedTypeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.feed_type));
        feedTypeSpinner.setAdapter(feedTypeAdapter);
        feedTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position) {
                    case 1:
                        unitSpinner.setAdapter(new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, view.getResources().getStringArray(R.array.unit_feed)));
                        break;
                    case 2:
                        unitSpinner.setAdapter(new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, view.getResources().getStringArray(R.array.unit_hay)));
                        break;
                    case 3:
                        unitSpinner.setAdapter(new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, view.getResources().getStringArray(R.array.unit_supplement)));
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //configure unit spinner
        unitSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.unit_feed)));

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
        } else {

            if (id == R.id.save_action_add_feeding_schedule) {
                if (!isFieldValid())
                    return super.onOptionsItemSelected(item);
                if (editMode == Constants.ADD) {
                    String feedName = feedNameEditText.getText().toString();
                    String feedType = (String) feedTypeSpinner.getSelectedItem();
                    double feedAmount = Double.parseDouble(amountEditText.getText().toString());
                    String unit = (String) unitSpinner.getSelectedItem();
                    FeedingSchedule newSchedule = new FeedingSchedule(feedName, feedRepeat, feedingHour, feedingMinute, feedAmount, unit, feedType);
                    File newFile = new File(HomeScreen.getFeedingScheduleFilePath()+"/"+newSchedule.getFileName());
                    try {
                        if (newFile.exists()){
                            newSchedule.setFileName(newSchedule.getFileName() + "1");
                            newFile = new File(HomeScreen.getFeedingScheduleFilePath()+"/"+newSchedule.getFileName());
                        }
                        newFile.createNewFile();
                        FileOutputStream scheduleFos = new FileOutputStream(newFile);
                        ObjectOutputStream scheduleOos = new ObjectOutputStream(scheduleFos);
                        scheduleOos.writeObject(newSchedule);
                        scheduleOos.flush();
                        scheduleFos.close();
                        scheduleOos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    HomeScreen.getFeedingScheduleList().add(newSchedule);
                    finish();
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_CHOOSE_REPEAT) {
            if (resultCode == RESULT_OK) {
                isFirstRepeat = false;
                feedRepeat = Arrays.copyOf((boolean[]) data.getExtras().get("selection"), 7);
                Log.i(TAG, "feed repeat result received");
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
                if (isEveryday(feedRepeat)) {
                    repeatText = getString(R.string.repeat_everyday);
                } else if (isNotRepeat(feedRepeat)) {
                    repeatText = getString(R.string.not_repeat);
                }
                repeatBtn.setText(repeatText);
            }
        }
    }

    static boolean isEveryday(boolean[] choices) {
        for (boolean choice : choices) {
            if (!choice)
                return false;
        }
        return true;
    }

    static boolean isNotRepeat(boolean[] choices) {
        for (boolean choice : choices) {
            if (choice)
                return false;
        }
        return true;
    }

    /**
     * this method will check each field to make sure it is properly filled out
     * @return true if all fields are okay
     */
    public boolean isFieldValid(){
        //check feed name and type
        boolean status = true;
        String feedType = (String) feedTypeSpinner.getSelectedItem();
        String[] feedTypeArr = getResources().getStringArray(R.array.feed_type);
        String feedName = feedNameEditText.getText().toString().trim();
        if (feedType.equals(feedTypeArr[0]) ||
                feedName.isEmpty() ||
                feedName.length() == 0 ||
                feedName.equals("")){
            feedNameTIL.setErrorEnabled(true);
            feedNameTIL.setError(getString(R.string.required_field));
            status = false;
        }

        //check for time
        TextView textView = (TextView) findViewById(R.id.time_error);
        if (feedingTimeBtn.getText().equals(getString(R.string.time))){
            textView.setVisibility(TextView.VISIBLE);
        } else {
            textView.setVisibility(TextView.GONE);
        }

        //check for amount
        TextInputLayout amountTIL = (TextInputLayout) findViewById(R.id.amount_TIL);
        String amount = (String) amountEditText.getText().toString().trim();
        if (amount.isEmpty() ||
                amount.length() == 0 ||
                amount.equals("")){
            amountTIL.setErrorEnabled(true);
            amountTIL.setError(getString(R.string.required_field));
        }

        return  status;
    }
}
