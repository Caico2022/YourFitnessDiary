package com.studienarbeit.YourFitnessDiary.Spezialfunktionen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.studienarbeit.YourFitnessDiary.Main.MainActivity;
import com.studienarbeit.YourFitnessDiary.R;

public class Schrittzaehler extends AppCompatActivity implements View.OnClickListener, SensorEventListener  {

    ImageButton imgbtn_SchrittZurueck;
    TextView tV_steps, tV_info;
    SensorManager sensorManager;
    boolean running = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schrittzaehler);

        imgbtn_SchrittZurueck = findViewById(R.id.imgbtn_SchrittZurueck);
        tV_steps = findViewById(R.id.tV_steps);
        tV_info = findViewById(R.id.tV_info);
        imgbtn_SchrittZurueck.setOnClickListener(this);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        running = true;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (countSensor != null) {
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
        } else {
            Toast.makeText(this, "Sensor not found!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        running = false;
        // if you unregister the hardware will stop detecting steps
        // sensorManager.unregisterListener(this);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if (running) {
            tV_steps.setText(String.valueOf(event.values[0]));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


    public void onClick(View v) {
        if (v.getId() == R.id.imgbtn_SchrittZurueck) {
            Intent zuMainActivity = new Intent(this, MainActivity.class);
            startActivity(zuMainActivity);
            this.finish();
        }
    }
}

