package com.example.hastanak;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Arayüz elemanları
    private Button addButton;
    private ListView patientListView;

    // Veritabanı yardımcı sınıfı
    private DatabaseHelper databaseHelper;

    // Hasta listesi
    private List<Patient> patientList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Arayüz elemanlarını tanımlama
        addButton = findViewById(R.id.add_button);
        patientListView = findViewById(R.id.patient_list_view);

        // Veritabanı yardımcı sınıfını oluşturma
        databaseHelper = new DatabaseHelper(this);

        // Hasta listesini veritabanından getirme
        patientList = databaseHelper.getAllPatients();

        // Hasta listesini göstermek için özel bir adaptör oluşturma
        PatientAdapter patientAdapter = new PatientAdapter(this, patientList);

        // Hasta listesini listview'e bağlama
        patientListView.setAdapter(patientAdapter);

        // Hasta ekleme butonuna tıklama işlemi
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // PatientFormActivity'e geçiş
                Intent intent = new Intent(MainActivity.this, PatientFormActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Hasta listesindeki bir öğeye tıklama işlemi
        patientListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Tıklanan hastayı alama
                Patient patient = patientList.get(position);
                // PatientFormActivity'e geçiş
                Intent intent = new Intent(MainActivity.this, PatientFormActivity.class);
                // Hasta nesnesini intent'e ekleyerek gönderme
                intent.putExtra("patient", patient);
                startActivity(intent);
                finish();
            }
        });

        // Hasta listesindeki bir öğeye uzun tıklama işlemi
        patientListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // Uzun tıklanan hastayı alama
                Patient patient = patientList.get(position);
                // Hasta nesnesini veritabanından silme
                boolean result = databaseHelper.deletePatient(patient.getId());
                // Silme işleminin sonucuna göre mesaj gösterme
                if (result) {
                    Toast.makeText(MainActivity.this, "Hasta başarıyla silindi", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Hasta silinirken bir hata oluştu", Toast.LENGTH_SHORT).show();
                }
                // MainActivity'i yeniden başlatma
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;
            }
        });
    }
}
