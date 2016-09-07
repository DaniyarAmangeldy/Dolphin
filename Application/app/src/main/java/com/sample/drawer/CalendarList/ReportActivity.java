package com.sample.drawer.CalendarList;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.DragEvent;
import android.view.View;
import android.widget.ImageView;

import com.github.fabtransitionactivity.SheetLayout;
import com.sample.drawer.R;
import com.sample.drawer.realm.Report;
import java.util.ArrayList;
import io.realm.Realm;
import io.realm.RealmResults;

public class ReportActivity extends ActionBarActivity  implements SheetLayout.OnFabAnimationEndListener {
    SheetLayout mSheetLayout;
    Intent intent;
    ArrayList<ReportList> list;
    RealmResults<Report> result;
    Realm realm;
    String monthName;
    int day;
    int month;
    int year;
    ReportAdapter adapter;
    RecyclerView lv;
    CoordinatorLayout coordinatorLayout;
    com.bowyer.app.fabtoolbar.FabToolbar mFabToolbar;
    int itemPosition;
    ImageView shareButton;
    ImageView deleteButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarReport);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTheme(R.style.AppThemeNonDrawer);
        mSheetLayout = (SheetLayout) findViewById(R.id.bottom_sheetReport);


        intent = getIntent();
        month = intent.getIntExtra("month", 0);
        day = intent.getIntExtra("day", 0);
        year = intent.getIntExtra("year", 0);

        //Get Name of Month

        switch (month) {

            case 0:
                monthName = getString(R.string.January);
                break;
            case 1:
                monthName = getString(R.string.February);
                break;
            case 2:
                monthName = getString(R.string.March);
                break;
            case 3:
                monthName = getString(R.string.April);
                break;
            case 4:
                monthName = getString(R.string.May);
                break;
            case 5:
                monthName = getString(R.string.June);
                break;
            case 6:
                monthName = getString(R.string.July);
                break;
            case 7:
                monthName = getString(R.string.August);
                break;
            case 8:
                monthName = getString(R.string.September);
                break;
            case 9:
                monthName = getString(R.string.October);
                break;
            case 10:
                monthName = getString(R.string.November);
                break;
            case 11:
                monthName = getString(R.string.December);
                break;
            default:
                monthName = " ";
                break;
        }
        //Add Layout
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(day + " " + monthName + " " + year);
        realm = Realm.getInstance(this);
        result = realm.where(Report.class).equalTo("dayId", intent.getStringExtra("report")).findAll();
        populateList();
        lv = (RecyclerView) findViewById(R.id.reportlist);
        lv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        LinearLayoutManager llm = new LinearLayoutManager(this);
        lv.setLayoutManager(llm);
        adapter = new ReportAdapter(list);
        lv.setAdapter(adapter);

        lv.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        mFabToolbar.expandFab();
                        mFabToolbar.slideInFab();
                        itemPosition=position;
                    }
                })
        );


        //FAB

        FloatingActionButton myFab = (FloatingActionButton) findViewById(R.id.fabReport);
        mFabToolbar = (com.bowyer.app.fabtoolbar.FabToolbar) findViewById(R.id.fabtoolbar);
        mFabToolbar.setFab(myFab);


        mSheetLayout.setFab(myFab);
        mSheetLayout.setFabAnimationEndListener(this);
        myFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSheetLayout.expandFab();

            }
        });
        shareButton = (ImageView)findViewById(R.id.shareButton);
        deleteButton = (ImageView)findViewById(R.id.deleteButton);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share(itemPosition);
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ReportActivity.this);
                builder.setTitle(R.string.delete)
                        .setMessage(R.string.deleteDescription)
                        .setIcon(R.drawable.help)
                        .setCancelable(false)
                        .setNegativeButton(R.string.cancel,
                                new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                })
                        .setPositiveButton(R.string.ok,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        deleteObject(itemPosition,lv,adapter);
                                        mFabToolbar.slideOutFab();
                                        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorReport);
                                        Snackbar snackbar = Snackbar
                                                .make(coordinatorLayout, "Удаление завершено!", Snackbar.LENGTH_LONG);

                                        snackbar.show();

                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();



            }
        });



    }
   //Array List for Adapter
    private void populateList() {
        list = new ArrayList<ReportList>();
        for (int i = 0; i < result.size(); i++) {
            list.add(new ReportList(result.get(i).getOwner().getTt_code(), String.valueOf(result.get(i).getMoney())));



        }
    }






    public void onFabAnimationEnd() {

        Intent fillReport = new Intent(ReportActivity.this, FillReport.class);
        fillReport.putExtra("id", intent.getStringExtra("report"));

        startActivityForResult(fillReport, 1);

    }
    //Refresh Database After Intent
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            realm.refresh();
            result = realm.where(Report.class).equalTo("dayId", data.getStringExtra("id")).findAll();
            populateList();
            adapter = new ReportAdapter(list);
            adapter.notifyDataSetChanged();
            lv.setAdapter(adapter);

            mSheetLayout.contractFab();
        }else{
            mSheetLayout.contractFab();
        }


        }

   /* public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.reportlist) {
            menu.add(Menu.NONE, 2, 1, R.string.delete);
            menu.add(Menu.NONE, 3, 2, R.string.share);

        }
    }
    public boolean onContextItemSelected(MenuItem item) {
        final ContextMenuRecyclerView.RecyclerContextMenuInfo info = (ContextMenuRecyclerView.RecyclerContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case 2:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.delete)
                        .setMessage(R.string.deleteDescription)
                        .setIcon(R.drawable.help)
                        .setCancelable(false)
                        .setNegativeButton(R.string.cancel,
                                new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                })
                        .setPositiveButton(R.string.ok,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        deleteObject(info.position, adapter);
                                        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorReport);
                                        Snackbar snackbar = Snackbar
                                                .make(coordinatorLayout, "Удаление завершено!", Snackbar.LENGTH_LONG);

                                        snackbar.show();

                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();
                break;
            case 3:
                String send = day+" "+monthName+" "+year +"\n"+getString(R.string.ttCode) + ": " + adapter.getResults().get(info.position).ttCode + "\n" +
                              getString(R.string.profit)+": "+adapter.getResults().get(info.position).profit+"₸";
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, send);
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.share)));
                break;
        }


        return false;
    }
    public void deleteObject(int position, ReportAdapter adapter) {


        realm.beginTransaction();
        Report report = realm.where(Report.class).contains("dayId",intent.getStringExtra("report")).equalTo("money",Integer.valueOf(adapter.getResults().get(position).profit)).findFirst();
        report.removeFromRealm();
        realm.commitTransaction();
        realm.refresh();
        result = realm.where(Report.class).equalTo("dayId", intent.getStringExtra("id")).findAll();
        populateList();
        lv.swapAdapter(new ReportAdapter(list), false);


    }*/

    public void hideFabtoolbar(){
        mFabToolbar.expandFab();
        mFabToolbar.slideOutFab();
    }

    public void share(int position) {
        String send = day+" "+monthName+" "+year +"\n"+getString(R.string.ttCode) + ": " + adapter.getResults().get(position).ttCode + "\n" +
                getString(R.string.profit)+": "+adapter.getResults().get(position).profit+"₸";
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, send);
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.share)));
        mFabToolbar.slideOutFab();
    }

    @Override
    public void onBackPressed() {
        if(mFabToolbar.isFabExpanded()==true){
            mFabToolbar.slideOutFab();
        }else {
            super.onBackPressed();
        }
    }
    public void deleteObject(int position,RecyclerView lv,ReportAdapter adapter){

        realm.beginTransaction();
        Report report = realm.where(Report.class).contains("dayId",intent.getStringExtra("report")).equalTo("money",Integer.valueOf(adapter.getResults().get(position).profit)).findFirst();
        report.removeFromRealm();
        realm.commitTransaction();
        list.remove(position);
        lv.removeViewAt(position);
        adapter.notifyItemRemoved(position);
        adapter.notifyItemRangeChanged(position, list.size());

    }
}


