package com.sample.drawer.Preview;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import static com.sample.drawer.Preview.Constant.FIRST_COLUMN;
import static com.sample.drawer.Preview.Constant.FOURTH_COLUMN;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import com.sample.drawer.EditCreateBlank.EditBlank;
import com.sample.drawer.R;
import java.util.ArrayList;
import java.util.HashMap;


public class PreviewActivity extends ActionBarActivity{
    private ArrayList<HashMap> list;
    Intent intent;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        setTheme(R.style.AppThemeNonDrawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarPreview);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        intent = getIntent();
        populateList();
        ListView lview = (ListView)findViewById(R.id.previewlist);
        PreviewAdapter adapter = new PreviewAdapter(this, list);
        lview.setAdapter(adapter);


    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_preview, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //Locate Button
        if (id == R.id.action_location) {
            String geo ="geo:<"+intent.getDoubleExtra("latitude",0)+">,<"+intent.getDoubleExtra("longitude",0)+">?q=<"+intent.getDoubleExtra("latitude",0)+">,<"+intent.getDoubleExtra("longitude",0)+">("+intent.getIntExtra("ttCode",0)+")";
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(geo)));
            return true;
        }
        //Edit button
        if (id == R.id.action_edit) {
            Intent edit = new Intent(this,EditBlank.class);
            edit.putExtra("ttCode",intent.getStringExtra("ttCode"));
            edit.putExtra("supervisor",intent.getStringExtra("supervisor"));
            edit.putExtra("supervisorCode",intent.getStringExtra("supervisorCode"));
            edit.putExtra("location",intent.getStringExtra("location"));
            edit.putExtra("agent",intent.getStringExtra("agent"));
            edit.putExtra("agentCode",intent.getStringExtra("agentCode"));
            edit.putExtra("owner",intent.getStringExtra("owner"));
            edit.putExtra("timetable",intent.getStringExtra("timetable"));
            edit.putExtra("region",intent.getStringExtra("region"));
            edit.putExtra("codeSbyta",intent.getStringExtra("codeSbyta"));
            edit.putExtra("ttCode",intent.getStringExtra("ttCode"));
            edit.putExtra("tt",intent.getStringExtra("tt"));
            edit.putExtra("latitude",intent.getDoubleExtra("latitude",0));
            edit.putExtra("longitude",intent.getDoubleExtra("longitude",0));
            startActivity(edit);

        }

        return super.onOptionsItemSelected(item);
    }

     //Create ArrayList for Adapter
    private void populateList() {

        list = new ArrayList<HashMap>();

        HashMap temp = new HashMap();
        temp.put(FIRST_COLUMN,getString(R.string.ttCode));
        temp.put(FOURTH_COLUMN, intent.getStringExtra("ttCode"));
        list.add(temp);

        HashMap temp1 = new HashMap();
        temp1.put(FIRST_COLUMN,getString(R.string.region));
        temp1.put(FOURTH_COLUMN, intent.getStringExtra("region"));
        list.add(temp1);

        HashMap temp2 = new HashMap();
        temp2.put(FIRST_COLUMN,getString(R.string.agent));
        temp2.put(FOURTH_COLUMN, intent.getStringExtra("agent"));
        list.add(temp2);

        HashMap temp3 = new HashMap();
        temp3.put(FIRST_COLUMN,getString(R.string.agentCode));
        temp3.put(FOURTH_COLUMN, String.valueOf(intent.getStringExtra("agentCode")));
        list.add(temp3);

        HashMap temp4 = new HashMap();
        temp4.put(FIRST_COLUMN,getString(R.string.supervisor));
        temp4.put(FOURTH_COLUMN, intent.getStringExtra("supervisor"));
        list.add(temp4);

        HashMap temp5 = new HashMap();
        temp5.put(FIRST_COLUMN,getString(R.string.supervisorCode));
        temp5.put(FOURTH_COLUMN, String.valueOf(intent.getStringExtra("supervisorCode")));
        list.add(temp5);

        HashMap temp6 = new HashMap();
        temp6.put(FIRST_COLUMN,getString(R.string.kanalSbyta));
        temp6.put(FOURTH_COLUMN, intent.getStringExtra("codeSbyta"));
        list.add(temp6);

        HashMap temp7 = new HashMap();
        temp7.put(FIRST_COLUMN,getString(R.string.ttType));
        if(intent.getBooleanExtra("ttType",false)==true)
        {
            temp7.put(FOURTH_COLUMN, getString(R.string.ttTypeTrue));
        }else{
            temp7.put(FOURTH_COLUMN, getString(R.string.ttTypeFalse));
        }
        list.add(temp7);

        HashMap temp8 = new HashMap();
        temp8.put(FIRST_COLUMN,getString(R.string.tt));
        temp8.put(FOURTH_COLUMN, intent.getStringExtra("tt"));
        list.add(temp8);

        HashMap temp9 = new HashMap();
        temp9.put(FIRST_COLUMN,getString(R.string.timetable));
        temp9.put(FOURTH_COLUMN, intent.getStringExtra("timetable"));
        list.add(temp9);

        HashMap temp10 = new HashMap();
        temp10.put(FIRST_COLUMN,getString(R.string.location));
        temp10.put(FOURTH_COLUMN, intent.getStringExtra("location"));
        list.add(temp10);

        HashMap temp11 = new HashMap();
        temp11.put(FIRST_COLUMN,getString(R.string.owner));
        temp11.put(FOURTH_COLUMN, intent.getStringExtra("owner"));
        list.add(temp11);

    }
}



