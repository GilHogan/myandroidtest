package com.nextreal.vr.myandroidtest;

import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaTimestamp;
import android.media.TimedMetaData;
import android.media.TimedText;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;

public class MainActivity2 extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    //定义一个缓冲区句柄（由屏幕合成程序管理）
    private Surface surface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mediaPlayer = new MediaPlayer();

        TextureView textureView = findViewById(R.id.textureView);
        textureView.setSurfaceTextureListener(surfaceTextureListener);


    }

    @Override
    protected void onStop() {
        super.onStop();
//        mediaPlayer.stop();
//        mediaPlayer.release();
//        mediaPlayer = null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 定义TextureView监听类SurfaceTextureListener
     * 重写4个方法
     */
    private TextureView.SurfaceTextureListener surfaceTextureListener = new TextureView.SurfaceTextureListener() {

        /**
         * 初始化好SurfaceTexture后调用
         * @param surfaceTexture
         * @param i
         * @param i1
         */
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
            surface = new Surface(surfaceTexture);
            //开启一个线程去播放视频
            new PlayerVideoThread().start();

        }

        /**
         * 视频尺寸改变后调用
         * @param surfaceTexture
         * @param i
         * @param i1
         */
        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {

        }

        /**
         * SurfaceTexture即将被销毁时调用
         * @param surfaceTexture
         * @return
         */
        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
            surface = null;
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
            return true;
        }

        /**
         * 通过SurfaceTexture.updateteximage()更新指定的SurfaceTexture时调用
         * @param surfaceTexture
         */
        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

        }
    };

    /**
     * 定义一个线程，用于播发视频
     */
    private class PlayerVideoThread extends Thread{
        @Override
        public void run(){
            try {
                mediaPlayer= new MediaPlayer();
                //设置播放资源(可以是应用的资源文件／url／sdcard路径)
                mediaPlayer.setDataSource(getAssets().openFd("1.mp4"));
                //设置渲染画板
                mediaPlayer.setSurface(surface);
                //设置播放类型
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

                //播放完成监听
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        System.out.println("mediaPlayer onCompletion = 1111");

                    }
                });
                //预加载监听
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        System.out.println("mediaPlayer onPrepared = 1111");
                        mediaPlayer.start();
                    }
                });
                mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                    @Override
                    public boolean onError(MediaPlayer mp, int what, int extra) {
                        System.out.println("mediaPlayer onError = 1111");
                        return false;
                    }
                });

                mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                    @Override
                    public void onBufferingUpdate(MediaPlayer mp, int percent) {
                        System.out.println("mediaPlayer onBufferingUpdate = 1111");

                    }
                });

                mediaPlayer.setOnMediaTimeDiscontinuityListener(new MediaPlayer.OnMediaTimeDiscontinuityListener() {
                    @Override
                    public void onMediaTimeDiscontinuity( MediaPlayer mp,  MediaTimestamp mts) {
                        System.out.println("mediaPlayer onMediaTimeDiscontinuity = 1111");

                    }
                });

                mediaPlayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
                    @Override
                    public void onSeekComplete(MediaPlayer mp) {
                        System.out.println("mediaPlayer onSeekComplete = 1111");

                    }
                });

                mediaPlayer.setOnTimedMetaDataAvailableListener(new MediaPlayer.OnTimedMetaDataAvailableListener() {
                    @Override
                    public void onTimedMetaDataAvailable(MediaPlayer mp, TimedMetaData data) {
                        System.out.println("mediaPlayer onTimedMetaDataAvailable = 1111");

                    }
                });

                mediaPlayer.setOnTimedTextListener(new MediaPlayer.OnTimedTextListener() {
                    @Override
                    public void onTimedText(MediaPlayer mp, TimedText text) {
                        System.out.println("mediaPlayer onTimedText = 1111");

                    }
                });
                //设置是否保持屏幕常亮
                mediaPlayer.setScreenOnWhilePlaying(true);
                //同步的方式装载流媒体文件
                mediaPlayer.prepare();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
