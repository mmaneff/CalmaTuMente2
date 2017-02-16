package com.adrianjaime.calmatumente2.views.alarma;

import com.adrianjaime.calmatumente2.domain.pojo.Alarma;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by emaneff on 09/01/2017.
 */
public interface AlarmaInteractor {

    interface OnAlarmaFinishedListener {
        void onSuccess(ArrayList<Alarma> alarmas);
        void onError(String error);
        //void onFinished(List<Alarma> alarmas);
    }

    void save(Alarma alarma, OnAlarmaFinishedListener listener);
    void update(Alarma alarma, OnAlarmaFinishedListener listener);
    List<Alarma> loadAlarmas(OnAlarmaFinishedListener listener);

    //Posibles métodos a ser implementados
    //void delete(Alarma alarma, OnAlarmaFinishedListener listener);
    //void get(int id, OnAlarmaFinishedListener listener);

}
