package com.alex.talk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alex.talk.utils.DBHelper;
import com.alex.talk.utils.FileUtils;
import com.alex.talk.utils.PlayerAdapter;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private DBHelper dbHelper;
    private PlayerAdapter adapter;
    private Button btn_start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        btn_start = findViewById(R.id.btnStart);

        populateRecyclerView();
        copyQuestions();

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ChooseModeActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_add:
                Intent intent0 = new Intent(MainActivity.this, AddOrViewRecordActivity.class);
                intent0.putExtra("addRecord", true);
                startActivity(intent0);
                break;
            case R.id.add_question:
                Toast.makeText(this, "Option noch nicht verfügbar", Toast.LENGTH_SHORT).show();
                break;
            case R.id.hilfe:
                Toast.makeText(this, "Option noch nicht verfügbar", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void populateRecyclerView() {
        dbHelper = new DBHelper(this);
        adapter = new PlayerAdapter(dbHelper.getPlayerList(), this);
        recyclerView.setAdapter(adapter);
    }

    /**
     * Copies questions from assets to external storage, if missing
     */
    public void copyQuestions() {
        String[] fileNames = {
                "fragenLiebe.in",
                "fragenGefuehle.in",
                "fragenLeben.in",
                "fragenPhilo.in",
                "fragenPolitik.in"
        };

        Context context = getApplicationContext();
        for(String fileName : fileNames){
            String path = context.getExternalFilesDir(null).getPath() + File.separator + fileName;
            if (!new File(path).exists()) {
                try {
                    FileUtils.CopyFromAssetsToStorage(context, fileName, path);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}