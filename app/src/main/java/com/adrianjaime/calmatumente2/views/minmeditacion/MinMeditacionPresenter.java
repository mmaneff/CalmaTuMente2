package com.adrianjaime.calmatumente2.views.minmeditacion;

import android.widget.ProgressBar;

/**
 * Created by emaneff on 09/01/2017.
 */
public interface MinMeditacionPresenter {

    void initView();
    void startMeditacion();
    void stopMeditacion();
    void onSelectedInicio();
    void onDestroy();

    void onProcessStart();
    void onMeditacionSuccess();
    void onMeditacionError(String error);
    void showProgressBarExterno1(float count);
    void showProgressBarExterno2(float count);
    void showProgressBarExterno3(float count);
    void showEstado(String estado);
    //void showCuenta(String cuenta);
    void startAnimacionAmpliar();
    void startAnimacionContraer();
    void onStartVideoView();
    void onStopVideoView();

}
