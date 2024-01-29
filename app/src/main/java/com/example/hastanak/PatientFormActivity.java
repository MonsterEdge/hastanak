package com.example.hastanak;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PatientFormActivity extends AppCompatActivity {

    // Arayüz elemanları
    private EditText nameEditText;
    private EditText diagnosisEditText;
    private EditText fromHospitalEditText;
    private EditText toHospitalEditText;
    private EditText dateEditText;
    private Button saveButton;

    // Veritabanı yardımcı sınıfı
    private DatabaseHelper databaseHelper;

    // Hasta nesnesi
    private Patient patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_form);

        // Arayüz elemanlarını tanımlama
        nameEditText = findViewById(R.id.name_edit_text);
        diagnosisEditText = findViewById(R.id.diagnosis_edit_text);
        fromHospitalEditText = findViewById(R.id.from_hospital_edit_text);
        toHospitalEditText = findViewById(R.id.to_hospital_edit_text);
        dateEditText = findViewById(R.id.date_edit_text);
        saveButton = findViewById(R.id.save_button);

        // Veritabanı yardımcı sınıfını oluşturma
        databaseHelper = new DatabaseHelper(this);

        // Hasta nesnesini MainActivity'den gelen veri ile oluşturma
        patient = (Patient) getIntent().getSerializableExtra("patient");

        // Hasta nesnesi null değilse, yani güncelleme işlemi yapılacaksa
        if (patient != null) {
            // Arayüz elemanlarını hasta bilgileri ile doldurma
            nameEditText.setText(patient.getName());
            diagnosisEditText.setText(patient.getDiagnosis());
            fromHospitalEditText.setText(patient.getFromHospital());
            toHospitalEditText.setText(patient.getToHospital());
            dateEditText.setText(patient.getDate());
        }

        // Kaydet butonuna tıklama işlemi
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Arayüz elemanlarından verileri alma
                String name = nameEditText.getText().toString();
                String diagnosis = diagnosisEditText.getText().toString();
                String fromHospital = fromHospitalEditText.getText().toString();
                String toHospital = toHospitalEditText.getText().toString();
                String date = dateEditText.getText().toString();

                // Verilerin boş olup olmadığını kontrol etme
                if (name.isEmpty() || diagnosis.isEmpty() || fromHospital.isEmpty() || toHospital.isEmpty() || date.isEmpty()) {
                    // Veriler boş ise uyarı mesajı gösterme
                    Toast.makeText(PatientFormActivity.this, "Lütfen tüm alanları doldurunuz", Toast.LENGTH_SHORT).show();
                } else {
                    // Veriler boş değil ise
                    // Hasta nesnesi null ise, yani ekleme işlemi yapılacaksa
                    if (patient == null) {
                        // Yeni bir hasta nesnesi oluşturma
                        patient = new Patient();
                        // Hasta nesnesine verileri atama
                        patient.setName(name);
                        patient.setDiagnosis(diagnosis);
                        patient.setFromHospital(fromHospital);
                        patient.setToHospital(toHospital);
                        patient.setDate(date);
                        // Hasta nesnesini veritabanına ekleme
                        boolean result = databaseHelper.insertPatient(patient);
                        // Ekleme işleminin sonucuna göre mesaj gösterme
                        if (result) {
                            Toast.makeText(PatientFormActivity.this, "Hasta başarıyla eklendi", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(PatientFormActivity.this, "Hasta eklenirken bir hata oluştu", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Hasta nesnesi null değil ise, yani güncelleme işlemi yapılacaksa
                        // Hasta nesnesine verileri atama
                        patient.setName(name);
                        patient.setDiagnosis(diagnosis);
                        patient.setFromHospital(fromHospital);
                        patient.setToHospital(toHospital);
                        patient.setDate(date);
                        // Hasta nesnesini veritabanında güncelleme
                        boolean result = databaseHelper.updatePatient(patient);
                        // Güncelleme işleminin sonucuna göre mesaj gösterme
                        if (result) {
                            Toast.makeText(PatientFormActivity.this, "Hasta başarıyla güncellendi", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(PatientFormActivity.this, "Hasta güncellenirken bir hata oluştu", Toast.LENGTH_SHORT).show();
                        }
                    }
                    // MainActivity'e geri dönme
                    Intent intent = new Intent(PatientFormActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
