package com.adrianjaime.calmatumente2.data.repository;

import android.content.Context;
import android.util.Log;

/**
 * The SessionFactory class is based on Singleton Pattern.
 * @author emaneff
 *
 * The clause final is the equivalent to sealed
 */
public final class SessionFactory {

    /**
     * Para más adelante revisar si es optimo utilizar el método synchronized(lock)
     * o bien utilizando la forma que se muestra en la pagina
     * http://mechanical-sympathy.blogspot.com.ar/2011/11/java-lock-implementations.html
     * http://tutorials.jenkov.com/java-concurrency/locks.html
     * http://www.cs.umd.edu/~pugh/java/memoryModel/DoubleCheckedLocking.html
     * http://solojava.blogspot.com.ar/2004/10/double-checked-locking-is-broken.html
     */

    private static final String LOGTAG = "SessionFactory";
    private static volatile Session _session = null;
    private static final Object lock = new Object();
    private static String _lastError = "";

    /**
     * Constructor protected
     */
    protected SessionFactory(){

    }

    /**
     *
     * @return
     * 		Return last error.
     */
    public static String LastError(){
        return _lastError;
    }

    /**
     * Create new Session with the DataBase
     * @return
     * 		Session
     *
     */
    public static Session getSession(Context context){
        if(_session == null){
            synchronized(lock){
                if(_session == null){
                    try{
                        _session = new SessionImpl(context);
                        Log.i(LOGTAG, "Session created.");
                    }
                    catch(Exception ex){
                        Log.e(LOGTAG, "Fatal Error.", ex);
                        _lastError = ex.getMessage();
                    }
                }
            }
        }

        return _session;
    }

}
