package com.example.demo.requests;

import com.example.demo.DTO.LatLonDTO;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RestRequests {
    @Value("${positionstack.key}")
    private String positionstackKey;
    @Value("${openweather.key}")
    private String openweatherKey;
    @Value("${yandex.key}")
    private String yandexKey;
    @Value("${weatherapi.key}")
    private String weatherApiKey;

    public LatLonDTO requestForFindCity(String cityAndCountry) throws IOException {
        String url = "http://api.positionstack.com/v1/forward";
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder httpBuider = HttpUrl
                .parse(url)
                .newBuilder()
                .addQueryParameter("query", cityAndCountry)
                .addQueryParameter("access_key", positionstackKey);
        ;
        Request request = new Request.Builder()
                .url(httpBuider.build())
                .get()
                .build();

        Response response = client.newCall(request).execute();
        String resStr = response.body().string();
        JSONObject json = new JSONObject(resStr);
        JSONObject jsonObject = json.getJSONArray("data").getJSONObject(0);
        LatLonDTO latLonDTO = LatLonDTO
                .builder()
                .latitude(jsonObject.get("latitude").toString())
                .longitude(jsonObject.get("longitude").toString())
                .build();
        return latLonDTO;
    }

    public float requestYandexWeather(LatLonDTO latLonDTO) throws IOException {
        String url = "https://api.weather.yandex.ru/v2/forecast";
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder httpBuider = HttpUrl
                .parse(url)
                .newBuilder()
                .addQueryParameter("lat", latLonDTO.getLatitude())
                .addQueryParameter("lon", latLonDTO.getLongitude())
                .addQueryParameter("lang", "en_US")
                .addQueryParameter("limit", "1")
                .addQueryParameter("hours", "false")
                .addQueryParameter("extra", "false");
        Request request = new Request.Builder()
                .url(httpBuider.build())
                .get()
                .addHeader("X-Yandex-API-Key", yandexKey)
                .build();

        Response response = client.newCall(request).execute();

        String resStr = response.body().string();
        JSONObject json = new JSONObject(resStr);
        float temp = Float.parseFloat(json.getJSONObject("fact").get("temp").toString());
        return temp;
    }

    public float requestOpenWeatherMap(LatLonDTO latLonDTO) throws IOException {
        String url = "https://api.openweathermap.org/data/2.5/weather";
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder httpBuider = HttpUrl.parse(url).newBuilder()
                .addQueryParameter("lat", latLonDTO.getLatitude())
                .addQueryParameter("lon", latLonDTO.getLongitude())
                .addQueryParameter("appid", openweatherKey)
                .addQueryParameter("units", "metric");
        Request request = new Request.Builder()
                .url(httpBuider.build())
                .get()
                .build();

        Response response = client.newCall(request).execute();
        String resStr = response.body().string();
        JSONObject json = new JSONObject(resStr);
        float temp = Float.parseFloat(json.getJSONObject("main").get("temp").toString());
        return temp;
    }

    public float requestWeatherAPI(LatLonDTO latLonDTO) throws IOException {
        String url = "http://api.weatherapi.com/v1/current.json";
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder httpBuider = HttpUrl.parse(url).newBuilder()
                .addQueryParameter("q", latLonDTO.getLatitude() + "," + latLonDTO.getLongitude())
                .addQueryParameter("key", weatherApiKey);
        Request request = new Request.Builder()
                .url(httpBuider.build())
                .get()
                .build();

        Response response = client.newCall(request).execute();
        String resStr = response.body().string();
        JSONObject json = new JSONObject(resStr);
        float temp = Float.parseFloat(json.getJSONObject("current").get("temp_c").toString());
        return temp;
    }
}
