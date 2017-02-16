package com.adrianjaime.calmatumente2.views.pattern;

import com.adrianjaime.calmatumente2.domain.pojo.Alarma;

import java.util.ArrayList;

/**
 * Created by emaneff on 14/02/2017.
 * http://thefinestartist.com/android/mvc-pattern
 * https://andhradroid.files.wordpress.com/2012/04/observer.pdf
 */
public interface Observer {

    public abstract void update(ArrayList<Alarma> alarmas);

}
