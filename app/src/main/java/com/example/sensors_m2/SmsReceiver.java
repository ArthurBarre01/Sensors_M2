package com.example.sensors_m2;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.SmsMessage;
import android.widget.TextView;

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
                String numéro = message.getOriginatingAddress();
                // Traitement du SMS ici
                GlobalClass.Phone.setText(numéro);
                GlobalClass.Text.setText(texte);
            }
        }
    }
}