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
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import data.DBHelper;

public class MainActivity extends Activity implements View.OnClickListener, AdapterView.OnItemLongClickListener{
    public static final String LOG = "LOG";
    private ListView listView;
    private EditText editText;
    private Button button;
    private ArrayAdapter<String> adapter;
    private Animation animation;
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private ContentValues cv;
    private Cursor c;

    private List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        dbHelper = new DBHelper(this, "myTable", null, 1);
        load();
        listView = (ListView) findViewById(R.id.listView);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, String.valueOf(parent.getAdapter().getItem(position)), Toast.LENGTH_SHORT).show();
            }
        });
        listView.setOnItemLongClickListener(this);
        editText = (EditText) findViewById(R.id.editText);
        button = (Button)findViewById(R.id.button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        animation = AnimationUtils.loadAnimation(this, R.anim.rotate);
        button.setBackgroundColor(getResources().getColor(R.color.green));
        button.startAnimation(animation);
        list.add(0, editText.getText().toString());
        adapter.notifyDataSetChanged();
        editText.setText("");
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

    @Override
    protected void onPause() {
        super.onPause();
        save();
    }

    private void save() {
        db = dbHelper.getWritableDatabase();
        db.delete("myTable", null, null);
        cv = new ContentValues();
        for (String s : list) {
            cv.put("name", s);
            db.insert("myTable", null, cv);
        }
    }
    private List<String> load() {
        db = dbHelper.getWritableDatabase();
        c = db.query("myTable", null,
                null, null, null, null, null);
        list = new ArrayList<>();
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            list.add(c.getString(c.getColumnIndex("name")));
        }
        return list;
    }
}