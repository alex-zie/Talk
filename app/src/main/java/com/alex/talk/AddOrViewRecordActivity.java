package com.alex.talk;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.alex.talk.model.Player;
import com.alex.talk.utils.DBHelper;

import java.util.stream.Collectors;


/**
 * Class to view or add records
 */
public class AddOrViewRecordActivity extends AppCompatActivity {
    private ImageView imgMan, imgWoman, imgNb;
    private EditText nameV;
    private DBHelper dbHelper;
    private Button btnSave;
    AppCompatButton red, salmon, orange, yellow, grass, lime, turquoise, dblue, purple, pink;
    private Player queriedPlayer;
    private byte gender;
    private int color;
    private boolean addRecord = true;
    private boolean changes = false;
    private long receivedId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_record);

        nameV = findViewById(R.id.input_name);
        btnSave = findViewById(R.id.btn_save);
        imgMan = findViewById(R.id.imageMan);
        imgWoman = findViewById(R.id.imageWoman);
        imgNb = findViewById(R.id.imageNb);
        red = findViewById(R.id.red);
        salmon = findViewById(R.id.salmon);
        orange = findViewById(R.id.orange);
        yellow = findViewById(R.id.yellow);
        grass = findViewById(R.id.light_green);
        lime = findViewById(R.id.dark_green);
        turquoise = findViewById(R.id.light_blue);
        dblue = findViewById(R.id.dark_blue);
        purple = findViewById(R.id.purple);
        pink = findViewById(R.id.pink);

        try {
            addRecord = getIntent().getBooleanExtra("addRecord", false);
            receivedId = getIntent().getLongExtra("USER_ID", -1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        dbHelper = new DBHelper(this);

        if(!addRecord) {
            setTitle("Spieler bearbeiten");
            queriedPlayer = dbHelper.getPlayer(receivedId);
            gender = queriedPlayer.getGender();
            color = queriedPlayer.getColor();
        } else
            setTitle("Spieler hinzufügen");

        switch (gender){
            case 0:
                imgMan.setBackgroundResource(R.drawable.image_border);
                break;
            case 1:
                imgWoman.setBackgroundResource(R.drawable.image_border);
                break;
            case 2:
                imgNb.setBackgroundResource(R.drawable.image_border);
                break;
        }

        updateImages();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    savePlayer();
                    if(queriedPlayer.getName().equals("")){
                        Toast.makeText(AddOrViewRecordActivity.this, "Bitte gib einen Namen ein!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(queriedPlayer.getColor()==0){
                        Toast.makeText(AddOrViewRecordActivity.this, "Bitte wähle eine Farbe aus!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(addRecord && dbHelper.getPlayerList().stream().map(p -> p.getName()).collect(Collectors.toList()).contains(queriedPlayer.getName())){
                        Toast.makeText(AddOrViewRecordActivity.this, "Der Name existiert bereits.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(changes)
                        addToDB();
                    else
                        startActivity(new Intent(AddOrViewRecordActivity.this, MainActivity.class));
                } catch (RuntimeException e) {
                    Toast.makeText(AddOrViewRecordActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        imgMan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgMan.setBackgroundResource(R.drawable.image_border);
                imgWoman.setBackgroundResource(0);
                imgNb.setBackgroundResource(0);
                gender = 0;
            }
        });

        imgWoman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgWoman.setBackgroundResource(R.drawable.image_border);
                imgMan.setBackgroundResource(0);
                imgNb.setBackgroundResource(0);
                gender = 1;
            }
        });

        imgNb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgNb.setBackgroundResource(R.drawable.image_border);
                imgMan.setBackgroundResource(0);
                imgWoman.setBackgroundResource(0);
                gender = 2;
            }
        });

        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color = 1;
                updateImages();
            }
        });

        salmon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color = 2;
                updateImages();
            }
        });

        orange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color = 3;
                updateImages();
            }
        });

        yellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color = 4;
                updateImages();
            }
        });

        grass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color = 5;
                updateImages();
            }
        });

        lime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color = 6;
                updateImages();
            }
        });

        turquoise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color = 7;
                updateImages();
            }
        });

        dblue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color = 8;
                updateImages();
            }
        });

        purple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color = 9;
                updateImages();
            }
        });

        pink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color = 10;
                updateImages();
            }
        });

        if(queriedPlayer!=null) {
            nameV.setText(queriedPlayer.getName());
        }
    }

    @Override
    public void onBackPressed() {
        savePlayer(); //to check if changes have been done
        if(changes) {
            AlertDialog.Builder builder = new AlertDialog.Builder(AddOrViewRecordActivity.this);
            builder.setTitle("Möchtest du zum Menü zurückkehren?");
            builder.setMessage("Deine Änderungen werden nicht gespeichert.");

            builder.setNegativeButton("Verwerfen", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent goBack = new Intent(AddOrViewRecordActivity.this, MainActivity.class);
                    startActivity(goBack);
                    dialog.dismiss();
                }
            });
            builder.setPositiveButton("Abbrechen", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            Intent goBack = new Intent(AddOrViewRecordActivity.this, MainActivity.class);
            startActivity(goBack);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_record_menu, menu);
        if(addRecord)
            menu.removeItem(R.id.action_remove);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_remove:
                dbHelper.deleteRecord(receivedId, this);
                startActivity(new Intent(AddOrViewRecordActivity.this, MainActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * - creates a person from the inputs
     * - checks, if changes were made
     * - sets queriedPerson to that person
     * - called before leaving the activity
     */
    private void savePlayer() {
        Player player = new Player();
        player.setName(nameV.getText().toString().trim());
        player.setGender(gender);
        player.setColor(color);
        if(!addRecord){
            player.setId(receivedId);
        }

        //check if any changes were made
        if(!player.equals(queriedPlayer)) {
            changes = true;
            queriedPlayer = player;
        }
    }

    private void addToDB(){
        if(addRecord)
            dbHelper.saveNewPlayer(queriedPlayer, this);
        else if (receivedId !=-1)
            dbHelper.updateRecord(receivedId, queriedPlayer, this);
        else
            Toast.makeText(this, "for some reason id = -1", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(AddOrViewRecordActivity.this, MainActivity.class));
    }

    private void updateImages(){
        switch (color){
            case 1:
                imgMan.setImageResource(R.drawable.man_blood);
                imgWoman.setImageResource(R.drawable.woman_blood);
                imgNb.setImageResource(R.drawable.nb_blood);
                break;
            case 2:
                imgMan.setImageResource(R.drawable.man_salmon);
                imgWoman.setImageResource(R.drawable.woman_salmon);
                imgNb.setImageResource(R.drawable.nb_salmon);
                break;
            case 3:
                imgMan.setImageResource(R.drawable.man_orange);
                imgWoman.setImageResource(R.drawable.woman_orange);
                imgNb.setImageResource(R.drawable.nb_orange);
                break;
            case 4:
                imgMan.setImageResource(R.drawable.man_gold);
                imgWoman.setImageResource(R.drawable.woman_gold);
                imgNb.setImageResource(R.drawable.nb_gold);
                break;
            case 5:
                imgMan.setImageResource(R.drawable.man_grass);
                imgWoman.setImageResource(R.drawable.woman_grass);
                imgNb.setImageResource(R.drawable.nb_grass);
                break;
            case 6:
                imgMan.setImageResource(R.drawable.man_lime);
                imgWoman.setImageResource(R.drawable.woman_lime);
                imgNb.setImageResource(R.drawable.nb_lime);
                break;
            case 7:
                imgMan.setImageResource(R.drawable.man_turquoise);
                imgWoman.setImageResource(R.drawable.woman_turquois);
                imgNb.setImageResource(R.drawable.nb_turquoise);
                break;
            case 8:
                imgMan.setImageResource(R.drawable.man_marine);
                imgWoman.setImageResource(R.drawable.woman_marine);
                imgNb.setImageResource(R.drawable.nb_marine);
                break;
            case 9:
                imgMan.setImageResource(R.drawable.man_purple);
                imgWoman.setImageResource(R.drawable.woman_purple);
                imgNb.setImageResource(R.drawable.nb_purple);
                break;
            case 10:
                imgMan.setImageResource(R.drawable.man_pink);
                imgWoman.setImageResource(R.drawable.woman_pink);
                imgNb.setImageResource(R.drawable.nb_pink);
                break;
            default:
                imgMan.setImageResource(R.drawable.man_gray);
                imgWoman.setImageResource(R.drawable.woman_gray);
                imgNb.setImageResource(R.drawable.nb_gray);
            }
        }
    }

