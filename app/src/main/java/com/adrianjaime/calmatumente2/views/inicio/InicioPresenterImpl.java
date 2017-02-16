package com.adrianjaime.calmatumente2.views.inicio;

import android.util.Log;

/**
 * Created by emaneff on 09/01/2017.
 */
public class InicioPresenterImpl implements InicioPresenter, InicioInteractor {

    private final static String LOGCAT = "InicioPresenterImpl";

    private InicioView inicioView;
    private InicioInteractor inicioInteractor;


    public InicioPresenterImpl(InicioView inicioView) {
        this.inicioView = inicioView;
        this.inicioInteractor = new InicioInteractorImpl();
    }

    /**
     * Inicio los datos de la vista
     */
    @Override
    public void initView() {
        try {
            //Inicio todos los controles
            inicioView.initControls();
            //Inico el vide de presentación y agrego los controles Media para el video
            inicioView.initPresentacion();
            //Preparo el video para ser mostrado
            inicioView.preparedVideo();
        } catch (Exception ex) {
            Log.e(LOGCAT, ex.getMessage());
        }
    }

    @Override
    public void onSelectedMain() {
        inicioView.showMain();
    }

    @Override
    public void onSelectedMeditacion() {
        inicioView.showMeditacion();
    }

    @Override
    public void onSelectedAlarma() {
        inicioView.showAlarma();
    }

    @Override
    public void onSelectedWebLink() {
        inicioView.showWeb();
    }

    @Override
    public void onDestroy() {
        inicioView = null;
    }

}
