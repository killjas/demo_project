package com.example.demo.controllers;

import com.example.demo.models.WeatherModel;
import com.example.demo.services.WeatherService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WeatherRestController.class)
class WeatherRestControllerTest {
    @MockBean
    private WeatherService service;
    @Autowired
    private MockMvc mockMvc;
    @Test
    public void testWith2Parameters() throws Exception {
        WeatherModel weatherModel = new WeatherModel(1, Timestamp.valueOf("2022-05-25 05:05:05"), 25.1f, "New York", "USA");
        List<WeatherModel> weatherModelList = Arrays.asList(weatherModel);
        Mockito.when(service.getWeather("New York", "USA")).thenReturn(weatherModelList);
        mockMvc.perform(get("/getWeather?city=New York&country=USA"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].city", Matchers.is("New York")))
                .andExpect(jsonPath("$[0].country", Matchers.is("USA")));
        mockMvc.perform(get("/getWeather?city=New York&country=123123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(0)));
        mockMvc.perform(get("/getWeather?city=12312&country=USA"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    public void testWith3Parameters() throws Exception {
        WeatherModel weatherModel = new WeatherModel(1, Timestamp.valueOf("2022-05-25 05:05:05"), 25.1f, "New York", "USA");
        List<WeatherModel> weatherModelList = Arrays.asList(weatherModel);
        Mockito.when(service.getWeather("New York", "USA", "2022-05-25")).thenReturn(weatherModelList);
        mockMvc.perform(get("/getWeather?city=New York&country=USA&date=2022-05-25"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].city", Matchers.is("New York")))
                .andExpect(jsonPath("$[0].country", Matchers.is("USA")))
                .andExpect(jsonPath("$[0].timestamp", Matchers.is("2022-05-25T02:05:05.000+00:00")));
        mockMvc.perform(get("/getWeather?city=New York&country=USA&date=2022-05-24"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(0)));
        mockMvc.perform(get("/getWeather?city=123&country=USA&date=2022-05-25"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(0)));
        mockMvc.perform(get("/getWeather?city=New York&country=123&date=2022-05-25"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(0)));
    }

}