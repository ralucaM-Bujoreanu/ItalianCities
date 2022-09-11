package it.ralucamb.laboratoriomobile.italiancities.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import it.ralucamb.laboratoriomobile.italiancities.R;
import it.ralucamb.laboratoriomobile.italiancities.database.Database;
import it.ralucamb.laboratoriomobile.italiancities.models.City;
import it.ralucamb.laboratoriomobile.italiancities.utility.AdapterCity;
import it.ralucamb.laboratoriomobile.italiancities.utility.RequestCallback;
import it.ralucamb.laboratoriomobile.italiancities.utility.Requests;

public class ListFragment extends Fragment {
    private AdapterCity adapter;
    List<City> cities= new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        adapter= new AdapterCity(cities);
        recyclerView.setAdapter(adapter);

        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(getContext());
        if(pref.getBoolean("firstTime",true)) {
            if (pref.getBoolean("connection", true)) {
                Requests.asyncRequest("https://comuni-ita.herokuapp.com/api/comuni", new RequestCallback() {
                    @Override
                    public void onCompleted(List<City> data) {
                        cities.addAll(data);
                        writeToDatabase(data);
                        requireActivity().runOnUiThread(()-> adapter.notifyDataSetChanged());


                    }
                });
            }
            pref.edit().putBoolean("firstTime", false).apply();
        }else{
            readFromDatabase(cities);
        }

    }
    private void readFromDatabase(List<City> data) {
        new Thread(()->{
            List<City> result= Database.getInstance(getContext()).findAll();
            data.addAll(result);
            requireActivity().runOnUiThread(()-> adapter.notifyDataSetChanged());
        }).start();
    }
    private void writeToDatabase(List<City> data) {
        new Thread(()->{
            for(City city: data){
                Database.getInstance(getContext()).insert(city);
            }
        }).start();
    }

}