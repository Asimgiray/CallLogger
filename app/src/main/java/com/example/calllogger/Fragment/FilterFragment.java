package com.example.calllogger.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

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

public class FilterFragment extends Fragment {

    View generalView;
    Context ctx;

    EditText edtLocation;
    LinearLayout linSearchLocation;
    RecyclerView rvSearchResults;

    Calls searchCall;
    List<Calls> searchList = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.filter_fragment,container,false);
        ctx = getContext();

        generalView = rootView;

        edtLocation = generalView.findViewById(R.id.edtLocation);
        linSearchLocation = generalView.findViewById(R.id.linSearchLocation);
        rvSearchResults = generalView.findViewById(R.id.rvSearchResults);



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

            searchCall = new Calls(phNumber,dir,dateString,callDuration,location);
            searchList.add(searchCall);



            //Log.i("CallDetail", "\nPhone Number: " + phNumber + "\nCallType: " + dir + "\nCall Date: " + dateString + "\nCall Duration: " + callDuration + " sec" + "\nCall Location: " + location);
            Log.i("CallDetail", " : " + searchList.get(0).getPhNumber());
            sb.append("\nPhone Number: " + phNumber + "\nCallType: " + dir + "\nCall Date: " + dateString + "\nCall Duration: " + callDuration + " sec" + "\nCall Location: " + location);
            sb.append("\n-------------------------------");

        }
        managedCursor.close();
        //return sb.toString();
    }
}
