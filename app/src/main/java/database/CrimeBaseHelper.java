package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bignerdranch.android.criminalintent.Crime;

import database.CrimeDbSchema.CrimeTable;

/**
 * Created by ghazi on 4/7/2016.
 */
public class CrimeBaseHelper extends SQLiteOpenHelper{
    private static final int VERSION = 1;
    private static final String DATABASE_CRIME = "CrimeDb.db";

    public CrimeBaseHelper(Context context){
        super(context,DATABASE_CRIME,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + CrimeTable.NAME + "("
                + " _id integer primary key autoincrement, " +
                CrimeTable.cols.TITLE + ", " +
                CrimeTable.cols.UUID + ", " +
                CrimeTable.cols.DATE + ", " +
                CrimeTable.cols.SOLVED + ", " +
                CrimeTable.cols.SUSPECT + ", " +
                CrimeTable.cols.NUMBER +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
