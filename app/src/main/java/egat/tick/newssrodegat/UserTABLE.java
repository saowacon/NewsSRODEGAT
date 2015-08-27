package egat.tick.newssrodegat;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by EGAT-USER on 8/27/2015.
 */
public class UserTABLE {
    // Explicit
    private MyOpenHelper objMyOpenHelper;
    private SQLiteDatabase writeSqLiteDatabase, readSqLiteDatabase; // W & R DB

    public UserTABLE(Context context) {
        objMyOpenHelper = new MyOpenHelper(context); // ต่อท่อ Data
        writeSqLiteDatabase = objMyOpenHelper.getWritableDatabase();
        readSqLiteDatabase = objMyOpenHelper.getReadableDatabase();

    } // Constructor

} // Main Class
