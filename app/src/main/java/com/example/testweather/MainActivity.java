package com.example.testweather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.data.JSONWeatherParser;
import com.data.WeatherHttpClient;
import com.model.Weather;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    private TextView cityName;
    private TextView temp;
    private TextView description;
    private TextView humidity;
    private TextView pressure;
    private TextView wind;
    private TextView sunrise;
    private TextView sunset;
    private TextView updated;
    private ImageView iconView;

    public static Weather weather = new Weather();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main);
        cityName = findViewById(R.id.cityText);
        temp = findViewById(R.id.tempText);
        description = findViewById(R.id.cloudText);
        humidity = findViewById(R.id.humidText);
        pressure = findViewById(R.id.pressureText);
        wind = findViewById(R.id.windText);
        sunrise = findViewById(R.id.riseText);
        sunset = findViewById(R.id.setText);
        updated = findViewById(R.id.updateText);
        iconView = findViewById(R.id.thumbnailIcon);

        renderWeatherData("Shiraz,IR");
    }
    public void renderWeatherData(String city){

        WeatherTask weatherTask = new WeatherTask();
        weatherTask.execute(new String[]{city + "&appid=828ad524bf8f8cb64b58c45eb56c420b&&units=metric"});

    }
    private class WeatherTask extends AsyncTask<String,Void,Weather>{

        @Override
        protected Weather doInBackground(String... strings) {
            String data = ((new WeatherHttpClient()).getWeatherData(strings[0]));
            weather = JSONWeatherParser.getWeather(data); //data که توی تابع getWeather بوده JSONWeatherParser حونده میشه و حیسون هارو ازش جدا میشن
          //  Log.v("Data: ",weather.place.getCity()); //تست شد
            //Log.v("Data: ",weather.currentCondition.getDescription()); // تست شد

            return weather;
        }

        @Override    //برای زمان نشر دادن پست هست
        protected void onPostExecute(Weather weather) {
            super.onPostExecute(weather);
            cityName.setText(weather.place.getCity() + "," + weather.place.getCountry());
            //نام شهر و کشور رو روی city name ست text کن

            DecimalFormat decimalFormat = new DecimalFormat("#.#");
                                                   //weather از توی currentCondition دما رو بگیر
            String tempFormat = decimalFormat.format(weather.currentCondition.getTemperature());
            temp.setText("" + tempFormat + "°C");
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        return super.onOptionsItemSelected(item);
    }
}