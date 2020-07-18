package com.example.gin.elder2;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.MediaController;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.VideoView;

import com.affectiva.android.affdex.sdk.detector.CameraDetector;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by User on 2/28/2017.
 */

public class Tab3Fragment extends Fragment {
    private static final String TAG = "Tab3Fragment";

    //parameters
    private GridView gridView;
    private TextView title;
    private int[] image = new int[20];
    private String[] imgText = new String[20];
    private String[] video = new String[20];
    String url;
    String titlestr;
    String[] AfterSplit;
    int valence=0;
    int flagimgText=0;
    int flagvideo=0;
    int flagimage=0;
    private VideoView vidView;
    private MediaController vidControl;
    SurfaceView cameraPreview;
    CameraDetector detector;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab3_fragment,container,false);

        //http request
        StrictMode
                .setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                        .detectDiskReads()
                        .detectDiskWrites()
                        .detectNetwork()   // or .detectAll() for all detectable problems
                        .penaltyLog()
                        .build());
        StrictMode
                .setVmPolicy(new StrictMode.VmPolicy.Builder()
                        .detectLeakedSqlLiteObjects()
                        .detectLeakedClosableObjects()
                        .penaltyLog()
                        .penaltyDeath()
                        .build());

        url="http://140.123.92.130/api/sharing3.php";
        HttpPost method = new HttpPost(url);
        ArrayList<NameValuePair> variable = new ArrayList<NameValuePair>();


        //variable.add(new BasicNameValuePair("id",Id));
        //variable.add(new BasicNameValuePair("pw",Pw));

        try {
            method.setEntity(new UrlEncodedFormEntity(variable, HTTP.UTF_8));
            HttpResponse response = new DefaultHttpClient().execute(method);
            if(response.getStatusLine().getStatusCode()==200)
            {
                String rlt= EntityUtils.toString(response.getEntity());

                AfterSplit = rlt.split(" ");
                for(int i=0;i<AfterSplit.length;i++){
                    if(i%2==0){
                        imgText[flagimgText++]=AfterSplit[i];
                        image[flagimage++]=R.drawable.user;
                    } else{
                        video[flagvideo++]=AfterSplit[i];
                    }
                }
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //execution gridView
        List<Map<String, Object>> items = new ArrayList<>();
        for (int i = 0; i < image.length; i++) {
            Map<String, Object> item = new HashMap<>();
            item.put("image", image[i]);
            item.put("text", imgText[i]);
            items.add(item);
        }
        SimpleAdapter adapter = new SimpleAdapter(getContext(),
                items, R.layout.grid_item, new String[]{"image", "text"},
                new int[]{R.id.image, R.id.text});
        gridView = (GridView)view.findViewById(R.id.gridView4000);
        gridView.setNumColumns(1);
        gridView.setAdapter(adapter);


        flagimgText=0;
        flagvideo=0;
        flagimage=0;

        return view;
    }
}
