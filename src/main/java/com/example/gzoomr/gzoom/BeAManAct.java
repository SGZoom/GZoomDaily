package com.example.gzoomr.gzoom;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2016/3/5.
 */
public class BeAManAct extends Activity {
    GameBeAMan gameBeAMan;
    SensorManager sensorManager;
    /**
     * use to get asset
     */
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        gameBeAMan = new GameBeAMan(this);
        setContentView(gameBeAMan);


        /**Sensor*/
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor magnetticsensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);//方向传感器
        Sensor acclerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(listener, magnetticsensor, SensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(listener, acclerSensor, SensorManager.SENSOR_DELAY_GAME);



    }

    public void deepFile(Context ctxDealFile, String path) {
        try {
            String str[] = ctxDealFile.getAssets().list(path);
            if (str.length > 0) {//如果是目录
                File file = new File("/data/" + path);
                file.mkdirs();
                for (String string : str) {
                    path = path + "/" + string;
                    System.out.println("zhoulc:\t" + path);
                    deepFile(ctxDealFile, path);
                    path = path.substring(0, path.lastIndexOf('/'));
                }
            } else {//如果是文件
                InputStream is = ctxDealFile.getAssets().open(path);
                FileOutputStream fos = new FileOutputStream(new File("/data/" + path));
                byte[] buffer = new byte[1024];
                int count = 0;
                while (true) {
                    count++;
                    int len = is.read(buffer);
                    if (len == -1) {
                        break;
                    }
                    fos.write(buffer, 0, len);
                }
                is.close();
                fos.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (sensorManager != null) {
            sensorManager.unregisterListener(listener);
        }
    }

    private SensorEventListener listener = new SensorEventListener() {
        float[] acceValues = new float[3];
        float[] magnetValues = new float[3];

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                acceValues = sensorEvent.values.clone();
            } else if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                magnetValues = sensorEvent.values.clone();
            }
            float[] R = new float[9];
            float[] values = new float[3];
            SensorManager.getRotationMatrix(R, null, acceValues, magnetValues);
            SensorManager.getOrientation(R, values);
            if (values[2] < -0.1) {
                gameBeAMan.man_runleft();
                gameBeAMan.setManOri(-1);

            } else if (values[2] > 0.1) {
                gameBeAMan.man_runright();

                gameBeAMan.setManOri(1);
            }else{
                gameBeAMan.setManOri(0);
            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };
}
