package com.example.blackjack;

import androidx.annotation.NonNull;import java.util.Locale;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class MainActivity extends AppCompatActivity {

    //Tworze cos takiego
    ImageView card;
    TextView twojescore, score, twojescore2, score2, textView;
    Button guzik, guzik2, button;
    private static final int REQUEST_CODE_SPEECH_INPUT = 1;

    //Tworze losowanie
    Random r;

    //Tworze zmienna do zbierania mojego wyniku
    int wynik =0;
    int wynik2 =0;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Tworze coś takiego
        card = (ImageView) findViewById(R.id.card);
        twojescore = (TextView) findViewById(R.id.twojescore);
        score = (TextView) findViewById(R.id.score);
        twojescore2 = (TextView) findViewById(R.id.twojescore2);
        score2 = (TextView) findViewById(R.id.score2);
        textView = findViewById(R.id.textView);
        button = (Button) findViewById(R.id.button);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent
                        = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
                        Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to text");
                try {
                    startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
                } catch (Exception e) {
                    Toast
                            .makeText(MainActivity.this, " " + e.getMessage(),
                                    Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    @Nullable Intent data)
    {
        //tutaj wklejam wszystko potrzebne z guzikow
        //Tworze losowanie
        r = new Random();

        //od nowa globalne
        //Formułka zeby dostać logo poczatkowe
        int odnowa = getResources().getIdentifier("starter","drawable", getPackageName());

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(
                        RecognizerIntent.EXTRA_RESULTS);
                textView.setText(Objects.requireNonNull(result).get(0));
                String blagam = textView.getText().toString();
                if(blagam.contentEquals("dawaj")){
                    textView.setText("Prosze bardzo");
                    int los = r.nextInt(12) + 2;
                    if(los>11){
                        wynik += los-10;
                    }else{
                        wynik +=los;
                    }

                    //Zmiana obrazka karty
                    int obrazek = getResources().getIdentifier("c" + los,"drawable", getPackageName());
                    card.setImageResource(obrazek);

                    score.setText(String.valueOf(wynik));
                }
                else if(blagam.contentEquals("stop")){
                    textView.setText("Koniec");
                    wynik2= r.nextInt(5) + 16;
                    score2.setText(String.valueOf(wynik2));


                    if(wynik>wynik2 && wynik<=21){
                        Toast.makeText(MainActivity.this, "GRATKI WYGRALES!!", Toast.LENGTH_SHORT).show();
                        wynik=0;
                        score2.setText(String.valueOf(wynik2));
                        wynik2=0;
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                score2.setText(String.valueOf(wynik2));
                                card.setImageResource(odnowa);
                                score.setText(String.valueOf(wynik));
                            }
                        }, 2500);

                    }
                    else if(wynik<wynik2 || wynik>=22){
                        Toast.makeText(MainActivity.this, "PRZEGRALES XD", Toast.LENGTH_SHORT).show();
                        wynik=0;
                        score2.setText(String.valueOf(wynik2));
                        wynik2=0;
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                score2.setText(String.valueOf(wynik2));
                                card.setImageResource(odnowa);
                                score.setText(String.valueOf(wynik));
                            }
                        }, 2500);

                    } else{
                        Toast.makeText(MainActivity.this, "TYM RAZEM REMIS", Toast.LENGTH_SHORT).show();
                        wynik=0;
                        score2.setText(String.valueOf(wynik2));
                        wynik2=0;
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                score2.setText(String.valueOf(wynik2));
                                card.setImageResource(odnowa);
                                score.setText(String.valueOf(wynik));
                            }
                        }, 2500);
                    }
                }
                else{
                    textView.setText("Źle powiedziales");
                }
            }
        }
    }

}