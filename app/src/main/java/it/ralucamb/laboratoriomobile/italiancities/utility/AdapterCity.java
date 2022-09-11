package it.ralucamb.laboratoriomobile.italiancities.utility;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import it.ralucamb.laboratoriomobile.italiancities.R;
import it.ralucamb.laboratoriomobile.italiancities.models.City;
import it.ralucamb.laboratoriomobile.italiancities.models.GeoLocation;

public class AdapterCity extends RecyclerView.Adapter<AdapterCity.ViewHolder>{
    private List<City> data;

    public AdapterCity(List<City> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_city, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textName, textProvince;
        private String textName1, textProvince1;

        public ViewHolder(View view) {
            super(view);

            LinearLayout layout = view.findViewById(R.id.layout);
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    City city= data.get(getAdapterPosition());
                    Bundle bundle = new Bundle();
                    bundle.putString("name", city.getName());
                    bundle.putString("province", city.getProvince());
                    bundle.putString("cadastralCode", city.getCadastralCode());
                    bundle.putString("cap", city.getCap());
                    bundle.putString("prefix", city.getPrefix());
                    bundle.putString("email", city.getEmail());
                    bundle.putString("pec", city.getPec());
                    bundle.putString("phoneNumber", city.getPhoneNumber());
                    bundle.putString("fax", city.getFax());
                    GeoLocation location = city.getLocation();
                    if(location != null) {
                        bundle.putDouble("latitude", location.getLatitude());
                        bundle.putDouble("longitude", location.getLongitude());
                    }
                    Navigation.findNavController(view)
                            .navigate(R.id.action_navList_to_detailFragment, bundle);
                }
            });
            textName= view.findViewById(R.id.textName);
            textName1= textName.getText().toString();
            textProvince= view.findViewById(R.id.textProvince);
            textProvince1= textProvince.getText().toString();
        }

        public void onBind(int position) {
            City city = data.get(position);
            textName.setText(textName1+" "+city.getName());
            textProvince.setText(textProvince1+"\n"+city.getProvince());
        }
    }
}
