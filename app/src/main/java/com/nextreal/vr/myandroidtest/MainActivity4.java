package com.nextreal.vr.myandroidtest;

import android.content.res.AssetFileDescriptor;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Surface;
import android.view.TextureView;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class MainActivity4 extends AppCompatActivity {

    private IjkMediaPlayer mediaPlayer;
    //定义一个缓冲区句柄（由屏幕合成程序管理）
    private Surface surface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = new IjkMediaPlayer();

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
            System.out.println("mediaPlayer onSurfaceTextureAvailable = 1111");

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
                mediaPlayer= new IjkMediaPlayer();
                //设置播放资源(可以是应用的资源文件／url／sdcard路径)
                AssetFileDescriptor afd = MainActivity4.this.getAssets().openFd("1.mp4");// 注意这里的区别
                //构建IjkPlayer能识别的IMediaDataSource，下面的RawDataSourceProvider实现了IMediaDataSource接口
                RawDataSourceProvider sourceProvider = new RawDataSourceProvider(afd);               //设置渲染画板
                mediaPlayer.setDataSource(sourceProvider);
                mediaPlayer.setSurface(surface);
                //设置播放类型
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

                mediaPlayer.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(IMediaPlayer iMediaPlayer) {
                        System.out.println("mediaPlayer onPrepared = 1111");
                        mediaPlayer.start();
                    }
                });
                mediaPlayer.setOnNativeInvokeListener(new IjkMediaPlayer.OnNativeInvokeListener() {
                    @Override
                    public boolean onNativeInvoke(int i, Bundle bundle) {
                        System.out.println("mediaPlayer onNativeInvoke = 1111");

                        return false;
                    }
                });

                mediaPlayer.setOnBufferingUpdateListener(new IMediaPlayer.OnBufferingUpdateListener() {
                    @Override
                    public void onBufferingUpdate(IMediaPlayer iMediaPlayer, int i) {
                        System.out.println("mediaPlayer onBufferingUpdate = 1111");

                    }
                });

                mediaPlayer.setOnErrorListener(new IMediaPlayer.OnErrorListener() {
                    @Override
                    public boolean onError(IMediaPlayer iMediaPlayer, int i, int i1) {
                        System.out.println("mediaPlayer onError = 1111");

                        return false;
                    }
                });
                //设置是否保持屏幕常亮
                mediaPlayer.setScreenOnWhilePlaying(true);
                //同步的方式装载流媒体文件
                mediaPlayer.prepareAsync();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
