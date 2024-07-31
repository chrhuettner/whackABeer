package backend.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper{

    private Context context;
    private static final String DATABASE_NAME ="whack_a_beer.db";
    private static final int DATABASE_VERSION =1;

    private static final String TABLE_NAME ="highscore";
    private static final String COLUMN_USER_ID ="id";
    private static final String COLUMN_USER_NAME ="name";
    private static final String COLUMN_SCORE ="score";

    public DatabaseHelper(@Nullable Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String query = "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_USER_ID + " INTEGER PRIMARY KEY, " + COLUMN_USER_NAME + " TEXT, " + COLUMN_SCORE + " INTEGER);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void setHighscore(int id, String name, int score){
        int points = getCurrentHighscore();

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_USER_ID, id);
        cv.put(COLUMN_USER_NAME, name);
        cv.put(COLUMN_SCORE, score);

        if(points < score) {
            long result = db.insert(TABLE_NAME, null, cv);

            if (result == -1) {
                Toast.makeText(context, "Fehler beim Aktualisieren des Highscores", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, name + " hat den Highscore geknackt", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public int getCurrentHighscore(){
        SQLiteDatabase db = this.getReadableDatabase();

        String readQuery = "SELECT " + COLUMN_SCORE + " FROM " + TABLE_NAME + " ORDER BY " + COLUMN_SCORE + " DESC LIMIT 1";
        Cursor cursor = db.rawQuery(readQuery, null);

        int highscore = 0;
        if (cursor.moveToFirst()) {
            highscore = cursor.getInt(0);
        }

        cursor.close();
        return highscore;
    }
}