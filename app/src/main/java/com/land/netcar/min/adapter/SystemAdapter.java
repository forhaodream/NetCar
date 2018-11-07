package com.land.netcar.min.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.land.netcar.R;
import com.land.netcar.min.AboutActivity;
import com.land.netcar.min.BindActivity;
import com.land.netcar.min.FeedbackActivity;
import com.land.netcar.min.MapActivity;
import com.land.netcar.min.RechargeActivity;
import com.land.netcar.min.SystemActivity;
import com.land.netcar.min.UpdateActivity;

import java.util.List;

/**
 * Created by VULCAN on 2018/2/28.
 */

public class SystemAdapter extends RecyclerView.Adapter {
    private final SystemActivity systemActivity;
    private final LayoutInflater inflater;
    private final List<String> stringA;
    private final List<Integer> stringB;
    private Intent intent;

    public SystemAdapter(SystemActivity systemActivity, List<String> stringA, List<Integer> stringB) {
        this.systemActivity = systemActivity;
        this.stringA = stringA;
        this.stringB = stringB;

        inflater = LayoutInflater.from(systemActivity);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SystemHolder(inflater.inflate(R.layout.system_main_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof SystemHolder) {
            ((SystemHolder) holder).tv.setText(stringA.get(position));
            Glide.with(systemActivity).load(stringB.get(position)).into(((SystemHolder) holder).iv);
            ((SystemHolder) holder).onc_system.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (position == 0) {
                        intent = new Intent(systemActivity, MapActivity.class);
                        systemActivity.startActivity(intent);
                    }
                    if (position == 1) {
                        intent = new Intent(systemActivity, BindActivity.class);
                        systemActivity.startActivity(intent);
                    }
                    if (position == 2) {
                        intent = new Intent(systemActivity, RechargeActivity.class);
                        systemActivity.startActivity(intent);
                    }
                    if (position == 3) {
                        intent = new Intent(systemActivity, FeedbackActivity.class);
                        systemActivity.startActivity(intent);
                    }
//                    if (position == 4) {
//                        intent = new Intent(systemActivity, UpdateActivity.class);
//                        systemActivity.startActivity(intent);
//                    }
                    if (position == 4) {
                        intent = new Intent(systemActivity, AboutActivity.class);
                        intent.putExtra("ssd","1");
                        systemActivity.startActivity(intent);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        Log.e("TAGddwa", ""+stringA.size());
        return stringA.size();
    }

    private class SystemHolder extends RecyclerView.ViewHolder {
        private RelativeLayout onc_system;
        private ImageView iv;
        private TextView tv;

        public SystemHolder(View inflate) {
            super(inflate);

            onc_system = inflate.findViewById(R.id.onc_system);
            iv = inflate.findViewById(R.id.iv);
            tv = inflate.findViewById(R.id.tv);
        }
    }
}
