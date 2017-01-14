package database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.bignerdranch.android.criminalintent.Crime;

import java.text.DateFormat;
import java.util.Date;
import java.util.UUID;

import database.CrimeDbSchema.CrimeTable;

/**
 * Created by ghazi on 4/8/2016.
 */
public class CrimeCursorWrapper extends CursorWrapper {
    public CrimeCursorWrapper(Cursor cursor){
        super(cursor);
    }

    public Crime getCrime(){
        String title = getString(getColumnIndex(CrimeTable.cols.TITLE));
        String uuidString = getString(getColumnIndex(CrimeTable.cols.UUID));
        long date = getLong(getColumnIndex(CrimeTable.cols.DATE));
        int solved = getInt(getColumnIndex(CrimeTable.cols.SOLVED));
        String suspect = getString(getColumnIndex(CrimeTable.cols.SUSPECT));
        String number = getString(getColumnIndex(CrimeTable.cols.NUMBER));

        Crime crime = new Crime(UUID.fromString(uuidString));
        crime.setTitle(title);
        crime.setDate(new Date(date));
        crime.setSolved(solved != 0);
        crime.setSuspect(suspect);
        crime.setSuspectPhoneNumber(number);

        return crime;
    }
}
