package com.sample.drawer.CalendarList;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.sample.drawer.R;
import com.sample.drawer.realm.Report;

import java.util.List;

import io.realm.RealmResults;

/**
 * Created by Daniyar_Amangeldy on 08/12/15.
 */
public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportViewHolder> {
   //Recycler View Adapter For ReportActivity
    public static class ReportViewHolder extends RecyclerView.ViewHolder  {
        static TextView text1;
        static TextView text2;
        LinearLayout layout;

        ReportViewHolder(View itemView) {
            super(itemView);
            text1 = (TextView) itemView.findViewById(R.id.ttCodeItem);
            text2 = (TextView) itemView.findViewById(R.id.profitItem);
            layout = (LinearLayout) itemView.findViewById(R.id.itemlayout);
        }


    }

    List<ReportList> report;

    ReportAdapter(List<ReportList> report) {
        this.report = report;
    }

    public int getItemCount() {
        return report.size();
    }

    public ReportViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.report_item, viewGroup, false);
        ReportViewHolder pvh = new ReportViewHolder(v);
        return pvh;
    }
    public void onBindViewHolder(ReportViewHolder ViewHolder, int i) {
        ReportViewHolder.text1.setText(report.get(i).ttCode);
        ReportViewHolder.text2.setText(String.valueOf(report.get(i).profit)+"₸");
        ViewHolder.itemView.setLongClickable(true);
    }



    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
    public List<ReportList> getResults(){
        return report;
    }
}

