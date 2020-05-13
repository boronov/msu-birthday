package com.tyre11.msubirthday;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.Spinner;

import org.joda.time.LocalDate;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper sqlHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    MyCursorAdapter userAdapter;
    ListView userList;
    EditText userFilter;
    Spinner direction;
    Spinner course;
    String[] naprav = {"Любое","МО", "ГМУ", "ХФММ", "ПМиИ", "Лингвистика", "Геология"};
    String[] kurs = {"Любой","1", "2" , "3", "4"};
    String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userList = findViewById(R.id.userList);
        userFilter = findViewById(R.id.userFilter);

        sqlHelper = new DatabaseHelper(getApplicationContext());
        // создаем базу данных
        sqlHelper.create_db();


        direction = findViewById(R.id.naprav);
        // Создаем адаптер ArrayAdapter с помощью массива строк и разметки элемета spinner
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, R.layout.color_spinner_layout, naprav);
        // Определяем разметку для использования при выборе элемента
        // Применяем адаптер к элементу spinner
        adapter1.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        direction.setAdapter(adapter1);


        course = findViewById(R.id.kurs);
        // Создаем адаптер ArrayAdapter с помощью массива строк и разметки элемета spinner
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,  R.layout.color_spinner_layout, kurs);
        // Определяем разметку для использования при выборе элемента
        // Применяем адаптер к элементу spinner
        adapter2.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        course.setAdapter(adapter2);
    }

    public void onResume() {
        super.onResume();
        try {
            db = sqlHelper.open();
            userCursor = db.rawQuery("select * from " + DatabaseHelper.TABLE +" order by " + DatabaseHelper.COLUMN_FNAME, null);

            String[] headers = new String[]{DatabaseHelper.COLUMN_FNAME, DatabaseHelper.COLUMN_NAPRAV, DatabaseHelper.COLUMN_YEAR, DatabaseHelper.COLUMN_AGE };
            userAdapter = new MyCursorAdapter(this, R.layout.adapter_view_layout,
                    userCursor, headers, new int[]{android.R.id.text1, android.R.id.text2 }, 0);

            // если в текстовом поле есть текст, выполняем фильтрацию
            // данная проверка нужна при переходе от одной ориентации экрана к другой
            if(!userFilter.getText().toString().isEmpty())
                userAdapter.getFilter().filter(userFilter.getText().toString());

            // установка слушателя изменения текста
            userFilter.addTextChangedListener(new TextWatcher() {

                public void afterTextChanged(Editable s) { }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
                // при изменении текста выполняем фильтрацию
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    userAdapter.getFilter().filter(s.toString()+code);
                }
            });

            //установка слушателя изменения направления
            OnItemSelectedListener itemSelectedListener1 = new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    // Получаем выбранный объект
                    String item = (String)parent.getItemAtPosition(position);

                    if (course.getSelectedItem().equals("Любой")) code = "0";
                    if (course.getSelectedItem().equals("1"))  code = "1";
                    if (course.getSelectedItem().equals("2"))  code = "2";
                    if (course.getSelectedItem().equals("3"))  code = "3";
                    if (course.getSelectedItem().equals("4"))  code = "4";

                    if (item.equals("Любое")) code = code + "-0";
                    if (item.equals("МО")) code = code + "-1";
                    if (item.equals("ПМиИ")) code = code + "-2";
                    if (item.equals("ГМУ")) code = code + "-3";
                    if (item.equals("Лингвистика")) code = code + "-4";
                    if (item.equals("Геология")) code = code + "-5";
                    if (item.equals("ХФММ")) code = code + "-6";
                    userAdapter.getFilter().filter(userFilter.getText() + code);
                   }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {}
            };


            //установка слушателя изменения курса
            OnItemSelectedListener itemSelectedListener2 = new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    // Получаем выбранный объект
                    String item = (String)parent.getItemAtPosition(position);

                    if (item.equals("Любой")) code = "0";
                    if (item.equals("1"))     code = "1";
                    if (item.equals("2"))     code = "2";
                    if (item.equals("3"))     code = "3";
                    if (item.equals("4"))     code = "4";

                    if (direction.getSelectedItem().equals("Любое"))       code = code + "-0";
                    if (direction.getSelectedItem().equals("МО"))          code = code + "-1";
                    if (direction.getSelectedItem().equals("ПМиИ"))        code = code + "-2";
                    if (direction.getSelectedItem().equals("ГМУ"))         code = code + "-3";
                    if (direction.getSelectedItem().equals("Лингвистика")) code = code + "-4";
                    if (direction.getSelectedItem().equals("Геология"))    code = code + "-5";
                    if (direction.getSelectedItem().equals("ХФММ"))        code = code + "-6";

                    userAdapter.getFilter().filter(userFilter.getText() + code);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {}
            };


            direction.setOnItemSelectedListener(itemSelectedListener1);
            course.setOnItemSelectedListener(itemSelectedListener2);


            // устанавливаем провайдер фильтрации
            userAdapter.setFilterQueryProvider(new FilterQueryProvider() {

                @Override
                public Cursor runQuery(CharSequence constraint) {

                    if (constraint == null || constraint.length() == 0) {

                        return db.rawQuery("select * from " + DatabaseHelper.TABLE+"\n order by " + DatabaseHelper.COLUMN_FNAME, null);
                    }
                    else {
                        String code = constraint.toString().trim();
                        code = code.substring(code.length()-3).trim();

                        String fname = constraint.toString().trim();
                        fname = fname.substring(0, fname.length()-3).trim();

                        String kr = "";
                        String np = "";

                        LocalDate nowData = LocalDate.now();
                        //тут нужно будет поправить !!!! не корректно работает определения курса
                        kr = String.valueOf(nowData.getYear() - Integer.parseInt(String.valueOf(code.charAt(0))) - 2000);
                        String kr2 = String.valueOf(nowData.getYear() - 2000);

                        if (code.charAt(2) == '0') np = "";
                        if (code.charAt(2) == '1') np = "МО";
                        if (code.charAt(2) == '2') np = "ПМиИ";
                        if (code.charAt(2) == '3') np = "ГМУ";
                        if (code.charAt(2) == '4') np = "Лингвистика";
                        if (code.charAt(2) == '5') np = "Геология";
                        if (code.charAt(2) == '6') np = "ХФММ";

                        //Toast.makeText(getApplicationContext(), kr + " " + np, Toast.LENGTH_LONG).show();
                        if (kr.equals(kr2)) {
                            return db.rawQuery("select * from " + DatabaseHelper.TABLE +
                                    " where " + DatabaseHelper.COLUMN_FNAME + " like '%" + fname + "%' " + " and " +
                                    DatabaseHelper.COLUMN_NAPRAV + " like '%" + np + "%' " +
                                    " order by " + DatabaseHelper.COLUMN_FNAME, null);
                        }else {
                            return db.rawQuery("select * from " + DatabaseHelper.TABLE +
                                    " where " + DatabaseHelper.COLUMN_FNAME + " like '%" + fname + "%' " + " and " +
                                    DatabaseHelper.COLUMN_NAPRAV + " like '%" + np + "%' and " +
                                    DatabaseHelper.COLUMN_YEAR +"="+kr+" "+
                                    " order by " + DatabaseHelper.COLUMN_FNAME, null);
                        }
                    }
                }
            });

            userList.setAdapter(userAdapter);
        }
        catch (SQLException ex){}
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        // Закрываем подключение и курсор
        db.close();
        userCursor.close();
    }
}