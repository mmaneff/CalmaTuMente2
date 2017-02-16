package com.adrianjaime.calmatumente2.data.repository;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import android.content.*;
import android.database.*;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

/**
 * Created by Matute on 23/01/2017.
 */
public abstract class RepositoryImpl<T> implements Repository<T>  {

    enum FieldType{
        String,
        Integer,
        Boolean,
        Decimal,
        Short,
        Long,
        Date,
        Null
    }

    //private final Logger log = Logger.getLogger(Repository.class);
    private static final String LOGTAG = "RepositoryImpl";
    private String _tableName;
    protected Session _session;

    /**
     *
     */
    protected RepositoryImpl(Context context){
        _session = SessionFactory.getSession(context);
    }

    public Session Session() {
        return _session;
    }

    public String getTableName(){
        return _tableName;
    }

    private FieldType getFieldType(Field field){
        Object fieldType = field.getType();
        String typeName = fieldType.toString().replace("class java.lang.", "");
        Log.i(LOGTAG, "Type Name: " + typeName);

        if(typeName.equals("String")){
            return FieldType.String;
        }else if(typeName.equals("Integer")){
            return FieldType.Integer;
        }else if(typeName.equals("Short")){
            return FieldType.Short;
        }else if(typeName.equals("Long")){
            return FieldType.Long;
        }else if(typeName.equals("Decimal")){
            return FieldType.Decimal;
        }else if(typeName.equals("Boolean")){
            return FieldType.Boolean;
        }else if(typeName.equals("Date")){
            return FieldType.Date;
        }else{
            return FieldType.Null;
        }
    }

    private String createQuery(Field[] fields, T entity)	{
        StringBuilder columns = new StringBuilder();
        StringBuilder values = new StringBuilder();
        StringBuilder query = new StringBuilder();

        columns.append("(");
        values.append("(");
        try {

            for(Field field : fields){
                String fieldName = field.getName();
                if(fieldName.equals("id")){
                }
                else{
                    field.setAccessible(true);
                    Object fieldValue = field.get(entity);
                    columns.append(fieldName).append(",");

                    FieldType type = getFieldType(field);
                    if(type.equals(FieldType.String)){
                        values.append("'").append(fieldValue).append("'").append(",");
                    }else{
                        values.append(fieldValue).append(",");
                    }
                }
            }
            columns.deleteCharAt(columns.length() - 1).append(")");
            values.deleteCharAt(values.length() - 1).append(")");

            Log.i(LOGTAG, "Columns: " + columns.toString());
            Log.i(LOGTAG, "Values: " + values.toString());
        } catch (IllegalArgumentException e) {
            Log.e(LOGTAG, e.getMessage());
        } catch (IllegalAccessException e) {
            Log.e(LOGTAG, e.getMessage());
        }

        query.append("INSERT INTO ").append(getTableName()).append(columns.toString()).append(" VALUES ").append(values.toString());
        Log.i(LOGTAG, "Query: " + query.toString());
        return query.toString();
    }

    private int getIdentity(SQLiteDatabase db){
        String[] column = new String[] {"seq"};
        Cursor cursor = db.query("sqlite_sequence", column, "name='" + getTableName() + "'", null, null, null, null);
        cursor.moveToFirst();
        int insertId = cursor.getInt(0);
        cursor.close();
        return insertId;
    }

    private Field[] removeFieldNotDeseable(Field[] fields) {
        Field[] aux = new Field[fields.length - 2];
        int i = 0;
        for(Field field : fields) {
            if(!field.getName().equals("$change")) {
                if(!field.getName().equals("serialVersionUID")) {
                    aux[i] = field;
                    i += 1;
                }
            }
        }
        return aux;
    }

    public void insert(T entity) {
        SQLiteDatabase db = null;
        try{
            Field[] fields = entity.getClass().getDeclaredFields();
            fields = removeFieldNotDeseable(fields);
            String query = createQuery(fields, entity);
            if(!_session.isOpen())
                _session.open();

            db = _session.connection();
            db.execSQL(query);
            int insertId = getIdentity(db);
            db.close();

            Field field = entity.getClass().getDeclaredField("id");
            field.setAccessible(true);
            field.set(entity, insertId);
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
    }

    public boolean update(T entity){
        boolean result = false;
        SQLiteDatabase db = null;
        try{
            Field[] fields = entity.getClass().getDeclaredFields();
            fields = removeFieldNotDeseable(fields);
            ContentValues values = new ContentValues();
            for(Field field : fields){
                String fieldName = field.getName();
                if(fieldName.equals("id")){
                }
                else{
                    field.setAccessible(true);
                    Object fieldValue = field.get(entity);
                    values.put(fieldName, fieldValue.toString());
                    Log.i(LOGTAG, "Field named " + fieldName + " - Field value " + fieldValue.toString());
                }
            }
            Field field = entity.getClass().getDeclaredField("id");
            field.setAccessible(true);
            Object fieldValue = field.get(entity);
            if(!_session.isOpen())
                _session.open();

            db = _session.connection();
            int rows = db.update(getTableName(), values, "id = " + fieldValue, null);
            Log.i(LOGTAG, getTableName() + " updated with id: " + fieldValue);
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

    public boolean delete(T entity){
        boolean result = false;
        SQLiteDatabase db = null;
        try{
            Field field = entity.getClass().getDeclaredField("id");
            field.setAccessible(true);
            Object fieldValue = field.get(entity);
            if(!_session.isOpen())
                _session.open();

            db = _session.connection();
            int rows = db.delete(getTableName(), "id = " + fieldValue, null);
            Log.i(LOGTAG, getTableName() + " deleted with id: " + fieldValue);
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

    public T get(int id){
        SQLiteDatabase db = null;
        T entity = null;
        try{
            if(!_session.isOpen())
                _session.open();

            db = _session.connection();
            Cursor cursor = db.query(getTableName(), null, "id = " + id, null, null, null, null);
            cursor.moveToFirst();
            entity = buildNewEntity(cursor);
            cursor.close();
            db.close();
        }
        catch(Exception ex){
            Log.e(LOGTAG, ex.getMessage());
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

    public List<T> getAll() {
        SQLiteDatabase db = null;
        List<T> list = new ArrayList<T>();

        try{
            if(!_session.isOpen())
                _session.open();

            db = _session.connection();
            Cursor cursor = db.query(getTableName(), null, null, null, null, null, null);

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
            //throw ex;
        }
        catch(Exception ex){
            Log.e(LOGTAG, ex.getMessage());
            //throw ex;
        }
        finally{
            if(db != null)
                if(db.isOpen()) db.close();
        }
        return list;
    }

    public List<T> queryList(String query, String[] args){
        SQLiteDatabase db = null;
        List<T> list = new ArrayList<T>();
        try{
            if(!_session.isOpen())
                _session.open();

            db = _session.connection();
            Cursor cursor = db.rawQuery(query, args);

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
            //throw ex;
        }
        catch(Exception ex){
            Log.e(LOGTAG, ex.getMessage());
            //throw ex;
        }
        finally{
            if(db != null)
                if(db.isOpen()) db.close();
        }
        return list;
    }

    public T buildNewEntity(Cursor cursor){
        return null;
    }

}
