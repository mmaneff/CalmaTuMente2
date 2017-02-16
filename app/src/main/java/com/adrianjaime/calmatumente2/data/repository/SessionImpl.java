package com.adrianjaime.calmatumente2.data.repository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.sqlite.*;
import android.util.Log;

/**
 * Created by Matute on 23/01/2017.
 */

public class SessionImpl extends SQLiteOpenHelper implements Session {

    private static final String LOGTAG = "SessionImpl";
    private static SQLiteDatabase _dataBase;
    private static final String DATABASE_NAME = "calmatumente.db";
    //private static String DB_PATH = "/data/data/com.adrianjaime.calmatumente2/databases/";
    private static String DB_PATH = "";
    private static final int DATABASE_VERSION = 1;
    private final Context _context;

    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     * @param context
     * @throws IOException
     */
    public SessionImpl(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        String dbPath = context.getDatabasePath(DATABASE_NAME).getPath();
        String dbAppDir = context.getApplicationInfo().dataDir;
        String packageName = context.getPackageName();

        if (android.os.Build.VERSION.SDK_INT >= 17) {
            //DB_PATH = dbPath + "/databases/";
            DB_PATH = dbPath;
        } else {
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }

        _context = context;

        try {
            createDataBase();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
    protected void createDataBase() throws IOException{
        boolean dbExist = checkDataBase();

        if(dbExist){
            //do nothing - database already exist
        }else{
            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();

            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase(){
        SQLiteDatabase checkDB = null;

        try{
            //String dbPath = DB_PATH + DATABASE_NAME;
            String dbPath = DB_PATH;
            checkDB = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
            //String dbPath = this._context.getDatabasePath(DATABASE_NAME).getPath();
            //checkDB = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
        }catch(SQLiteException e){
            //database does't exist yet.
        }

        if(checkDB != null){
            checkDB.close();
        }

        return checkDB != null ? true : false;
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException{
        //Open your local db as the input stream
        InputStream myInput = _context.getAssets().open(DATABASE_NAME);

        // Path to the just created empty db
        //String outFileName = DB_PATH + DATABASE_NAME;
        String outFileName = DB_PATH;;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0){
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public synchronized void close() {
        try{
            if(_dataBase != null)
                _dataBase.close();

            super.close();

            _dataBase = null;
            Log.i(LOGTAG, "Database closed.");
        }
        catch(SQLiteException ex){
            Log.e(LOGTAG, "Error closing the database.", ex);
            throw ex;
        }
    }

    public boolean open() {
        //boolean opened = false;
        try{
            //String dbPath = DB_PATH + DATABASE_NAME;
            String dbPath = DB_PATH;
            _dataBase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
            //opened = _dataBase.isOpen();
            // Enable foreign key constraints
            //if(!_dataBase.isReadOnly()) {
            //	_dataBase.execSQL("PRAGMA foreign_keys = ON;");
            //}
            Log.i(LOGTAG, "Database opened.");
            return true;
        }
        catch(SQLiteException ex){
            //opened = false;
            Log.e(LOGTAG, "Error opening the database.", ex);
            _dataBase = null;
            return false;
            //throw ex;
        }
        //return opened;
    }

    public boolean isOpen() {
        return _dataBase.isOpen();
    }

    public boolean transactionInProcess() {
        return _dataBase.inTransaction();
    }

    public void beginTransaction() {
        _dataBase.beginTransaction();
    }

    public void transactionSuccessful() {
        _dataBase.setTransactionSuccessful();
    }

    public void endTransaction() {
        _dataBase.endTransaction();
    }

    public SQLiteDatabase connection() {
        return _dataBase;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
