package com.adrianjaime.calmatumente2.views.tipomeditacion;

import java.util.List;

/**
 * Created by emaneff on 09/01/2017.
 */
public interface TipoMeditacionPresenter {

    void initView(int meditacion);

    void onStartAudio();
    void onStartVideoView();
    //void onStopVideoView();
    //void onSelectedMeditacion();

    void onDestroy();

}
