package com.example.cara_o_cruz.data.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tiradas")
public class TiradaEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "resultado")
    private String resultado;

    @ColumnInfo(name = "puntos")
    private int puntos;

    public TiradaEntity(String resultado, int puntos) {
        this.resultado = resultado;
        this.puntos = puntos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }
}
