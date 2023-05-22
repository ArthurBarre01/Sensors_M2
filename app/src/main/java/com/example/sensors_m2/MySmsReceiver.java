package com.example.sensors_m2;


import android.bluetooth.le.ScanSettings;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.github.mikephil.charting.data.Entry;

public class MySmsReceiver extends BroadcastReceiver {

        private static final String TAG = MySmsReceiver.class.getSimpleName();
        public static final String pdu_type = "pdus";


        @Override
        public void onReceive(Context context, Intent intent) {
            // Get the SMS message.
            Bundle bundle = intent.getExtras();
            SmsMessage[] msgs;
            String strMessage = null;
            String dataSms = null;
            String num=null;
            String format = bundle.getString("format");

            // Retrieve the SMS message received.
            Object[] pdus = (Object[]) bundle.get(pdu_type);
            if (pdus != null) {
                // Check the Android version.
                boolean isVersionM = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M);
                // Fill the msgs array.
                msgs = new SmsMessage[pdus.length];
                for (int i = 0; i < msgs.length; i++) {
                    // Check Android version and use appropriate createFromPdu.
                    if (isVersionM) {
                        // If Android version M or newer:
                        msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i], format);
                    } else {
                        // If Android version L or older:
                        msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    }
                    // Build the message to show.
                    strMessage += "SMS from " + msgs[i].getOriginatingAddress();
                    strMessage += " :" + msgs[i].getMessageBody() + "\n";
                    dataSms=msgs[i].getMessageBody();
                    num=msgs[i].getOriginatingAddress();
                }
                if(num.equals("0647967364")){
                    //ProcessSms(dataSms);
                    // Log and display the SMS message.
                    Log.d(TAG, "onReceive: " + strMessage);
                    Toast.makeText(context, strMessage, Toast.LENGTH_LONG).show();
                }
            }
        }

        private String[] ProcessSms(String msg){

            String x;
            String y;
            String nameData;
            nameData=msg.substring(0,msg.indexOf(":"));
            x=msg.substring(msg.indexOf(":")+1,msg.indexOf("/"));
            y=msg.substring(msg.indexOf("/")+1);

            Log.d(TAG,"Data : "+nameData);
            Log.d(TAG, "x : " + x);
            Log.d(TAG,"y : "+y);

            switch (nameData){
                case "Temp":
                    GlobalClass.Temp_values.add(new Entry(Integer.parseInt(x),Integer.parseInt(y)));
                    break;
                case "CO2" :
                    GlobalClass.CO2_values.add(new Entry(Integer.parseInt(x),Integer.parseInt(y)));
                    break;
                case "Smoke"   :
                    GlobalClass.Smoke_values.add(new Entry(Integer.parseInt(x),Integer.parseInt(y)));
                    break;
            }
            return new String[0];
        }




    }