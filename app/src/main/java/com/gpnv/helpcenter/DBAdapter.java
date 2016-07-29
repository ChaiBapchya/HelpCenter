package com.gpnv.helpcenter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;

public class DBAdapter {
 public static String lunchTime;
 public static String dinnerTime;
    public static String storeNumber="8097502399";
 public static String medicine_name;
    DBHelper dbHelper;
    public DBAdapter(Context context){
    dbHelper= new DBHelper(context);

    }
    public long insertMedicines(String medicineName, String dosageType, int dosageUnits, String dosageTime, String medicineDuration, int stock, String storeName, String storeNumber){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(DBHelper.Col_Medicine_medicineName, medicineName);
        contentValues.put(DBHelper.Col_Medicine_dosageType, dosageType);
        contentValues.put(DBHelper.Col_Medicine_dosageUnits, dosageUnits);
        contentValues.put(DBHelper.Col_Medicine_dosageTime, dosageTime);
        contentValues.put(DBHelper.Col_Medicine_medicineDuration, medicineDuration);
        contentValues.put(DBHelper.Col_Medicine_stock, stock);
        contentValues.put(DBHelper.Col_Medicine_storeName, storeName);
        contentValues.put(DBHelper.Col_Medicine_storeNumber, storeNumber);
        long id= db.insert(DBHelper.MEDICINE_TABLE_NAME,null,contentValues);
        this.storeNumber=storeNumber;
        medicine_name=medicineName;
        return id;

    }

    public long insertUser(String userName, String userEmail, String userAge, String lunchTime, String dinnerTime){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(DBHelper.Col_User_name, userName);
        contentValues.put(DBHelper.Col_User_email, userEmail);
        contentValues.put(DBHelper.Col_User_age, userAge);
        contentValues.put(DBHelper.Col_User_ideal_LunchTime, lunchTime);
        contentValues.put(DBHelper.Col_User_ideal_dinnerTime, dinnerTime);
        long id= db.insert(DBHelper.USER_TABLE_NAME,null,contentValues);
        this.lunchTime=lunchTime;
        this.dinnerTime=dinnerTime;
        return id;

    }
    public String getMedicines()
    {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        String[] columns={DBHelper.Col_Medicine_medicineName,DBHelper.Col_Medicine_dosageType,DBHelper.Col_Medicine_dosageUnits,DBHelper.Col_Medicine_dosageTime,DBHelper.Col_Medicine_medicineDuration,DBHelper.Col_Medicine_stock,DBHelper.Col_Medicine_storeName,DBHelper.Col_Medicine_storeNumber};
        Cursor cursor=db.query(DBHelper.MEDICINE_TABLE_NAME, columns, null, null, null, null, null);
        StringBuffer buffer=new StringBuffer();
        while(cursor.moveToNext())
        {
            int index2 = cursor.getColumnIndex(DBHelper.Col_Medicine_medicineName);
            int index3 = cursor.getColumnIndex(DBHelper.Col_Medicine_dosageType);
            int index4 = cursor.getColumnIndex(DBHelper.Col_Medicine_dosageUnits);
            int index5 = cursor.getColumnIndex(DBHelper.Col_Medicine_dosageTime);
            int index6 = cursor.getColumnIndex(DBHelper.Col_Medicine_medicineDuration);
            int index7 = cursor.getColumnIndex(DBHelper.Col_Medicine_stock);
int index8 = cursor.getColumnIndex(DBHelper.Col_Medicine_storeNumber);

            String name = cursor.getString(index2);
            String type = cursor.getString(index3);
            String units = cursor.getString(index4);
            String time = cursor.getString(index5);
            String duration = cursor.getString(index6);
            String stock = cursor.getString(index7);
            String number = cursor.getString(index8);
            buffer.append(name+" "+type+" "+units+" "+time+" "+duration+" "+stock+"  "+number+"\n");
        }
        return buffer.toString();
    }


    public boolean updateMedicineStock(String medicineName,int stock,int dosageUnits)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("stock", stock-dosageUnits);
        db.update("Meals",contentValues,"medicineName="+medicineName,null);
        return true;

    }
    public Cursor getMedicineTime()
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] columns = {DBHelper.Col_Medicine_dosageTime};
        Cursor cursor = db.query(DBHelper.MEDICINE_TABLE_NAME, columns, null, null, null, null, null);
        return cursor;

    }

    public String getLunchTime()
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] columns = {DBHelper.Col_User_ideal_LunchTime};
        Cursor cursor = db.query(DBHelper.USER_TABLE_NAME, columns, null, null, null, null, null);
        String string="";
        while (cursor.moveToNext()) {
            int col = cursor.getColumnIndex(DBHelper.Col_User_ideal_LunchTime);
            Log.i("inc", col + "");
            string = cursor.getString(col);
        }
        return string;

    }

    public Cursor getDinnerTime()
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] columns = {DBHelper.Col_User_ideal_dinnerTime};
        Cursor cursor = db.query(DBHelper.USER_TABLE_NAME, columns, null, null, null, null, null);
        return cursor;

    }

    public Cursor getMedicinesCursor()
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] columns = {DBHelper.Col_Medicine_medicineName, DBHelper.Col_Medicine_dosageType, DBHelper.Col_Medicine_dosageUnits, DBHelper.Col_Medicine_dosageTime, DBHelper.Col_Medicine_medicineDuration, DBHelper.Col_Medicine_stock, DBHelper.Col_Medicine_storeName, DBHelper.Col_Medicine_storeNumber};
        Cursor cursor = db.query(DBHelper.MEDICINE_TABLE_NAME, columns, null, null, null, null, null);
    return cursor;
    }

    public boolean insertLunchTime(String time,String date)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("lunchTime", time);
        contentValues.put("dinnerTime", 0);
        contentValues.put("date", date);
        db.insert("Meals", null, contentValues);
        return true;
    }

    public boolean insertDinnerTime(String time,String date)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("lunchTime", time);
        contentValues.put("dinnerTime", 0);
        contentValues.put("date", date);
        db.update("Meals",contentValues,"date="+date,null);
        return true;
    }

    public String getUser()
    {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        String[] columns={DBHelper.Col_User_name,DBHelper.Col_User_email,DBHelper.Col_User_age,DBHelper.Col_User_ideal_LunchTime,DBHelper.Col_User_ideal_dinnerTime};
        Cursor cursor=db.query(DBHelper.USER_TABLE_NAME, columns, null, null, null, null, null);
        StringBuffer buffer=new StringBuffer();
        while(cursor.moveToNext())
        {
            int index2 = cursor.getColumnIndex(DBHelper.Col_User_name);
            int index3 = cursor.getColumnIndex(DBHelper.Col_User_email);
            int index4 = cursor.getColumnIndex(DBHelper.Col_User_age);
            int index5 = cursor.getColumnIndex(DBHelper.Col_User_ideal_LunchTime);
            int index6 = cursor.getColumnIndex(DBHelper.Col_User_ideal_dinnerTime);


            String name = cursor.getString(index2);
            String email = cursor.getString(index3);
            String age = cursor.getString(index4);
            String lunchTime = cursor.getString(index5);
            String dinnerTime = cursor.getString(index6);

            buffer.append(name+" "+email+" "+age+" "+lunchTime+" "+dinnerTime+"\n");
        }
        return buffer.toString();
    }


    static class DBHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "MyDBName.db";

        private static final String MEDICINE_TABLE_NAME = "Medicines";
        private static final String USER_TABLE_NAME = "User";
        private static final String MEAL_TABLE_NAME = "Meals";

       // private static final String UID="_id";

        private static final int DATABASE_VERSION=6;

        //private static final String CONTACTS_COLUMN_ID = "id";

        private static final String Col_User_name = "name";
        private static final String Col_User_email="email";
        private static final String Col_User_age="age";
        private static final String Col_User_ideal_LunchTime="ideal_lunchTime";
        private static final String Col_User_ideal_dinnerTime="ideal_dinnerTime";

        private static final String Col_Medicine_medicineName="medicineName";
        private static final String Col_Medicine_dosageType="dosageType";
        private static final String Col_Medicine_dosageUnits="dosageUnits";
        private static final String Col_Medicine_dosageTime="dosageTime";
        private static final String Col_Medicine_medicineDuration="medicineDuration";
        private static final String Col_Medicine_stock="stock";
        private static final String Col_Medicine_storeName="storeName";
        private static final String Col_Medicine_storeNumber="storeNumber";

        private static final String Col_Meal_lunchTime="lunchTime";
        private static final String Col_Meal_dinnerTime="dinnerTime";
        private static final String Col_Meal_date="date";

        private static final String create_meals="CREATE TABLE " + MEAL_TABLE_NAME + "(" +

                Col_Meal_lunchTime + " VARCHAR(255)," +

                Col_Meal_dinnerTime+ " VARCHAR(255) ," +
                Col_Meal_date + " VARCHAR(255));";


        private static final String DROP_TABLE=" DROP TABLE IF EXISTS "+MEDICINE_TABLE_NAME;

        private static final String create_medicines="CREATE TABLE " + MEDICINE_TABLE_NAME + "(" +
            Col_Medicine_medicineName + " VARCHAR(255)," +
            Col_Medicine_dosageType + " VARCHAR(255)," +
            Col_Medicine_dosageUnits + " INTEGER ," +
            Col_Medicine_dosageTime + " VARCHAR(255)," +
            Col_Medicine_medicineDuration + " VARCHAR(255)," +
            Col_Medicine_stock + " INTEGER ," +
            Col_Medicine_storeName+ " VARCHAR(255) ," +
            Col_Medicine_storeNumber + " VARCHAR(255));";

        private static  final String create_user="CREATE TABLE " + USER_TABLE_NAME + "(" +
                Col_User_name + " VARCHAR(255)," +
                Col_User_email + " VARCHAR(255)," +
                Col_User_age + " VARCHAR(255)," +
                Col_User_ideal_LunchTime + " VARCHAR(255)," +
                Col_User_ideal_dinnerTime + " VARCHAR(255));";


        private HashMap hp;
        private Context context;
    //SQLiteDatabase sqliteDatabase;
public DBHelper(Context context)
    {

        super(context, DATABASE_NAME , null, DATABASE_VERSION);
        this.context=context;
        Toast.makeText(context, "constructor called", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        try {
            db.execSQL(create_medicines);
            db.execSQL(create_user);
            db.execSQL(create_meals);
            Toast.makeText(context, "table created", Toast.LENGTH_LONG).show();
            Toast.makeText(context, "onCreate called", Toast.LENGTH_LONG).show();
        }catch (Exception e) {
            Toast.makeText(context, "e", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL(DROP_TABLE);
        onCreate(db);
        Toast.makeText(context, "onUpgrade called", Toast.LENGTH_SHORT).show();
    }
/*
    public boolean insertContact  (String name, String phone, String email, String street,String place)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("street", street);
        contentValues.put("place", place);
        db.insert("contacts", null, contentValues);
        return true;
    }

    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from contacts where id="+id+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
        return numRows;
    }

    public boolean updateContact (Integer id, String name, String phone, String email, String street,String place)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("street", street);
        contentValues.put("place", place);
        db.update("contacts", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteContact (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("contacts",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public ArrayList<String> getAllCotacts()
    {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from contacts", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME)));
            res.moveToNext();
        }
        return array_list;
    }
   */
}
}