package com.adrianjaime.calmatumente2.views.meditacion;

/**
 * Created by emaneff on 09/01/2017.
 */
public class MeditacionPresenterImpl implements MeditacionPresenter, MeditacionInteractor {

    private final static String LOGCAT = "MeditacionPresenterImpl";

    private MeditacionView meditacionView;
    private MeditacionInteractor meditacionInteractor;


    public MeditacionPresenterImpl(MeditacionView meditacionView) {
        this.meditacionView = meditacionView;
        this.meditacionInteractor = new MeditacionInteractorImpl();
    }


    @Override
    public void initView() {
        if(meditacionView != null){
            //Inicio todos los controles
            meditacionView.initControls();
            meditacionView.initTypeFace();
        }
    }

    @Override
    public void onSelectedMeditacion() {
        if(meditacionView != null){
            meditacionView.showMeditacion();
        }
    }

    @Override
    public void onSelectedAlarma() {
        if(meditacionView != null){
            meditacionView.showAlarma();
        }
    }

    @Override
    public void onSelectedMinutoMeditacion() {
        if(meditacionView != null){
            meditacionView.showMinutoMeditacion();
        }
    }

    @Override
    public void onSelectedMeditacionMenteCuerpo() {
        if(meditacionView != null){
            meditacionView.showMeditacionMenteCuerpo();
        }
    }

    @Override
    public void onSelectedMeditacionLiberaEstres() {
        if(meditacionView != null){
            meditacionView.showMeditacionLiberaEstres();
        }
    }

    @Override
    public void onSelectedMeditacionInsomnio() {
        if(meditacionView != null){
            meditacionView.showMeditacionInsomnio();
        }
    }

    @Override
    public void onDestroy() {
        meditacionView = null;
    }
}
