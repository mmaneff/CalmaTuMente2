package com.adrianjaime.calmatumente2.views.tipomeditacion;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
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
 * https://www.tutorialspoint.com/android/android_mediaplayer.htm
 */
public class TipoMeditacionActivity extends AppCompatActivity implements TipoMeditacionView, View.OnClickListener {

    @Bind(R.id.btnPlay)         ImageButton btnPlay;
    @Bind(R.id.btnStop)         ImageButton btnStop;
    @Bind(R.id.btnEscena)       ImageButton btnEscena;
    @Bind(R.id.vidInicio)       VideoView videoView;
    @Bind(R.id.meditacionFondo) RelativeLayout meditacionFondo;
    @Bind(R.id.layoutVideo)     LinearLayout layoutVideo;
    @Bind(R.id.tvProgress)      TextView tvProgress;
    @Bind(R.id.seekbar)         SeekBar seekbar;

    private MediaPlayer mediaPlayer;
    private int[] videos;
    private Uri path;
    private int meditacion;
    int currentVideo = 0;

    //Esto es nuevo para el manejo del MediaPlayer
    private long startTime = 0;
    private long finalTime = 0;
    public static int oneTimeOnly = 0;

    private Handler handler = new Handler();


    private  TipoMeditacionPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_meditacion);

        //Recupero el dato pasado por el intent
        Bundle datos = this.getIntent().getExtras();
        meditacion = datos.getInt("meditacion");

        presenter = new TipoMeditacionPresenterImpl(this, getApplicationContext());
        presenter.initView(meditacion);

    }

   @Override
    public void initControls() {
        ButterKnife.bind(this);
        seekbar.setClickable(false);
    }

    /*
    @Override
    public void showMeditacion() {
        Intent intent = new Intent(TipoMeditacionActivity.this, MeditacionActivity.class);
        startActivity(intent);
        finish();
    }
    */

    @Override
    public void initVideos() {
        videos = new int[]{ R.raw.med_lago, R.raw.med_playa, R.raw.med_rio, -1 };
    }

    @Override
    public void initFondoVideo(int meditacion) {
        switch (meditacion) {
            case 1:
                meditacionFondo.setBackgroundResource(R.drawable.img_2);
                break;
            case 2:
                meditacionFondo.setBackgroundResource(R.drawable.img_3);
                break;
            case 3:
                meditacionFondo.setBackgroundResource(R.drawable.img_4);
                break;
        }
    }

    @Override
    public void initMeditacion(int meditacion) {
        switch (meditacion) {
            case 1:
                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.meditacion_1);
                break;
            case 2:
                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.meditacion_2);
                break;
            case 3:
                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.meditacion_3);
                break;
        }
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        seekbar.setMax(mediaPlayer.getDuration()); //Revisar si va o no
    }

    @Override
    public void playMeditacion() {
        if(mediaPlayer.isPlaying()) {
            btnPlay.setImageResource(R.drawable.ic_med_play);
            mediaPlayer.pause();
        } else {
            btnPlay.setImageResource(R.drawable.ic_med_pause);
            mediaPlayer.start();

            finalTime = mediaPlayer.getDuration();
            startTime = mediaPlayer.getCurrentPosition();

            /*
            if (oneTimeOnly == 0) {
                seekbar.setMax((int) finalTime);
                oneTimeOnly = 1;
            }*/

            tvProgress.setText(Utilities.milliSecondsToTimer(startTime) + "/" + Utilities.milliSecondsToTimer(finalTime));

            seekbar.setProgress((int)startTime);
            handler.postDelayed(UpdateSongTime, 100);
        }
    }

    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            startTime = mediaPlayer.getCurrentPosition();

            tvProgress.setText(Utilities.milliSecondsToTimer(startTime) + "/" + Utilities.milliSecondsToTimer(finalTime));
            seekbar.setProgress((int)startTime);
            handler.postDelayed(this, 100);
        }
    };

    @Override
    public void limpiarMediaPlayer() {
        if(mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        handler.removeCallbacks(UpdateSongTime);
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
        nextVideo(currentVideo);
    }

    /*
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
    */

    private void nextVideo(int currentVideo) {
        switch (currentVideo) {
            case 0:
                btnEscena.setColorFilter(getResources().getColor(R.color.escena_1));
                break;
            case 1:
                btnEscena.setColorFilter(getResources().getColor(R.color.escena_2));
                break;
            case 2:
                btnEscena.setColorFilter(getResources().getColor(R.color.escena_3));
                break;
            default:
                btnEscena.setColorFilter(getResources().getColor(R.color.font_white));
                break;
        }

        this.currentVideo = (currentVideo == 3) ? 0 : currentVideo + 1;
    }

    /*
    @Override
    public void stopVideoView() {

        if(mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            btnStop.setColorFilter(getResources().getColor(R.color.colorAccent));
            btnPlay.setImageResource(R.drawable.ic_med_play);
        }

    }
    */

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnPlay:
                presenter.onStartAudio();
                break;
            case R.id.btnStop:
                //presenter.onStopVideoView();
                //presenter.onSelectedMeditacion();
                Intent intent = new Intent(TipoMeditacionActivity.this, MeditacionActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btnEscena:
                presenter.onStartVideoView();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //mediaPlayer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //mediaPlayer.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

}
