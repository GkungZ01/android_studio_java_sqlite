package com.ratchanon.lab6_sqlite;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class InputFragment extends Fragment {
    String[] year;
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> arrayAdapter;

    // Temp Form
    public String Year = "1";
    public String Sex = "1";

    public TextView textViewHeader;
    public TextInputEditText inputName;
    public TextInputEditText inputLastName;
    public RadioGroup radioGroupSex;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_input, container, false);

        autoCompleteTextView = view.findViewById(R.id.autoCompleteYear);
        textViewHeader = view.findViewById(R.id.textHeader);
        radioGroupSex = view.findViewById(R.id.radioGroupSex);

        year = getResources().getStringArray(R.array.year);
        arrayAdapter = new ArrayAdapter<String>(view.getContext(), R.layout.list_item, year);
        autoCompleteTextView.setText(arrayAdapter.getItem(0).toString(), false);

        autoCompleteTextView.setAdapter(arrayAdapter);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Year = item;
            }
        });

        radioGroupSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Sex = (checkedId == R.id.radioButtonMale) ? "1" : "2" ;
            }
        });

        return view;
    }

    public void setYear(String numberYear) {
        autoCompleteTextView.setText(numberYear, false);
        Year = numberYear;
    }
}