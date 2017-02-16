package com.adrianjaime.calmatumente2.views.meditacion;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.adrianjaime.calmatumente2.views.alarma.AlarmaActivity;
import com.adrianjaime.calmatumente2.views.inicio.InicioActivity;
import com.adrianjaime.calmatumente2.views.main.MainActivity;
import com.adrianjaime.calmatumente2.views.minmeditacion.MinMeditacionActivity;
import com.adrianjaime.calmatumente2.views.tipomeditacion.TipoMeditacionActivity;
import com.adrianjaime.calmatumente2.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Matute on 26/12/2016.
 *
 */
public class MeditacionActivity extends AppCompatActivity implements MeditacionView, View.OnClickListener {

    @Bind(R.id.btnMinutoMeditacion)         ImageButton btnMinutoMeditacion;
    @Bind(R.id.btnMeditacion)               ImageButton btnMeditacion;
    @Bind(R.id.btnConfiguracion)            ImageButton btnConfiguracion;
    @Bind(R.id.btnMeditacionMenteCuerpo)    Button btnMeditacionMenteCuerpo;
    @Bind(R.id.btnMeditacionLiberaEstres)   Button btnMeditacionLiberaEstres;
    @Bind(R.id.btnMeditacionInsomnio)       Button btnMeditacionInsomnio;
    @Bind(R.id.tvTitulo)                    TextView tvTitulo;


    private MeditacionPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meditacion);

        presenter = new MeditacionPresenterImpl(this);
        presenter.initView();
    }

    @Override
    public void initControls() {
        ButterKnife.bind(this);
        setTitle("Medita");
    }

    @Override
    public void initTypeFace() {
        Typeface customFont = Typeface.createFromAsset(getAssets(), "fonts/Ubuntu-Regular.ttf");
        btnMeditacionMenteCuerpo.setTypeface(customFont);
        btnMeditacionLiberaEstres.setTypeface(customFont);
        btnMeditacionInsomnio.setTypeface(customFont);
        tvTitulo.setTypeface(customFont);
    }

    @Override
    public void showMeditacion() {
        //No se implementa nada en esta sección
    }

    @Override
    public void showAlarma() {
        Intent intent = new Intent(MeditacionActivity.this, AlarmaActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void showMinutoMeditacion() {
        Intent intent = new Intent(MeditacionActivity.this, MinMeditacionActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void showMeditacionMenteCuerpo() {
        Intent intent = new Intent(MeditacionActivity.this, TipoMeditacionActivity.class);
        intent.putExtra("meditacion", 1);
        startActivity(intent);
        finish();
    }

    @Override
    public void showMeditacionLiberaEstres() {
        Intent intent = new Intent(MeditacionActivity.this, TipoMeditacionActivity.class);
        intent.putExtra("meditacion", 2);
        startActivity(intent);
        finish();
    }

    @Override
    public void showMeditacionInsomnio() {
        Intent intent = new Intent(MeditacionActivity.this, TipoMeditacionActivity.class);
        intent.putExtra("meditacion", 3);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnMinutoMeditacion:
                presenter.onSelectedMinutoMeditacion();
                break;
            case R.id.btnMeditacion:
                presenter.onSelectedMeditacion();
                break;
            case R.id.btnConfiguracion:
                presenter.onSelectedAlarma();
                break;
            case R.id.btnMeditacionMenteCuerpo:
                presenter.onSelectedMeditacionMenteCuerpo();
                break;
            case R.id.btnMeditacionLiberaEstres:
                presenter.onSelectedMeditacionLiberaEstres();
                break;
            case R.id.btnMeditacionInsomnio:
                presenter.onSelectedMeditacionInsomnio();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

}
