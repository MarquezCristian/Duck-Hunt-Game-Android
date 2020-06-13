package com.example.patohuntproject;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import com.example.patohuntproject.common.Constantes;

public class GameActivity extends AppCompatActivity {

    TextView tvCounterDucks , tvTimer,tvNick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        tvCounterDucks =findViewById(R.id.textViewCounter);
        tvTimer =findViewById(R.id.textViewTimer);
        tvNick =findViewById(R.id.textViewNick);

        //Cambiar tipo de fuente
        Typeface typeface = Typeface.createFromAsset(getAssets(),"pixel.ttf");
        tvCounterDucks.setTypeface(typeface);
        tvTimer.setTypeface(typeface);
        tvNick.setTypeface(typeface);

        //EXTRAS :Obtener nick y setearlo en textview
        //conjunto de variables que fueron recibidas a traves del objeto Intent
        Bundle extras = getIntent().getExtras();
        String nick =extras.getString(Constantes.EXTRA_NICK);
        tvNick.setText(nick);


    }
}
