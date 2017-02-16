package com.adrianjaime.calmatumente2.data.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.adrianjaime.calmatumente2.BuildConfig;
import com.adrianjaime.calmatumente2.data.repository.Repository;
import com.adrianjaime.calmatumente2.data.repository.Session;
import com.adrianjaime.calmatumente2.data.repository.SessionFactory;
import com.adrianjaime.calmatumente2.domain.pojo.Alarma;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matute on 23/01/2017.
 */
public class AlarmaRepository implements Repository<Alarma> {

    private static final String LOGTAG = "AlarmaRepository";
    private String TABLENAME = "alarmas";
    protected Session session;
    private Context context;

    public AlarmaRepository(Context context) {
        session = SessionFactory.getSession(context);
        this.context = context;
    }

    @Override
    public Session Session() {
        return session;
    }

    private int getIdentity(SQLiteDatabase db){
        String[] column = new String[] {"seq"};
        Cursor cursor = db.query("sqlite_sequence", column, "name='" + TABLENAME + "'", null, null, null, null);
        cursor.moveToFirst();
        int insertId = cursor.getInt(0);
        cursor.close();
        return insertId;
    }

    @Override
    public void insert(Alarma entity) {
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

            if(!session.isOpen())
                session.open();

            db = session.connection();
            db.insert(TABLENAME, null, values);
            int insertId = getIdentity(db);
            entity.setId(insertId);
            db.close();
        }
        catch(SQLiteException ex){
            Log.e(LOGTAG, ex.getMessage());
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        catch(Exception ex){
            Log.e(LOGTAG, ex.getMessage());
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        finally{
            if(db != null)
                if(db.isOpen()) db.close();
        }
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

            if(!session.isOpen())
                session.open();

            db = session.connection();
            int rows = 0;
            //Si es igual a la version 5 (Lollipop)
            if(Build.VERSION_CODES.LOLLIPOP == Build.VERSION.SDK_INT || Build.VERSION_CODES.LOLLIPOP_MR1 == Build.VERSION.SDK_INT) {
                rows = db.update(TABLENAME, values, "_id = " + entity.getId(), null);
            } else {
                rows = db.update(TABLENAME, values, "id = " + entity.getId(), null);
            }

            result = (rows > 0) ? true : false;
            db.close();
        }
        catch(SQLiteException ex){
            Log.e(LOGTAG, ex.getMessage());
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        catch(Exception ex){
            Log.e(LOGTAG, ex.getMessage());
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        finally{
            if(db != null)
                if(db.isOpen()) db.close();
        }
        return result;
    }

    @Override
    public boolean delete(Alarma entity) {
        boolean result = false;
        SQLiteDatabase db = null;
        try{
            if(!session.isOpen())
                session.open();

            db = session.connection();
            int rows = 0;
            //Si es igual a la version 5 (Lollipop)
            if(Build.VERSION_CODES.LOLLIPOP == Build.VERSION.SDK_INT || Build.VERSION_CODES.LOLLIPOP_MR1 == Build.VERSION.SDK_INT) {
                rows = db.delete(TABLENAME, "_id=" + entity.getId(), null);
            } else {
                rows = db.delete(TABLENAME, "id=" + entity.getId(), null);
            }

            result = (rows > 0) ? true : false;
            db.close();
        }
        catch(SQLiteException ex){
            Log.e(LOGTAG, ex.getMessage());
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        catch(Exception ex){
            Log.e(LOGTAG, ex.getMessage());
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        finally{
            if(db != null)
                if(db.isOpen()) db.close();
        }
        return result;
    }

    @Override
    public Alarma get(int id) {
        SQLiteDatabase db = null;
        Alarma entity = null;
        try{
            if(!session.isOpen())
                session.open();

            db = session.connection();
            Cursor cursor = null;
            //Si es igual a la version 5 (Lollipop)
            if(Build.VERSION_CODES.LOLLIPOP == Build.VERSION.SDK_INT || Build.VERSION_CODES.LOLLIPOP_MR1 == Build.VERSION.SDK_INT) {
                cursor = db.query(TABLENAME, null, "_id = " + id, null, null, null, null);
            } else {
                cursor = db.query(TABLENAME, null, "id = " + id, null, null, null, null);
            }
            cursor.moveToFirst();
            entity = buildNewEntity(cursor);
            cursor.close();
            db.close();
        }
        catch(Exception ex){
            Log.e(LOGTAG, ex.getMessage());
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        finally{
            if(db != null)
                if(db.isOpen()) {
                    db.close();
                    Log.i(LOGTAG, "Base de datos cerrada");
                }
        }
        return entity;
    }

    @Override
    public List<Alarma> getAll() {
        SQLiteDatabase db = null;
        List<Alarma> list = new ArrayList<Alarma>();

        try{
            if(!session.isOpen())
                session.open();

            db = session.connection();
            Cursor cursor = null;
            //Cursor cursor = db.query(TABLENAME, null, null, null, null, null, null);
            /*
            if(BuildConfig.DEBUG) {
                cursor = db.rawQuery("SELECT _id, hora, minuto, dia, lunes, martes, miercoles, jueves, viernes, sabados, domingos, isnotified FROM alarmas ORDER BY hora, minuto", null);
            } else {
                cursor = db.rawQuery("SELECT id, hora, minuto, dia, lunes, martes, miercoles, jueves, viernes, sabados, domingos, isnotified FROM alarmas ORDER BY hora, minuto", null);
            }*/
            //Si es igual a la version 5 (Lollipop)
            if(Build.VERSION_CODES.LOLLIPOP == Build.VERSION.SDK_INT || Build.VERSION_CODES.LOLLIPOP_MR1 == Build.VERSION.SDK_INT) {
                cursor = db.rawQuery("SELECT _id, hora, minuto, dia, lunes, martes, miercoles, jueves, viernes, sabados, domingos, isnotified FROM alarmas ORDER BY hora, minuto", null);
            } else {
                cursor = db.rawQuery("SELECT id, hora, minuto, dia, lunes, martes, miercoles, jueves, viernes, sabados, domingos, isnotified FROM alarmas ORDER BY hora, minuto", null);
            }

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                list.add(buildNewEntity(cursor));
                cursor.moveToNext();
            }
            cursor.close();
            db.close();
        }
        catch(SQLiteException ex){
            Log.e(LOGTAG, ex.getMessage());
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        catch(Exception ex){
            Log.e(LOGTAG, ex.getMessage());
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        finally{
            if(db != null)
                if(db.isOpen()) db.close();
        }
        return list;
    }

    @Override
    public List<Alarma> queryList(String query, String[] args) {
        return null;
    }

    private Alarma buildNewEntity(Cursor cursor) {
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
}
