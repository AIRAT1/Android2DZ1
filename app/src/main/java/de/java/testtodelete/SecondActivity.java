package de.java.testtodelete;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

public class SecondActivity extends Activity {
    private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        editText = (EditText)findViewById(R.id.editText);
        String companyName = getIntent().getStringExtra(MainActivity.COMPANY_NAME);
        editText.setHint(getResources().getString(R.string.enter_person_name) + " "
                + companyName + " here.");
    }
}
