package com.example.queueclient.views.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.queueclient.R;
import com.example.queueclient.persistences.Server;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

/**
 * Created by shintabmt on 7/1/2016.
 */
public class ServerAdapter extends RecyclerView.Adapter<ServerAdapter.ViewHolder> {
    public interface OnSettingClickListener {
        void onSettingClick(Server server);
    }

    private List<Server> serverList = new ArrayList<>();
    private OnSettingClickListener onSettingClickListener;

    public void setItems(List<Server> serverList) {
        this.serverList = serverList;
        notifyDataSetChanged();
    }

    public OnSettingClickListener getOnSettingClickListener() {
        return onSettingClickListener;
    }

    public void setOnSettingClickListener(OnSettingClickListener onSettingClickListener) {
        this.onSettingClickListener = onSettingClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.serverIp)
        TextView ip;
        @BindView(R.id.serverName)
        TextView name;
        @BindView(R.id.serverSetting)
        ImageView setting;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.server_list_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Server item = serverList.get(position);
        holder.ip.setText(item.getIpAddress());
        holder.name.setText(item.getName());
        holder.setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onSettingClickListener == null) {
                    return;
                }
                onSettingClickListener.onSettingClick(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return serverList.size();
    }
}
