package com.example.cara_o_cruz.ui.caraocruz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.cara_o_cruz.R;
import com.example.cara_o_cruz.data.database.AppDatabase;
import com.example.cara_o_cruz.data.database.TiradaEntity;

import java.util.Random;

public class CaraOCruzFragment extends Fragment {

    private TextView textViewPuntosCara;
    private TextView textViewPuntosCruz;
    private TextView textViewHistorial;
    private Button buttonStartGame;
    private Button buttonReset;
    private AppDatabase appDatabase;

    private int puntosCara;
    private int puntosCruz;
    private String[] historial;
    private int tiradasRestantes;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        textViewPuntosCara = root.findViewById(R.id.textViewPuntosCara);
        textViewPuntosCruz = root.findViewById(R.id.textViewPuntosCruz);
        textViewHistorial = root.findViewById(R.id.textViewHistorial);
        buttonStartGame = root.findViewById(R.id.buttonStartGame);
        buttonReset = root.findViewById(R.id.buttonReset);
        buttonReset.setVisibility(View.GONE);

        // Obtener instancia de la base de datos
        appDatabase = AppDatabase.getInstance(requireContext());

        resetearJuego();

        buttonStartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jugar();
            }
        });

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetearJuego();
            }
        });

        return root;
    }

    private void resetearJuego() {
        puntosCara = 0;
        puntosCruz = 0;
        tiradasRestantes = 5;
        historial = new String[5];
        actualizarVistas();
        buttonReset.setVisibility(View.GONE);

        // Eliminar todas las entradas de la base de datos
        new Thread(new Runnable() {
            @Override
            public void run() {
                appDatabase.tiradaDao().deleteAllTiradas();
            }
        }).start();
    }


    private void jugar() {
        if (tiradasRestantes > 0) {
            Random random = new Random();
            boolean cara = random.nextBoolean();
            int puntos = random.nextInt(10) + 1; // Genera un número aleatorio entre 1 y 10
            if (cara) {
                puntosCara += puntos;
                historial[5 - tiradasRestantes] = "Cara - Puntos: " + puntos;
            } else {
                puntosCruz += puntos;
                historial[5 - tiradasRestantes] = "Cruz - Puntos: " + puntos;
            }

            // Insertar la tirada en la base de datos
            new Thread(new Runnable() {
                @Override
                public void run() {
                    appDatabase.tiradaDao().insert(new TiradaEntity(cara ? "Cara" : "Cruz", puntos));
                }
            }).start();

            tiradasRestantes--;
            actualizarVistas();
            if (tiradasRestantes == 0) {
                buttonReset.setVisibility(View.VISIBLE);
            }
        } else {
            textViewHistorial.setText("Has alcanzado el límite de tiradas.");
        }
    }




    private void actualizarVistas() {
        textViewPuntosCara.setText("Puntos Cara: " + puntosCara);
        textViewPuntosCruz.setText("Puntos Cruz: " + puntosCruz);
        textViewHistorial.setText("Historial de tiradas:\n");
        for (String tirada : historial) {
            if (tirada != null) {
                textViewHistorial.append(tirada + "\n");
            }
        }
    }
}
