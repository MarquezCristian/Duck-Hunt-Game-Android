package com.example.patohuntproject.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.patohuntproject.R;
import com.example.patohuntproject.common.Constantes;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Random;

public class GameActivity extends AppCompatActivity {

    TextView tvCounterDucks , tvTimer,tvNick ;
    ImageView ivDuck;
    int counter=0;
    int anchoPantalla ;
    int altoPantalla;
    Random aleatorio;
    boolean gameOver = false;
    String id, nick;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        db = FirebaseFirestore.getInstance();

        initViewComponents();
        eventos();
        initPantalla();
        moveDuck();
        initCuentaAtras();

    }

    private void initCuentaAtras() {
        new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                long segundosRestantes = millisUntilFinished / 1000;
                tvTimer.setText(segundosRestantes + "s");
            }

            public void onFinish() {
                tvTimer.setText("0s");
                gameOver =true;
                mostrarDialogoGameOver();
                saveResultFirestore();
            }
            }.start();

        }

    private void saveResultFirestore() {
        db.collection("users")
                .document(id)
                .update("patos",counter);
    }

    private void mostrarDialogoGameOver() {
        // 1. Instantiate an <code><a href="/reference/android/app/AlertDialog.Builder.html">AlertDialog.Builder</a></code> with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);

        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage("Matasta a : " + counter + " patos")
                .setTitle("Game Over");

        builder.setPositiveButton( "Reiniciar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Con estos valores se reinicia el juego
                counter=0;
                tvCounterDucks.setText("0");
                gameOver= false;
                initCuentaAtras();
                moveDuck();
            }
        });
        builder.setNegativeButton( "Ver ranking", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
                dialog.dismiss();
                Intent i = new Intent(GameActivity.this,RankingActivity.class);
                startActivity(i);
            }
        });


        // 3. Get the <code><a href="/reference/android/app/AlertDialog.html">AlertDialog</a></code> from <code><a href="/reference/android/app/AlertDialog.Builder.html#create()">create()</a></code>
        AlertDialog dialog = builder.create();

        //4.Mostrar el dialogo
        dialog.show();
    }

    private void initPantalla() {
        // 1. Obtener el tamaño de la pantalla del despositivo en el que se esta ejeuctando la app
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        anchoPantalla = size.x;
        altoPantalla=size.y;
        //2. inicializar el obejto para generar numero aleatorios
        aleatorio =new Random();
    }


    private void initViewComponents() {
        tvCounterDucks =findViewById(R.id.textViewCounter);
        tvTimer =findViewById(R.id.textViewTimer);
        tvNick =findViewById(R.id.textViewNick);
        ivDuck =findViewById(R.id.imageViewDuck);

        //Cambiar tipo de fuente
        Typeface typeface = Typeface.createFromAsset(getAssets(),"pixel.ttf");
        tvCounterDucks.setTypeface(typeface);
        tvTimer.setTypeface(typeface);
        tvNick.setTypeface(typeface);

        //EXTRAS :Obtener nick y setearlo en textview
        //conjunto de variables que fueron recibidas a traves del objeto Intent
        Bundle extras = getIntent().getExtras();
        String nick =extras.getString(Constantes.EXTRA_NICK);
        id = extras.getString(Constantes.EXTRA_ID);
        tvNick.setText(nick);
    }

    private void eventos() {
        ivDuck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!gameOver){
              counter++;
              tvCounterDucks.setText(String.valueOf(counter));

              ivDuck.setImageResource(R.drawable.duck_clicked);

              new Handler().postDelayed(new Runnable() {
                  @Override
                  public void run() {
                      ivDuck.setImageResource(R.drawable.duck);
                      moveDuck();
                  }
              }, 500);
                }
            }
         });
       }


    private void moveDuck() {
        int min = 0;
        int maximoX = anchoPantalla - ivDuck.getWidth();
        int maximoY = altoPantalla - ivDuck.getHeight();

        //Se genera don numeros aleatorios, uno para la coordenada x y otro para la cooderdanada Y
        int randomX = aleatorio.nextInt(((maximoX - min) +1) + min );
        int randomY = aleatorio.nextInt(((maximoY - min) +1) + min );

        //Se utliza los numero aleatorios para mover el pato a esa posicion
        ivDuck.setX(randomX);
        ivDuck.setY(randomY);
    }
}
