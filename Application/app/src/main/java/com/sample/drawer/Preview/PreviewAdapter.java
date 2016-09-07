package com.sample.drawer.Preview;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sample.drawer.R;
import static com.sample.drawer.Preview.Constant.FIRST_COLUMN;
import static com.sample.drawer.Preview.Constant.FOURTH_COLUMN;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Daniyar_Amangeldy on 03/12/15.
 */
public class PreviewAdapter extends BaseAdapter {
    public ArrayList<HashMap> list;
    Activity activity;

    public PreviewAdapter(Activity activity, ArrayList<HashMap> list) {
        super();
        this.activity = activity;
        this.list = list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    private class ViewHolder {
        TextView txtFirst;
        TextView txtFourth;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        // TODO Auto-generated method stub
        ViewHolder holder;
        LayoutInflater inflater =  activity.getLayoutInflater();

        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.preview_row, null);
            holder = new ViewHolder();
            holder.txtFirst = (TextView) convertView.findViewById(R.id.FirstText);
            holder.txtFourth = (TextView) convertView.findViewById(R.id.FourthText);
            holder.txtFirst.setTextSize(18);
            holder.txtFourth.setTextSize(18);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        HashMap map = list.get(position);

        holder.txtFirst.setText(String.valueOf(map.get(FIRST_COLUMN)));
        holder.txtFourth.setText(String.valueOf(map.get(FOURTH_COLUMN)));


        return convertView;
    }

}