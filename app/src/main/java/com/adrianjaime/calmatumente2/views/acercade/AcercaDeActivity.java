package com.adrianjaime.calmatumente2.views.acercade;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.ImageButton;
import android.widget.TextView;

import com.adrianjaime.calmatumente2.R;
import com.adrianjaime.calmatumente2.views.main.MainView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by emaneff on 02/02/2017.
 * http://www.tutorialesprogramacionya.com/javaya/androidya/detalleconcepto.php?codigo=168&inicio=
 * https://danielme.com/tip-android-4-dibujar-lineas-separadoras/
 */
public class AcercaDeActivity extends AppCompatActivity implements AcercaDeView  {

    @Bind(R.id.tvTexto1)    TextView tvTexto1;
    @Bind(R.id.tvTexto2)    TextView tvTexto2;
    @Bind(R.id.tvTexto3)    TextView tvTexto3;
    @Bind(R.id.tvTexto5)    TextView tvTexto5;

    private AcercaDePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acerca_de);

        presenter = new AcercaDePresenterImpl(this, getApplicationContext());
        presenter.initView();

    }

    @Override
    public void initControls() {
        ButterKnife.bind(this);
        setTitle("Acerca de...");
    }

    @Override
    public void initTypeFace() {
        Typeface customFont = Typeface.createFromAsset(getAssets(), "fonts/Ubuntu-Regular.ttf");
        tvTexto1.setTypeface(customFont);
        tvTexto2.setTypeface(customFont);
        tvTexto3.setTypeface(customFont);
        tvTexto5.setTypeface(customFont);

        tvTexto1.setText(Html.fromHtml("<font color='#000'>El objetivo de los <strong>Programas Mindfulness</strong> del <strong>Dr. Adrian Jaime</strong> es asistirte a descubrir el poder de habitar en el AHORA, logrando abandonar hábitos que generan un modo de vida acelerado, invadido por el Estrés, la Ansiedad o la Insatisfacción Personal.</font>"));
        tvTexto2.setText(Html.fromHtml("<font color='#000'><strong>Nuestro Propósito</strong> es que Vuelvas al eje, la Sonrisa y el Disfrute.</font>"));
        //tvTexto3.setText(Html.fromHtml("<font color='#6F448A'>Nuestro Propósito</font> es que Vuelvas al eje, la Sonrisa y el Disfrute."));
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }
}
