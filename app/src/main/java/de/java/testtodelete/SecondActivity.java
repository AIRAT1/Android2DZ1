package de.java.testtodelete;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

public class SecondActivity extends Activity {
    private EditText editText;
    private LinearLayout linearLayoutRoot;
    private Button button;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        init();
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
    }
}
