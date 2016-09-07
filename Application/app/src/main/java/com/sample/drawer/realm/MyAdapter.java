package com.sample.drawer.realm;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;
import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;



/**
 * Created by Daniyar_Amangeldy on 02/12/15.
 */
    public class MyAdapter extends RealmBaseAdapter<Owner> implements ListAdapter {

    /*Create Adapter for Fragment1*/

    private static class MyViewHolder {
        TextView tt_code;
        TextView supervisor;
    }

    public MyAdapter(Activity context, int resId,
                     RealmResults<Owner> realmResults,
                     boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
        this.context = context;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder viewHolder;

        if (convertView == null) {
            convertView = inflater.inflate(android.R.layout.simple_expandable_list_item_2,
                    null);
            viewHolder = new MyViewHolder();
            viewHolder.supervisor = (TextView) convertView.findViewById(android.R.id.text2);
            viewHolder.tt_code = (TextView) convertView.findViewById(android.R.id.text1);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (MyViewHolder) convertView.getTag();

        }


        Owner item = realmResults.get(position);
        viewHolder.tt_code.setText(item.getTt_code() + "");
        viewHolder.supervisor.setText(item.getSupervisor() + "");

        return convertView;
    }

    public RealmResults<Owner> getRealmResults() {
        return realmResults;
    }


}
