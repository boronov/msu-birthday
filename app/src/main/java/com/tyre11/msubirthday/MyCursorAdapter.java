package com.tyre11.msubirthday;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class MyCursorAdapter extends SimpleCursorAdapter {
    private int myLayout;


    public MyCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
        myLayout = layout;
    }

    public View newView(Context context, Cursor _cursor, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        View view = inflater.inflate(myLayout, parent, false);
        return view;
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String fname = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_FNAME));
        String age = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_AGE));
        int year = Integer.parseInt(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_YEAR)));
        String naprav = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAPRAV));



        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd.MM.yyyy");
        LocalDate dateTime = LocalDate.parse(age, formatter);
        LocalDate nowData = LocalDate.now();

        LocalDate nextBDay = dateTime.withYear(nowData.getYear());
        // Если в этом году День Рождения уже прошёл, то добавляем один год.
        if (nextBDay.isBefore(nowData) || nextBDay.isEqual(nowData)) {
            nextBDay = nextBDay.plusYears(1);
        }
        Days p = Days.daysBetween(nowData, nextBDay);

        long p2 = p.getDays();

        TextView textView1 = view.findViewById(R.id.textView1);
        TextView textView2 = view.findViewById(R.id.textView2);
        TextView textView3 = view.findViewById(R.id.textView3);
        TextView textView4 = view.findViewById(R.id.textView4);
        TextView textView5 = view.findViewById(R.id.textView5);
        textView1.setText(fname);
        year += 2000;
        textView2.setText(naprav + "-" + (nowData.getYear() - year));
        textView3.setText(age);
        textView4.setText(p2+ " дней до ДР");
        textView5.setText("#"+(cursor.getPosition()+1));

    }
}
