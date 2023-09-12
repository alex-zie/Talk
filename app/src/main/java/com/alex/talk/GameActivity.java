package com.alex.talk;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alex.talk.model.Player;
import com.alex.talk.model.Question;
import com.alex.talk.utils.DBHelper;
import com.alex.talk.utils.FileUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity {
    /**
     * 0: Liebe
     * 1: Gefuehle
     * 2: Leben
     * 3: Philosophie
     * 4: Politik
     * 5: Mix
     */
    int mode;
    boolean[] topics = new boolean[5];
    boolean randomOrder;
    int nTimes; //number of times a question can occur
    List<Player> players;
    List<Question> questions = new ArrayList<>();
    int currentPlayerId;
    int currentQuestionIndex;

    TextView nameV, questionV;
    Button btnNext, btnOther;
    DBHelper dbHelper;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        nameV = findViewById(R.id.nameView);
        questionV = findViewById(R.id.questionView);
        btnNext = findViewById(R.id.btnNext);
        btnOther = findViewById(R.id.btnOther);

        dbHelper = new DBHelper(this);
        players = dbHelper.getPlayerList();
        prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        randomOrder = prefs.getBoolean("randomOrder", false);
        int option = prefs.getInt("option", 1);
        if(option == 0)
            nTimes = 1;
        else if(option == 2)
            nTimes = players.size();
        else
            nTimes = players.size()/2;

        try {
            mode = getIntent().getIntExtra("mode", -1);
            if(mode == 5) //mix
                topics = getIntent().getBooleanArrayExtra("topicArray");
            else
                topics[mode] = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questions.get(currentQuestionIndex).addPlayer(currentPlayerId);
                if(questions.get(currentQuestionIndex).askedAtLeast(nTimes))
                    questions.remove(currentQuestionIndex);
                if(questions.size()>0)
                    newTurn();
                else
                    noMoreQuestions();
            }
        });

        btnOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questions.get(currentQuestionIndex).addPlayer(currentPlayerId);
                if(questions.get(currentQuestionIndex).askedAtLeast(nTimes))
                    questions.remove(currentQuestionIndex);
                currentQuestionIndex = getNextQuestionIndex();
                questionV.setText(questions.get(currentQuestionIndex).getContent());
            }
        });

        createQuestionList();
        newTurn();
    }

    public void createQuestionList() {
        for(int i=0; i<topics.length; i++){
            if(topics[i]){
                try {
                    questions.addAll(FileUtils.getQuestions(i, this));
                } catch (IOException e){
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private int getNextPlayerId(){
        if(randomOrder)
            return (int) (Math.random() * (players.size()));
        return (currentPlayerId+1)%players.size();
    }

    private int getNextQuestionIndex(){
        int i = (int) (Math.random() * (questions.size()));
        Question question = questions.get(i);

        int counter = 0;
        while(question.alreadyAskedTo(currentPlayerId) && counter<10) {
            i = (int) (Math.random() * (questions.size()));
            question = questions.get(i);
            counter++;
        }
        if(counter==10)
            noMoreQuestionsFor(players.get(currentPlayerId).getName());
        return i;
    }

    private void newTurn(){
        currentPlayerId = getNextPlayerId();
        nameV.setText(players.get(currentPlayerId).getName());
        currentQuestionIndex = getNextQuestionIndex();
        questionV.setText(questions.get(currentQuestionIndex).getContent());
    }




    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Möchtest du das Spiel wirklich verlassen?");
        builder.setPositiveButton("Verlassen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(GameActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
    public void noMoreQuestions() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Glückwunsch!");
        builder.setMessage("Ihr habt alle Fragen durchgespielt.");

        builder.setPositiveButton("Spiel Beenden", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(GameActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Fragen Resetten", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                questions.clear();
                createQuestionList();
                players = dbHelper.getPlayerList();
                newTurn();
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    public void noMoreQuestionsFor(String name) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(""+name+",");
        builder.setMessage("es gibt keine Fragen mehr für dich.");

        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                players.remove(currentPlayerId);
                if(players.size()==0)
                    noMoreQuestions();
                else
                    newTurn();
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

}
