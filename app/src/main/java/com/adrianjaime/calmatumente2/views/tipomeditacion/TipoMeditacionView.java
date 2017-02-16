package com.adrianjaime.calmatumente2.views.tipomeditacion;

/**
 * Created by emaneff on 09/01/2017.
 */

public interface TipoMeditacionView {

    void initControls();
    void initVideos();
    void setFondoVideo();
    void showMeditacion();
    void startVideoView() throws InterruptedException;
    void stopVideoView();
    void startAudio();
    void limpiarMediaPlayer();
    void limpiarVideoView();



}
