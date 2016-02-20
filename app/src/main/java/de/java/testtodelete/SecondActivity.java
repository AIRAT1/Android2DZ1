package de.java.testtodelete;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import data.DBHelper;

public class SecondActivity extends Activity implements AdapterView.OnItemLongClickListener{
    private EditText editText;
    private LinearLayout linearLayoutRoot;
    private Button button;
    private ListView listView;
    private List<String> list;
    private Animation animation;
    private ArrayAdapter<String> adapter;
    private String companyName;

    private DBHelper dbHelper;
    static SQLiteDatabase db;
    private ContentValues cv;
    private Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        init();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animation = AnimationUtils.loadAnimation(SecondActivity.this, R.anim.rotate);
                button.setBackgroundColor(getResources().getColor(R.color.green));
                button.setAnimation(animation);
                list.add(0, editText.getText().toString());
                adapter.notifyDataSetChanged();
                editText.setText("");
            }
        });
    }
    void init() {
        editText = (EditText)findViewById(R.id.editText);
        companyName = getIntent().getStringExtra(MainActivity.COMPANY_NAME);
        editText.setHint(getResources().getString(R.string.enter_person_name) + " "
                + companyName + " here.");
        linearLayoutRoot = (LinearLayout)findViewById(R.id.linearLayoutRoot);
        linearLayoutRoot.setBackgroundColor(getResources().getColor(R.color.linearLayoutRoot));
        editText.setBackgroundColor(getResources().getColor(R.color.linearLayoutRoot));
        listView = (ListView)findViewById(R.id.listView);
        listView.setBackgroundColor(getResources().getColor(R.color.linearLayoutRoot));
        button = (Button)findViewById(R.id.button);
        load();

        adapter = new ArrayAdapter<>(SecondActivity.this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        save();
    }

    private void save() {
        db = dbHelper.getWritableDatabase();
        db.delete(DBHelper.TABLE_NAME, null, null);
        cv = new ContentValues();
        for (String s : list) {
            cv.put(DBHelper.COLUMN_NAME, s);
            db.insert(DBHelper.TABLE_NAME, null, cv);

        }
    }
    private List<String> load() {
        dbHelper = new DBHelper(this, DBHelper.DB_NAME + companyName, null, 1);
        db = dbHelper.getWritableDatabase();
        cursor = db.query(DBHelper.TABLE_NAME, null,
                null, null, null, null, null);
        list = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            list.add(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_NAME)));
        }
        return list;
    }

    @Override
    public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete")
                .setMessage("Are you really wont to delete this?")
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_backward);
                        button.setBackgroundColor(getResources().getColor(R.color.red));
                        button.startAnimation(animation);
                        adapter.remove(parent.getItemAtPosition(position).toString());
                        adapter.notifyDataSetChanged();
                        if (list.size() == 0) button.setBackgroundColor(getResources().getColor(R.color.base_color));
                    }
                }).create().show();
        return true;
    }
}
