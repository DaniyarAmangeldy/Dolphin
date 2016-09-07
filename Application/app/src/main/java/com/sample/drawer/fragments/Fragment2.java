package com.sample.drawer.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sample.drawer.OnBackPressedListener;
import com.sample.drawer.R;
import com.sample.drawer.realm.Owner;
import io.realm.Realm;
import io.realm.RealmResults;


public class Fragment2 extends Fragment implements OnBackPressedListener{

    MapView mMapView;
    private GoogleMap googleMap;
    RealmResults<Owner> result;
    Realm realm;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle(R.string.info_fr2);
        realm = Realm.getInstance(getActivity());

        /*android.support.v7.app.ActionBar actionBar = ((ActionBarActivity)getActivity()).getSupportActionBar();*/

        // inflate and return the layout
        View v = inflater.inflate(R.layout.fragment_2, container,
                false);
        mMapView = (MapView) v.findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        Realm realm = Realm.getInstance(getActivity());
        result = realm.where(Owner.class).findAll();

        mMapView.onResume();// needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        googleMap = mMapView.getMap();

            //add marker from Owner
            for(int j=0;j<result.size();j++){
                googleMap.addMarker(new MarkerOptions().position(
                        new LatLng(result.get(j).getLatitude(), result.get(j).getLongitude())).icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_ROSE))
                        .title(getString(R.string.ttCode)+
                                ": "+
                                String.valueOf(result.get(j).getTt_code())+
                                "\n"+
                                getString(R.string.supervisor)+
                                ": "+
                                result.get(j).getSupervisor()));


            }


         //Set Camera Position
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(43.255058, 76.912628)).zoom(13).build();
        googleMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));

        // Perform any camera updates here
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onBackPressed() {
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




