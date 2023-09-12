package com.alex.talk;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class ChooseModeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_mode);
        ConstraintLayout liebe = findViewById(R.id.liebe);
        ConstraintLayout gefuehle = findViewById(R.id.gefuehle);
        ConstraintLayout leben = findViewById(R.id.leben);
        ConstraintLayout philo = findViewById(R.id.philo);
        ConstraintLayout politik = findViewById(R.id.politik);
        ConstraintLayout mix = findViewById(R.id.mix);

        liebe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGame(0);
            }
        });

        gefuehle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGame(1);
            }
        });

        leben.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGame(2);
            }
        });

        philo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGame(3);
            }
        });

        politik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGame(4);
            }
        });

        mix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChooseModeActivity.this, MixSettings.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.choose_mode_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                Intent intent = new Intent(ChooseModeActivity.this, GameSettings.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void startGame(int topic){
        Intent intent = new Intent(ChooseModeActivity.this, GameActivity.class);
        intent.putExtra("mode", topic);
        startActivity(intent);
    }
}
