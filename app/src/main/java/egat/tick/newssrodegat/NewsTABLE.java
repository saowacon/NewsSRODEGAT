package egat.tick.newssrodegat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by EGAT-USER on 8/27/2015.
 */
public class NewsTABLE {
    // Explicit
    private MyOpenHelper objMyOpenHelper;
    private SQLiteDatabase writeSqLiteDatabase ,readSQLiteDatabase;

    // สร้างตัวแปรที่เป็น Static และไม่สามารถแก้ไขได้ด้วย Method ใดๆ (Final) กำหนดชื่อตารางให้ตัวแปรเหมือนกับใน MyOpenHelper
    public static final String NEWS_TABLE = "newsTABLE";
    public static final String COLUMN_ID_NEWS = "_id";
    public static final String COLUMN_DATE = "Date";
    public static final String COLUMN_HEAD = "Head";
    public static final String COLUMN_DETAIL = "Detail";
    public static final String COLUMN_IMAGE = "Image";
    public static final String COLUMN_OWNER = "Owner";

    public NewsTABLE(Context context) {
        objMyOpenHelper = new MyOpenHelper(context); // ต่อท่อ Data
        writeSqLiteDatabase = objMyOpenHelper.getWritableDatabase();
        readSQLiteDatabase = objMyOpenHelper.getReadableDatabase();
    } // Constructor

    public String[] readAllDate() {
        String strDate[] = null;
        Cursor objCursor = readSQLiteDatabase.query(NEWS_TABLE,
                new String[]{COLUMN_ID_NEWS, COLUMN_DATE},
                null, null, null, null, null);
        if (objCursor != null) {
            objCursor.moveToFirst();
            strDate = new String[objCursor.getCount()]; //จองหน่วยความจำ
            for (int i = 0; i < objCursor.getCount(); i++) { // เก็บค่าวันที่
                strDate[i] = objCursor.getString(objCursor.getColumnIndex(COLUMN_DATE));
                objCursor.moveToNext();
            } // for
        } // if
        objCursor.close();

        return strDate;
    } // readAllDate

    public String[] readAllHead() {
        String strDate[] = null;
        Cursor objCursor = readSQLiteDatabase.query(NEWS_TABLE,
                new String[]{COLUMN_ID_NEWS, COLUMN_HEAD},
                null, null, null, null, null);
        if (objCursor != null) {
            objCursor.moveToFirst();
            strDate = new String[objCursor.getCount()]; //จองหน่วยความจำ
            for (int i = 0; i < objCursor.getCount(); i++) { // เก็บค่าหัวข้อข่าว
                strDate[i] = objCursor.getString(objCursor.getColumnIndex(COLUMN_HEAD));
                objCursor.moveToNext();
            } // for
        } // if
        objCursor.close();

        return strDate;
    } // readAllHead

    public String[] readAllDetail() {
        String strDate[] = null;
        Cursor objCursor = readSQLiteDatabase.query(NEWS_TABLE,
                new String[]{COLUMN_ID_NEWS, COLUMN_DETAIL},
                null, null, null, null, null);
        if (objCursor != null) {
            objCursor.moveToFirst();
            strDate = new String[objCursor.getCount()]; //จองหน่วยความจำ
            for (int i = 0; i < objCursor.getCount(); i++) { // เก็บค่าเนื้อหาข่าว
                strDate[i] = objCursor.getString(objCursor.getColumnIndex(COLUMN_DETAIL));
                objCursor.moveToNext();
            } // for
        } // if
        objCursor.close();

        return strDate;
    } // readAllDetail

    public String[] readAllImage() {
        String strDate[] = null;
        Cursor objCursor = readSQLiteDatabase.query(NEWS_TABLE,
                new String[]{COLUMN_ID_NEWS, COLUMN_IMAGE},
                null, null, null, null, null);
        if (objCursor != null) {
            objCursor.moveToFirst();
            strDate = new String[objCursor.getCount()]; //จองหน่วยความจำ
            for (int i = 0; i < objCursor.getCount(); i++) { // เก็บค่าวันที่
                strDate[i] = objCursor.getString(objCursor.getColumnIndex(COLUMN_IMAGE));
                objCursor.moveToNext();
            } // for
        } // if
        objCursor.close();

        return strDate;
    } // readAllImage

    public String[] readAllOwner() {
        String strDate[] = null;
        Cursor objCursor = readSQLiteDatabase.query(NEWS_TABLE,
                new String[]{COLUMN_ID_NEWS, COLUMN_OWNER},
                null, null, null, null, null);
        if (objCursor != null) {
            objCursor.moveToFirst();
            strDate = new String[objCursor.getCount()]; //จองหน่วยความจำ
            for (int i = 0; i < objCursor.getCount(); i++) { // เก็บค่าวันที่
                strDate[i] = objCursor.getString(objCursor.getColumnIndex(COLUMN_OWNER));
                objCursor.moveToNext();
            } // for
        } // if
        objCursor.close();

        return strDate;
    } // readAllOwner

    public long addNews(String strDate, String strHead, String strDetail, String strImage, String strOwner) {
        ContentValues objContentValues = new ContentValues();
        objContentValues.put(COLUMN_DATE, strDate);
        objContentValues.put(COLUMN_HEAD, strHead);
        objContentValues.put(COLUMN_DETAIL, strDetail);
        objContentValues.put(COLUMN_IMAGE, strImage);
        objContentValues.put(COLUMN_OWNER, strOwner);
        return writeSqLiteDatabase.insert(NEWS_TABLE, null, objContentValues);
    } // Add New News

} // Main Class
