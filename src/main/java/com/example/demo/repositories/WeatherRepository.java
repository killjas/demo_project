package com.example.demo.repositories;

import com.example.demo.models.WeatherModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Repository
public interface WeatherRepository extends JpaRepository<WeatherModel, Long> {
    List<WeatherModel> findByTimestampBetweenAndCityAndCountry(Date timestampStart, Date timestampEnd, String city, String country);

    WeatherModel findFirstByCityAndCountryOrderByTimestampDesc(String city, String country);

}
