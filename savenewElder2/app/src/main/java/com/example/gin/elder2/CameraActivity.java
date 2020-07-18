package com.example.gin.elder2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.affectiva.android.affdex.sdk.Frame;
import com.affectiva.android.affdex.sdk.detector.Detector;
import com.affectiva.android.affdex.sdk.detector.Face;
import com.affectiva.android.affdex.sdk.detector.PhotoDetector;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CameraActivity extends Activity implements SurfaceHolder.Callback,
        Camera.AutoFocusCallback, Detector.FaceListener, Detector.ImageListener {
    private static final int CAMERA_REQUEST=1888;
    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;
    private Button mTaskPicture;
    private Camera camera;
    PhotoDetector detector = new PhotoDetector(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 全螢幕
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // 無標題
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 直式
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_camera);


        detector.setFaceListener(this);
        detector.setDetectAllExpressions(true);
        detector.setDetectAllEmotions(true);
        detector.setDetectAllEmojis(true);
        detector.setDetectAllAppearances(true);

        detector.start();



        initViews();



    }

    private void initViews() {
        mSurfaceView = (SurfaceView) this.findViewById(R.id.svPreview);
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(this);
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB)
            mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        mTaskPicture = (Button) this.findViewById(R.id.taskPicture);
        mTaskPicture.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    camera.autoFocus(CameraActivity.this);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onAutoFocus(boolean success, Camera camera) {
        camera.takePicture(null, null, jpeg);

    }

    private String getPictureName(){
        SimpleDateFormat sdf= new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timestamp=sdf.format(new Date());
        return "/sdcard/demo/"+timestamp+".jpg";

    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        camera = Camera.open();

        if (Build.VERSION.SDK_INT >= 8)
            camera.setDisplayOrientation(90);

        try {
            camera.setPreviewDisplay(surfaceHolder);
        } catch (IOException exception) {
            camera.release();
            camera = null;
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {
        // 取得相機參數
        Camera.Parameters parameters = camera.getParameters();
        // 取得照片尺寸
        List supportedPictureSizes = parameters.getSupportedPictureSizes();
//        int sptw = supportedPictureSizes.get(supportedPictureSizes.size() - 1).width;
//        int spth = supportedPictureSizes.get(supportedPictureSizes.size() - 1).height;

        // 取得預覽尺寸
        List supportedPreviewSizes = parameters.getSupportedPreviewSizes();
//        int prvw = supportedPreviewSizes.get(0).width;
//        int prvh = supportedPreviewSizes.get(0).height;

        parameters.setPictureFormat(ImageFormat.JPEG);
        parameters.setPreviewSize(640, 480);

        camera.setParameters(parameters);
        camera.startPreview();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        camera.stopPreview();
        camera.release();
        camera = null;
    }

    Camera.PictureCallback jpeg = new Camera.PictureCallback() {

        public void onPictureTaken(byte[] imgData, Camera camera) {
            if (imgData != null) {
                Bitmap picture = BitmapFactory.decodeByteArray(imgData, 0, imgData.length);
                picture = rotationBitmap(picture);
                saveBitmap(picture);

                Frame.ByteArrayFrame frame = new Frame.ByteArrayFrame(imgData, 640, 480,
                        Frame.COLOR_FORMAT.YUV_NV21);
                detector.process(frame);
            }

            camera.startPreview();
        }
    };

    public void saveBitmap(Bitmap bitmap) {

        FileOutputStream fOut;
        try {
            File dir = new File("/sdcard/demo/");
            if (!dir.exists()) {
                dir.mkdir();
            }

            String tmp = getPictureName();
            fOut = new FileOutputStream(tmp);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);

            try {
                fOut.flush();
                fOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            sendToGallery(this, tmp);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Bitmap rotationBitmap(Bitmap picture) {
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(picture,picture.getWidth(),picture.getHeight(),true);
        Bitmap rotatedBitmap = Bitmap.createBitmap(scaledBitmap , 0, 0, scaledBitmap .getWidth(), scaledBitmap .getHeight(), matrix, true);
        return rotatedBitmap;
    }

    public void sendToGallery(Context ctx, String path) {
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(new File(path));
        intent.setData(contentUri);
        ctx.sendBroadcast(intent);
    }

    public void onImageResults(List<Face> faces, Frame image, float timestamp) {

        if (faces == null)
            return; //frame was not processed

        if (faces.size() == 0)
            return; //no face found

        //For each face found
        for (int i = 0 ; i < faces.size() ; i++) {
            Face face = faces.get(i);

            int faceId = face.getId();

            //Appearance
            Face.GENDER genderValue = face.appearance.getGender();
            Face.GLASSES glassesValue = face.appearance.getGlasses();
            Face.AGE ageValue = face.appearance.getAge();
            Face.ETHNICITY ethnicityValue = face.appearance.getEthnicity();


            //Some Emoji
            float smiley = face.emojis.getSmiley();
            float laughing = face.emojis.getLaughing();
            float wink = face.emojis.getWink();


            //Some Emotions
            float joy = face.emotions.getJoy();
            float anger = face.emotions.getAnger();
            float disgust = face.emotions.getDisgust();

            //Some Expressions
            float smile = face.expressions.getSmile();
            float brow_furrow = face.expressions.getBrowFurrow();
            float brow_raise = face.expressions.getBrowRaise();

            //Measurements
            float interocular_distance = face.measurements.getInterocularDistance();
            float yaw = face.measurements.orientation.getYaw();
            float roll = face.measurements.orientation.getRoll();
            float pitch = face.measurements.orientation.getPitch();

            //Face feature points coordinates
            PointF[] points = face.getFacePoints();

        }
    }

    @Override
    public void onFaceDetectionStarted() {

    }

    @Override
    public void onFaceDetectionStopped() {

    }
}






