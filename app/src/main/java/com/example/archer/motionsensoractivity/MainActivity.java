package com.example.archer.motionsensoractivity;
/**
 * 动作传感器的使用
 *
 * 设备从左到右推动，X轴为正。
 *
 * 设备朝着自己推动 Y为正。
 *
 * 设备朝着天空推动，Z轴为正。
 */

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener{


     private SensorManager mSensorManager;
    private TextView tvAccelerometer;
    private float[] gravity=new float[3];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       tvAccelerometer = (TextView) findViewById(R.id.MotionTv);

         mSensorManager= (SensorManager) getSystemService(SENSOR_SERVICE);
        //注册传感器


    }

    @Override
    protected void onResume() {
        super.onResume();

        //注册传感器
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_UI);
        mSensorManager.registerListener(this,mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY),SensorManager.SENSOR_DELAY_FASTEST);
    }

    //取消传感器的注册
    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);

    }

    //传感器数据变化
    @Override
    public void onSensorChanged(SensorEvent event) {
        switch (event.sensor.getType()){
            case Sensor.TYPE_ACCELEROMETER://加速度传感器

                //去杂音
                final  float alpha= (float) 0.8;

                gravity[0]= alpha*gravity[0]+(1-alpha)*event.values[0];
                gravity[1]= alpha*gravity[1]+(1-alpha)*event.values[1];
                gravity[2]= alpha*gravity[2]+(1-alpha)*event.values[2];

                String accelerometer="加速度\n"+"X"+ (event.values[0]-gravity[0])+"\n"+"Y"+ (event.values[1]-gravity[1])+"\n"+"Z"+ (event.values[2]-gravity[2])+"\n";
               tvAccelerometer.setText(accelerometer);

                Log.d("Z", String.valueOf(event.values[2]-gravity[2]));

                //9.81m/s^2
                break;

            case Sensor.TYPE_GRAVITY:

                gravity[0]=event.values[0];
                gravity[1]=event.values[1];
                gravity[2]=event.values[2];

                break;

            default:
                break;



        }

    }

    //精度变化调用这个回调函数
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }



}

