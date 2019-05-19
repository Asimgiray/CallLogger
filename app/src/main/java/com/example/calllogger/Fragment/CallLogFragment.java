package com.example.calllogger.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.calllogger.Adapter.CallAdapter;
import com.example.calllogger.MainActivity;
import com.example.calllogger.Model.Calls;
import com.example.calllogger.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CallLogFragment extends Fragment {

    View generalView;
    public Context ctx;
    Calls currentCall;
    List<Calls> callList = new ArrayList<>();
    List<Calls> dataBaseCallList = new ArrayList<>();
    TextView txtGetCallDetail;
    ImageView imgFilter;
    RecyclerView rvCallLog;

    FilterFragment filterFragment;


    DatabaseReference dbRef= FirebaseDatabase.getInstance().getReference().child("Calls");


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.call_log_fragment,container,false);

        generalView = rootView;

        ctx = getContext();

        txtGetCallDetail =  generalView.findViewById(R.id.txtGetCallDetail);
        imgFilter = generalView.findViewById(R.id.imgFilter);
        rvCallLog = generalView.findViewById(R.id.rvCallLog);


        getCallDetails();


        txtGetCallDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CallAdapter itemArrayAdapter = new CallAdapter(R.layout.row_call_detail, dataBaseCallList);
                rvCallLog.setLayoutManager(new LinearLayoutManager(ctx));
                rvCallLog.setAdapter(itemArrayAdapter);

            }
        });


        imgFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                filterFragment = new FilterFragment();


                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragMain,filterFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();



            }
        });

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds : dataSnapshot.getChildren())
                {
                    Calls dataBaseCurrentCall = ds.getValue(Calls.class);
                    dataBaseCallList.add(dataBaseCurrentCall);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Log.i("CallListSize" , ":" + dataBaseCallList.size());


        return  rootView;

    }



    public void getCallDetails() {


        StringBuffer sb = new StringBuffer();
        @SuppressLint("MissingPermission") Cursor managedCursor = ctx.getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, null);
        int number = ((Cursor) managedCursor).getColumnIndex(CallLog.Calls.NUMBER);
        int type = ((Cursor) managedCursor).getColumnIndex(CallLog.Calls.TYPE);
        int date = ((Cursor) managedCursor).getColumnIndex(CallLog.Calls.DATE);
        int duration = ((Cursor) managedCursor).getColumnIndex(CallLog.Calls.DURATION);
        int loc = ((Cursor) managedCursor).getColumnIndex(CallLog.Calls.GEOCODED_LOCATION);

        //sb.append("Call Details:\n\n");
        while (managedCursor.moveToNext()) {
            String phNumber = managedCursor.getString(number);
            String callType = managedCursor.getString(type);
            String callDate = managedCursor.getString(date);
            Date callDayTime = new Date(Long.valueOf(callDate));
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM--yy HH:mm");
            String dateString = formatter.format(callDayTime);
            String callDuration = managedCursor.getString(duration);
            String location = managedCursor.getString(loc);
            String dir = null;
            int dircode = Integer.parseInt(callType);
            switch (dircode) {
                case CallLog.Calls.OUTGOING_TYPE:
                    dir = "OUTGOING";
                    break;
                case CallLog.Calls.INCOMING_TYPE:
                    dir = "INCOMING";
                    break;
                case CallLog.Calls.MISSED_TYPE:
                    dir = "MISSED";
                    break;
            }

            currentCall = new Calls(phNumber,dir,dateString,callDuration,location);
            callList.add(currentCall);
            dbRef.push().setValue(currentCall);


            //Log.i("CallDetail", "\nPhone Number: " + phNumber + "\nCallType: " + dir + "\nCall Date: " + dateString + "\nCall Duration: " + callDuration + " sec" + "\nCall Location: " + location);
            Log.i("CallDetail", " : " + callList.get(0).getPhNumber());
            sb.append("\nPhone Number: " + phNumber + "\nCallType: " + dir + "\nCall Date: " + dateString + "\nCall Duration: " + callDuration + " sec" + "\nCall Location: " + location);
            sb.append("\n-------------------------------");

        }
        managedCursor.close();
        //return sb.toString();
    }


}
