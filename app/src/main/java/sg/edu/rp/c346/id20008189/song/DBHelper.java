package sg.edu.rp.c346.id20008189.song;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "simplenotes.db";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_SONG = "song";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_SINGERS = "Singers";
    private static final String COLUMN_YEAR = "year";
    private static final String COLUMN_STARS = "stars";
    private static final String COLUMN_SONG_CONTENT = "song_content";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createNoteTableSql = "CREATE TABLE " + TABLE_SONG + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                +COLUMN_TITLE + "TEXT,"
                +COLUMN_SINGERS+ "TEXT,"
                +COLUMN_YEAR+ "INTEGER,"
                +COLUMN_STARS+ "INTEGER)";
        db.execSQL(createNoteTableSql);
        Log.i("info", "created tables");

        //Dummy records, to be inserted when the database is created
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        // db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTE);
        db.execSQL("ALTER TABLE " + TABLE_SONG);
        onCreate(db);
    }
    public long insertSong(String SongContent) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        String selectQuery = "SELECT"+COLUMN_ID+","+
                COLUMN_TITLE+","+COLUMN_YEAR+","+
                COLUMN_STARS+"FROM"+TABLE_SONG;
        values.put(COLUMN_SONG_CONTENT, selectQuery);
        long result = db.insert(TABLE_SONG, null, values);
        db.close();
        Log.d("SQL Insert","Song:"+ result); //id returned, shouldn’t be -1
        return result;
    }
    public ArrayList<Song> getAllSongs(String keyword) {
        ArrayList<Song> songsList = new ArrayList<Song>();

        String selectQuery = "SELECT id"+COLUMN_ID+","+
                COLUMN_TITLE+","+COLUMN_YEAR+","+
                COLUMN_STARS+"FROM"+TABLE_SONG;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String singers= cursor.getString(2);
                int year = cursor.getInt(3);
                int stars = cursor.getInt(4);
                Song newSong= new Song(id,title,singers,year,stars);
                songsList.add(newSong);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return songsList;
    }
    public int updateSong(Song data){
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT"+COLUMN_ID+","+
                COLUMN_TITLE+","+COLUMN_YEAR+","+
                COLUMN_STARS+"FROM"+TABLE_SONG;
        ContentValues values = new ContentValues();
        values.put(selectQuery, data.getSongContent());
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(data.getId())};
        int result = db.update(TABLE_SONG, values, condition, args);
        db.close();
        return result;
    }
    public int deleteNote(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_SONG, condition, args);
        db.close();
        return result;
    }
    public ArrayList<Song> getAllSongs() {
        ArrayList<Song> songsList = new ArrayList<Song>();
        String selectQuery = "SELECT "+COLUMN_ID+","+
                COLUMN_TITLE+","+COLUMN_YEAR+","+
                COLUMN_STARS+"FROM"+TABLE_SONG;
       SQLiteDatabase db = this.getReadableDatabase();
       Cursor cursor =db.rawQuery(selectQuery,null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String singers= cursor.getString(2);
                int year = cursor.getInt(3);
                int stars = cursor.getInt(4);
                Song newSong= new Song(id,title,singers,year,stars);

                songsList.add(newSong);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return songsList;
    }
public ArrayList<Song> getAllSongsByStars(int starsFilter){
    ArrayList<Song> songsList = new ArrayList<Song>();

    SQLiteDatabase db = this.getReadableDatabase();
    String[] columns= {COLUMN_ID, COLUMN_TITLE,COLUMN_SINGERS,COLUMN_YEAR,COLUMN_STARS};
    String condition = COLUMN_STARS + ">=?";
    String[] args = {String.valueOf(starsFilter)};
    Cursor cursor;
    cursor=db.query(TABLE_SONG,columns,condition,args,null,null,null);
    if (cursor.moveToFirst()) {
        do {
            int id = cursor.getInt(0);
            String title = cursor.getString(1);
            String singers= cursor.getString(2);
            int year = cursor.getInt(3);
            int stars = cursor.getInt(4);
            Song song = new Song(id,title,singers,year,stars);
            songsList.add(song);
        } while (cursor.moveToNext());
    }
    cursor.close();
    db.close();
    return songsList;
}
}





