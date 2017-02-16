package com.adrianjaime.calmatumente2.views.minmeditacion;


import android.content.Context;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.util.Log;

import com.adrianjaime.calmatumente2.R;

/**
 * Created by emaneff on 09/01/2017.
 */
public class MinMeditacionInteractorImpl extends AsyncTask<Integer, Integer, Boolean> {

    float x = 0;

    int inhala = 0;
    int reten = 0;
    int exhala = 0;
    boolean isPreparate = true;
    private MediaPlayer timbre;

    private MinMeditacionPresenter minMeditacionPresenter;

    public MinMeditacionInteractorImpl(Context context, MinMeditacionPresenter minMeditacionPresenter) {
        this.minMeditacionPresenter = minMeditacionPresenter;
        timbre = MediaPlayer.create(context, R.raw.campana);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        minMeditacionPresenter.onProcessStart();
    }

    @Override
    protected void onPostExecute(Boolean response) {
        super.onPostExecute(response);
        if(response) {
            minMeditacionPresenter.onMeditacionSuccess();
        } else {
            minMeditacionPresenter.onMeditacionError("Error en minuto de meditacion");
        }
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    protected void onCancelled(Boolean result) {
        super.onCancelled(result);
    }

    @Override
    protected Boolean doInBackground(Integer... params) {
        boolean response = true;
        Log.i("params", Integer.toString(params[0]));
        for (int i=3; i >= 0; i--) {
            try {
                if(isCancelled())
                    break;
                publishProgress(i);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                response = false;
            }
        }
        isPreparate = false;
        //for (int count = 0; count <= params[0]; count++) {
        for (int count = 0; count <= params[0] - 3; count++) {
            try {
                if(isCancelled())
                    break;
                publishProgress(count);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                response = false;
            }
        }

        return response;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        //Log.i("onProgressUpdate", Integer.toString(values[0]));

        if(isPreparate) {
            minMeditacionPresenter.showEstado("Preparate");
            //minMeditacionPresenter.showCuenta(Integer.toString(values[0]));
            if(values[0] == 0)
                timbre.start();
        } else {
            if(values[0] == 1 || values[0] == 20 || values[0] == 39) {
                minMeditacionPresenter.startAnimacionAmpliar();
            }
            if(values[0] == 12 || values[0] == 31 || values[0] == 50) {
                minMeditacionPresenter.startAnimacionContraer();
            }
            minMeditacionPresenter.showEstado(getTexto(values[0]));
            //minMeditacionPresenter.showCuenta(getCuenta(values[0]));
            showProgressBar(values[0]);
        }
    }

    private void showProgressBar(int value) {
        if ((value >= 1 && value <= 4) || (value > 19 && value <= 23) || (value > 38 && value <= 42)) {
            inhala = inhala + 1;
            minMeditacionPresenter.showProgressBarExterno1((float) (inhala * 8.5));
        }
        if ((value > 4 && value <= 11) || (value > 23 && value <= 30) || (value > 42 && value <= 49)) {
            reten = reten + 1;
            minMeditacionPresenter.showProgressBarExterno2((float) (reten * 4.8571));
        }
        if ((value > 11 && value <= 19) || (value > 30 && value <= 38) || (value > 49 && value <= 57)) {
            exhala = exhala + 1;
            minMeditacionPresenter.showProgressBarExterno3((float) (exhala * 4.25));
        }

        if(inhala > 0)
            Log.i("Timpre Inhala: ", Integer.toString(inhala));
        if(reten > 0)
            Log.i("Timpre reten: ", Integer.toString(reten));
        if(exhala > 0)
            Log.i("Timpre exhala: ", Integer.toString(exhala));

        if(inhala == 4 || reten == 7 || exhala == 8) {
            inhala = 0; reten = 0; exhala = 0;
            timbre.start();
        }
    }

    /**
     * @param value
     * @return
     */
    private String getTexto(Integer value) {
        if ((value >= 1 && value <= 4) || (value > 19 && value <= 23) || (value > 38 && value <= 42)) {
            return "Inhala";
        }
        if ((value > 4 && value <= 11) || (value > 23 && value <= 30) || (value > 42 && value <= 49)) {
            return "Reten";
        }
        if ((value > 11 && value <= 19) || (value > 30 && value <= 38) || (value > 49 && value <= 60)) {
            return "Exhala";
        }

        return "";
    }

    /**
     *
     * @param value
     * @return
     */
    private String getCuenta(Integer value) {
        String cuenta = "";

        if ((value >= 1 && value <= 4) || (value > 19 && value <= 23) || (value > 38 && value <= 42)) {
            inhala = inhala + 1;
            minMeditacionPresenter.showProgressBarExterno1((float) (inhala * 8.5));
            cuenta = Integer.toString(inhala);
        }
        if ((value > 4 && value <= 11) || (value > 23 && value <= 30) || (value > 42 && value <= 49)) {
            reten = reten + 1;
            minMeditacionPresenter.showProgressBarExterno2((float) (reten * 4.8571));
            cuenta = Integer.toString(reten);
        }
        if ((value > 11 && value <= 19) || (value > 30 && value <= 38) || (value > 49 && value <= 57)) {
            exhala = exhala + 1;
            minMeditacionPresenter.showProgressBarExterno3((float) (exhala * 4.25));
            cuenta = Integer.toString(exhala);
        }

        if(inhala == 4 || reten == 7 || exhala == 8) {
            inhala = 0; reten = 0; exhala = 0;
            timbre.start();
        }

        return cuenta;
    }
}
