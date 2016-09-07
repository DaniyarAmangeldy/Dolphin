package com.sample.drawer.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.TextView;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.sample.drawer.R;
import com.sample.drawer.fragments.Fragment1;
import com.sample.drawer.fragments.Fragment2;
import com.sample.drawer.fragments.Fragment3;

public class Utils {







    public static void hideSoftKeyboard(Activity activity) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            //
        }

    }


    public static Drawer.OnDrawerItemClickListener handlerOnClick(final Drawer.Result drawerResult, final ActionBarActivity activity) {
        return new Drawer.OnDrawerItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
                //check if the drawerItem is set.
                //there are different reasons for the drawerItem to be null
                //--> click on the header
                //--> click on the footer
                //those items don't contain a drawerItem

                if (drawerItem != null) {

                    if (drawerItem.getIdentifier() == 1) {

                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new Fragment1(),"ListFragment").commit();

                    } else if (drawerItem.getIdentifier() == 2) {

                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new Fragment2()).commit();
                    } else if (drawerItem.getIdentifier() == 5) {

                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new Fragment3()).commit();
                    } else if (drawerItem.getIdentifier() == 3) {
                       MyOtherAlertDialog.create(activity).show();


                    }else if (drawerItem.getIdentifier() == 4) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
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
                                                drawerResult.setSelectionByIdentifier(1, true);
                                                Intent intent = new Intent(Intent.ACTION_MAIN);
                                                intent.addCategory(Intent.CATEGORY_HOME);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                activity.startActivity(intent);
                                            }
                                        });
                        AlertDialog alert = builder.create();
                        alert.show();



                        }
                    } else if (drawerItem.getIdentifier() == 70) {

                        try {
                            Intent int_rate = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + activity.getApplicationContext().getPackageName()));
                            int_rate.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            activity.getApplicationContext().startActivity(int_rate);
                        } catch (Exception e) {
                            //
                        }
                    }

                }

        };
    }



    public static Drawer.Result createCommonDrawer(final ActionBarActivity activity, Toolbar toolbar, AccountHeader.Result headerResult) {

        Drawer.Result drawerResult = new Drawer()
                .withActivity(activity)
                .withHeader(R.layout.drawer_header)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.info_fr1).withIcon(GoogleMaterial.Icon.gmd_list).withIdentifier(1),
                        new PrimaryDrawerItem().withName(R.string.info_fr2).withIcon(GoogleMaterial.Icon.gmd_map).withIdentifier(2),
                        new PrimaryDrawerItem().withName(R.string.info_fr5).withIcon(GoogleMaterial.Icon.gmd_today).withIdentifier(5),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName(R.string.info_fr3).withIcon(GoogleMaterial.Icon.gmd_help).withIdentifier(3),
                        new PrimaryDrawerItem().withName(R.string.info_fr4).withIcon(GoogleMaterial.Icon.gmd_exit_to_app).withIdentifier(4)

                )
                .withOnDrawerListener(new Drawer.OnDrawerListener() {
                    @Override
                    public boolean equals(Object o) {
                        return super.equals(o);
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {

                        hideSoftKeyboard(activity);
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {

                    }
                })
                .build();


        drawerResult.setOnDrawerItemClickListener(handlerOnClick(drawerResult, activity));

        return drawerResult;
    }
    public static class MyOtherAlertDialog {

        public static AlertDialog create(Context context) {
            final TextView message = new TextView(context);
            message.setGravity(Gravity.CENTER_HORIZONTAL);
            message.setPadding(0,10,0,10);
            final SpannableString s =
                    new SpannableString(context.getText(R.string.about_fr3));
            Linkify.addLinks(s, Linkify.ALL);
            message.setText(s);
            message.setMovementMethod(LinkMovementMethod.getInstance());
            return new AlertDialog.Builder(context)
                    .setTitle(R.string.info_fr3)
                    .setCancelable(true)
                    .setIcon(R.drawable.info)
                    .setNegativeButton(R.string.close,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            })
                    .setView(message)
                    .create();
        }
    }
    


}
