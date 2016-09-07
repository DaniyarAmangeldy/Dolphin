package com.sample.drawer.EditCreateBlank;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import com.sample.drawer.Location;
import com.sample.drawer.R;
import com.sample.drawer.realm.Owner;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import java.util.Calendar;

import fr.ganfra.materialspinner.MaterialSpinner;
import io.realm.Realm;



public class FillBlank extends ActionBarActivity {
    MaterialSpinner supervisor;
    MaterialSpinner agent;
    EditText ttCode;
    EditText location;
    EditText tt;
    String supervisorCode;



    String agentCode;
    EditText ownerResult;
    Switch ttType;
    RadioGroup kanalSbytaRadio;
    EditText region;
    RadioButton kanalSbytaAL;
    RadioButton kanalSbytaMB;
    RadioButton kanalSbytaML;
    double latitude;
    double longitude;
    String timetable;
    int kanalSbytaId;
    Intent intent;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_blank);
        setTheme(R.style.AppThemeNonDrawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.Filltoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        intent = new Intent();
        String[] supervisorItems = new String[]{"Пахирдинов Искандер", "Меирбеков Тимур", "Светличный Михаил"};

        String[] agentItems = new String[]{"Лысенко Роман","Байтемиров Марат","Осадчий Александр","Плотников Иван",
                                           "Тазабеков Нуржан","Тайфуров Алик","Зейначев Данияр","Иминов Тахир",
                                            "Абдрахманов Бахтияр","Ветров Александр","Сейсенов Нурсултан","Мамиров Данияр",
                                            "Шаймухамбетов Мурат"};

        //Create Spinner
        ArrayAdapter<String> supervisorAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, supervisorItems);
        ArrayAdapter<String> agentAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, agentItems);
        supervisorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        agentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        supervisor = (MaterialSpinner) findViewById(R.id.supervisorBlank);
        agent = (MaterialSpinner) findViewById(R.id.agentBlank);
        supervisor.setAdapter(supervisorAdapter);
        agent.setAdapter(agentAdapter);

       //Declare Fields
        ttCode = (EditText) findViewById(R.id.ttCodeBlank);
        tt = (EditText) findViewById(R.id.ttBlank);
        ttType = (Switch) findViewById(R.id.ttTypeSwitch);
        location = (EditText) findViewById(R.id.locationBlank);
        ownerResult = (EditText) findViewById(R.id.ownerBlank);
        region = (EditText) findViewById(R.id.regionBlank);
        kanalSbytaRadio = (RadioGroup) findViewById(R.id.kanalSbytaRadio);
        kanalSbytaAL = (RadioButton)findViewById(R.id.kanalSbytaAlradioRadioButton);
        kanalSbytaMB = (RadioButton)findViewById(R.id.kanalSbytaMbradioButton);
        kanalSbytaML = (RadioButton)findViewById(R.id.kanalSbytaMlradioButton);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_fill_blank, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();






        //Save Data to Database
        if (id == R.id.action_save) {


            if (ownerResult.getText().toString().equals("") ||
                    supervisor.getSelectedItem().toString().equals(getString(R.string.supervisor)) ||
                    location.getText().toString().equals("") ||
                    agent.getSelectedItem().toString().equals(getString(R.string.agent)) ||
                    tt.getText().toString().equals("") ||
                    ttCode.getText().toString().equals("") ||
                    timetable.equals("") ||
                    region.getText().toString().equals("")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.FillErrorTitle)
                        .setMessage(R.string.FillErrorDescription)
                        .setIcon(R.drawable.error)
                        .setCancelable(false)
                        .setNegativeButton(R.string.close,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();

            } else{

                Realm realm = Realm.getInstance(this);
                realm.beginTransaction();

                Owner owner = realm.createObject(Owner.class);
                owner.setOwner(ownerResult.getText().toString());
                owner.setAgent(agent.getSelectedItem().toString());
                owner.setLocation(location.getText().toString());
                owner.setRegion(region.getText().toString());
                owner.setTt(tt.getText().toString());
                owner.setLatitude(latitude);
                owner.setLongitude(longitude);
                owner.setSupervisor(supervisor.getSelectedItem().toString());
                owner.setTimetable(timetable);
                owner.setTt_code(ttCode.getText().toString());
                owner.setTt_type(ttType.isChecked());
                kanalSbytaId = kanalSbytaRadio.getCheckedRadioButtonId();
                //Kanal Sbyta

                switch (kanalSbytaId) {
                    case R.id.kanalSbytaAlradioRadioButton:
                        owner.setCode_sbyta("AL");
                        break;
                    case R.id.kanalSbytaMbradioButton:
                        owner.setCode_sbyta("MB");
                        break;
                    case R.id.kanalSbytaMlradioButton:
                        owner.setCode_sbyta("ML");
                        break;
                }
                 //Set AgentCode and Supervisor Code
                switch(supervisor.getSelectedItemPosition()) {
                    case 0:
                        supervisorCode = "4210000000000008";
                        break;
                    case 1:
                        supervisorCode = "0210000000000025";
                        break;
                    case 2:
                        supervisorCode = "4210000000000001";
                        break;
                    default:
                        supervisorCode = " ";
                        break;
                }
                switch(agent.getSelectedItemPosition()) {
                    case 0:
                        agentCode = "4210000000000019";
                        break;
                    case 1:
                        agentCode = "0210000000000024";
                        break;
                    case 2:
                        agentCode = "4210000000000004";
                        break;
                    case 3:
                        agentCode = "4210000000000015";
                        break;
                    case 4:
                        agentCode = "0210000000000026";
                        break;
                    case 5:
                        agentCode = "0210000000000027";
                        break;
                    case 6:
                        agentCode = "0210000000000022";
                        break;
                    case 7:
                        agentCode = "0210000000000023";
                        break;
                    case 8:
                        agentCode = "4210000000000010";
                        break;
                    case 9:
                        agentCode = "4210000000000003";
                        break;
                    case 10:
                        agentCode = "0210000000000020";
                        break;
                    case 11:
                        agentCode = "0210000000000029";
                        break;
                    case 12:
                        agentCode = "4210000000000016";
                        break;

                    default:
                        agentCode = " ";
                        break;
                }
                owner.setSupervisor_code(supervisorCode);
                owner.setAgent_code(agentCode);

                realm.commitTransaction();
                setResult(RESULT_OK, intent);
                finish();
                return true;
            }


            }

        if (id == android.R.id.home) {
            setResult(0,intent);
            finish();
        }



            return super.onOptionsItemSelected(item);
        }

   //Location Button
    public void goLocate(View view) {
        Intent locationIntent = new Intent(this,Location.class);
        startActivityForResult(locationIntent,1);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {return;}
         latitude=data.getDoubleExtra("latitude",0);
         longitude=data.getDoubleExtra("longitude",0);



    }

    //If EditText is not focused , keyboard will hide
    public void hideSoftKeyboard(View view) {InputMethodManager inputMethodManager = (InputMethodManager)  getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }


    //Set Date button
    public void goTimetable(View view) {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        monthOfYear++;
                        String day="" ,month="";
                        if(dayOfMonth<10){ day = "0"+dayOfMonth;}else{ day =dayOfMonth+"";}
                        if(monthOfYear<10){ month = "0"+monthOfYear;}else{ month =monthOfYear+"";}
                        timetable = day+"."+month+"."+year;
                        Snackbar snackbar = Snackbar
                                .make(findViewById(android.R.id.content), timetable, Snackbar.LENGTH_LONG);
                        snackbar.show();

                    }
                },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "Datepickerdialog");

    }

    public void onBackPressed() {
        setResult(0,intent);
        finish();
        super.onBackPressed();
    }

}




