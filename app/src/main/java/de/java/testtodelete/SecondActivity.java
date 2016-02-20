package de.java.testtodelete;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class SecondActivity extends Activity implements AdapterView.OnItemLongClickListener{
    private EditText editText;
    private LinearLayout linearLayoutRoot;
    private Button button;
    private ListView listView;
    private List<String> list;
    private Animation animation;
    private ArrayAdapter<String> adapter;
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
        String companyName = getIntent().getStringExtra(MainActivity.COMPANY_NAME);
        editText.setHint(getResources().getString(R.string.enter_person_name) + " "
                + companyName + " here.");
        linearLayoutRoot = (LinearLayout)findViewById(R.id.linearLayoutRoot);
        linearLayoutRoot.setBackgroundColor(getResources().getColor(R.color.linearLayoutRoot));
        editText.setBackgroundColor(getResources().getColor(R.color.linearLayoutRoot));
        listView = (ListView)findViewById(R.id.listView);
        listView.setBackgroundColor(getResources().getColor(R.color.linearLayoutRoot));
        button = (Button)findViewById(R.id.button);

        list = new ArrayList<>();

        adapter = new ArrayAdapter<>(SecondActivity.this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(this);
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
