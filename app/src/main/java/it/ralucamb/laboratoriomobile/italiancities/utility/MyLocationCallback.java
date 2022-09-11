package it.ralucamb.laboratoriomobile.italiancities.utility;

import android.location.Location;

import androidx.annotation.NonNull;

public interface MyLocationCallback {
    public void onLocationChanged(@NonNull Location location);
}
