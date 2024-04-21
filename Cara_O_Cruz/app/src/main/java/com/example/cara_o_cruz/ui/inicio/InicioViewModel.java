package com.example.cara_o_cruz.ui.inicio;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class InicioViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public InicioViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Hola a todos, os presento el juego de Cara o Cruz, esta es la pestaña de Inicio, para poder jugar, seleccionar arriba a la izquierda en las tres rallas, si quereis jugar a cara o cruz o ver el historial de el ganador");
    }

    public LiveData<String> getText() {
        return mText;
    }
}