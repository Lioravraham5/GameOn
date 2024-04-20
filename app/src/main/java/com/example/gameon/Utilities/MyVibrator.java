package com.example.gameon.Utilities;

import static androidx.core.content.ContextCompat.getSystemService;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

public class MyVibrator {

    private Context context;

    public MyVibrator(Context context){
        this.context = context;
    }

    public void vibrate() {
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        }
        else {
            //deprecated in API 26
            v.vibrate(500);
        }
    }

}
