package com.adrianjaime.calmatumente2.views.minmeditacion;

/**
 * Created by emaneff on 09/01/2017.
 */
public interface MinMeditacionInteractor {

    interface OnMeditacionFinishedListener {
        void onSuccess();
    }

    void startMeditacion(OnMeditacionFinishedListener listener);

}
