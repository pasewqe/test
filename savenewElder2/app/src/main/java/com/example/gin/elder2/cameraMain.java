package com.example.gin.elder2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.affectiva.android.affdex.sdk.Frame;
import com.affectiva.android.affdex.sdk.detector.CameraDetector;
import com.affectiva.android.affdex.sdk.detector.Detector;
import com.affectiva.android.affdex.sdk.detector.Face;

import java.util.List;

/**
 * Created by GIN on 2018/3/20.
 */

public class cameraMain  extends AppCompatActivity implements Detector.ImageListener {
    private static final String LOG_TAG = "Affectiva";


    Button camera;
    Button start_learn;
    CameraDetector detector;
    SurfaceView cameraPreview;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_main);

        camera=(Button)findViewById(R.id.camera_button);
        start_learn=(Button)findViewById(R.id.start_learn);
        textView=(TextView)findViewById(R.id.processor_fps_text);
        detector=new CameraDetector(this, CameraDetector.CameraType.CAMERA_FRONT, (SurfaceView)findViewById(R.id.camera_view));
        detector.setImageListener(this);
        //detector.setDetectSmile(true);
        detector.setDetectAllEmotions(true);
        //detector.setDetectAllAppearances(true);
        detector.setMaxProcessRate(20);

        camera.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                detector.start();
                camera.setEnabled(false);
            }
        });
        start_learn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(detector.isRunning())
                    detector.stop();
                Intent intent = new Intent();
                intent.setClass(cameraMain.this, scenario.class);
                startActivity(intent);
                cameraMain.this.finish();
            }
        });

    }

    @Override
    public void onImageResults(List<Face> faces, Frame frame, float v) {
        TextView textView = (TextView) findViewById(R.id.processor_fps_text);
        TextView textView2 = (TextView) findViewById(R.id.camera_fps_text);
        TextView textView3 = (TextView) findViewById(R.id.disgustv);
        TextView textView4 = (TextView) findViewById(R.id.fearv);
        TextView textView5 = (TextView) findViewById(R.id.joyv);
        TextView textView6 = (TextView) findViewById(R.id.sadv);
        TextView textView7 = (TextView) findViewById(R.id.surprisev);
        TextView textView8 = (TextView) findViewById(R.id.statusv);
        if (faces.size()==0){
            textView8.setText("未偵測");
        } else{
            textView8.setText("偵測中");
            Face face = faces.get(0);
            textView.setText(String.format("%.2f",face.emotions.getAnger()));
            textView2.setText(String.format("%.2f",face.emotions.getContempt()));
            textView3.setText(String.format("%.2f",face.emotions.getDisgust()));
            textView4.setText(String.format("%.2f",face.emotions.getFear()));
            textView5.setText(String.format("%.2f",face.emotions.getJoy()));
            textView6.setText(String.format("%.2f",face.emotions.getSadness()));
            textView7.setText(String.format("%.2f",face.emotions.getSurprise()));
            //textView2.setText(String.format("%.2f",face.emotions.getValence()));
            //textView2.setText(face.appearance.getGender().toString());
        }
    }

    @Override
    public void onBackPressed() {
        if(detector.isRunning()) {
            detector.stop();
        }
        super.onBackPressed();
    }
}
