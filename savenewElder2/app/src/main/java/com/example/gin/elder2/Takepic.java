package com.example.gin.elder2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Takepic extends Activity {

    private CameraPreview preview;
    private Camera camera;
    private ToneGenerator tone;
    private static final int OPTION_SNAPSHOT = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preview = new CameraPreview(this);
        setContentView(preview);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch(itemId){
            case OPTION_SNAPSHOT:
                //拍摄照片
                camera.takePicture(shutterCallback, null, jpegCallback);
                break;
        }
        return true;
    }
    //返回照片的JPEG格式的数据
    private PictureCallback jpegCallback = new PictureCallback(){

        public void onPictureTaken(byte[] data, Camera camera) {
            Parameters ps = camera.getParameters();
            if(ps.getPictureFormat() == PixelFormat.JPEG){
                //存储拍照获得的图片
                String path = save(data);
                //将图片交给Image程序处理
                Uri uri = Uri.fromFile(new File(path));
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                intent.setDataAndType(uri, "image/jpeg");
                startActivity(intent);
            }
        }
    };

    //快门按下的时候onShutter()被回调
    private ShutterCallback shutterCallback = new ShutterCallback(){
        public void onShutter() {
            if(tone == null)
                //发出提示用户的声音
                tone = new ToneGenerator(AudioManager.STREAM_MUSIC,
                        ToneGenerator.MAX_VOLUME);
            tone.startTone(ToneGenerator.TONE_PROP_BEEP2);
        }
    };

    private String save(byte[] data){
        String path = "/sdcard/"+System.currentTimeMillis()+".jpg";
        try {
            //判断是否装有SD卡
            if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                //判断SD卡上是否有足够的空间
                String storage = Environment.getExternalStorageDirectory().toString();
                StatFs fs = new StatFs(storage);
                long available = fs.getAvailableBlocks()*fs.getBlockSize();
                if(available<data.length){
                    //空间不足直接返回空
                    return null;
                }
                File file = new File(path);
                if(!file.exists())
                    //创建文件
                    file.createNewFile();
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(data);
                fos.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return path;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, OPTION_SNAPSHOT, 0, R.string.app_name);
        return super.onCreateOptionsMenu(menu);
    }

    class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
        SurfaceHolder mHolder;

        public CameraPreview(Context context) {
            super(context);
            mHolder = getHolder();
            mHolder.addCallback(this);
            mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        //Sureface创建的时候，此方法被调用
        public void surfaceCreated(SurfaceHolder holder) {
            //打开摄像头，获得Camera对象
            camera = Camera.open();
            try {
                //设置显示
                camera.setPreviewDisplay(holder);
            } catch (IOException exception) {
                camera.release();
                camera = null;
            }
        }

        //Surface销毁的时候，此方法被调用
        public void surfaceDestroyed(SurfaceHolder holder) {
            camera.stopPreview();
            //释放Camera
            camera.release();
            camera = null;
        }

        public void surfaceChanged(SurfaceHolder holder, int format, int w,
                                   int h) {
            //已经获得Surface的width和height，设置Camera的参数
            Camera.Parameters parameters = camera.getParameters();
            parameters.setPreviewSize(w, h);
            camera.setParameters(parameters);
            //开始预览
            camera.startPreview();
        }
    }
}
