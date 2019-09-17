package com.nextreal.vr.myandroidtest.presentation.presentation;

import android.app.Presentation;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.widget.TextView;
import android.widget.VideoView;

import com.nextreal.vr.myandroidtest.R;

/**
 * @Author: Guhuangjin
 * @DATE: 2019/9/17  13:34
 * @Description:
 * @Email: huangjin.gu@nextreal.tech
 */
public class SecondScreenPresentation extends Presentation {

    TextView secondTextView;
    VideoView secondVideoView;

    public SecondScreenPresentation(Context outerContext, Display display) {
        super(outerContext, display);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_presentation);
        secondTextView = findViewById(R.id.secondTextView);
        secondVideoView = findViewById(R.id.secondVideoView);
    }

    public void setSecondTextViewText(String text) {
        secondTextView.setText(text);
    }

    public void startSecondVideo() {

        secondVideoView.setVideoURI(Uri.parse("android.resource://com.nextreal.vr.myandroidtest/" + R.raw.video_good));
        /**视频准备完成时回调
         * */
        secondVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                Log.i("tag", "--------------视频准备完毕,可以进行播放.......");
            }
        });
        /**
         * 视频播放完成时回调
         */
        secondVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Log.i("tag", "------------------视频播放完毕..........");
                /**播放完成时，再次循环播放*/
                secondVideoView.start();
            }
        });

        /**
        * 视频播放发送错误时回调
        */
        secondVideoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Log.i("tag", "---------------------视频播放失败...........");
                return false;
            }
        });

        /**开始播放视频
         * */
        secondVideoView.start();

    }
}
