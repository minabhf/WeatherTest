package com.data;

import com.Util.Utils;
import com.model.Place;
import com.model.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONWeatherParser {

    public static Weather getWeather(String data) {
        Weather weather = new Weather();
        try {
            JSONObject jsonObject = new JSONObject(data);
            Place place = new Place();

            // get the coord obj
            JSONObject coordObj = Utils.getObject("coord", jsonObject);
            place.setLat(Utils.getFloat("lat", coordObj));
            place.setLon(Utils.getFloat("lon", coordObj));

            //get the sys obj
            JSONObject sysObj = Utils.getObject("sys", jsonObject);
            place.setCountry(Utils.getString("country", sysObj));
            place.setSunrise(Utils.getInt("sunrise", sysObj));
            place.setSunset(Utils.getInt("sunset", sysObj));

            place.setLastupdate(Utils.getInt("dt",jsonObject));
            place.setCity(Utils.getString("name",jsonObject));
            weather.place=place;  //place رو میریزیم داخل متغیر place که توی weather هست

            //This is an array for weather info
            JSONArray jsonArray = jsonObject.getJSONArray("weather");
            JSONObject jsonWeather = jsonArray.getJSONObject(0);
            // weather یه متغیر داره به اسم currentCondotion /میخوام setWeatherId کنم
            weather.currentCondition.setWeatherId(Utils.getInt("id", jsonWeather));
            weather.currentCondition.setDescription(Utils.getString("description", jsonWeather));
            weather.currentCondition.setCondition(Utils.getString("main", jsonWeather));
            weather.currentCondition.setIcon(Utils.getString("icon",jsonWeather));

            //setup wind
            JSONObject windObj = Utils.getObject("wind", jsonObject); //باد رو از حیسون آبحکت اصلی میگیریم
            weather.wind.setSpeed(Utils.getFloat("speed", windObj)); //سرعت باد رو از windObj
            weather.wind.setDeg(Utils.getFloat("deg", windObj));

            // setUp clouds
            JSONObject cloudObj = Utils . getObject("clouds",jsonObject);
            weather.clouds.setPrecipitation(Utils.getInt("all",cloudObj));

            // Let's get the main object
            JSONObject mainObj = Utils.getObject("main", jsonObject);
            weather.currentCondition.setHumidity(Utils.getInt("humidity", mainObj));
            weather.currentCondition.setPressure(Utils.getInt("pressure", mainObj));
            weather.currentCondition.setMinTemp(Utils.getFloat("temp_min", mainObj));
            weather.currentCondition.setMaxTemp(Utils.getFloat("temp_max", mainObj));
            weather.currentCondition.setTemperature(Utils.getDouble("temp", mainObj));


            return weather;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
