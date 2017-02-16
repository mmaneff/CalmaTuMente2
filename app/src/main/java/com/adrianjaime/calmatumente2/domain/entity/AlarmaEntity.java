package com.adrianjaime.calmatumente2.domain.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by emaneff on 01/12/2016.
 * https://elbauldelprogramador.com/adapter-personalizado-en-android/
 */
//https://danielme.com/2013/10/09/diseno-android-listview-con-checkbox/
public class AlarmaEntity implements Parcelable {

    private int id;
    private int hora;
    private int minuto;
    private int dia;
    private int lunes;
    private int martes;
    private int miercoles;
    private int jueves;
    private int viernes;
    private int sabados;
    private int domingos;
    private int isNotified;

    public AlarmaEntity(){}

    public AlarmaEntity(int hora, int minuto, int dia, int lunes, int martes, int miercoles,
                  int jueves, int viernes, int sabados, int domingos) {
        this.hora = hora;
        this.minuto = minuto;
        this.dia = dia;
        this.lunes = lunes;
        this.martes = martes;
        this.miercoles = miercoles;
        this.jueves = jueves;
        this.viernes = viernes;
        this.sabados = sabados;
        this.domingos = domingos;
        this.isNotified = 0;
    }

    public AlarmaEntity(Parcel in) {
        this.hora = in.readInt();
        this.minuto = in.readInt();
        this.dia = in.readInt();
        this.lunes = in.readInt();
        this.martes = in.readInt();
        this.miercoles = in.readInt();
        this.jueves = in.readInt();
        this.viernes = in.readInt();
        this.sabados = in.readInt();
        this.domingos = in.readInt();
        this.isNotified = in.readInt();
    }

    public int getId() { return id; }
    public int getHora() {
        return this.hora;
    }
    public int getMinuto() {
        return this.minuto;
    }
    public int getDia() { return this.dia; }
    public int getLunes() {
        return this.lunes;
    }
    public int getMartes() {
        return this.martes;
    }
    public int getMiercoles() {
        return this.miercoles;
    }
    public int getJueves() {
        return this.jueves;
    }
    public int getViernes() {
        return this.viernes;
    }
    public int getSabados() {
        return this.sabados;
    }
    public int getDomingos() {
        return this.domingos;
    }
    public int getIsNotified() { return this.isNotified; }

    public void setId(int id) { this.id = id; }
    public void setHora(int hora) {
        this.hora = hora;
    }
    public void setMinuto(int minuto) {
        this.minuto = minuto;
    }
    public void setDia(int dia) { this.dia = dia; }
    public void setLunes(int lunes) {
        this.lunes = lunes;
    }
    public void setMartes(int martes) {
        this.martes = martes;
    }
    public void setMiercoles(int miercoles) {
        this.miercoles = miercoles;
    }
    public void setJueves(int jueves) {
        this.jueves = jueves;
    }
    public void setViernes(int viernes) {
        this.viernes = viernes;
    }
    public void setSabados(int sabados) {
        this.sabados = sabados;
    }
    public void setDomingos(int domingos) {
        this.domingos = domingos;
    }
    public void setIsNotified(int isNotified) { this.isNotified = isNotified; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getHora());
        dest.writeInt(getMinuto());
        dest.writeInt(getLunes());
    }

    /**
     *
     */
    public static final Parcelable.Creator<AlarmaEntity> CREATOR = new Parcelable.Creator<AlarmaEntity>() {
        public AlarmaEntity createFromParcel(Parcel in) {
            return new AlarmaEntity(in);
        }

        public AlarmaEntity[] newArray(int size) {
            return new AlarmaEntity[size];
        }
    };

    @Override
    public String toString() {
        return "Alarma: " + Integer.toString(this.hora) + ":" + Integer.toString(this.minuto);
    }
}
