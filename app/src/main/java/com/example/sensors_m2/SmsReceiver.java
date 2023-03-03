package com.example.sensors_m2;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import com.github.mikephil.charting.data.Entry;

public class SmsReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            // On récupère les SMS reçus
            Object[] pdus = (Object[]) bundle.get("pdus");
            if (pdus.length == 0) {
                return;
            }
            // On crée un tableau de SmsMessage pour stocker les SMS
            SmsMessage[] messages = new SmsMessage[pdus.length];
            for (int i = 0; i < pdus.length; i++) {
                messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
            }
            // On parcourt les SMS reçus
            for (SmsMessage message : messages) {
                String texte = message.getMessageBody();
                String number = message.getOriginatingAddress();
                if (number.equals(GlobalClass.PHONE_NUMBER)){
                    // Traitement du SMS ici
                    String[] text= ProcessSMS(texte);
                    GlobalClass.Temp_values.add(new Entry(Integer.parseInt(text[0]),Integer.parseInt(text[1])));
                }
            }
        }
    }

    public String[] ProcessSMS (String message){
        String x;
        String y;
        String z;

        x=message.substring(0,message.indexOf("/"));
        y= message.substring(message.indexOf("/"),message.indexOf("-"));
        z=message.substring(message.indexOf("-")+1);


        return new String[]{x,y,z};
    }
}