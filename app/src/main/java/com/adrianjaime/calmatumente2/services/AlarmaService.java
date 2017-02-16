package com.adrianjaime.calmatumente2.services;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.adrianjaime.calmatumente2.data.repositories.AlarmaRepository;
import com.adrianjaime.calmatumente2.data.repository.Session;
import com.adrianjaime.calmatumente2.data.repository.SessionFactory;
import com.adrianjaime.calmatumente2.views.main.MainActivity;
import com.adrianjaime.calmatumente2.R;
import com.adrianjaime.calmatumente2.domain.pojo.Alarma;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import static com.adrianjaime.calmatumente2.services.Constants.ACTUALIZAR_ALARMA;

/**
 * Created by emaneff on 29/12/2016.
 * http://www.hermosaprogramacion.com/2015/07/tutorial-para-crear-un-servicio-en-android/
 * Un {@link Service} que notifica la cantidad de memoria disponible en el sistema
 */
public class AlarmaService extends Service {

    private static final String TAG = AlarmaService.class.getSimpleName();

    private NotificationCompat.Builder notBuilder;
    private static final int MY_NOTIFICATION_ID = 12345;
    private static final int MY_REQUEST_CODE = 100;

    TimerTask timerTask;
    private ArrayList<Alarma> alarmas;
    private Session session;
    private AlarmaRepository repository;

    public AlarmaService() { }

    /**
     *
     */
    private void initNotification() {
        notBuilder = new NotificationCompat.Builder(this);
        // The message will automatically be canceled when the user clicks on Panel
        this.notBuilder.setAutoCancel(true);
    }

    /**
     * Armo la notificación y la muestro
     * http://androcode.es/2012/09/notificaciones-metodo-tradicional-notification-builder-y-jelly-bean/
     * @param ticker
     * @param title
     * @param text
     */
    private void showNotification(String ticker, String title, String text) {
        // --------------------------
        // Prepare a notification
        // --------------------------
        notBuilder.setSmallIcon(R.drawable.ic_menu_meditation);
        notBuilder.setTicker(ticker);
        // Set the time that the event occurred.
        // Notifications in the panel are sorted by this time.
        notBuilder.setWhen(System.currentTimeMillis() + 10 * 1000);
        notBuilder.setContentTitle(title);
        notBuilder.setContentText(text);
        notBuilder.setVibrate(new long[] {100, 250, 100, 500}); // Uso en API 11 o mayor
        notBuilder.setLights(Color.RED, 1, 0); // API 11 o mayor
        notBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));  // Uso en API 11 o mayor
        // Create Intent
        Intent intent = new Intent(this, MainActivity.class);
        // PendingIntent.getActivity(..) will start an Activity, and returns PendingIntent object.
        // It is equivalent to calling Context.startActivity(Intent).
        PendingIntent pendingIntent = PendingIntent.getActivity(this, MY_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        this.notBuilder.setContentIntent(pendingIntent);

        // Get a notification service (A service available on the system).
        NotificationManager notificationService = (NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);

        // Builds notification and issue it
        Notification notification =  notBuilder.build();
        notificationService.notify(MY_NOTIFICATION_ID, notification);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        Log.d(TAG, "Servicio creado...");

        session = SessionFactory.getSession(getApplicationContext());
        repository = new AlarmaRepository(getApplicationContext());

        initNotification();
    }

    private void reloadAlarmas() {
        try {
            session.open();
            alarmas = (ArrayList<Alarma>) repository.getAll();
        }
        catch (Exception ex) {
            Log.e("Error", ex.getMessage());
        }
        finally {
            session.close();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "Servicio iniciado...");

        reloadAlarmas();

        Timer timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {

                Log.i("ACTUALIZAR_ALARMA", Boolean.toString(ACTUALIZAR_ALARMA));

                if(ACTUALIZAR_ALARMA) {
                    reloadAlarmas();
                    ACTUALIZAR_ALARMA = false;
                }

                final Calendar calendar = Calendar.getInstance();
                int dia = calendar.get(Calendar.DAY_OF_WEEK);
                int hora = calendar.get(Calendar.HOUR);
                int minuto = calendar.get(Calendar.MINUTE);

                Log.d(TAG, "Hay " + Integer.toString(alarmas.size()) + " alarmas");

                for(Alarma alarma : alarmas) {
                    if(alarma.getHora() == hora
                            && alarma.getMinuto() == minuto
                            && alarma.getIsNotified() == 0) {
                        if(alarma.getLunes() == Calendar.MONDAY || dia == Calendar.MONDAY) {
                            showNotify(alarma);
                        } else if(alarma.getMartes() == Calendar.TUESDAY || dia == Calendar.TUESDAY) {
                            showNotify(alarma);
                        } else if(alarma.getMiercoles() == Calendar.WEDNESDAY || dia == Calendar.WEDNESDAY) {
                            showNotify(alarma);
                        } else if(alarma.getJueves() == Calendar.THURSDAY || dia == Calendar.THURSDAY) {
                            showNotify(alarma);
                        } else if(alarma.getViernes() == Calendar.FRIDAY || dia == Calendar.FRIDAY) {
                            showNotify(alarma);
                        } else if(alarma.getSabados() == Calendar.SATURDAY || dia == Calendar.SATURDAY) {
                            showNotify(alarma);
                        } else if(alarma.getDomingos() == Calendar.SUNDAY || dia == Calendar.SUNDAY) {
                            showNotify(alarma);
                        }
                    } else if(alarma.getHora() < hora && alarma.getIsNotified() == 1) {
                        //Log.w(TAG, alarma.toString() + " isNotifiy = false");
                        try {
                            alarma.setIsNotified(0);
                            session.open();
                            repository.update(alarma);
                        }
                        catch (Exception ex) {
                            Log.e("Error", ex.getMessage());
                        }
                        finally {
                            session.close();
                        }
                    }
                }

                //Intent localIntent = new Intent(Constants.ACTION_RUN_SERVICE).putExtra(Constants.EXTRA_MEMORY, availMem);
                Intent localIntent = new Intent(Constants.ALARMA_LISTENER);
                // Emitir el intent a la actividad
                LocalBroadcastManager.getInstance(AlarmaService.this).sendBroadcast(localIntent);
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 1000);

        return START_NOT_STICKY;
    }

    private void showNotify(Alarma alarma) {
        //Log.w(TAG, "Debo mostrar Notificación " + alarma.toString() + " hs");
        showNotification("CalmaTuMente", alarma.toString(), "Te toca meditar");
        try {
            alarma.setIsNotified(1);
            session.open();
            repository.update(alarma);
        }
        catch (Exception ex) {
            Log.e("Error", ex.getMessage());
        }
        finally {
            session.close();
        }
    }

    @Override
    public void onDestroy() {
        timerTask.cancel();
        Intent localIntent = new Intent(Constants.ALARMA_LISTENER);
        // Emitir el intent a la actividad
        LocalBroadcastManager.getInstance(AlarmaService.this).sendBroadcast(localIntent);
        Log.d(TAG, "Servicio destruido...");
    }

}
