package it.ralucamb.laboratoriomobile.italiancities.utility;

import org.json.JSONException;
import org.json.JSONObject;

import it.ralucamb.laboratoriomobile.italiancities.models.City;
import it.ralucamb.laboratoriomobile.italiancities.models.GeoLocation;

public class Parser {
    public static City parseCity(JSONObject json){
        /*{"codice":"028001",
        "nome":"Abano Terme",
        "nomeStraniero":null,
        "codiceCatastale":"A001",
        "cap":"35031",
        "prefisso":"049",
        "provincia":"028",
        "email":"protocollo@abanoterme.net",
        "pec":"abanoterme.pd@cert.ip-veneto.net",
        "telefono":"+39 049 8245111",
        "fax":"+39 049 8600499",
        "coordinate":{
        "lat":45.361900329589844,
        "lng":11.792400360107422}}
         */
        try{
            City city= new City();
            city.setName(json.getString("nome"));
            if(json.getString("nomeStraniero") != null){
                city.setForeingName(json.getString("nomeStraniero"));
            }
            city.setCadastralCode(json.getString("codiceCatastale"));
            city.setCap(json.getString("cap"));
            city.setPrefix(json.getString("prefisso"));
            city.setProvince(json.getString("provincia"));
            city.setEmail(json.getString("email"));
            city.setPec(json.getString("pec"));
            city.setPhoneNumber(json.getString("telefono"));
            city.setFax(json.getString("fax"));
            GeoLocation location = new GeoLocation();
            JSONObject jsonAddress = json.optJSONObject("coordinate");
            location.setLatitude(jsonAddress.getDouble("lat"));
            location.setLongitude(jsonAddress.getDouble("lng"));
            city.setLocation(location);
            return city;

        }catch (JSONException e){
            e.printStackTrace();
        }
        return null;
    }
}
