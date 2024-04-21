package com.example.cara_o_cruz.ui.historial;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.cara_o_cruz.R;
import com.example.cara_o_cruz.data.database.AppDatabase;
import com.example.cara_o_cruz.data.database.TiradaEntity;

import java.util.List;

public class HistorialFragment extends Fragment {

    private TableLayout tableLayoutCara;
    private TableLayout tableLayoutCruz;
    private TextView textMaxPoints;
    private AppDatabase appDatabase;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        tableLayoutCara = root.findViewById(R.id.tableLayoutCara);
        tableLayoutCruz = root.findViewById(R.id.tableLayoutCruz);
        textMaxPoints = root.findViewById(R.id.textMaxPoints);

        appDatabase = AppDatabase.getInstance(requireContext());

        populateTables();
        showMaxPoints();

        return root;
    }

    private void populateTables() {
        new Thread(() -> {
            List<TiradaEntity> tiradas = appDatabase.tiradaDao().getUltimasTiradas();
            getActivity().runOnUiThread(() -> {
                // Limpiar tablas antes de poblarlas
                tableLayoutCara.removeAllViews();
                tableLayoutCruz.removeAllViews();

                // Iterar sobre las tiradas y agregarlas a las tablas correspondientes
                for (TiradaEntity tirada : tiradas) {
                    TableRow row = new TableRow(requireContext());
                    TextView textViewResultado = new TextView(requireContext());
                    textViewResultado.setText(tirada.getResultado());
                    TextView textViewPuntos = new TextView(requireContext());
                    textViewPuntos.setText(String.valueOf(tirada.getPuntos()));
                    row.addView(textViewResultado);
                    row.addView(textViewPuntos);

                    // Determinar en qué tabla agregar la fila
                    if (tirada.getResultado().equals("Cara")) {
                        tableLayoutCara.addView(row);
                    } else {
                        tableLayoutCruz.addView(row);
                    }
                }
            });
        }).start();
    }





    private void showMaxPoints() {
        new Thread(() -> {
            int maxCaraPoints = appDatabase.tiradaDao().getMaxCaraPoints();
            int maxCruzPoints = appDatabase.tiradaDao().getMaxCruzPoints();

            getActivity().runOnUiThread(() -> {
                textMaxPoints.setText("Máxima puntuación de Cara: " + maxCaraPoints +
                        "\nMáxima puntuación de Cruz: " + maxCruzPoints);
            });
        }).start();
    }

}
