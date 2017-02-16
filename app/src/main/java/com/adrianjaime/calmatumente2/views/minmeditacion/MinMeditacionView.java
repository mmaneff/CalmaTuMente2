package com.adrianjaime.calmatumente2.views.minmeditacion;

/**
 * Created by emaneff on 09/01/2017.
 */
public interface MinMeditacionView {

    void initControls();
    void showEstado(String estado);
    //void showCuenta(String cuenta);
    void initVideos();
    void limpiarVideoView();
    void startVideoView();
    void stopVideoView();
    void showInicio();
    void initTypeFace();
    void showProgressBarExterno1(float progress);
    void showProgressBarExterno2(float progress);
    void showProgressBarExterno3(float progress);
    void showPlayIcon();
    void showPauseIcon();
    void showStopIcon();
    void showStop();
    void startAnimacionAmpliar();
    void startAnimacionContraer();

}
