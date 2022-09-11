package it.ralucamb.laboratoriomobile.italiancities.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import it.ralucamb.laboratoriomobile.italiancities.models.City;
import it.ralucamb.laboratoriomobile.italiancities.models.GeoLocation;

public class CityTable {
    public static final String TABLE_NAME = "cities";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_FOREIGN_NAME = "foreign_name";
    public static final String COLUMN_CADASTRAL_CODE = "cadastral_code";
    public static final String COLUMN_CAP = "cap";
    public static final String COLUMN_PREFIX = "prefix";
    public static final String COLUMN_PROVINCE = "province";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PEC = "pec";
    public static final String COLUMN_PHONE_NUMBER = "phone_number";
    public static final String COLUMN_FAX = "fax";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";

    public static void create(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT NOT NULL, "
                + COLUMN_FOREIGN_NAME + " TEXT, "
                + COLUMN_CADASTRAL_CODE + " TEXT, "
                + COLUMN_CAP + " TEXT, "
                + COLUMN_PREFIX + " TEXT, "
                + COLUMN_PROVINCE + " TEXT, "
                + COLUMN_EMAIL + " TEXT, "
                + COLUMN_PEC + " TEXT, "
                + COLUMN_PHONE_NUMBER + " TEXT, "
                + COLUMN_FAX + " TEXT, "
                + COLUMN_LATITUDE + " REAL, "
                + COLUMN_LONGITUDE +" REAL);";
        db.execSQL(sql);
    }

    public static void upgrade_1_2(SQLiteDatabase db) {

    }

    public static void insert(SQLiteDatabase db, City city) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, city.getName());
        values.put(COLUMN_FOREIGN_NAME, city.getForeingName());
        values.put(COLUMN_CADASTRAL_CODE, city.getCadastralCode());
        values.put(COLUMN_CAP, city.getCap());
        values.put(COLUMN_PREFIX, city.getPrefix());
        values.put(COLUMN_PROVINCE, city.getProvince());
        values.put(COLUMN_EMAIL, city.getEmail());
        values.put(COLUMN_PEC, city.getPec());
        values.put(COLUMN_PHONE_NUMBER, city.getPhoneNumber());
        values.put(COLUMN_FAX, city.getFax());
        GeoLocation location = city.getLocation();
        if (location != null) {
            values.put(COLUMN_LATITUDE, location.getLatitude());
            values.put(COLUMN_LONGITUDE, location.getLongitude());
        }
        long id = db.insert(TABLE_NAME, null, values);
        if (id != -1) {
            city.setId(id);
        }
    }

    public static List<City> findAll(SQLiteDatabase db) {
        String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + COLUMN_NAME + " ASC;";
        Cursor cursor = db.rawQuery(sql, null);
        List<City> cities = new ArrayList<>();
        while(cursor.moveToNext()){
            City city= new City();
            city.setId(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID)));
            city.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)));
            city.setForeingName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FOREIGN_NAME)));
            city.setCadastralCode(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CADASTRAL_CODE)));
            city.setCap(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CAP)));
            city.setPrefix(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PREFIX)));
            city.setProvince(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PROVINCE)));
            city.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL)));
            city.setPec(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PEC)));
            city.setPhoneNumber(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE_NUMBER)));
            city.setFax(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FAX)));
            GeoLocation location = new GeoLocation();
            location.setLatitude(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_LATITUDE)));
            location.setLongitude(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_LONGITUDE)));
            city.setLocation(location);
            cities.add(city);
        }
        cursor.close();
        return cities;
    }
}
