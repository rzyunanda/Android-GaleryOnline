package pemrogramanmobile.galeryonline;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import pemrogramanmobile.galeryonline.Model.Galery;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "galeryonline";
    public static final String TABLE_NAME = "galeries";
    public static final String TABLE_NAME2 = "favorite";
    public static final String COL_1 = "id";
    public static final String COL_2 = "nama";
    public static final String COL_3 = "lokasi";
    public static final String COL_4 = "gambar";
    public static final String COL_5 = "lat";
    public static final String COL_6 = "lng";
    public static final String COL_7 = "deskripsi";
    public static final String fav = "fav";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + COL_1 +
                "INTEGER PRIMARYKEY," + COL_2 + "TEXT,"+COL_2+"TEXT,"+COL_3+"TEXT,"+COL_4+"TEXT,"+COL_5+"TEXT,"+COL_6+"TEXT,"+COL_7+"TEXT )";

        String CREATE_TABLE2= "CREATE TABLE "+TABLE_NAME2+"("+fav+"TEXT)";
        db.execSQL(CREATE_TABLE);
        db.execSQL(CREATE_TABLE2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
       // db.execSQL("DROP TABLE IF EXISTS"+TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //tambah data
    public void tambah_foto(Galery galery){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_2, galery.getNama());
        values.put(COL_4, galery.getGambar_url());
        values.put(COL_7, galery.getDeskripsi());
        values.put(COL_5, galery.getLat());
        values.put(COL_6, galery.getLng());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public Galery getd(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[] {COL_1,
                        COL_2, COL_4, COL_3, COL_5, COL_7,COL_6 }, COL_1 + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Galery data = new Galery(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5),
                cursor.getString(6),
                cursor.getString(7));

        return data;
    }

    //Favorite
    public void addToFavorite(String fav){
        SQLiteDatabase debe = getReadableDatabase();
        String query = String.format("INSERT INTO favorite(fav) VALUES('%s'); ",fav);
        debe.execSQL(query);
    }
    public void removeFavorite(String fav){
        SQLiteDatabase debe = getReadableDatabase();
        String query = String.format("DELETE FROM favorite WHERE fav='%s'",fav);
        debe.execSQL(query);
    }

    public boolean isFavorite(Galery fav){
        SQLiteDatabase debe = getReadableDatabase();
        String query = String.format("SELECT * FROM favorite WHERE fav='%s'",fav);
        Cursor cursor = debe.rawQuery(query,null);
        if (cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

}
