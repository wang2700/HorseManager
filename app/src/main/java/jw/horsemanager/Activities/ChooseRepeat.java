package jw.horsemanager.Activities;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.Arrays;

import jw.horsemanager.Misc.Constants;
import jw.horsemanager.R;

public class ChooseRepeat extends AppCompatActivity {

    private CheckBox[] dayOfWeekCb;
    private boolean[] dayOfWeek;
    private boolean[] currentSelection;
    private boolean isFirst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_repeat);

        currentSelection = Arrays.copyOf((boolean[])getIntent().getExtras().get("currentSelection"), 7);
        isFirst = (boolean) getIntent().getExtras().get("first");

        final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.repeat_radio_group);
        dayOfWeekCb = new CheckBox[7];
        dayOfWeekCb[0] = (CheckBox) findViewById(R.id.monday_check_box);
        dayOfWeekCb[1] = (CheckBox) findViewById(R.id.tuesday_check_box);
        dayOfWeekCb[2] = (CheckBox) findViewById(R.id.wednesday_check_box);
        dayOfWeekCb[3] = (CheckBox) findViewById(R.id.thursday_check_box);
        dayOfWeekCb[4] = (CheckBox) findViewById(R.id.friday_check_box);
        dayOfWeekCb[5] = (CheckBox) findViewById(R.id.saturday_check_box);
        dayOfWeekCb[6] = (CheckBox) findViewById(R.id.sunday_check_box);

        Button okayBtn = (Button) findViewById(R.id.okay_btn_choose_repeat);
        Button cancelBtn = (Button) findViewById(R.id.cancel_btn_choose_repeat);
        RadioButton repeatRb = (RadioButton) findViewById(R.id.repeat_radio_button);
        RadioButton notRepeatRb = (RadioButton) findViewById(R.id.not_repeat_radio_button);

        //assign previous selection
        if (AddEditFeedingSchedule.isNotRepeat(currentSelection)){
            notRepeatRb.setChecked(true);
        } else {
            if (isFirst){
                repeatRb.setChecked(true);
            } else {
                int i = 0;
                repeatRb.setChecked(true);
                for (CheckBox cb :
                        dayOfWeekCb) {
                    cb.setChecked(currentSelection[i]);
                    i++;
                }
            }
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int id) {
                if (id == R.id.not_repeat_radio_button) {
                    for (CheckBox cb :
                            dayOfWeekCb) {
                        cb.setEnabled(false);
                    }
                } else if (id == R.id.repeat_radio_button) {
                    for (CheckBox cb :
                            dayOfWeekCb) {
                        cb.setEnabled(true);
                    }
                }
            }
        });

        //when the user clicks okay button
        okayBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                dayOfWeek = new boolean[7];
                int i = 0;
                //when the not repeat radio button is selected
                if (radioGroup.getCheckedRadioButtonId() == R.id.not_repeat_radio_button){
                    Arrays.fill(dayOfWeek, false);
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.repeat_radio_button) {  //when the repeat radio button is selected
                    for (CheckBox cb :
                            dayOfWeekCb) {
                        dayOfWeek[i] = cb.isChecked();
                        i++;
                    }
                }
                Intent intent = new Intent();
                intent.putExtra("selection", dayOfWeek);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        //when user click the cancel button
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }
}
