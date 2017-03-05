package com.adrianjaime.calmatumente2.views.tipomeditacion;

/**
 * Created by emaneff on 09/01/2017.
 */

public interface TipoMeditacionView {

    void initControls();
    void initVideos();
    void initFondoVideo(int meditacion);
    void initMeditacion(int meditacion);

    //void showMeditacion();
    //void stopVideoView();
    void startVideoView() throws InterruptedException;
    void playMeditacion();

    void limpiarMediaPlayer();
    void limpiarVideoView();

}
