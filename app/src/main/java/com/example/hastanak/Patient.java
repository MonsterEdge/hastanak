package com.example.hastanak;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.List;

// Ana aktivite sınıfı
public class MainActivity extends AppCompatActivity {

    // Sınıf değişkenleri
    private RecyclerView recyclerView; // RecyclerView nesnesi
    private PatientAdapter patientAdapter; // PatientAdapter nesnesi
    private DatabaseHelper databaseHelper; // DatabaseHelper nesnesi
    private Button addButton; // Hasta ekleme butonu

    // onCreate metodu
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // RecyclerView nesnesini layout dosyasındaki elemanla bağlamak
        recyclerView = findViewById(R.id.recycler_view);
        // RecyclerView nesnesine bir düzen yöneticisi atamak
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // DatabaseHelper nesnesi oluşturmak
        databaseHelper = new DatabaseHelper(this);
        // Hastaların listesini veritabanından almak
        List<Patient> patientList = databaseHelper.getAllPatients();
        // PatientAdapter nesnesi oluşturmak
        patientAdapter = new PatientAdapter(this, patientList);
        // RecyclerView nesnesine bir adaptör atamak
        recyclerView.setAdapter(patientAdapter);
        // Hasta ekleme butonunu layout dosyasındaki elemanla bağlamak
        addButton = findViewById(R.id.add_button);
        // Hasta ekleme butonuna tıklama olayı tanımlamak
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hasta ekleme aktivitesini başlatmak için Intent nesnesi oluşturmak
                Intent intent = new Intent(MainActivity.this, PatientFormActivity.class);
                // Hasta ekleme aktivitesini başlatmak
                startActivity(intent);
            }
        });
    }

    // Aktivite yeniden başlatıldığında çağrılan metot
    @Override
    protected void onRestart() {
        super.onRestart();
        // Hastaların listesini veritabanından yeniden almak
        List<Patient> patientList = databaseHelper.getAllPatients();
        // PatientAdapter nesnesine yeni listeyi atamak
        patientAdapter.setPatientList(patientList);
        // PatientAdapter nesnesini yenilemek
        patientAdapter.notifyDataSetChanged();
    }
}
