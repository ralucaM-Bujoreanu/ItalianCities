package it.ralucamb.laboratoriomobile.italiancities.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.List;

import it.ralucamb.laboratoriomobile.italiancities.models.City;

public class Database extends SQLiteOpenHelper {

    private volatile static Database instance = null;

    public synchronized static Database getInstance(Context context) {
        if(instance == null) {
            synchronized (Database.class) {
                instance = new Database(context);
            }
        }
        return instance;
    }

    private Database(@Nullable Context context) {
        super(context, "database.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        CityTable.create(db);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion == 1 && newVersion == 2) {
            CityTable.upgrade_1_2(db);
        }
    }
    public void insert(City city) {
        CityTable.insert(getWritableDatabase(), city);
    }
    public List<City> findAll() {
        return CityTable.findAll(getReadableDatabase());
    }
}
