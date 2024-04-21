package com.example.cara_o_cruz.data.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface TiradaDao {

    @Insert
    void insert(TiradaEntity tirada);

    @Query("SELECT * FROM tiradas ORDER BY puntos DESC")
    Flowable<List<TiradaEntity>> getAllTiradasOrderedByPoints();

    @Query("SELECT SUM(puntos) FROM (SELECT puntos FROM tiradas WHERE resultado = 'Cara' ORDER BY id DESC LIMIT 5)")
    int getMaxCaraPoints();

    @Query("SELECT SUM(puntos) FROM (SELECT puntos FROM tiradas WHERE resultado = 'Cruz' ORDER BY id DESC LIMIT 5)")
    int getMaxCruzPoints();

    @Query("DELETE FROM tiradas")
    void deleteAllTiradas();
    @Query("SELECT * FROM tiradas ORDER BY id DESC LIMIT 5")
    List<TiradaEntity> getUltimasTiradas();


}
