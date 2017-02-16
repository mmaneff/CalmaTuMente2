package com.adrianjaime.calmatumente2.views.inicio;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.VideoView;

import com.adrianjaime.calmatumente2.R;
import com.adrianjaime.calmatumente2.views.main.MainActivity;
import com.adrianjaime.calmatumente2.views.meditacion.MeditacionActivity;
import com.adrianjaime.calmatumente2.views.alarma.AlarmaActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Matute on 26/12/2016.
 */
public class InicioActivity extends AppCompatActivity implements InicioView, View.OnClickListener {

    @Bind(R.id.btnMain)         ImageButton btnMain;
    @Bind(R.id.btnMeditacion)   ImageButton btnMeditacion;
    @Bind(R.id.btnConfiguracion)ImageButton btnConfiguracion;
    @Bind(R.id.vvInicio)        VideoView vvInicio;
    @Bind(R.id.btnWebLink)      Button btnWebLink;

    private MediaController mediaController;
    private int position = 0;

    private InicioPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        presenter = new InicioPresenterImpl(this);
        presenter.initView();
    }

    @Override
    public void initControls() {
        ButterKnife.bind(this);
        setTitle("Calma Tu Mente");
    }

    @Override
    public void initPresentacion() {
        //AMPLIACIÓN DE LOS CONTROLES
        // Set the media controller buttons
        if (mediaController == null) {
            mediaController = new MediaController(InicioActivity.this);
            // Set the videoView that acts as the anchor for the MediaController.
            mediaController.setAnchorView(vvInicio);
            // Set MediaController for VideoView
            vvInicio.setMediaController(mediaController);
        }
        try {
            // ID of video file.
            int id = getRawResIdByName("presentacion");
            vvInicio.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + id));
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        vvInicio.requestFocus();
    }

    @Override
    public void preparedVideo() {
        // When the video file ready for playback.
        vvInicio.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            public void onPrepared(MediaPlayer mediaPlayer) {
                vvInicio.seekTo(position);
                if (position == 0) {
                    vvInicio.start();
                }
                // When video Screen change size.
                mediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                        // Re-Set the videoView that acts as the anchor for the MediaController
                        mediaController.setAnchorView(vvInicio);
                    }
                });
            }
        });
    }


    /**
     * Find ID corresponding to the name of the resource (in the directory raw).
     * @param resName
     * @return
     */
    public int getRawResIdByName(String resName) {
        String pkgName = this.getPackageName();
        // Return 0 if not found.
        int resID = this.getResources().getIdentifier(resName, "raw", pkgName);
        Log.i("AndroidVideoView", "Res Name: " + resName + "==> Res ID = " + resID);
        return resID;
    }

    // When you change direction of phone, this method will be called.
    // It store the state of video (Current position)
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        // Store current position.
        savedInstanceState.putInt("CurrentPosition", vvInicio.getCurrentPosition());
        vvInicio.pause();
    }

    // After rotating the phone. This method is called.
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Get saved position.
        position = savedInstanceState.getInt("CurrentPosition");
        vvInicio.seekTo(position);
    }


    @Override
    public void showMain() {
        Intent intent = new Intent(InicioActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void showMeditacion() {
        Intent intent = new Intent(InicioActivity.this, MeditacionActivity.class);
        intent.putExtra("meditacion", 1);
        startActivity(intent);
        finish();
    }

    @Override
    public void showAlarma() {
        Intent intent = new Intent(InicioActivity.this, AlarmaActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void showWeb() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.adrianjaime.com.ar/"));
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        mediaController = null;
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnMain: {
                presenter.onSelectedMain();
                break;
            }
            case R.id.btnMeditacion: {
                presenter.onSelectedMeditacion();
                break;
            }
            case R.id.btnConfiguracion: {
                presenter.onSelectedAlarma();
                break;
            }
            case R.id.btnWebLink: {
                presenter.onSelectedWebLink();
                break;
            }
        }
    }
}
