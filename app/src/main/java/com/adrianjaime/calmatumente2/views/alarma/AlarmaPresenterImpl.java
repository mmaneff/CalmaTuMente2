package com.adrianjaime.calmatumente2.views.alarma;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.adrianjaime.calmatumente2.domain.pojo.Alarma;
import com.adrianjaime.calmatumente2.views.pattern.Observer;

import java.util.ArrayList;
import java.util.Calendar;


import static com.adrianjaime.calmatumente2.services.Constants.ACTUALIZAR_ALARMA;

/**
 * Created by emaneff on 09/01/2017.
 */
public class AlarmaPresenterImpl implements AlarmaPresenter, AlarmaInteractor.OnAlarmaFinishedListener {

    private final static String LOGCAT = "AlarmaPresenterImpl";

    private Context context;
    private AlarmaView alarmaView;
    private AlarmaInteractor alarmaInteractor;
    ArrayList<Alarma> alarmas;


    public AlarmaPresenterImpl(AlarmaView alarmaView, Context context) {
        this.alarmaView = alarmaView;
        this.context = context;
        this.alarmaInteractor = new AlarmaInteractorImpl(context);
    }

    @Override
    public void initView() {
        try {
            alarmaView.initControls();
            alarmaView.initTypeFace();
            alarmaView.setToolbar();
            alarmaView.setTimePickerTextColor();
        } catch (Exception ex) {
            Log.e(LOGCAT, ex.getMessage());
        }
    }

    @Override
    public void loadAlarmas(Bundle savedInstanceState) {
        try {
            alarmas = (ArrayList<Alarma>) alarmaInteractor.loadAlarmas(this);
            if(alarmas.size() > 0) {
                alarmaView.loadAlarmas(savedInstanceState, alarmas);
                alarmaView.hideCrearAlarma();
                alarmaView.showAlarmaList();
            } else {
                alarmaView.hideAlarmaList();
                alarmaView.showCrearAlarma();
            }
        } catch (Exception ex) {
            Log.e(LOGCAT, ex.getMessage());
        }
    }

    @Override
    public void saveTime(Alarma alarma, int hora, int minuto) {
        try {
            if(alarma == null) {
                Calendar calendar = Calendar.getInstance();

                alarmaInteractor.save(createAlarma(hora, minuto, calendar.get(Calendar.DAY_OF_WEEK)), this);
            } else {
                if(alarma.getId() > 0) {
                    alarma.setHora(hora);
                    alarma.setMinuto(minuto);
                    alarma.setIsNotified(0);

                    alarmaInteractor.update(alarma, this);
                } else {
                    Calendar calendar = Calendar.getInstance();

                    alarmaInteractor.save(createAlarma(hora, minuto, calendar.get(Calendar.DAY_OF_WEEK)), this);
                }
            }
            alarmaView.hideCrearAlarma();
            alarmaView.hideTimePicker();
            alarmaView.showAlarmaList();
            ACTUALIZAR_ALARMA = true;
        } catch (Exception ex) {
            Log.e(LOGCAT, ex.getMessage());
        }
    }

    @Override
    public void onSelectedAcercaDe() {
        if(alarmaView != null){
            alarmaView.showAcercaDe();
        }
    }

    @Override
    public void onSelectedWebLink() {
        if(alarmaView != null){
            alarmaView.showWebLink();
        }
    }

    @Override
    public void onSelectedInicio() {
        if(alarmaView != null){
            alarmaView.showInicio();
        }
    }

    @Override
    public void onSelectedCrearAlarma() {
        if(alarmaView != null){
            alarmaView.hideAlarmaList();
            alarmaView.hideCrearAlarma();
            alarmaView.showTimePicker();
        }
    }

    @Override
    public void onSelectedCancelTime() {
        if(alarmaView != null){
            alarmaView.hideTimePicker();
            alarmas = (ArrayList<Alarma>) alarmaInteractor.loadAlarmas(this);
            if(alarmas.size() == 0) {
                alarmaView.showCrearAlarma();
            } else {
                alarmaView.showAlarmaList();
            }
        }
    }

    /**
     * @param hora
     * @param minuto
     * @param dia
     * @return
     */
    private Alarma createAlarma(int hora, int minuto, int dia) {
        Alarma alarma = new Alarma(hora, minuto, dia,
                (dia == Calendar.MONDAY) ? Calendar.MONDAY : 0,
                (dia == Calendar.TUESDAY) ? Calendar.TUESDAY : 0,
                (dia == Calendar.WEDNESDAY) ? Calendar.WEDNESDAY : 0,
                (dia == Calendar.THURSDAY) ? Calendar.THURSDAY : 0,
                (dia == Calendar.FRIDAY) ? Calendar.FRIDAY : 0,
                (dia == Calendar.SATURDAY) ? Calendar.SATURDAY : 0,
                (dia == Calendar.SUNDAY) ? Calendar.SUNDAY : 0);

        return alarma;
    }

    @Override
    public void onDestroy() {
        alarmaView = null;
    }

    @Override
    public void onSuccess(ArrayList<Alarma> alarmas) {
        Log.i(LOGCAT, "Operación exitosa");
        alarmaView.loadAdapter(alarmas);
    }

    @Override
    public void onError(String error) {
        alarmaView.showError(error);
        Log.e(LOGCAT, error);
    }


}
