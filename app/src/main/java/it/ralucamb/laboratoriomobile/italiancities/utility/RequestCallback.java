package it.ralucamb.laboratoriomobile.italiancities.utility;

import java.util.List;

import it.ralucamb.laboratoriomobile.italiancities.models.City;

public interface RequestCallback {
    void onCompleted(List<City> data);

}
