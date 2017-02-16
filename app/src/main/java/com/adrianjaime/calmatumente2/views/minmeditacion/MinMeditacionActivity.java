package com.adrianjaime.calmatumente2.views.minmeditacion;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.adrianjaime.calmatumente2.views.control.TwoLevelCircularProgressBar;
import com.adrianjaime.calmatumente2.R;
import com.adrianjaime.calmatumente2.views.alarma.AlarmaActivity;
import com.adrianjaime.calmatumente2.views.inicio.InicioActivity;
import com.adrianjaime.calmatumente2.views.main.MainActivity;
import com.adrianjaime.calmatumente2.views.meditacion.MeditacionActivity;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;


import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Matute on 26/12/2016.
 *
 */
public class MinMeditacionActivity extends AppCompatActivity implements MinMeditacionView, View.OnClickListener {

    @Bind(R.id.layoutVideo) LinearLayout layoutVideo;
    @Bind(R.id.btnPlay)     ImageButton btnPlay;
    @Bind(R.id.btnStop)     ImageButton btnStop;
    @Bind(R.id.btnEscena)   ImageButton btnEscena;
    @Bind(R.id.tvEstado)    TextView tvEstado;
    @Bind(R.id.vidInicio)   VideoView videoView;
    //@Bind(R.id.tvCuenta)    TextView tvCuenta;
    @Bind(R.id.progressAnimation)   ImageView progressAnimation;


    TwoLevelCircularProgressBar progressBar_1;

    private int[] videos;
    private Uri path;
    int currentVideo = 0;

    private MinMeditacionPresenter presenter;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_min_meditacion);

        presenter = new MinMeditacionPresenterImpl(this, getApplicationContext());
        presenter.initView();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void initControls() {
        ButterKnife.bind(this);
        setTitle("");
        progressBar_1 = (TwoLevelCircularProgressBar) findViewById(R.id.progressBar_1);
    }

    @Override
    public void initTypeFace() {
        Typeface customFont = Typeface.createFromAsset(getAssets(), "fonts/Ubuntu-Regular.ttf");
        tvEstado.setTypeface(customFont);
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
        if(videoView.isPlaying()) {
            videoView.pause();
            btnStop.setColorFilter(getResources().getColor(R.color.colorAccent));
            btnPlay.setImageResource(R.drawable.ic_med_play);
        }
    }

    @Override
    public void showInicio() {
        Intent intent = new Intent(MinMeditacionActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void showEstado(String estado) {
        tvEstado.setText(estado);
    }

    /*
    @Override
    public void showCuenta(String cuenta) {
        tvCuenta.setText(cuenta);
    }
    */

    @Override
    public void showProgressBarExterno1(float progress) {
        progressBar_1.setProgressValue(progress);
    }

    @Override
    public void showProgressBarExterno2(float progress) {
        progressBar_1.setProgressValue2(progress);
    }

    @Override
    public void showProgressBarExterno3(float progress) {
        progressBar_1.setProgressValue3(progress);
    }

    @Override
    public void showPauseIcon() {
        btnPlay.setImageResource(R.drawable.ic_med_pause);
    }

    @Override
    public void showPlayIcon() {
        btnPlay.setImageResource(R.drawable.ic_med_play);
    }

    @Override
    public void showStopIcon() {
        btnStop.setColorFilter(getResources().getColor(R.color.font_white));
    }

    @Override
    public void showStop() {
        btnStop.setColorFilter(getResources().getColor(R.color.colorAccent));
    }

    @Override
    public void startAnimacionAmpliar() {
        Animation ampliar;
        ampliar = AnimationUtils.loadAnimation(this, R.anim.ampliar);
        //ampliar.reset();
        progressAnimation.startAnimation(ampliar);
    }

    @Override
    public void startAnimacionContraer() {
        Animation contraer;
        contraer = AnimationUtils.loadAnimation(this, R.anim.contraer);
        //ampliar.reset();
        progressAnimation.startAnimation(contraer);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnPlay: {
                presenter.startMeditacion();
                break;
            }
            case R.id.btnStop: {
                presenter.stopMeditacion();
                presenter.onSelectedInicio();
                break;
            }
            case R.id.btnEscena: {
                presenter.onStartVideoView();
                break;
            }
        }
    }

    int i = 0;
    float x = 0;


    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("MinMeditacion Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
