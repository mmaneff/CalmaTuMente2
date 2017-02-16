package com.adrianjaime.calmatumente2.views.meditacion;

/**
 * Created by emaneff on 09/01/2017.
 */

public interface MeditacionPresenter {

    void initView();
    void onSelectedMinutoMeditacion();
    void onSelectedMeditacion();
    void onSelectedAlarma();
    void onSelectedMeditacionMenteCuerpo();
    void onSelectedMeditacionLiberaEstres();
    void onSelectedMeditacionInsomnio();
    void onDestroy();

}
