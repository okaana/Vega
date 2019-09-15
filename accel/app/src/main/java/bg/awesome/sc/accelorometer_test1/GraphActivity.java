package bg.awesome.sc.accelorometer_test1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class GraphActivity extends AppCompatActivity {
    File file;
    String filename = "xyz.csv";
    InputStream inputStream;
    float test = 0;
    ArrayList<Float> acc_x_vals = new ArrayList<Float>();
    ArrayList<Float> acc_y_vals = new ArrayList<Float>();
    ArrayList<Float> acc_z_vals = new ArrayList<Float>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //citation : How to Parse CSV or Microsoft Excel file in Android Studio with example(https://www.youtube.com/watch?v=PmUI8NZc7JI),https://www.dev2qa.com/android-read-write-internal-storage-file-example/

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        String str,data[];

        String dir = getApplicationContext().getExternalFilesDir("").toString();
        filename = dir +"/"+filename;
        file = new File(filename);
        try {
            inputStream = new FileInputStream(filename);
        } catch (FileNotFoundException e) {
            Toast.makeText(getApplicationContext(),"File not found",Toast.LENGTH_SHORT).show();
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        if (inputStream!=null) {
            try{
                while ((str = reader.readLine()) != null) {
                    data = str.split(",");

                    acc_x_vals.add(Float.parseFloat(data[0]));
                    acc_y_vals.add(Float.parseFloat(data[1]));
                    acc_z_vals.add(Float.parseFloat(data[2]));
                    //Toast.makeText(getApplicationContext(),data[0] + " " + data[1] + " " + data[2],Toast.LENGTH_SHORT).show();
                }
            }catch (Exception e){
                Toast.makeText(getApplicationContext(),"not working",Toast.LENGTH_SHORT).show();
            }
        }
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
