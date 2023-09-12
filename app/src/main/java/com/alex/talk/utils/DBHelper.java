package com.alex.talk.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.alex.talk.model.Player;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "people.db";
    private static final int DATABASE_VERSION = 3;
    public static final String TABLE_NAME = "People";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String GENDER = "gender";
    public static final String COLOR = "color";
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" CREATE TABLE " + TABLE_NAME + " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NAME + " TEXT NOT NULL, " +
                GENDER + " INTEGER NOT NULL, " +
                COLOR + " INTEGER NOT NULL);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    /**create record**/
    public void saveNewPlayer(Player player, Context context) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, player.getName());
        values.put(GENDER, player.getGender());
        values.put(COLOR, player.getColor());
        db.insert(TABLE_NAME,null, values);
        db.close();
        Toast.makeText(context, "Spieler hinzugefügt", Toast.LENGTH_SHORT).show();
    }

    /**
     * @return List of all Players in Database
     */
    public List<Player> getPlayerList() {
        String query = "SELECT  * FROM " + TABLE_NAME;
        List<Player> playerArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Player player;

        if (cursor.moveToFirst()) {
            do {
                player = new Player(cursor.getLong(
                        cursor.getColumnIndexOrThrow(ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(NAME)),
                        (byte)cursor.getInt(cursor.getColumnIndexOrThrow(GENDER)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLOR))
                );
                playerArrayList.add(player);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return playerArrayList;
    }

    /**Query only 1 record**/
    public Player getPlayer(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT  * FROM " + TABLE_NAME + " WHERE id="+ id;
        Cursor cursor = db.rawQuery(query, null);

        Player player = null;
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            player = new Player(cursor.getLong(
                    cursor.getColumnIndexOrThrow(ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(NAME)),
                    (byte)cursor.getInt(cursor.getColumnIndexOrThrow(GENDER)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLOR))
            );
        }
        return player;
    }

    /**delete record**/
    public void deleteRecord(long id, Context context) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_NAME+" WHERE id='"+id+"'");
        Toast.makeText(context, "Spieler gelöscht", Toast.LENGTH_SHORT).show();
    }

    /**update record**/
    public void updateRecord(long personId, Player updatedPlayer, Context context) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE "+TABLE_NAME+""+
                " SET name ='"+ updatedPlayer.getName() + "'," +
                " gender ='" + updatedPlayer.getGender() + "'," +
                " color ='" + updatedPlayer.getColor() + "'" +
                " WHERE id='" + personId + "'");
        db.close();
        Toast.makeText(context, "Spieler aktualisiert", Toast.LENGTH_SHORT).show();
    }
}
