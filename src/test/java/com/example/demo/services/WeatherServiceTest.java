package com.example.demo.services;

import com.example.demo.models.WeatherModel;
import com.example.demo.repositories.WeatherRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class WeatherServiceTest {
    @Mock
    WeatherRepository weatherRepository;
    @InjectMocks
    WeatherService weatherService;

    @Test
    void downloadWeather() {
    }

    @Test
    void getWeatherWithDate() {
        WeatherModel weatherModel = new WeatherModel(1, Timestamp.valueOf("2022-05-25 05:05:05"), 25.1f, "New York", "USA");
        List<WeatherModel> weatherModelList = Arrays.asList(weatherModel);
        Mockito.when(weatherRepository.findByTimestampBetweenAndCityAndCountry(Date.valueOf("2022-05-25"), Date.valueOf("2022-05-26"), "New York", "USA")).thenReturn(weatherModelList);
        List<WeatherModel> list = weatherService.getWeather("New York", "USA", "2022-05-25");
        List<WeatherModel> list1 = weatherService.getWeather("New York", "USA", "2022-05-26");
        List<WeatherModel> list2 = weatherService.getWeather("123", "USA", "2022-05-25");
        List<WeatherModel> list3 = weatherService.getWeather("New York", "123", "2022-05-25");
        assertEquals(list.get(0), weatherModel);
        assertNotNull(list);
        assertTrue(list1.isEmpty());
        assertTrue(list2.isEmpty());
        assertTrue(list3.isEmpty());
    }

    @Test
    void getWeatherWithoutDate() {
        WeatherModel weatherModel = new WeatherModel(1, Timestamp.valueOf("2022-05-25 05:05:05"), 25.1f, "New York", "USA");
        Mockito.when(weatherRepository.findFirstByCityAndCountryOrderByTimestampDesc("New York", "USA")).thenReturn(weatherModel);
        List<WeatherModel> list = weatherService.getWeather("New York", "USA");
        List<WeatherModel> list1 = weatherService.getWeather("123", "USA");
        List<WeatherModel> list2 = weatherService.getWeather("New York", "123");
        assertEquals(list.get(0), weatherModel);
        assertNotNull(list);
        assertTrue(list1.contains(null));
        assertTrue(list2.contains(null));
        assertEquals(list1.size(), 1);
        assertEquals(list2.size(), 1);
    }
}