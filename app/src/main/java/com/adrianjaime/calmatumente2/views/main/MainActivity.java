package com.adrianjaime.calmatumente2.views.main;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.adrianjaime.calmatumente2.R;
import com.adrianjaime.calmatumente2.views.alarma.AlarmaActivity;
import com.adrianjaime.calmatumente2.views.meditacion.MeditacionActivity;
import com.adrianjaime.calmatumente2.views.minmeditacion.MinMeditacionActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

/**
 * http://paraisoexperto.com/animaciones-en-android-usando-android-studio/
 * http://www.appcelerator.com/blog/2014/11/adding-transparent-background/
 * http://stackoverflow.com/questions/3337371/androiddrawableleft-margin-and-or-padding
 *
 */
public class MainActivity extends AppCompatActivity implements MainView, View.OnClickListener {

    @Bind(R.id.btnInicio)       ImageButton btnInicio;
    @Bind(R.id.btnMeditacion)   ImageButton btnMeditacion;
    @Bind(R.id.btnConfiguracion)ImageButton btnConfiguracion;
    @Bind(R.id.imgViewLogo)     ImageView imgViewLogo;
    @Bind(R.id.imgViewAppName)  ImageView imgViewAppName;
    @Bind(R.id.tvWebLink)       TextView tvWebLink;

    //private static final Logger logger = LoggerFactory.getLogger(MainActivity.class);

    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //logger.info("Activity loading....");

        presenter = new MainPresenterImpl(this, getApplicationContext());
        presenter.initView();

        presenter.startAlarmaService();
        presenter.initIntentFilter();
    }

    @Override
    public void initControls() {
        ButterKnife.bind(this);
    }

    @Override
    public void initTypeFace() {
        Typeface customFont = Typeface.createFromAsset(getAssets(), "fonts/Ubuntu-Regular.ttf");
        tvWebLink.setTypeface(customFont);
    }


    @Override
    public void initAnimation() {
        final Animation animationFadeIn_1 = AnimationUtils.loadAnimation(this, R.anim.fadein_1);
        final Animation animationFadeIn_2 = AnimationUtils.loadAnimation(this, R.anim.fadein_2);
        final Animation animationFadeIn_3 = AnimationUtils.loadAnimation(this, R.anim.fadein_3);

        btnInicio.startAnimation(animationFadeIn_1);
        btnMeditacion.startAnimation(animationFadeIn_1);
        btnConfiguracion.startAnimation(animationFadeIn_1);
        imgViewLogo.startAnimation(animationFadeIn_2);
        imgViewAppName.startAnimation(animationFadeIn_3);
        tvWebLink.startAnimation(animationFadeIn_3);
    }

    @Override
    public void showInicio() {
        Intent intent = new Intent(MainActivity.this, MinMeditacionActivity.class);
        startActivity(intent);
    }

    @Override
    public void showMeditacion() {
        Intent intent = new Intent(MainActivity.this, MeditacionActivity.class);
        intent.putExtra("meditacion", 1);
        startActivity(intent);
    }

    @Override
    public void showAlarma() {
        Intent intent = new Intent(MainActivity.this, AlarmaActivity.class);
        startActivity(intent);
    }

    @Override
    public void showWeb() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.adrianjaime.com.ar/"));
        startActivity(intent);
    }


    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnInicio:
                presenter.onSelectedInicio();
                break;
            case R.id.btnMeditacion:
                presenter.onSelectedMeditacion();
                break;
            case R.id.btnConfiguracion:
                presenter.onSelectedAlarma();
                break;
            case R.id.tvWebLink:
                presenter.onSelectedWebLink();
                break;
            default:
                break;

        }
    }


}
