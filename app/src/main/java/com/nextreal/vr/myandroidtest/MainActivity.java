package com.nextreal.vr.myandroidtest;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.View;

import org.videolan.libvlc.IVLCVout;
import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.Media;
import org.videolan.libvlc.MediaPlayer;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mMediaPlayer;
    //定义一个缓冲区句柄（由屏幕合成程序管理）
    private Surface surface;
    private LibVLC mLibVLC = null;
    private IVLCVout mIVLCVout;

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

        SurfaceView surfaceView = findViewById(R.id.surfaceView);
        mLibVLC = new LibVLC(this);

        mMediaPlayer = new MediaPlayer(mLibVLC);
        mIVLCVout = mMediaPlayer.getVLCVout();
        if (surfaceView != null) {
            mIVLCVout.setVideoView(surfaceView);
        }

        mIVLCVout.attachViews();


        Media media;
        try {
            media = new Media(mLibVLC, getAssets().openFd("1.mp4"));
            mMediaPlayer.setMedia(media);
            media.release();
            mMediaPlayer.play();

            media.setEventListener(new Media.EventListener() {
                @Override
                public void onEvent(Media.Event event) {
                    System.out.println("mMediaPlayer media event type = " + event.type);

                }
            });

            mMediaPlayer.setEventListener(new MediaPlayer.EventListener() {
                @Override
                public void onEvent(MediaPlayer.Event event) {
                    System.out.println("mMediaPlayer event type = " + event.type);

                    if (MediaPlayer.Event.EncounteredError == event.type) {
                        System.out.println("mMediaPlayer EncounteredError = ");

                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }


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

}
