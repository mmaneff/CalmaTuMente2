package com.adrianjaime.calmatumente2.views.acercade;

import android.content.Context;
import android.content.Intent;

import com.adrianjaime.calmatumente2.views.main.MainInteractorImpl;
import com.adrianjaime.calmatumente2.views.main.MainView;


public class AcercaDePresenterImpl implements AcercaDePresenter {

    private final static String LOGCAT = "AcercaDePresenterImpl";

    private Context context;
    private AcercaDeView acercaDeView;

    public AcercaDePresenterImpl(AcercaDeView acercaDeView, Context context) {
        this.context = context;
        this.acercaDeView = acercaDeView;
    }

    /**
     * Inicio los datos de la vista
     */
    @Override
    public void initView() {
        if(acercaDeView != null) {
            acercaDeView.initControls();
            acercaDeView.initTypeFace();
        }
    }

    @Override
    public void onDestroy() {
        acercaDeView = null;
    }

}
