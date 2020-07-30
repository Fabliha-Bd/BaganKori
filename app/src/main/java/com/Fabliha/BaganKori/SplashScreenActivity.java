package com.Fabliha.BaganKori;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.VideoView;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        try {
            //VideoView videoHolder = new VideoView(this);
            VideoView videoHolder= (VideoView) findViewById(R.id.videoView);
           // setContentView(videoHolder);
            videoHolder.setZOrderOnTop(true);
            ViewGroup.MarginLayoutParams params= (ViewGroup.MarginLayoutParams) videoHolder.getLayoutParams();
           // params.setMargins(pixel_left, pixel_top, pixel_right, pixel_bottom);
            params.setMargins(30,400, 30, 100);
            videoHolder.setLayoutParams(params);
            Uri video = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.splashscreen);
            videoHolder.setVideoURI(video);
            videoHolder.start();

            videoHolder.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    jump();
                }
            });
            videoHolder.start();
        } catch (Exception ex) {
            jump();
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        jump();
        return true;
    }

    private void jump() {
        if (isFinishing())
            return;
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}