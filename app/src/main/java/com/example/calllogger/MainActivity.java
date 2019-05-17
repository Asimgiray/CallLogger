package com.example.calllogger;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.CallLog;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.calllogger.Model.Calls;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Calls currentCall;

    List<Calls> callList = new ArrayList<>() ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        TextView textView;

        textView = findViewById(R.id.txtView);

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_CALL_LOG)) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_CALL_LOG}, 1);
            } else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_CALL_LOG}, 1);
            }
        } else {

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(MainActivity.this,
                            Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "Permission granted!", Toast.LENGTH_SHORT).show();
                        TextView textView =  findViewById(R.id.txtView);
                        textView.setText(getCallDetails());
                    }
                } else {
                    Toast.makeText(this, "No permission granted!", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    private String getCallDetails() {
        StringBuffer sb = new StringBuffer();
        Cursor managedCursor = getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, null);
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

            //Log.i("CallDetail", "\nPhone Number: " + phNumber + "\nCallType: " + dir + "\nCall Date: " + dateString + "\nCall Duration: " + callDuration + " sec" + "\nCall Location: " + location);
            Log.i("CallDetail", " : " + callList.get(0).getPhNumber());
            sb.append("\nPhone Number: " + phNumber + "\nCallType: " + dir + "\nCall Date: " + dateString + "\nCall Duration: " + callDuration + " sec" + "\nCall Location: " + location);
            sb.append("\n-------------------------------");

        }
        managedCursor.close();
        return sb.toString();
    }
}
