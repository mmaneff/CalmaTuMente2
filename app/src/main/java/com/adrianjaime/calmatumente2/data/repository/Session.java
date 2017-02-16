package com.adrianjaime.calmatumente2.data.repository;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Matute on 23/01/2017.
 */
public interface Session {

    void close();
    boolean open();
    boolean isOpen();
    boolean transactionInProcess();
    void beginTransaction();
    void transactionSuccessful();
    void endTransaction();
    SQLiteDatabase connection();

}
