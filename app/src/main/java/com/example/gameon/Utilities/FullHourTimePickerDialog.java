package com.example.gameon.Utilities;

import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.TimePicker;

public class FullHourTimePickerDialog extends TimePickerDialog {

    private final int MINUTE_INTERVAL = 60; // Set the minute interval to 60 minutes

    public FullHourTimePickerDialog(Context context, int timePickerStyle, OnTimeSetListener listener, int hourOfDay, int minute) {
        super(context, timePickerStyle,listener, hourOfDay, minute, true);
        updateTime(hourOfDay, minute);
    }

    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        super.onTimeChanged(view, hourOfDay, minute);

        // Adjust the displayed minute to the nearest multiple of MINUTE_INTERVAL
        int adjustedMinute = Math.round(minute / MINUTE_INTERVAL) * MINUTE_INTERVAL;
        if (minute != adjustedMinute){
            view.setMinute(adjustedMinute);
        }
    }
}
