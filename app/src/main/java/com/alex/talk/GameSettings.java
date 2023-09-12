package com.alex.talk;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Switch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class GameSettings extends AppCompatActivity {
    SharedPreferences prefs;
    Button btnSave;
    RadioButton check0, check1, check2;
    Switch orderSwitch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_settings);
        check0 = findViewById(R.id.check_a);
        check1 = findViewById(R.id.check_b);
        check2 = findViewById(R.id.check_c);
        orderSwitch = findViewById(R.id.order_switch);
        btnSave = findViewById(R.id.btnSettings);
        prefs = getSharedPreferences("prefs", MODE_PRIVATE);

        boolean randomOrder = prefs.getBoolean("randomOrder", false);
        int option = prefs.getInt("option", 1);

        if(randomOrder)
            orderSwitch.setChecked(true);

        switch (option){
            case 0:
                check0.setChecked(true);
                break;
            case 2:
                check2.setChecked(true);
                break;
            default:
                check1.setChecked(true);
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = prefs.edit();
                if(orderSwitch.isChecked())
                    editor.putBoolean("randomOrder", true);
                if(check0.isChecked())
                    editor.putInt("option", 0);
                else if(check2.isChecked())
                    editor.putInt("option", 2);
                else
                    editor.putInt("option", 1);
                editor.apply();
                Intent intent = new Intent(GameSettings.this, ChooseModeActivity.class);
                startActivity(intent);
            }
        });
    }
}
