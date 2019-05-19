package com.example.calllogger.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.calllogger.Model.Calls;
import com.example.calllogger.R;

import java.util.ArrayList;
import java.util.List;

public class CallAdapter extends RecyclerView.Adapter<CallAdapter.ViewHolder> {

    private int itemLayout;
    private List<Calls> callsList;

    public CallAdapter(int layoutId , List<Calls> callsList) {

        itemLayout = layoutId;
        this.callsList = callsList;


    }

    @NonNull
    @Override
    public CallAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(itemLayout,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CallAdapter.ViewHolder viewHolder, int i) {

        ImageView imgCallType = viewHolder.imgCallType;
        TextView txtNumber = viewHolder.txtNumber;
        TextView txtDate = viewHolder.txtDate;
        TextView txtLocation = viewHolder.txtLocation;
        TextView txtDuration = viewHolder.txtDuration;


        txtNumber.setText(callsList.get(i).getPhNumber());
        txtDate.setText(callsList.get(i).getCallDate());
        txtLocation.setText(callsList.get(i).getLocation() );
        txtDuration.setText(callsList.get(i).getCallDuration() + "  second");

        switch (callsList.get(i).getCallType())
        {
            case "OUTGOING":
                imgCallType.setImageResource(R.drawable.icon_outgoing);
                break;
            case "MISSED":
                imgCallType.setImageResource(R.drawable.icon_missed);
                break;
            case "INCOMING":
                imgCallType.setImageResource(R.drawable.icon_incoming);
                break;
        }


    }

    @Override
    public int getItemCount() {
        return callsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgCallType;
        TextView txtNumber,txtDate,txtLocation,txtDuration;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            imgCallType = itemView.findViewById(R.id.imgCallType);
            txtNumber = itemView.findViewById(R.id.txtNumber);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtLocation = itemView.findViewById(R.id.txtLocation);
            txtDuration = itemView.findViewById(R.id.txtDuration);
        }
    }
}
