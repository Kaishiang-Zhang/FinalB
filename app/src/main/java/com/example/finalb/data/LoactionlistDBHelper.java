package com.example.finalb.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LoactionlistDBHelper extends SQLiteOpenHelper {
    private  static  LoactionlistDBHelper mInstance = null;
    private static final String DATABASE_NAME = "locationlist.db";
    private  static final  int DATABASE_VERSION = 1;

    private  static LoactionlistDBHelper getInstance(Context ctx){
        if(mInstance == null){
            mInstance = new LoactionlistDBHelper(ctx.getApplicationContext());
        }
        return mInstance;
    }
    public LoactionlistDBHelper(Context context) {
        super(context,DATABASE_NAME ,null,DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_LOCATION_TABLE = "CREATE TABLE " + LocationclistContract.LocationclistEntry.TABLE_NAME+" ("+
                LocationclistContract.LocationclistEntry.LOCATION_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT,"+
                LocationclistContract.LocationclistEntry.LOCATION_LONGITUDE+" FLOAT NOT NULL, "+
                LocationclistContract.LocationclistEntry.LOCATION_LATITUDE+" FLOAT NOT NULL, "+
                LocationclistContract.LocationclistEntry.LOCATION_NAME+" TEXT NOT NULL "+
                "); ";
        db.execSQL(SQL_LOCATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+LocationclistContract.LocationclistEntry.TABLE_NAME);
        onCreate(db);
    }

}
