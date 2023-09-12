package com.alex.talk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MixSettings extends AppCompatActivity {
    /**
     * 0: Liebe
     * 1: Gefuehle
     * 2: Leben
     * 3: Philosophie
     * 4: Politik
     */
    private boolean[] topics = new boolean[5];

    CheckBox checkLiebe, checkGefuehle, checkLeben, checkPhilo, checkPolitik;
    Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mix_settings);
        checkLiebe = findViewById(R.id.checkLiebe);
        checkGefuehle = findViewById(R.id.checkGefuehle);
        checkLeben = findViewById(R.id.checkLeben);
        checkPhilo = findViewById(R.id.checkPhilo);
        checkPolitik = findViewById(R.id.checkPolitik);
        btnStart = findViewById(R.id.btnStart2);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!checkLiebe.isChecked() && !checkGefuehle.isChecked() && !checkLeben.isChecked() && !checkPhilo.isChecked() && !checkPolitik.isChecked()){
                    Toast.makeText(MixSettings.this, "Wähle mindestens ein Thema aus!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(checkLiebe.isChecked())
                    topics[0] = true;
                if(checkGefuehle.isChecked())
                    topics[1] = true;
                if(checkLeben.isChecked())
                    topics[2] = true;
                if(checkPhilo.isChecked())
                    topics[3] = true;
                if(checkPolitik.isChecked())
                    topics[4] = true;

                Intent intent = new Intent(MixSettings.this, GameActivity.class);
                intent.putExtra("topicArray", topics);
                intent.putExtra("mode", 5);
                startActivity(intent);
            }
        });
    }
}
