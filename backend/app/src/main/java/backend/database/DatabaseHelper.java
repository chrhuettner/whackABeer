package backend.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DatabaseHelper extends SQLiteOpenHelper{

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
    public void onUpdate(SQLiteDatabase db, int i, int i1){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    void setHighscore(int id, String name, int score){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_USER_ID, id);
        cv.put(COLUMN_USER_NAME, name);
        cv.put(COLUMN_SCORE, score);

        long result = db.insert(TABLE_NAME, null, cv);

        if(resault == -1){
            Toast.makeText(context, "Failed to udpate the Highscore", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(context, name + " made a new Highscore", Toast.LENGTH_SHORT).show();
        }
    }
}