package com.data;

import com.Util.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherHttpClient {
    //قلب پروژه. در این قسمت از سایت مقادیر Json خود را دریافت میکنیم
          //خروجی
    public String getWeatherData(String place) {

        HttpURLConnection connection = null;
        InputStream inputStream = null;
        try {
            //توی کلاس utils بود
            connection = (HttpURLConnection) new URL(Utils.BASE_URL + place).openConnection();
            connection.setRequestMethod("GET");  //میخوایم از کانکشن اطلاعات بگیریم
            connection.setDoInput(true); //Sets the flag indicating whether this {@code HRLConnection} allows input
            connection.setDoOutput(true);  //برای زمانی هست که پست میکنیم
            connection.connect();

            //خواندن پاسخ

            StringBuffer stringBuffer = new StringBuffer();  //آبجکت گرفتیم
            inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line + "\r\n");
            }
            //اضافه کردن
            inputStream.close();  //کارمون با input stream تموم شد
            connection.disconnect();

            return stringBuffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    }
