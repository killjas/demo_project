package com.example.demo.requests;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@WebMvcTest(RestRequests.class)
class RestRequestsTest {
    OkHttpClient client = new OkHttpClient();
    @Value("${positionstack.key}")
    private String positionstackKey;
    @Value("${openweather.key}")
    private String openweatherKey;
    @Value("${yandex.key}")
    private String yandexKey;
    @Value("${weatherapi.key}")
    private String weatherApiKey;

    @Test
    void requestForFindCity() throws Exception {
        Request request = new Request.Builder().url("http://api.positionstack.com/v1/forward?query=New York USA&access_key=" + positionstackKey)
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();

        assertEquals(response.code(), 200);
    }

    @Test
    void requestYandexWeather() throws IOException {
        Request request = new Request.Builder().url("https://api.weather.yandex.ru/v2/forecast?lat=39.91987&lon=32.85427&lang=en_US&limit=1&hours=false&extra=fasle")
                .addHeader("X-Yandex-API-Key", yandexKey)
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();

        assertEquals(response.code(), 200);
    }

    @Test
    void requestOpenWeatherMap() throws IOException {
        Request request = new Request.Builder().url("https://api.openweathermap.org/data/2.5/weather?lat=39.91987&lon=32.85427&appid=" + openweatherKey + "&units=metric")
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();

        assertEquals(response.code(), 200);
    }

    @Test
    void requestWeatherAPI() throws IOException {
        Request request = new Request.Builder().url("http://api.weatherapi.com/v1/current.json?q=39.91987,32.85427&key=" + weatherApiKey)
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();

        assertEquals(response.code(), 200);
    }
}