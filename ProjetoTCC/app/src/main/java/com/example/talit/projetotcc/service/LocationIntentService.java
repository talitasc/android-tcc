package com.example.talit.projetotcc.service;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;

import com.example.talit.projetotcc.activities.PaginalnicialConsumidor;
import com.example.talit.projetotcc.domain.MessageEB;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.greenrobot.event.EventBus;

/**
 * Created by talit on 14/05/2017.
 */

public class LocationIntentService  extends IntentService {


    public LocationIntentService() {
        super("worker_thread");
    }
    @Override
    protected void onHandleIntent(Intent intent) {

        Location location = intent.getParcelableExtra(PaginalnicialConsumidor.LOCATION);
        int type = intent.getIntExtra(PaginalnicialConsumidor.TYPE, 1);
        String address = intent.getStringExtra(PaginalnicialConsumidor.ADDRESS);

        List<Address> list = new ArrayList<Address>();
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        String error = "";
        String resultAddress = "";


        try {
            if(type == 2 || address == null) {
                // retorna o endereço por latitude
                 list = (ArrayList<Address>) geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            }
            else{
                // retorna a latitude por endereço
                list = (ArrayList<Address>) geocoder.getFromLocationName(address, 1);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            error = "Network problem";
        }
        catch (IllegalArgumentException e){
            e.printStackTrace();
            error = "Illegal arguments";
        }


        if(list != null && list.size() > 0){
            Address a = list.get(0);
            if(type == 2 || address == null){
                int tam = a.getMaxAddressLineIndex();
                for(int i = 0; i <= tam; i++){
                    resultAddress += a.getAddressLine(i);
                    resultAddress += i < tam - 1 ? ", " : "";
                }
            }
            else{
                resultAddress += a.getLatitude()+"\n";
                resultAddress += a.getLongitude();
            }
        }
        else{
            resultAddress = error;
        }

        MessageEB m = new MessageEB();
        m.setResultMessage(resultAddress);

        EventBus.getDefault().post(m);

    }
}
