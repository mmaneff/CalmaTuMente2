package com.adrianjaime.calmatumente2.views.minmeditacion;


import android.content.Context;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.util.Printer;
import android.widget.ProgressBar;

import com.adrianjaime.calmatumente2.R;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by emaneff on 09/01/2017.
 */
public class MinMeditacionPresenterImpl implements MinMeditacionPresenter {

    private final static String LOGCAT = "MinMeditacionPresenterImpl";

    private Context context;
    private MinMeditacionView minMeditacionView;
    private MinMeditacionInteractorImpl minMeditacionInteractor;


    public MinMeditacionPresenterImpl(MinMeditacionView minMeditacionView, Context context) {
        this.minMeditacionView = minMeditacionView;
        this.context = context;
    }

    /**
     * Inicio los datos de la vista
     */
    @Override
    public void initView() {
        if(minMeditacionView != null) {
            minMeditacionView.initControls();
            minMeditacionView.initTypeFace();
            minMeditacionView.initVideos();
        }
    }

    @Override
    public void onStartVideoView() {
        if(minMeditacionView != null) {
            minMeditacionView.startVideoView();
        }
    }

    @Override
    public void onStopVideoView() {
        if(minMeditacionView != null) {
            minMeditacionView.stopVideoView();
        }
    }

    @Override
    public void onProcessStart() {
        minMeditacionView.showStopIcon();
        initProgressBar();
    }

    @Override
    public void onMeditacionSuccess() {
        minMeditacionView.showStopIcon();
        minMeditacionView.showPlayIcon();
        initProgressBar();
    }

    @Override
    public void onMeditacionError(String error) {
        minMeditacionView.showStopIcon();
        minMeditacionView.showPlayIcon();
        initProgressBar();
    }

    @Override
    public void showProgressBarExterno1(float count) {
        minMeditacionView.showProgressBarExterno1(count);
    }

    @Override
    public void showProgressBarExterno2(float count) {
        minMeditacionView.showProgressBarExterno2(count);
    }

    @Override
    public void showProgressBarExterno3(float count) {
        minMeditacionView.showProgressBarExterno3(count);
    }

    @Override
    public void showEstado(String estado) {
        minMeditacionView.showEstado(estado);
    }

    /*
    @Override
    public void showCuenta(String cuenta) {
        minMeditacionView.showCuenta(cuenta);
    }
    */

    @Override
    public void startAnimacionAmpliar() {
        minMeditacionView.startAnimacionAmpliar();
    }

    @Override
    public void startAnimacionContraer() {
        minMeditacionView.startAnimacionContraer();
    }

    @Override
    public void onDestroy() {
        if(minMeditacionInteractor != null) {
            minMeditacionInteractor.cancel(true);
            minMeditacionInteractor = null;
        }
        minMeditacionView.limpiarVideoView();
        minMeditacionView = null;
    }

    @Override
    public void startMeditacion() {
        if(minMeditacionView != null) {
            minMeditacionView.showPauseIcon();
            minMeditacionView.showStopIcon();

            if(minMeditacionInteractor == null || (minMeditacionInteractor.getStatus() == AsyncTask.Status.FINISHED)) {
                minMeditacionInteractor = null;
                minMeditacionInteractor = new MinMeditacionInteractorImpl(context, this);
                minMeditacionInteractor.execute(60);
            } else {
                if(minMeditacionInteractor.getStatus() == AsyncTask.Status.RUNNING) {
                    minMeditacionInteractor.cancel(true);
                }
                minMeditacionInteractor = null;
                minMeditacionView.showStopIcon();
                minMeditacionView.showPlayIcon();
                initProgressBar();
            }

        }
    }

    @Override
    public void stopMeditacion() {
        if(minMeditacionView != null) {
            if(minMeditacionInteractor != null && minMeditacionInteractor.getStatus() == AsyncTask.Status.RUNNING) {
                minMeditacionInteractor.cancel(true);
                minMeditacionView.showStop();
            }
            minMeditacionInteractor = null;
            minMeditacionView.showPlayIcon();
            initProgressBar();
            minMeditacionView.showInicio();
        }
    }

    @Override
    public void onSelectedInicio() {
        if(minMeditacionView != null) {
            minMeditacionView.showInicio();
        }
    }

    private void initProgressBar() {
        minMeditacionView.showEstado("Preparate");
        //minMeditacionView.showCuenta("3");
        minMeditacionView.showProgressBarExterno1(34);
        minMeditacionView.showProgressBarExterno2(34);
        minMeditacionView.showProgressBarExterno3(34);
    }

}
