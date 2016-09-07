package com.sample.drawer.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListView;

import com.github.fabtransitionactivity.SheetLayout;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.mikepenz.materialdrawer.Drawer;
import com.sample.drawer.EditCreateBlank.EditBlank;
import com.sample.drawer.OnBackPressedListener;
import com.sample.drawer.Preview.PreviewActivity;
import com.sample.drawer.realm.MyAdapter;
import com.sample.drawer.realm.Owner;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import com.sample.drawer.EditCreateBlank.FillBlank;
import com.sample.drawer.R;
import com.sample.drawer.realm.Report;

import java.util.ArrayList;


public class Fragment1 extends Fragment implements SheetLayout.OnFabAnimationEndListener,OnBackPressedListener {
    SheetLayout mSheetLayout;
    MyAdapter adapter1;
    Realm realm;
    String supervisor;
    String agent;
    String ttCode;
    private Drawer.Result drawerResult = null;
    String region;
    String agentCode;
    String supervisorCode;
    String codeSbyta;
    String location;
    String tt;
    MaterialSearchView searchView;
    String ownerResult;
    String timetable;
    CoordinatorLayout coordinatorLayout;
    RealmResults<Owner> owner;
    double latitude;
    double longitude;
    boolean ttType;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        realm = Realm.getInstance(getActivity());
        setHasOptionsMenu(true);
        realm.beginTransaction();
        realm.setAutoRefresh(true);
        getActivity().setTitle(R.string.info_fr1);
        View view = inflater.inflate(R.layout.fragment_1, container, false);
        mSheetLayout = (SheetLayout) view.findViewById(R.id.bottom_sheet);
        // находим список
        final ListView lvMain = (ListView) view.findViewById(R.id.list);
        lvMain.setTextFilterEnabled(true);
        owner = realm.where(Owner.class).findAll();

        // создаем адаптер
        adapter1 = new MyAdapter(getActivity(), 1, owner, true);
        registerForContextMenu(lvMain);


        //Send Data to PreviewActivity
        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                realm.beginTransaction();

                //Get result from Item
                supervisor = adapter1.getRealmResults().get(position).getSupervisor();
                agent = adapter1.getRealmResults().get(position).getAgent();
                tt = adapter1.getRealmResults().get(position).getTt();
                ttCode = adapter1.getRealmResults().get(position).getTt_code();
                region = adapter1.getRealmResults().get(position).getRegion();
                agentCode = adapter1.getRealmResults().get(position).getAgent_code();
                supervisorCode = adapter1.getRealmResults().get(position).getSupervisor_code();
                codeSbyta = adapter1.getRealmResults().get(position).getCode_sbyta();
                ttType = adapter1.getRealmResults().get(position).getTt_type();
                location = adapter1.getRealmResults().get(position).getLocation();
                ownerResult = adapter1.getRealmResults().get(position).getOwner();
                timetable = adapter1.getRealmResults().get(position).getTimetable();
                latitude = adapter1.getRealmResults().get(position).getLatitude();
                longitude = adapter1.getRealmResults().get(position).getLongitude();
                realm.commitTransaction();


                Intent intent = new Intent(getActivity(), PreviewActivity.class);

                //Send results to DetailActivity
                intent.putExtra("supervisor", supervisor);
                intent.putExtra("agent", agent);
                intent.putExtra("ttCode", ttCode);
                intent.putExtra("region", region);
                intent.putExtra("agentCode", agentCode);
                intent.putExtra("supervisorCode", supervisorCode);
                intent.putExtra("codeSbyta", codeSbyta);
                intent.putExtra("ttType", ttType);
                intent.putExtra("tt", tt);
                intent.putExtra("location", location);
                intent.putExtra("owner", ownerResult);
                intent.putExtra("timetable", timetable);
                intent.putExtra("latitude", latitude);
                intent.putExtra("longitude", longitude);


                startActivity(intent);
            }
        });


        // присваиваем адаптер списку
        lvMain.setAdapter(adapter1);
        realm.commitTransaction();
        FloatingActionButton myFab = (FloatingActionButton) view.findViewById(R.id.fabBtn);
        mSheetLayout.setFab(myFab);
        mSheetLayout.setFabAnimationEndListener(this);
        myFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSheetLayout.expandFab();
            }
        });


        searchView = (MaterialSearchView) getActivity().findViewById(R.id.search_view);
        searchView.setVoiceSearch(true);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Do some magic
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                RealmQuery<Owner> realmQuery = realm.where(Owner.class).contains("supervisor", newText.toString()).or().contains("tt_code", newText.toString());
                owner = realmQuery.findAll();
                adapter1.updateRealmResults(owner);

                return false;
            }
        });


        return view;

    }

    //Creating Context menu (on long click item)
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.list) {
            menu.add(Menu.NONE, 1, 0, R.string.edit);
            menu.add(Menu.NONE, 2, 1, R.string.delete);
            menu.add(Menu.NONE, 3, 2, R.string.share);

        }
    }

    public boolean onContextItemSelected(MenuItem item) {

        // Get extra info about list item that was long-pressed
        final AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        // Perform action according to selected item from context menu
        switch (item.getItemId()) {

            case 1:
                // Edit
                realm.beginTransaction();


                supervisor = adapter1.getRealmResults().get(menuInfo.position).getSupervisor();
                agent = adapter1.getRealmResults().get(menuInfo.position).getAgent();
                ttCode = adapter1.getRealmResults().get(menuInfo.position).getTt_code();
                tt = adapter1.getRealmResults().get(menuInfo.position).getTt();
                region = adapter1.getRealmResults().get(menuInfo.position).getRegion();
                agentCode = adapter1.getRealmResults().get(menuInfo.position).getAgent_code();
                supervisorCode = adapter1.getRealmResults().get(menuInfo.position).getSupervisor_code();
                codeSbyta = adapter1.getRealmResults().get(menuInfo.position).getCode_sbyta();
                ttType = adapter1.getRealmResults().get(menuInfo.position).getTt_type();
                location = adapter1.getRealmResults().get(menuInfo.position).getLocation();
                ownerResult = adapter1.getRealmResults().get(menuInfo.position).getOwner();
                timetable = adapter1.getRealmResults().get(menuInfo.position).getTimetable();
                latitude = adapter1.getRealmResults().get(menuInfo.position).getLatitude();
                longitude = adapter1.getRealmResults().get(menuInfo.position).getLongitude();
                realm.commitTransaction();


                Intent intent = new Intent(getActivity(), EditBlank.class);
                intent.putExtra("supervisor", supervisor);
                intent.putExtra("agent", agent);
                intent.putExtra("ttCode", ttCode);
                intent.putExtra("region", region);
                intent.putExtra("agentCode", agentCode);
                intent.putExtra("supervisorCode", supervisorCode);
                intent.putExtra("codeSbyta", codeSbyta);
                intent.putExtra("ttType", ttType);
                intent.putExtra("tt", tt);
                intent.putExtra("location", location);
                intent.putExtra("owner", ownerResult);
                intent.putExtra("timetable", timetable);
                intent.putExtra("latitude", latitude);
                intent.putExtra("longitude", longitude);
                startActivity(intent);
                break;

            case 2:
                // Delete

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
                                        deleteObject(menuInfo.position, adapter1);
                                        coordinatorLayout = (CoordinatorLayout) getActivity().findViewById(R.id.coordinatorLayout);
                                        Snackbar snackbar = Snackbar
                                                .make(coordinatorLayout, "Удаление завершено!", Snackbar.LENGTH_LONG);

                                        snackbar.show();

                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();


                break;
            case 3:
                Owner owner = adapter1.getRealmResults().get(menuInfo.position);
                String send = getString(R.string.ttCode) + ": " + owner.getTt_code() + "\n" + getString(R.string.supervisor) + ": " +
                        owner.getSupervisor() + "(" + owner.getSupervisor_code() + ")\n" + getString(R.string.agent) +
                        ": " + owner.getAgent() + "(" + owner.getAgent_code() + ")";
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, send);
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.share)));

        }

            return true;
    }



    //Delete Function
    public void deleteObject(int position, MyAdapter adapter) {


        realm.beginTransaction();
        Owner owner = adapter.getRealmResults().get(position);
        if(realm.where(Report.class).contains("ttCode",adapter.getRealmResults().get(position).getTt_code()).findAll().size()>=1){
        Report report = realm.where(Report.class).contains("ttCode",adapter.getRealmResults().get(position).getTt_code()).findFirst();
            report.removeFromRealm();
        }
        owner.removeFromRealm();
        realm.commitTransaction();
        adapter.notifyDataSetChanged();


    }

    //Search View
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);


        super.onCreateOptionsMenu(menu, inflater);

    }



    @Override
    public void onFabAnimationEnd() {
        startActivityForResult(new Intent(getActivity(), FillBlank.class), 1);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);




        if(requestCode == 1){
            mSheetLayout.contractFab();
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && matches.size() > 0) {
                String searchWrd = matches.get(0);
                if (!TextUtils.isEmpty(searchWrd)) {
                    searchView.setQuery(searchWrd, false);
                }
            }

            return;
        }else{
            mSheetLayout.contractFab();
        }
    }




        public void onBackPressed(){
            if (searchView.isSearchOpen()) {
                searchView.closeSearch();
            }else {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.info_fr4)
                        .setMessage(R.string.exit_confirm)
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
                                        Intent intent = new Intent(Intent.ACTION_MAIN);
                                        intent.addCategory(Intent.CATEGORY_HOME);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();
            }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        searchView.closeSearch();
    }
    public void hideSoftKeyboard(View view) {InputMethodManager inputMethodManager = (InputMethodManager)  getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    public void onStop() {
        super.onStop();
        searchView.closeSearch();
    }
}




