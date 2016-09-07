package com.sample.drawer.CalendarList;


import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import com.sample.drawer.R;
import com.sample.drawer.realm.Owner;
import com.sample.drawer.realm.Report;
import fr.ganfra.materialspinner.MaterialSpinner;
import io.realm.Realm;
import io.realm.RealmResults;

public class FillReport extends ActionBarActivity {

    String[] items;
    MaterialSpinner spinner;
    TextInputLayout text;
    Realm realm;
    Intent received;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_report);
        realm = Realm.getInstance(this);
        realm.setAutoRefresh(true);
        RealmResults<Owner> report = realm.where(Owner.class).findAll();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarFillReport);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        received = getIntent();
        items = new String[report.size()];


        for (int i = 0; i < report.size(); i++) {
            items[i] = report.get(i).getTt_code();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = (MaterialSpinner) findViewById(R.id.input_ttCode);
        spinner.setAdapter(adapter);
        text = (TextInputLayout) findViewById(R.id.input_layout_money);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_fill_blank, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();
        if (id == android.R.id.home) {
            setResult(0,received);
            finish();
        }


        if (id == R.id.action_save) {
            if (spinner.getSelectedItem().toString().equals(getString(R.string.ttCode)) && text.getEditText().getText().toString().trim().isEmpty()) {
                spinner.setError(getString(R.string.FillErrorDescription));
                text.setError(getString(R.string.itemFillErrorDescription));
            } else if (text.getEditText().getText().toString().trim().isEmpty()) {
                text.setError(getString(R.string.itemFillErrorDescription));
            }else if (spinner.getSelectedItem().toString().equals(getString(R.string.ttCode))) {
                spinner.setError(getString(R.string.FillErrorDescription));
            } else {
                realm.beginTransaction();
                Report r = realm.createObject(Report.class);
                Owner owner = realm.where(Owner.class).equalTo("tt_code", spinner.getSelectedItem().toString()).findFirst();
                r.setOwner(owner);
                r.setDayId(received.getStringExtra("id"));
                r.setMoney(Integer.valueOf(text.getEditText().getText().toString()));
                r.setTtCode(owner.getTt_code());
                realm.commitTransaction();
                received.putExtra("id",r.getDayId());
                setResult(1, received);
                finish();

            }
        }
        return true;
    }

    public void hideSoftKeyboard(View view) {InputMethodManager inputMethodManager = (InputMethodManager)  getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    public void onBackPressed() {
        setResult(0,received);
        finish();
        super.onBackPressed();

    }

}




