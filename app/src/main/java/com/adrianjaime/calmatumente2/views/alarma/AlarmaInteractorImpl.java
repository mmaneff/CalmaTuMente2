package com.adrianjaime.calmatumente2.views.alarma;


import android.content.Context;

import com.adrianjaime.calmatumente2.domain.pojo.Alarma;
import com.adrianjaime.calmatumente2.data.repositories.AlarmaRepository;
import com.adrianjaime.calmatumente2.data.repository.Session;
import com.adrianjaime.calmatumente2.data.repository.SessionFactory;

import java.util.ArrayList;
import java.util.List;


public class AlarmaInteractorImpl implements AlarmaInteractor {

    private Session _session;
    private final AlarmaRepository _repository;

    public AlarmaInteractorImpl(Context context) {
        _session = SessionFactory.getSession(context);
        _repository = new AlarmaRepository(context);
    }

    @Override
    public void save(final Alarma alarma, final OnAlarmaFinishedListener listener) {
        try {
            _session.open();
            _repository.insert(alarma);

            List<Alarma> alarmas = _repository.getAll();
            listener.onSuccess((ArrayList<Alarma>)alarmas);
        }
        catch (Exception ex) {
            listener.onError(ex.getMessage());
            throw ex;
        }
        finally{
            _session.close();
        }
    }

    @Override
    public void update(final Alarma alarma, final OnAlarmaFinishedListener listener) {
        try {
            _session.open();
            _repository.update(alarma);
            listener.onSuccess((ArrayList<Alarma>)_repository.getAll());
        }
        catch (Exception ex) {
            listener.onError(ex.getMessage());
            throw ex;
        }
        finally{
            _session.close();
        }
    }

    @Override
    public List<Alarma> loadAlarmas(final OnAlarmaFinishedListener listener) {
        try {
            _session.open();
            return _repository.getAll();
        }
        catch (Exception ex) {
            listener.onError(ex.getMessage());
            throw ex;
        }
        finally{
            _session.close();
        }
    }

    /*
    @Override
    public void delete(final Alarma alarma, final OnAlarmaFinishedListener listener) {
        try {
            _session.open();
            _repository.delete(alarma);
            listener.onSuccess((ArrayList<Alarma>)_repository.getAll());
        }
        catch (Exception ex) {
            listener.onError(ex.getMessage());
            throw ex;
        }
        finally{
            _session.close();
        }
    }

    @Override
    public void get(final int id, final OnAlarmaFinishedListener listener) {
        try {
            _session.open();
            _repository.get(id);
            listener.onSuccess((ArrayList<Alarma>)_repository.getAll());
        }
        catch (Exception ex) {
            listener.onError(ex.getMessage());
            throw ex;
        }
        finally{
            _session.close();
        }
    }
    */

}
