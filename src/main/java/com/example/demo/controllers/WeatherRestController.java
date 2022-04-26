package com.example.demo.controllers;

import com.example.demo.models.WeatherModel;
import com.example.demo.services.WeatherService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
public class WeatherRestController {
    @Autowired
    WeatherService weatherService;

    @ApiOperation(value = "Получить погоду", notes = "Получить погоду по дате и названии города и страны")
    @RequestMapping(method = RequestMethod.GET, value = "/getWeather")
    private ResponseEntity<List<WeatherModel>> getWeather(@ApiParam(name = "city", value = "Название города", example = "New York", required = true) String city,
                                                          @ApiParam(name = "country", value = "Название страны", example = "USA", required = true) String country,
                                                          @ApiParam(name = "date", value = "yyyy-MM-dd", example = "2022-04-25") String date) throws IOException {
        if (Optional.ofNullable(date).isPresent()) {
            return new ResponseEntity<>(weatherService.getWeather(city, country, date), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(weatherService.getWeather(city, country), HttpStatus.OK);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/add")
    private void add() throws IOException {
        weatherService.downloadWeather();
    }
}
