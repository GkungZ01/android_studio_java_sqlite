package com.ratchanon.lab6_sqlite;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class EditFragment extends Fragment {
    String[] year;
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> arrayAdapter;

    // Temp Form
    public String Year = "1";
    public String Sex = "1";

    // Value
    public MainActivity mainAcrivity;
    public int valueID;
    public String valueName;
    public String valueLastName;
    public String valueYear;
    public String valueSex;
    public String valueBook;
    public String valueGaming;

    TextView textViewHeader;
    TextInputEditText inputName;
    TextInputEditText inputLastName;
    RadioGroup radioGroupSex;
    RadioButton radioButtonMale;
    RadioButton radioButtonFemale;
    CheckBox checkBoxBook;
    CheckBox checkBoxGaming;
    MaterialButton buttonSubmit;
    MaterialButton buttonDelete;

    DatabaseHelper databaseHelper;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit, container, false);

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
        buttonDelete = view.findViewById(R.id.buttonDelete);

        textViewHeader.setText(R.string.text_header_edit);
        buttonSubmit.setText(R.string.btn_edit);
        buttonSubmit.setIconResource(R.drawable.baseline_edit_24);

        year = getResources().getStringArray(R.array.year);
        arrayAdapter = new ArrayAdapter<String>(view.getContext(), R.layout.list_item, year);
        autoCompleteTextView.setAdapter(arrayAdapter);

        // Set Data
        setInputName(valueName);
        setInputLastName(valueLastName);
        setYear(valueYear);
        setSex(valueSex);
        setHobbyBook(valueBook);
        setHobbyGaming(valueGaming);

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
                Sex = (checkedId == R.id.radioButtonMale) ? "1" : "2";
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

                if (valueName.length() != 0 && valueLastName.length() != 0) {
                    mDb.execSQL("UPDATE " + DatabaseHelper.TABLE_NAME + " SET "
                            + DatabaseHelper.COL_NAME + "='" + valueName + "', "
                            + DatabaseHelper.COL_LASTNAME + "='" + valueLastName + "', "
                            + DatabaseHelper.COL_YEAR + "='" + valueYear + "', "
                            + DatabaseHelper.COL_SEX + "='" + valueSex + "', "
                            + DatabaseHelper.COL_BOOK + "='" + valueBook + "', "
                            + DatabaseHelper.COL_GAME + "='" + valueGaming
                            + "' WHERE " + DatabaseHelper.COL_ID + "='" + String.valueOf(valueID) + "';");

                    Toast.makeText(view.getContext(), "แก้ไขข้อมูลเพื่อนเรียบร้อยแล้ว"
                            , Toast.LENGTH_SHORT).show();

                    mainAcrivity.bottomNavigation.setSelectedItemId(R.id.item_1);

                } else {
                    Toast.makeText(view.getContext(), "กรุณาใส่ข้อมูลเพื่อนให้ครบทุกช่อง"
                            , Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonDelete.setOnClickListener(v -> {
            mDb.execSQL("DELETE FROM " + DatabaseHelper.TABLE_NAME + " WHERE " + DatabaseHelper.COL_ID + " = " + valueID + ";");
            Toast.makeText(view.getContext(), "ลบข้อมูลเพื่อนเรียบร้อยแล้ว"
                    , Toast.LENGTH_SHORT).show();
            mainAcrivity.bottomNavigation.setSelectedItemId(R.id.item_1);
        });

        return view;
    }

    void setInputName(String value) {
        inputName.setText(value);
    }

    void setInputLastName(String value) {
        inputLastName.setText(value);
    }

    void setYear(String value) {
        autoCompleteTextView.setText(value, false);
        Year = value;
    }

    void setSex(String value) {
        if (Objects.equals(value, "1")) {
            radioButtonMale.setChecked(true);
        } else {
            radioButtonFemale.setChecked(true);
        }
        Sex = value;
    }

    void setHobbyBook(String value) {
        checkBoxBook.setChecked(Objects.equals(value, "1"));
    }

    void setHobbyGaming(String value) {
        checkBoxGaming.setChecked(Objects.equals(value, "1"));
    }
}