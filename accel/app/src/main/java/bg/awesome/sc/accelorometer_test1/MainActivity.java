package bg.awesome.sc.accelorometer_test1;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    String text;
    TextView xValue,yValue,zValue;
    Button btn_start,btn_stop,btn_plot;
    int flag=0;
    File file;
    FileWriter csvWriter = null;
    String filename = "xyz.csv";
    private static final String TAG = "MainActivity";
    private  SensorManager sensorManager;
    Sensor accelerometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        xValue = findViewById(R.id.xValue);
        yValue = findViewById(R.id.yValue);
        zValue = findViewById(R.id.zValue);
        btn_start = findViewById(R.id.start_btn);
        btn_stop = findViewById(R.id.stop_btn);
        btn_plot = findViewById(R.id.plot_btn);
        btn_plot.setEnabled(false);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(MainActivity.this,accelerometer,SensorManager.SENSOR_DELAY_NORMAL);

        String dir = getApplicationContext().getExternalFilesDir("").toString();
        filename = dir +"/"+filename;
        file = new File(filename);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(file.exists()){
            //Toast.makeText(getApplicationContext(),"File Exist",Toast.LENGTH_SHORT).show();
        }else {
            //Toast.makeText(getApplicationContext(),"File doesnt Exist. Making new file",Toast.LENGTH_SHORT).show();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            csvWriter = new FileWriter(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=1;
                btn_plot.setEnabled(true);
            }
        });
        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=0;
            }
        });
        btn_plot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(getApplicationContext(),GraphActivity.class);
                try {
                    csvWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                startActivity(next);
                finish();
            }
        });
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(flag==1){
            xValue.setText("xValue : " + event.values[0]);
            yValue.setText("yValue : " + event.values[1]);
            zValue.setText("zValue : " + event.values[2]);

            text = "";
            text = Float.toString(event.values[0]);
            text = text + "," + Float.toString(event.values[1]);
            text = text + "," + Float.toString(event.values[2]) + "\n";
            try {
                csvWriter.append(text);
                csvWriter.flush();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(),"File not found",Toast.LENGTH_SHORT).show();
            }
        }
    }
}