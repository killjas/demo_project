package com.example.demo.repositories;

import com.example.demo.models.WeatherModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class WeatherRepositoryTest {

    @Autowired
    WeatherRepository weatherRepository;

    @Test
    void findByTimestampBetweenAndCityAndCountry() {
        WeatherModel weatherModel = new WeatherModel(1, Timestamp.valueOf("2022-05-25 05:05:05"), 25.1f, "New York", "USA");
        WeatherModel weatherModel1 = new WeatherModel(2, Timestamp.valueOf("2022-05-26 06:05:05"), 25.1f, "New York", "USA");
        WeatherModel weatherModel2 = new WeatherModel(3, Timestamp.valueOf("2022-05-25 06:05:05"), 25.1f, "New York", "USA");
        List<WeatherModel> weatherModelList = Arrays.asList(weatherModel, weatherModel1, weatherModel2);
        weatherRepository.saveAll(weatherModelList);

        List<WeatherModel> list = weatherRepository.findByTimestampBetweenAndCityAndCountry(Date.valueOf("2022-05-25"), Date.valueOf("2022-05-26"), "New York", "USA");
        assertNotNull(list);
        assertEquals(list.size(), 2);
        assertEquals(list.get(0).getTimestamp(), Timestamp.valueOf("2022-05-25 05:05:05"));
        assertEquals(list.get(1).getTimestamp(), Timestamp.valueOf("2022-05-25 06:05:05"));
    }

    @Test
    void findFirstByCityAndCountryOrderByTimestampDesc() {
        WeatherModel weatherModel = new WeatherModel(1, Timestamp.valueOf("2022-05-27 05:05:05"), 25.1f, "New York", "USA");
        WeatherModel weatherModel1 = new WeatherModel(2, Timestamp.valueOf("2022-05-28 06:05:05"), 25.1f, "New York", "USA");
        WeatherModel weatherModel2 = new WeatherModel(3, Timestamp.valueOf("2022-05-27 06:05:05"), 25.1f, "New York", "USA");
        List<WeatherModel> weatherModelList = Arrays.asList(weatherModel, weatherModel1, weatherModel2);
        weatherRepository.saveAll(weatherModelList);

        WeatherModel weatherModel3 = weatherRepository.findFirstByCityAndCountryOrderByTimestampDesc("New York", "USA");

        assertNotNull(weatherModel3);
        assertEquals(weatherModel3.getTimestamp(), Timestamp.valueOf("2022-05-28 06:05:05"));
    }
}