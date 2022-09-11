package it.ralucamb.laboratoriomobile.italiancities.fragments;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Map;

import it.ralucamb.laboratoriomobile.italiancities.R;
import it.ralucamb.laboratoriomobile.italiancities.models.City;
import it.ralucamb.laboratoriomobile.italiancities.models.GeoLocation;
import it.ralucamb.laboratoriomobile.italiancities.utility.LocationHelper;
import it.ralucamb.laboratoriomobile.italiancities.utility.MyLocationCallback;

public class DetailFragment extends Fragment implements OnMapReadyCallback, MyLocationCallback {
    private City selectedCity;
    private GoogleMap googleMap;
    private Marker deviceMarker;
    private LocationHelper locationHelper = new LocationHelper();

    private final ActivityResultLauncher<String[]> locationPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestMultiplePermissions(),
            new ActivityResultCallback<Map<String, Boolean>>() {
                @Override
                public void onActivityResult(Map<String, Boolean> result) {

                    boolean check = true;
                    for(Map.Entry<String, Boolean> entry: result.entrySet()) {
                        check = entry.getValue();
                        if(!check) break;
                    }

                    if(check) {
                        getDeviceLocation();
                    } else {
                        Toast.makeText(requireContext(), R.string.location_disabled, Toast.LENGTH_LONG)
                                .show();
                    }
                }
            });

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle= getArguments();
        if(bundle!=null){
            String name= bundle.getString("name","");
            String province= bundle.getString("province","");
            String email= bundle.getString("email","");
            String pec= bundle.getString("pec","");
            String phoneNumber= bundle.getString("phoneNumber","");
            String fax= bundle.getString("fax","");
            String cadastralCode= bundle.getString("cadastralCode","");
            String cap= bundle.getString("cap","");
            String prefix= bundle.getString("prefix","");
            GeoLocation location= new GeoLocation();
            location.setLatitude(bundle.getDouble("latitude",0));
            location.setLongitude(bundle.getDouble("longitude",0));
            selectedCity= new City(name,cadastralCode,cap,prefix,province,email,pec,phoneNumber,fax);
            selectedCity.setLocation(location);
            TextView textName= view.findViewById(R.id.name);
            TextView textProvince= view.findViewById(R.id.province);
            TextView textCadastralCode= view.findViewById(R.id.cadastralCode);
            TextView textCap= view.findViewById(R.id.cap);
            TextView textPrefix= view.findViewById(R.id.prefix);
            TextView textContacts= view.findViewById(R.id.contacts);
            textName.setText(textName.getText()+selectedCity.getName());
            textProvince.setText(textProvince.getText()+selectedCity.getProvince());
            textCadastralCode.setText(textCadastralCode.getText()+selectedCity.getCadastralCode());
            textCap.setText(textCap.getText()+selectedCity.getCap());
            textPrefix.setText(textPrefix.getText()+selectedCity.getPrefix());
            textContacts.setText(textContacts.getText()+ "\n"+"email:"+selectedCity.getEmail()+"\n"+"pec:"+ selectedCity.getPec()+"\n"+ "num:"+ selectedCity.getPhoneNumber()+"\n"+"fax:"+selectedCity.getFax());

        }
        SupportMapFragment fragment= (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if(fragment!=null) fragment.getMapAsync(this);
    }

    @Override
    public void onStop(){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(requireContext());
            locationHelper.stop(requireContext());
        super.onStop();
    }
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        this.googleMap = googleMap;

        // Add a marker on selected user position
        LatLng selectedCityPosition = new LatLng(
                selectedCity.getLocation().getLatitude(),
                selectedCity.getLocation().getLongitude());

        MarkerOptions options = new MarkerOptions();
        options.title(selectedCity.getName());
        options.position(selectedCityPosition);
        this.googleMap.addMarker(options);

        getDeviceLocation();
    }
    @Override
    public void onLocationChanged(@NonNull Location location) {

        // Add the device marker and update it
        LatLng position = new LatLng(location.getLatitude(), location.getLongitude());

        if(deviceMarker == null) {
            MarkerOptions options = new MarkerOptions();
            options.title(getString(R.string.position));
            options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
            options.position(position);
            deviceMarker = this.googleMap.addMarker(options);
        } else {
            deviceMarker.setPosition(position);
        }

    }
    private void getDeviceLocation() {

        // Get user location
        int fineLocation = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION);
        int coarseLocation = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION);
        if(fineLocation == PackageManager.PERMISSION_GRANTED
                && coarseLocation == PackageManager.PERMISSION_GRANTED) {
            // Request location

            SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(requireContext());
                locationHelper.start(requireContext(), this);
        } else {
            locationPermissionLauncher.launch(new String[] {
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            });
        }
    }
}
