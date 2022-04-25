package com.example.demo.requests;

import com.example.demo.DTO.LatLonDTO;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RestRequests {
    public LatLonDTO requestForFindCity(String city) throws IOException {
        String url = "http://api.positionstack.com/v1/forward";
        OkHttpClient client = new OkHttpClient();
        HttpUrl.Builder httpBuider = HttpUrl.parse(url).newBuilder();
        httpBuider.addQueryParameter("query", city);
        httpBuider.addQueryParameter("access_key", "51226b95e15dfc6f76763d63f64dc31a");
        Request request = new Request.Builder()
                .url(httpBuider.build())
                .get()
                .addHeader("X-RapidAPI-Host", "weatherbit-v1-mashape.p.rapidapi.com")
                .addHeader("X-RapidAPI-Key", "1841d0ca9bmsh5fef73690eab59ap1d04a3jsnaa3bac5e469d")
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
        HttpUrl.Builder httpBuider = HttpUrl.parse(url).newBuilder();
        httpBuider.addQueryParameter("lat", latLonDTO.getLatitude());
        httpBuider.addQueryParameter("lon", latLonDTO.getLongitude());
        httpBuider.addQueryParameter("lang", "en_US");
        httpBuider.addQueryParameter("limit", "1");
        httpBuider.addQueryParameter("hours", "false");
        httpBuider.addQueryParameter("extra", "false");
        Request request = new Request.Builder()
                .url(httpBuider.build())
                .get()
                .addHeader("X-Yandex-API-Key", "4de55d98-1c87-4bec-ae05-4141f3ddb106")
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
        HttpUrl.Builder httpBuider = HttpUrl.parse(url).newBuilder();
        httpBuider.addQueryParameter("lat", latLonDTO.getLatitude());
        httpBuider.addQueryParameter("lon", latLonDTO.getLongitude());
        httpBuider.addQueryParameter("appid", "c034971d9df9d6335fc1037f75eedc8b");
        httpBuider.addQueryParameter("units", "metric");
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
        HttpUrl.Builder httpBuider = HttpUrl.parse(url).newBuilder();
        httpBuider.addQueryParameter("q", latLonDTO.getLatitude() + "," + latLonDTO.getLongitude());
        httpBuider.addQueryParameter("key", "05510f86ae06403e8b9152004222404");
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
