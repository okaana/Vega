package bg.awesome.sc.accelorometer_test1;

import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
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
    int dataSize = 0;
    String filename = "xyz.csv";
    InputStream inputStream;
    ArrayList<Float> acc_x_vals = new ArrayList<Float>();
    ArrayList<Float> acc_y_vals = new ArrayList<Float>();
    ArrayList<Float> acc_z_vals = new ArrayList<Float>();
    private LineGraphSeries<DataPoint> x_series,y_series,z_series;

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
                    dataSize = dataSize + 1;
                    //series.appendData(new DataPoint(graph_x,dataSize),true,500);
                    //Toast.makeText(getApplicationContext(),data[0] + " " + data[1] + " " + data[2],Toast.LENGTH_SHORT).show();
                }
                //citation:Android Beginner Tutorial #17 - Android Beginner Graphing(https://www.youtube.com/watch?v=VriiDn676PQ)
                float x=0,y,z;
                int datapoints = dataSize;
                GraphView graph = (GraphView) findViewById(R.id.graph);
                x_series = new LineGraphSeries<>();
                y_series = new LineGraphSeries<>();
                z_series = new LineGraphSeries<>();

                 for(int i =0;i<datapoints;i++)
              {
                  x = acc_x_vals.get(i);
                  y = acc_y_vals.get(i);
                  z = acc_z_vals.get(i);

                  x_series.appendData(new DataPoint(i,x),true,datapoints);
                  y_series.appendData(new DataPoint(i,y),true,datapoints);
                  z_series.appendData(new DataPoint(i,z),true,datapoints);
              }

                Paint x_paint = new Paint();
                Paint y_paint = new Paint();
                Paint z_paint = new Paint();
                x_paint.setStyle(Paint.Style.STROKE);
                x_paint.setStrokeWidth(10);
                x_paint.setColor(Color.RED);
                y_paint.setStyle(Paint.Style.STROKE);
                y_paint.setStrokeWidth(10);
                y_paint.setColor(Color.BLUE);
                z_paint.setStyle(Paint.Style.STROKE);
                z_paint.setStrokeWidth(10);
                z_paint.setColor(Color.GREEN);
                x_series.setCustomPaint(x_paint);
                y_series.setCustomPaint(y_paint);
                z_series.setCustomPaint(z_paint);

                //citation : 017: Legend in Graph : Android Graph View tutorial(https://www.youtube.com/watch?v=4NYljUle2u4)

                graph.getLegendRenderer().setVisible(true);
                graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);


                x_series.setTitle("X");
                x_series.setColor(Color.RED);
                y_series.setTitle("Y");
                y_series.setColor(Color.BLUE);
                z_series.setTitle("Z");
                z_series.setColor(Color.GREEN);


                graph.addSeries(x_series);
                 graph.addSeries(y_series);
                 graph.addSeries(z_series);
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
