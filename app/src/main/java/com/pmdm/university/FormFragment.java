package com.pmdm.university;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.pmdm.university.databinding.FragmentFormBinding;
import com.pmdm.university.entidad.UniversityDetail;
import com.pmdm.university.implementacion.UniversityDetailSQLiteHelper;


public class FormFragment extends Fragment {

    public static final String NAME = "name";

    private UniversityDetail detail;

    private FragmentFormBinding binding;

    private UniversityDetailSQLiteHelper universitydb;


    private EditText url_imagen;
    private EditText description;
    private Button btnGuardar;
    private Button btnDelete;

    public FormFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        updateContent();

        String name = getArguments().getString(NAME);
        if (name !=null)
            if(detail == null)
                detail = new UniversityDetail();

        detail.setName(name);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFormBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        updateContent();

        url_imagen=(EditText) binding.universityUrl;
        description=(EditText) binding.universityDescription;
        btnGuardar=(Button) binding.buttonSave;
        btnDelete=(Button) binding.buttonDelete;

        universitydb = new UniversityDetailSQLiteHelper((getContext()));

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        return rootView;

    }
    private void updateContent() {

        if(detail != null){
            binding.universityName.setText(detail.getName());

        }
    }
}
