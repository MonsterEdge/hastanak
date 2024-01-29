package com.example.hastanak;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

// Hasta listesini göstermek için özel bir adaptör sınıfı
public class PatientAdapter extends BaseAdapter {

    // Sınıf değişkenleri
    private Context context; // Uygulama bağlamı
    private List<Patient> patientList; // Hasta listesi
    private LayoutInflater layoutInflater; // Arayüz oluşturmak için layout inflater nesnesi

    // Kurucu metot
    public PatientAdapter(Context context, List<Patient> patientList) {
        this.context = context;
        this.patientList = patientList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    // Hasta listesinin boyutunu döndüren metot
    @Override
    public int getCount() {
        return patientList.size();
    }

    // Hasta listesinin belirli bir pozisyondaki elemanını döndüren metot
    @Override
    public Object getItem(int position) {
        return patientList.get(position);
    }

    // Hasta listesinin belirli bir pozisyondaki elemanının id'sini döndüren metot
    @Override
    public long getItemId(int position) {
        return position;
    }

    // Hasta listesinin belirli bir pozisyondaki elemanının görünümünü döndüren metot
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Görünümü tanımlama
        View view = convertView;
        // Görünüm null ise, arayüzü oluşturma
        if (view == null) {
            view = layoutInflater.inflate(R.layout.patient_item, null);
        }
        // Görünümdeki arayüz elemanlarını tanımlama
        TextView nameTextView = view.findViewById(R.id.name_text_view);
        TextView diagnosisTextView = view.findViewById(R.id.diagnosis_text_view);
        TextView fromHospitalTextView = view.findViewById(R.id.from_hospital_text_view);
        TextView toHospitalTextView = view.findViewById(R.id.to_hospital_text_view);
        TextView dateTextView = view.findViewById(R.id.date_text_view);
        // Hasta listesinden belirli bir pozisyondaki hastayı alama
        Patient patient = patientList.get(position);
        // Arayüz elemanlarını hasta bilgileri ile doldurma
        nameTextView.setText(patient.getName());
        diagnosisTextView.setText(patient.getDiagnosis());
        fromHospitalTextView.setText(patient.getFromHospital());
        toHospitalTextView.setText(patient.getToHospital());
        dateTextView.setText(patient.getDate());
        // Görünümü döndürme
        return view;
    }
}
