package com.adrianjaime.calmatumente2.views.tipomeditacion;


import android.content.Context;
import android.os.AsyncTask;

import com.adrianjaime.calmatumente2.views.minmeditacion.MinMeditacionInteractorImpl;

/**
 * Created by emaneff on 09/01/2017.
 */
public class TipoMeditacionPresenterImpl implements TipoMeditacionPresenter {

    private final static String LOGCAT = "TipoMeditacionPresenterImpl";

    private Context context;
    private TipoMeditacionView tipoMeditacionView;


    public TipoMeditacionPresenterImpl(TipoMeditacionView tipoMeditacionView, Context context) {
        this.tipoMeditacionView = tipoMeditacionView;
        this.context = context;
    }

    @Override
    public void initView() {
        if(tipoMeditacionView != null) {
            tipoMeditacionView.initControls();
            tipoMeditacionView.initVideos();
            tipoMeditacionView.setFondoVideo();
        }
    }

    @Override
    public void onStartAudio() {
        if(tipoMeditacionView != null) {
            tipoMeditacionView.startAudio();
        }
    }

    @Override
    public void onStartVideoView() {
        if(tipoMeditacionView != null) {
            try {
                tipoMeditacionView.startVideoView();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onStopVideoView() {
        if(tipoMeditacionView != null) {
            tipoMeditacionView.stopVideoView();
        }
    }

    @Override
    public void onSelectedMeditacion() {
        if(tipoMeditacionView != null) {
            tipoMeditacionView.showMeditacion();
        }
    }

    @Override
    public void onDestroy() {
        tipoMeditacionView.limpiarMediaPlayer();
        tipoMeditacionView.limpiarVideoView();
        tipoMeditacionView = null;
    }


}
