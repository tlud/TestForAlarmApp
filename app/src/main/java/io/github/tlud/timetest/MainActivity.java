package io.github.tlud.timetest;

//AndroidX
import androidx.appcompat.app.AppCompatActivity;
//import android.support.v7.app.AppCompatActivity;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Handler handler = new Handler();
//    private Handler activityHandler = new Handler();

//    private Runnable startActivityRunnable;

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            count++;
//            Log.d("count", String.valueOf(count));
            timerText.setText(dataFormat.
                    format(initTime - count*period));
            handler.postDelayed(this, period);
        }
    };

    private TextView timerText;
    private SimpleDateFormat dataFormat =
            new SimpleDateFormat("mm:ss", Locale.JAPAN);

    private int count, period, initTime;
//    private int period;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        count = 0;
        period = 1000;
        initTime = 1 *60 * 1000;

        timerText = findViewById(R.id.timer);
        timerText.setText(dataFormat.format(0));

        handler.post(runnable);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent_fail = new Intent(getApplication(), Fail.class);
                startActivity(intent_fail);
            }
        }, initTime);

        // タイマー終了
        Button stopButton = findViewById(R.id.stop_button);
        stopButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                handler.removeCallbacks(runnable);
                timerText.setText(dataFormat.format(0));
                count = 0;
            }
        });
    }
}