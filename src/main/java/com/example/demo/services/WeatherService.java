package com.example.demo.services;

import com.example.demo.DTO.LatLonDTO;
import com.example.demo.models.WeatherModel;
import com.example.demo.repositories.WeatherRepository;
import com.example.demo.requests.RestRequests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class WeatherService {
    @Autowired
    private RestRequests requests;
    @Autowired
    private WeatherRepository weatherRepository;
    @Value("#{'${list.cities}'.split(';')}")
    private List<String> listCities;

    //@Scheduled(cron = "${cron}")
    //scheduled method which take temp from api
    public void downloadWeather() throws IOException {
        List<WeatherModel> entities = new ArrayList<>();
        for (int i = 0; i < listCities.size(); i++) {
            LatLonDTO latLonDTO = requests.requestForFindCity(listCities.get(i));
            WeatherModel weatherModel = WeatherModel.builder()
                    .city(listCities.get(i).split(",")[0])
                    .country(listCities.get(i).split(",")[1])
                    .temperature((requests.requestWeatherAPI(latLonDTO)
                            + requests.requestYandexWeather(latLonDTO)
                            + requests.requestOpenWeatherMap(latLonDTO)) / 3)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
            entities.add(weatherModel);
        }
        weatherRepository.saveAll(entities);
    }
    public List<WeatherModel> getWeather(String city, String country, String date){
        Date timestamp = Date.valueOf(date);
        Date timestamp1 = Date.valueOf(timestamp.toLocalDate().plusDays(1));
        return weatherRepository.findByTimestampBetweenAndCityAndCountry(timestamp, timestamp1, city, country);
    }

    public List<WeatherModel> getWeather(String city, String country){
        return Collections.singletonList(weatherRepository.findFirstByCityAndCountryOrderByTimestampDesc(city, country));
    }
}
