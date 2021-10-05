package org.hashhackers.focus.Features.Fitness;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import org.hashhackers.focus.R;
import org.hashhackers.focus.Tools.Date_Format;
import org.hashhackers.focus.Tools.listner;

import static android.hardware.Sensor.TYPE_STEP_DETECTOR;

public class Steps_Counter extends Service implements SensorEventListener  {
    SensorManager sensorManager;
    Sensor steps_counter;
    Sensor steps_Detector;
    int steps_Taken=0;
    int StepsDetected=0;
    long stepTime;
    int reportedSteps=0;
    Sensor steps_Accelator;

    TextView steps;
    Boolean Automets;
listner listner;


    Context context;
    public Steps_Counter(final Context context,listner listner,Boolean AutoMets){
        this.context=context;
        this.Automets=AutoMets;
        this.listner = listner;

        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        steps_Detector = sensorManager.getDefaultSensor(TYPE_STEP_DETECTOR);
        steps_counter = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        steps_Accelator = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        RegisterSensors();

    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        Date_Format date_format = new Date_Format();
        Sensor sensor = event.sensor;
//      switch (event.sensor.getType()){
//          case Sensor.TYPE_ACCELEROMETER:
//              break;
//
//              case Sensor.TYPE_STEP_DETECTOR:
//                  StepsDetected++;
//                  Log.i("StepsDetect", String.valueOf(StepsDetected));
//                  steps.setText(String.valueOf(StepsDetected));
//              break;
//
//              case Sensor.TYPE_STEP_COUNTER:
//                  if(steps_Taken<1){
//
//                      reportedSteps=(int) event.values[0];
//                  }
//                  steps_Taken=(int)event.values[0]-reportedSteps;


//              break;
//
//      }

        if(event.sensor.getType()==Sensor.TYPE_STEP_DETECTOR){
            StepsDetected++;


         listner.Steps_detect(StepsDetected);




        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
   public void RegisterSensors(){
        if(!CheckSensorCaps()){
//            Toast.makeText(context, "Need sensors", Toast.LENGTH_SHORT).show();
        }
        else {

//            sensorManager.registerListener((SensorEventListener) Steps_Counter.this, steps_Accelator, SensorManager.SENSOR_STATUS_ACCURACY_HIGH);
//            sensorManager.registerListener((SensorEventListener) Steps_Counter.this, steps_counter, SensorManager.SENSOR_STATUS_ACCURACY_HIGH);
            sensorManager.registerListener((SensorEventListener) Steps_Counter.this, steps_Detector, SensorManager.SENSOR_DELAY_FASTEST);

        }  }
    public void UNRegisterSensors(){
        sensorManager.unregisterListener((SensorEventListener) context, sensorManager.getDefaultSensor(TYPE_STEP_DETECTOR));
        sensorManager.unregisterListener((SensorEventListener) context,sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER));
        sensorManager.unregisterListener((SensorEventListener) context,sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
    }
    public Boolean CheckSensorCaps(){
        PackageManager packageManager = context.getPackageManager();
        int currentAPI = Build.VERSION.SDK_INT;
        System.out.println(packageManager.hasSystemFeature(PackageManager.FEATURE_SENSOR_STEP_COUNTER));
        System.out.println( packageManager.hasSystemFeature(PackageManager.FEATURE_SENSOR_STEP_DETECTOR));
        System.out.println(packageManager.hasSystemFeature(PackageManager.FEATURE_SENSOR_ACCELEROMETER));
        return  currentAPI >=19
                && packageManager.hasSystemFeature(PackageManager.FEATURE_SENSOR_STEP_COUNTER)
                && packageManager.hasSystemFeature(PackageManager.FEATURE_SENSOR_STEP_DETECTOR)
                && packageManager.hasSystemFeature(PackageManager.FEATURE_SENSOR_ACCELEROMETER);

    }
//    private void acquireWakeLock() {
//        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
//        int wakeFlags;
//        if (mPedometerSettings.wakeAggressively()) {
//            wakeFlags = PowerManager.SCREEN_DIM_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP;
//        }
//        else if (mPedometerSettings.keepScreenOn()) {
//            wakeFlags = PowerManager.SCREEN_DIM_WAKE_LOCK;
//        }
//        else {
//            wakeFlags = PowerManager.PARTIAL_WAKE_LOCK;
//        }
//        wakeLock = pm.newWakeLock(wakeFlags, TAG);
//        wakeLock.acquire();
//    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
