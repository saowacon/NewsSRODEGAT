package egat.tick.newssrodegat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by EGAT-USER on 8/27/2015.
 */
public class UserTABLE {
    // Explicit
    private MyOpenHelper objMyOpenHelper;
    private SQLiteDatabase writeSqLiteDatabase, readSqLiteDatabase; // W & R DB

    // สร้างตัวแปรที่เป็น Static และไม่สามารถแก้ไขได้ด้วย Method ใดๆ (Final) กำหนดชื่อตารางให้ตัวแปรเหมือนกับใน MyOpenHelper
    public static final String USER_TABLE = "userTABLE";
    public static final String COLUMN_ID_USER = "_id";
    public static final String COLUMN_USER = "User";
    public static final String COLUMN_PASSWORD = "Password";
    public static final String COLUMN_NAME = "Name";


    public UserTABLE(Context context) {
        objMyOpenHelper = new MyOpenHelper(context); // ต่อท่อ Data
        writeSqLiteDatabase = objMyOpenHelper.getWritableDatabase();
        readSqLiteDatabase = objMyOpenHelper.getReadableDatabase();
    } // Constructor คือ Method ที่มีชื่อเดียวกับ Class

    public String[] searchUser(String strUser) { // โยนค่า Array แต่ละ Record 4 ค่า (Column) ในตารางกลับ
        try {
            String strResult[] = null;
            // สร้าง พื้นที่ RAM ในการทำ Operation อ่าน Table ใน DB แล้ว Return กลับไปแสดงผลที่โปรแกรม
            Cursor objCursor = readSqLiteDatabase.query(USER_TABLE,
                    new String[]{COLUMN_ID_USER, COLUMN_USER, COLUMN_PASSWORD, COLUMN_NAME},
                    COLUMN_USER + "=?",
                    new String[]{String.valueOf(strUser)},
                    null, null, null, null);
            if (objCursor != null) { // ถ้าค่าในตารางไม่ว่างเปล่า
                if (objCursor.moveToFirst()) {
                    strResult = new String[4];
                    strResult[0] = objCursor.getString(0);
                    strResult[1] = objCursor.getString(1);
                    strResult[2] = objCursor.getString(2);
                    strResult[3] = objCursor.getString(3);
                } // if
            } // if
            objCursor.close();
            return strResult;

        } catch (Exception e) {
            return null;
        }

        // return new String[0];
    } // searchUser


    public long addNewUser(String strUser, String strPassword, String strName) {
        ContentValues objContentValues = new ContentValues();
        objContentValues.put(COLUMN_USER, strUser);
        objContentValues.put(COLUMN_PASSWORD, strPassword);
        objContentValues.put(COLUMN_NAME, strName);
        return writeSqLiteDatabase.insert(USER_TABLE, null, objContentValues);
    } // Add New User

} // Main Class
