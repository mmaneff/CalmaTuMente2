package com.adrianjaime.calmatumente2.views.main;

/**
 * Created by emaneff on 09/01/2017.
 */
public interface MainPresenter {

    void initView();
    void startAlarmaService();
    void initIntentFilter();
    void onSelectedInicio();
    void onSelectedMeditacion();
    void onSelectedAlarma();
    void onSelectedWebLink();
    void onDestroy();

}
