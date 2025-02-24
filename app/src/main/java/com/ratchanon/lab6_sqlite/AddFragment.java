package com.ratchanon.lab6_sqlite;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class AddFragment extends Fragment {
    String[] year;
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> arrayAdapter;

    // Temp Form
    public String Year = "1";
    public String Sex = "1";

    // Value
    public MainActivity mainAcrivity;
    public String valueName;
    public String valueLastName;
    public String valueYear;
    public String valueSex;
    public String valueBook;
    public String valueGaming;

    public TextView textViewHeader;
    public TextInputEditText inputName;
    public TextInputEditText inputLastName;
    RadioGroup radioGroupSex;
    RadioButton radioButtonMale;
    RadioButton radioButtonFemale;
    CheckBox checkBoxBook;
    CheckBox checkBoxGaming;
    MaterialButton buttonSubmit;

    DatabaseHelper databaseHelper;
    Cursor mCursor;

    public AddFragment(MainActivity mainAcrivity) {
        this.mainAcrivity = mainAcrivity;
    }

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_input, container, false);

        databaseHelper = new DatabaseHelper(getContext());
        SQLiteDatabase mDb = databaseHelper.getReadableDatabase();

        autoCompleteTextView = view.findViewById(R.id.autoCompleteYear);
        textViewHeader = view.findViewById(R.id.textHeader);
        inputName = view.findViewById(R.id.textFieldName);
        inputLastName = view.findViewById(R.id.textFieldLastName);
        radioGroupSex = view.findViewById(R.id.radioGroupSex);
        radioButtonMale = view.findViewById(R.id.radioButtonMale);
        radioButtonFemale = view.findViewById(R.id.radioButtonFemale);
        checkBoxBook = view.findViewById(R.id.checkBoxBook);
        checkBoxGaming = view.findViewById(R.id.checkBoxGaming);
        buttonSubmit = view.findViewById(R.id.buttonSubmit);

        textViewHeader.setText(R.string.text_header_add);
        buttonSubmit.setText(R.string.btn_add);
        buttonSubmit.setIconResource(R.drawable.baseline_add_24);

        year = getResources().getStringArray(R.array.year);
        arrayAdapter = new ArrayAdapter<String>(view.getContext(), R.layout.list_item, year);
        autoCompleteTextView.setText(arrayAdapter.getItem(0).toString(), false);

        autoCompleteTextView.setAdapter(arrayAdapter);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Year = parent.getItemAtPosition(position).toString();
            }
        });

        radioGroupSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Sex = (checkedId == R.id.radioButtonMale) ? "1" : "2" ;
            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                valueName = inputName.getText().toString();
                valueLastName = inputLastName.getText().toString();
                valueYear = Year;
                valueSex = Sex;
                valueBook = checkBoxBook.isChecked() ? "1" : "0";
                valueGaming = checkBoxGaming.isChecked() ? "1" : "0";

                if(!valueName.isEmpty() && !valueLastName.isEmpty()) {
                    mCursor = mDb.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE_NAME
                            + " WHERE " + DatabaseHelper.COL_NAME + "='" + valueName + "'"
                            + " AND " + DatabaseHelper.COL_LASTNAME + "='" + valueLastName + "'", null);

                    if(mCursor.getCount() == 0) {
                        mDb.execSQL("INSERT INTO " + DatabaseHelper.TABLE_NAME + " ("
                                + DatabaseHelper.COL_NAME + ", " + DatabaseHelper.COL_LASTNAME + ", " + DatabaseHelper.COL_YEAR + ", " + DatabaseHelper.COL_SEX + ", " + DatabaseHelper.COL_BOOK + ", " + DatabaseHelper.COL_GAME
                                + ") VALUES ('"
                                + valueName + "', '" + valueLastName + "', '" + valueYear + "', '" + valueSex + "', '" + valueBook + "', '" + valueGaming + "');");
                        Toast.makeText(getContext(), "เพิ่มข้อมูลเพื่อนเรียบร้อยแล้ว", Toast.LENGTH_SHORT).show();

                        mainAcrivity.bottomNavigation.setSelectedItemId(R.id.item_1);

                    } else {
                        Toast.makeText(getContext(), "คุณมีข้อมูลเพื่อนคนนี้อยู่แล้ว", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(view.getContext(), "กรุณาใส่ข้อมูลเพื่อนให้ครบทุกช่อง"
                            , Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}