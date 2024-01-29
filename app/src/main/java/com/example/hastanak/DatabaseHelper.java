package com.example.hastanak;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

// Hastaların bilgilerini veritabanında tutmak için yardımcı sınıf
public class DatabaseHelper extends SQLiteOpenHelper {

    // Sınıf değişkenleri
    private static final String DATABASE_NAME = "Patient.db"; // Veritabanı adı
    private static final int DATABASE_VERSION = 1; // Veritabanı sürümü
    private static final String TABLE_NAME = "patient_table"; // Tablo adı
    private static final String COLUMN_ID = "id"; // Tablodaki id sütunu
    private static final String COLUMN_NAME = "name"; // Tablodaki name sütunu
    private static final String COLUMN_DIAGNOSIS = "diagnosis"; // Tablodaki diagnosis sütunu
    private static final String COLUMN_FROM_HOSPITAL = "from_hospital"; // Tablodaki from_hospital sütunu
    private static final String COLUMN_TO_HOSPITAL = "to_hospital"; // Tablodaki to_hospital sütunu
    private static final String COLUMN_DATE = "date"; // Tablodaki date sütunu

    // Kurucu metot
    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Veritabanı oluşturulduğunda çağrılan metot
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tabloyu oluşturmak için SQL komutu
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_DIAGNOSIS + " TEXT, " +
                COLUMN_FROM_HOSPITAL + " TEXT, " +
                COLUMN_TO_HOSPITAL + " TEXT, " +
                COLUMN_DATE + " TEXT)";
        // SQL komutunu çalıştırmak
        db.execSQL(createTable);
    }

    // Veritabanı güncellendiğinde çağrılan metot
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Eski tabloyu silmek için SQL komutu
        String dropTable = "DROP TABLE IF EXISTS " + TABLE_NAME;
        // SQL komutunu çalıştırmak
        db.execSQL(dropTable);
        // Yeni tabloyu oluşturmak için onCreate metotunu çağırmak
        onCreate(db);
    }

    // Hasta bilgilerini veritabanına kaydetmek için metot
    public void addPatient(Patient patient) {
        // Veritabanına yazılabilir bir bağlantı almak
        SQLiteDatabase db = this.getWritableDatabase();
        // ContentValues nesnesi oluşturmak
        ContentValues values = new ContentValues();
        // Hasta bilgilerini bu nesneye eklemek
        values.put(COLUMN_NAME, patient.getName());
        values.put(COLUMN_DIAGNOSIS, patient.getDiagnosis());
        values.put(COLUMN_FROM_HOSPITAL, patient.getFromHospital());
        values.put(COLUMN_TO_HOSPITAL, patient.getToHospital());
        values.put(COLUMN_DATE, patient.getDate());
        // Veritabanına bu nesneyi kaydetmek
        db.insert(TABLE_NAME, null, values);
        // Bağlantıyı kapatmak
        db.close();
    }

    // Hasta bilgilerini veritabanında güncellemek için metot
    public void updatePatient(Patient patient) {
        // Veritabanına yazılabilir bir bağlantı almak
        SQLiteDatabase db = this.getWritableDatabase();
        // ContentValues nesnesi oluşturmak
        ContentValues values = new ContentValues();
        // Hasta bilgilerini bu nesneye eklemek
        values.put(COLUMN_NAME, patient.getName());
        values.put(COLUMN_DIAGNOSIS, patient.getDiagnosis());
        values.put(COLUMN_FROM_HOSPITAL, patient.getFromHospital());
        values.put(COLUMN_TO_HOSPITAL, patient.getToHospital());
        values.put(COLUMN_DATE, patient.getDate());
        // Veritabanında bu nesneyi güncellemek
        db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{String.valueOf(patient.getId())});
        // Bağlantıyı kapatmak
        db.close();
    }

    // Hasta bilgilerini veritabanından silmek için metot
    public void deletePatient(Patient patient) {
        // Veritabanına yazılabilir bir bağlantı almak
        SQLiteDatabase db = this.getWritableDatabase();
        // Veritabanından hastayı silmek
        db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(patient.getId())});
        // Bağlantıyı kapatmak
        db.close();
    }

    // Tüm hastaların bilgilerini veritabanından almak için metot
    public List<Patient> getAllPatients() {
        // Hasta listesi oluşturmak
        List<Patient> patientList = new ArrayList<>();
        // Veritabanından tüm hastaları seçmek için SQL komutu
        String selectAll = "SELECT * FROM " + TABLE_NAME;
        // Veritabanına okunabilir bir bağlantı almak
        SQLiteDatabase db = this.getReadableDatabase();
        // SQL komutunu çalıştırmak ve sonuçları bir Cursor nesnesine atamak
        Cursor cursor = db.rawQuery(selectAll, null);
        // Cursor nesnesindeki verileri hasta listesine eklemek
        if (cursor.moveToFirst()) {
            do {
                // Cursor nesnesinden hasta bilgilerini almak
                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                String diagnosis = cursor.getString(cursor.getColumnIndex(COLUMN_DIAGNOSIS));
                String fromHospital = cursor.getString(cursor.getColumnIndex(COLUMN_FROM_HOSPITAL));
                String toHospital = cursor.getString(cursor.getColumnIndex(COLUMN_TO_HOSPITAL));
                String date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE));
                // Hasta nesnesi oluşturmak
                Patient patient = new Patient(id, name, diagnosis, fromHospital, toHospital, date);
                // Hasta nesnesini hasta listesine eklemek
                patientList.add(patient);
            } while (cursor.moveToNext());
        }
        // Cursor nesnesini kapatmak
        cursor.close();
        // Bağlantıyı kapatmak
        db.close();
        // Hasta listesini döndürmek
        return patientList;
    }
}
