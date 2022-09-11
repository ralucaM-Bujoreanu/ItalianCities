package it.ralucamb.laboratoriomobile.italiancities.utility;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import androidx.annotation.NonNull;

public class LocationHelper implements LocationListener {

    private MyLocationCallback callBack;

    @SuppressLint("MissingPermission")
    public void start(Context context, MyLocationCallback callback){
        this.callBack = callback;
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
    }
    public void stop(Context context){
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        manager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        if(this.callBack != null) this.callBack.onLocationChanged(location);
    }
}
