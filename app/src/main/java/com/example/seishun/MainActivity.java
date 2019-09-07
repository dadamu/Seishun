package com.example.seishun;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import  android.widget.CheckBox;
import  android.widget.TextView;
import android.os.Bundle;

import com.example.seishun.R;

public class MainActivity extends AppCompatActivity {

    private RadioGroup daySwitch;
    private RadioButton normalNoonButton, normalNightButton, holidayButton;
    private int dayValue = 2;

    private TextView personText, timeText;
    private SeekBar personBar, timeBar;
    private int personCount = 4;
    private int timeCount = 4;

    private RadioGroup machineSwitch;
    private RadioButton normalMachineButton, specialMachineButton;
    private int machineValue = 0;

    private CheckBox birthdayCheck, pointCheck;
    private boolean isBirthday = false;
    private boolean isPoint = false;

    private TextView timePriceText, personPriceText, totalPriceText, perPriceText;
    private int timePrice, personPrice, totalPrice, perPrice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        settingUI();
        ChangeText();
    }

    private void settingUI() {
        daySwitch = (RadioGroup) findViewById(R.id.daySwitch);
        normalNoonButton = (RadioButton) findViewById(R.id.normalNoonButton);
        normalNightButton = (RadioButton) findViewById(R.id.normalNightButton);
        holidayButton = (RadioButton) findViewById(R.id.holidayButton);
        daySwitch.setOnCheckedChangeListener(dayChangeListener);

        personBar = (SeekBar) findViewById(R.id.personBar);
        personBar.setOnSeekBarChangeListener(personChangeListener);
        timeBar = (SeekBar) findViewById(R.id.timeBar);
        timeBar.setOnSeekBarChangeListener(timeChangeListener);

        machineSwitch = (RadioGroup) findViewById(R.id.machineSwitch);
        machineSwitch.setOnCheckedChangeListener(machineChangeListener);
        normalMachineButton = (RadioButton) findViewById(R.id.normalMachineButton);
        specialMachineButton = (RadioButton) findViewById(R.id.specialMachineButton);

        birthdayCheck = (CheckBox) findViewById(R.id.birthdayCheck);
        birthdayCheck.setOnClickListener(birthdayClickListener);
        pointCheck = (CheckBox) findViewById(R.id.pointCheck);
        pointCheck.setOnClickListener(pointClickListener);

        personText = (TextView)findViewById(R.id.personText);
        timeText = (TextView)findViewById(R.id.timeText);
        timePriceText = (TextView)findViewById(R.id.timePriceText);
        personPriceText = (TextView)findViewById(R.id.personPriceText);
        totalPriceText = (TextView)findViewById(R.id.totalPriceText);
        perPriceText = (TextView)findViewById(R.id.perPriceText);
    }

    //Listener
    RadioGroup.OnCheckedChangeListener dayChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.normalNoonButton)
                dayValue = 0;
            else if (checkedId == R.id.normalNightButton)
                dayValue = 1;
            else
                dayValue = 2;
            ChangeText();
        }
    };

    private SeekBar.OnSeekBarChangeListener personChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            personCount = progress;
            ChangeText();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    };
    private SeekBar.OnSeekBarChangeListener timeChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            timeCount = progress;
            ChangeText();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };
    private RadioGroup.OnCheckedChangeListener machineChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.normalMachineButton)
                machineValue = 0;
            else
                machineValue = 1;
            ChangeText();
        }
    };
    private View.OnClickListener birthdayClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (birthdayCheck.isChecked())
                isBirthday = true;
            else
                isBirthday = false;
            ChangeText();
        }
    };
    private View.OnClickListener pointClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (pointCheck.isChecked())
                isPoint = true;
            else
                isPoint = false;
            ChangeText();
        }
    };

    //fuction
    private void Price(int dayValue, int machineValue) {
       if(dayValue == 0){
           personPrice = 220;
           if (machineValue == 0)
               timePrice = 230;
           else
               timePrice = 300;
       }
       else if(dayValue == 1){
           personPrice = 250;
           if (machineValue == 0)
               timePrice = 280;
           else
               timePrice = 340;
       }
       else{
           personPrice = 280;
           if (machineValue == 0)
               timePrice = 340;
           else
               timePrice = 420;
       }
    }
    private void Calc(int personCount, int timeCount, boolean isBirthday, boolean isPoint) {
        Price(dayValue,machineValue);
        int birthdayDiscount = isBirthday?1:0;
        int pointDiscount = isPoint?1:0;
        if(timeCount <=3)
            timeCount = 3;
        if(personCount<4)
            totalPrice = (timePrice - birthdayDiscount * 25 - pointDiscount * 30) * timeCount;
        else
            totalPrice = (timePrice - birthdayDiscount * 25 - pointDiscount * 30) * timeCount + personPrice * (personCount-3);
        perPrice = totalPrice / personCount;
    }
    private void ChangeText(){
        Calc(personCount,timeCount,isBirthday,isPoint);
        String timeTextTemp = getResources().getString(R.string.timeText);
        String personTextTemp = getResources().getString(R.string.personText);
        String timePriceTextTemp = getResources().getString(R.string.timePriceText);
        String personPriceTextTemp = getResources().getString(R.string.personPriceText);
        String totalPriceTextTemp = getResources().getString(R.string.totalPriceText);
        String perPriceTextTemp = getResources().getString(R.string.perPriceText);
        personText.setText(personTextTemp+personCount);
        timeText.setText(timeTextTemp+timeCount);
        timePriceText.setText(timePriceTextTemp + timePrice );
        personPriceText.setText(personPriceTextTemp + personPrice);
        totalPriceText.setText(totalPriceTextTemp+totalPrice);
        perPriceText.setText(perPriceTextTemp+perPrice);
    }
}
