package com.project.telephonedirectory;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class PersonsDao {

    public ArrayList<Persons> allPersons(Database db) {

        ArrayList<Persons> personsArrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();

        Cursor c = sqLiteDatabase.rawQuery("SELECT * FROM persons",null);

        while (c.moveToNext()) {
            Persons p = new Persons(c.getInt(c.getColumnIndexOrThrow("person_id"))
                    ,c.getString(c.getColumnIndexOrThrow("person_name"))
                    ,c.getString(c.getColumnIndexOrThrow("phone_number"))
                    ,c.getString(c.getColumnIndexOrThrow("person_email")));

            personsArrayList.add(p);
        }
        sqLiteDatabase.close();
        return personsArrayList;
    }

    public ArrayList<Persons> personSearch(Database db,String searchWords) {

        ArrayList<Persons> personsArrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();

        Cursor c = sqLiteDatabase.rawQuery("SELECT * FROM persons WHERE person_name like '%"+searchWords+"%'",null);

        while (c.moveToNext()) {
            Persons p = new Persons(c.getInt(c.getColumnIndexOrThrow("person_id"))
                    ,c.getString(c.getColumnIndexOrThrow("person_name"))
                    ,c.getString(c.getColumnIndexOrThrow("phone_number"))
                    ,c.getString(c.getColumnIndexOrThrow("person_email")));
            personsArrayList.add(p);
        }
        sqLiteDatabase.close();
        return personsArrayList;
    }

    public void personDelete(Database db,int person_id) {
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        sqLiteDatabase.delete("persons","person_id=?",new String[] {String.valueOf(person_id)});
        sqLiteDatabase.close();
    }

    public void personAdd(Database db,String person_name,String phone_number,String person_email) {
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("person_name",person_name);
        values.put("phone_number",phone_number);
        values.put("person_email",person_email);

        sqLiteDatabase.insertOrThrow("persons",null,values);
        sqLiteDatabase.close();
    }

    public void personUpdate(Database db,int person_id,String person_name,String phone_number,String person_email) {
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("person_name",person_name);
        values.put("phone_number",phone_number);
        values.put("person_email",person_email);

        sqLiteDatabase.update("persons",values,"person_id=?",new String[] {String.valueOf(person_id)});
        sqLiteDatabase.close();
    }


}
