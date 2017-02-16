package com.adrianjaime.calmatumente2.views.alarma;

import android.os.Bundle;

import com.adrianjaime.calmatumente2.domain.pojo.Alarma;

import java.util.ArrayList;

/**
 * Created by emaneff on 09/01/2017.
 */
public interface AlarmaView {

    void initControls();
    void initTypeFace();
    void setTimePickerTextColor();
    void loadAlarmas(Bundle savedInstanceState, ArrayList<Alarma> alarmas);
    void loadAdapter(ArrayList<Alarma> alarmas);
    void hideCrearAlarma();
    void showCrearAlarma();
    void showTimePicker();
    void hideTimePicker();
    void showAlarmaList();
    void hideAlarmaList();
    void showInicio();
    void showAcercaDe();
    void showWebLink();
    void setToolbar();
    void showError(String error);

}
