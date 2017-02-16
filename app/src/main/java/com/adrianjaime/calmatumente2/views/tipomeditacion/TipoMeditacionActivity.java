package com.adrianjaime.calmatumente2.views.tipomeditacion;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.adrianjaime.calmatumente2.R;
import com.adrianjaime.calmatumente2.views.alarma.AlarmaActivity;
import com.adrianjaime.calmatumente2.views.helper.Utilities;
import com.adrianjaime.calmatumente2.views.inicio.InicioActivity;
import com.adrianjaime.calmatumente2.views.meditacion.MeditacionActivity;


import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Matute on 26/12/2016.
 */
public class TipoMeditacionActivity extends AppCompatActivity implements TipoMeditacionView, View.OnClickListener {

    @Bind(R.id.btnPlay)         ImageButton btnPlay;
    @Bind(R.id.btnStop)         ImageButton btnStop;
    @Bind(R.id.btnEscena)       ImageButton btnEscena;
    @Bind(R.id.vidInicio)       VideoView videoView;
    @Bind(R.id.meditacionFondo) RelativeLayout meditacionFondo;
    @Bind(R.id.layoutVideo)     LinearLayout layoutVideo;
    //@Bind(R.id.tvProgress)      TextView tvProgress;
    //@Bind(R.id.pbrAvance)       ProgressBar pbrAvance;

    private MediaPlayer mediaPlayer;
    private int[] videos;
    private Uri path;
    private int meditacion;
    int currentVideo = 0;
    private Utilities utils;


    private  TipoMeditacionPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_meditacion);

        //Recupero el dato pasado por el intent
        Bundle datos = this.getIntent().getExtras();
        meditacion = datos.getInt("meditacion");

        presenter = new TipoMeditacionPresenterImpl(this, getApplicationContext());
        presenter.initView();
    }

   @Override
    public void initControls() {
        ButterKnife.bind(this);
        utils = new Utilities();
        mediaPlayer = new MediaPlayer();
    }

    @Override
    public void showMeditacion() {
        Intent intent = new Intent(TipoMeditacionActivity.this, MeditacionActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void initVideos() {
        videos = new int[]{
                R.raw.med_lago,
                R.raw.med_playa,
                R.raw.med_rio,
                -1
        };
    }

    @Override
    public void setFondoVideo() {
        if(meditacion == 1) {
            //setTitle(getResources().getText(R.string.meditacion_2));
            meditacionFondo.setBackgroundResource(R.drawable.img_2);
            mediaPlayer = MediaPlayer.create(this, R.raw.meditacion_1);
        } else if(meditacion == 2) {
            //setTitle(getResources().getText(R.string.meditacion_3));
            meditacionFondo.setBackgroundResource(R.drawable.img_3);
            mediaPlayer = MediaPlayer.create(this, R.raw.meditacion_2);
        } else {
            //setTitle(getResources().getText(R.string.meditacion_4));
            meditacionFondo.setBackgroundResource(R.drawable.img_4);
            mediaPlayer = MediaPlayer.create(this, R.raw.meditacion_3);
        }
        btnPlay.setImageResource(R.drawable.ic_med_pause);
        //mediaPlayer.pause();
    }

    @Override
    public void limpiarMediaPlayer() {
        if(mediaPlayer != null) {
            if(mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public void limpiarVideoView() {
        if(videoView != null) {
            if(videoView.isPlaying()) {
                videoView.pause();
            }
            videoView = null;
        }
    }

    @Override
    public void startAudio() {
        if(mediaPlayer.isPlaying()) {
            btnPlay.setImageResource(R.drawable.ic_med_play);
            mediaPlayer.pause();
        } else {
            btnStop.setColorFilter(getResources().getColor(R.color.font_white));
            btnPlay.setImageResource(R.drawable.ic_med_pause);
            mediaPlayer.start();
        }
    }

    @Override
    public void startVideoView() {

        if(videos[currentVideo] == -1) {
            if(videoView.isPlaying()) {
                videoView.pause();
            }
            videoView = null;
            layoutVideo.setVisibility(View.GONE);
        } else {

            if(videoView == null) {
                videoView = (VideoView) findViewById(R.id.vidInicio);
            }
            if(videoView.isPlaying()) {
                videoView.pause();
            }

            path = Uri.parse("android.resource://com.adrianjaime.calmatumente2/" + videos[currentVideo]);
            videoView.setVideoURI(path);

            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.setLooping(true);
                }
            });

            videoView.start();

            if(layoutVideo.getVisibility() == View.GONE) {
                layoutVideo.setVisibility(View.VISIBLE);
                try {
                    Thread.sleep(1000);
                }catch (InterruptedException e) {
                    Log.e("startVideoView", e.toString());
                }
            }
        }
        nextVideo();

    }

    private void nextVideo() {
        if(currentVideo == 0) {
            currentVideo = 1;
            btnEscena.setColorFilter(getResources().getColor(R.color.escena_1));
        } else if(currentVideo == 1) {
            currentVideo = 2;
            btnEscena.setColorFilter(getResources().getColor(R.color.escena_2));
        } else if(currentVideo == 2) {
            currentVideo = 3;
            btnEscena.setColorFilter(getResources().getColor(R.color.escena_3));
        } else if(currentVideo == 3) {
            currentVideo = 0;
            btnEscena.setColorFilter(getResources().getColor(R.color.font_white));
        }
    }

    @Override
    public void stopVideoView() {
        if(mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            btnStop.setColorFilter(getResources().getColor(R.color.colorAccent));
            btnPlay.setImageResource(R.drawable.ic_med_play);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnPlay:
                presenter.onStartAudio();
                break;
            case R.id.btnStop:
                presenter.onStopVideoView();
                presenter.onSelectedMeditacion();
                break;
            case R.id.btnEscena:
                presenter.onStartVideoView();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        mediaPlayer.start();
        super.onResume();
    }

    @Override
    protected void onPause() {
        //mediaPlayer.pause();
        super.onPause();
    }
}
