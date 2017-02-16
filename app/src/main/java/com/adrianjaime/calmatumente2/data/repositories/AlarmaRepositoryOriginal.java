package com.adrianjaime.calmatumente2.data.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.adrianjaime.calmatumente2.data.repository.RepositoryImpl;
import com.adrianjaime.calmatumente2.domain.pojo.Alarma;

/**
 * Created by Matute on 23/01/2017.
 */
public class AlarmaRepositoryOriginal extends RepositoryImpl<Alarma> {

    private static final String LOGTAG = "AlarmaRepository";

    @Override
    public String getTableName() {
        return "alarmas";
    }

    @Override
    public Alarma buildNewEntity(Cursor cursor) {
        Alarma alarma = new Alarma();
        alarma.setId(cursor.getInt(0));
        alarma.setHora(cursor.getInt(1));
        alarma.setMinuto(cursor.getInt(2));
        alarma.setDia(cursor.getInt(3));
        alarma.setLunes(cursor.getInt(4));
        alarma.setMartes(cursor.getInt(5));
        alarma.setMiercoles(cursor.getInt(6));
        alarma.setJueves(cursor.getInt(7));
        alarma.setViernes(cursor.getInt(8));
        alarma.setSabados(cursor.getInt(9));
        alarma.setDomingos(cursor.getInt(10));
        alarma.setIsNotified(cursor.getInt(11));

        return alarma;
    }

    public AlarmaRepositoryOriginal(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }


    @Override
    public boolean update(Alarma entity) {
        boolean result = false;
        SQLiteDatabase db = null;
        try{
            ContentValues values = new ContentValues();
            values.put("hora", entity.getHora());
            values.put("minuto", entity.getMinuto());
            values.put("dia", entity.getDia());
            values.put("lunes", entity.getLunes());
            values.put("martes", entity.getMartes());
            values.put("miercoles", entity.getMiercoles());
            values.put("jueves", entity.getJueves());
            values.put("viernes", entity.getViernes());
            values.put("sabados", entity.getSabados());
            values.put("domingos", entity.getDomingos());
            values.put("isnotified", entity.getIsNotified());

            if(!_session.isOpen())
                _session.open();

            db = _session.connection();
            int rows = db.update(getTableName(), values, " id = " + entity.getId(), null);
            result = (rows > 0) ? true : false;
            db.close();
        }
        catch(SQLiteException ex){
            Log.e(LOGTAG, ex.getMessage());
        }
        catch(Exception ex){
            Log.e(LOGTAG, ex.getMessage());
        }
        finally{
            if(db != null)
                if(db.isOpen()) db.close();
        }
        return result;
    }


}
