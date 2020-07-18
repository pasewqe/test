package com.example.gin.elder2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
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

public class emotionresult extends AppCompatActivity implements Detector.ImageListener {
    private static final String LOG_TAG = "Affectiva";


    Button camera;
    CameraDetector detector;
    SurfaceView cameraPreview;
    TextView textView;
    Button learn;
    Button share;
    String flag="1";
    String titlestr;
    int valence;
    int beforevalence;
    TextView rlt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emorlt);

        Intent intent = this.getIntent();
        //取得傳遞過來的資料
        flag = intent.getStringExtra("flag");
        valence = Integer.valueOf(intent.getStringExtra("valence"));
        beforevalence = Integer.valueOf(intent.getStringExtra("beforevalence"));
        titlestr = intent.getStringExtra("title");

        share = (Button) findViewById(R.id.sharebt);
        learn = (Button) findViewById(R.id.learn);
        rlt=(TextView) findViewById(R.id.textView14);
        camera=(Button)findViewById(R.id.camera_button);
        textView=(TextView)findViewById(R.id.processor_fps_text);
        detector=new CameraDetector(this, CameraDetector.CameraType.CAMERA_FRONT, (SurfaceView)findViewById(R.id.camera_view2));
        detector.setImageListener(this);
        //detector.setDetectSmile(true);
        detector.setDetectAllEmotions(true);
        detector.setDetectAllAppearances(true);
        detector.setMaxProcessRate(20);
        detector.start();


        rlt.setText("情緒值越高代表越正向\n"+"情緒值越低代表越負向\n\n"+"學習前的情緒值: "+String.valueOf(beforevalence)+"\n"+"學習後的情緒值: "+String.valueOf(valence));
        if(valence>beforevalence){
            rlt.append("\n\n你在學習後，有較正面的情緒影響，可以進行其他主題學習，或學習補充教材!");
        } else if(valence==beforevalence){
            rlt.append("\n\n你在學習後，沒有明顯的情緒影響，建議可以學習補充教材，再進行其他主題學習!");
            //share.setEnabled(false);
        }else {
            rlt.append("\n\n你在學習後，有較負面的情緒影響，建議可以學習補充教材，再進行其他主題學習!");
            //share.setEnabled(false);
        }



        if(flag.equals("2")){
            learn.setText("補充學習");
            intent.putExtra("flag", "2");
        }
        learn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "請稍候", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                detector.stop();
                Intent intent = new Intent();
                intent.setClass(emotionresult.this, materials.class);
                intent.putExtra("flag", "１");
                intent.putExtra("title", titlestr);
                startActivity(intent);
                emotionresult.this.finish();
            }
        });


        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "請稍候", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                if(detector.isRunning()) {
                    detector.stop();
                }
                Intent intent = new Intent();
                intent.setClass(emotionresult.this, share.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onImageResults(List<Face> faces, Frame frame, float v) {
        TextView textView = (TextView) findViewById(R.id.processor_fps_text);
        TextView textView2 = (TextView) findViewById(R.id.camera_fps_text);
        if (faces.size()==0){

        } else{
            Face face = faces.get(0);
            textView.setText("年齡: "+face.appearance.getAge().toString());
            textView2.setText("性別: "+face.appearance.getGender().toString());
        }
    }
}
