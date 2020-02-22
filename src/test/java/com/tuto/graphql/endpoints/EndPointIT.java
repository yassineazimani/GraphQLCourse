/*
 * Copyright 2020 Yassine AZIMANI
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tuto.graphql.endpoints;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuto.graphql.model.Animal;
import com.tuto.graphql.model.City;
import com.tuto.graphql.model.Person;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests d'intégrations
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EndPointIT {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper;

    @Before
    public void setUp(){
        this.mapper = new ObjectMapper();
    }// setUp()

    @Test
    public void unknown_endpoint_query_should_return_code_404() throws Exception {
        this.mockMvc.perform(post("/api/public/foo")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andDo(print()).andExpect(status().isNotFound());
    }// unknown_endpoint_query_should_return_code_404()

    @Test
    public void all_persons_query_should_success() throws Exception {
        ResultActions resultActions = this.mockMvc.perform(post("/api/public/query")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ allPersons{ id, firstName, lastName, age, city { id, name}, animals { id, name, color} } }"))
                .andDo(print())
                .andExpect(status().isOk());
        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        Map<String, Object> resultBody = this.mapper.readValue(contentAsString, Map.class);
        List<Person> results = this.mapper.convertValue(resultBody.get("allPersons"), new TypeReference<List<Person>>() {});
        assertThat(results).isNotNull();
        assertThat(results).isNotEmpty();
        assertThat(results).hasSize(3);

        for(int i = 0; i < results.size(); ++i){
            assertThat(results.get(i).getId()).isEqualTo(i + 1);
        }

        Person p1 = results.get(0);
        assertThat(p1.getAge()).isEqualTo(29);
        assertThat(p1.getFirstName()).isEqualTo("Yassine");
        assertThat(p1.getLastName()).isEqualTo("AZIMANI");
        assertThat(p1.getCity()).isNotNull();
        assertThat(p1.getCity().getId()).isEqualTo(1);
        assertThat(p1.getCity().getName()).isEqualTo("Salon");
        assertThat(p1.getAnimals()).isNullOrEmpty();

        Person p2 = results.get(1);
        assertThat(p2.getAge()).isEqualTo(35);
        assertThat(p2.getFirstName()).isEqualTo("Daniel");
        assertThat(p2.getLastName()).isEqualTo("PHAN");
        assertThat(p2.getCity()).isNotNull();
        assertThat(p2.getCity().getId()).isEqualTo(2);
        assertThat(p2.getCity().getName()).isEqualTo("Montpellier");
        assertThat(p2.getAnimals()).isNotNull();
        assertThat(p2.getAnimals()).isNotEmpty();
        assertThat(p2.getAnimals()).hasSize(2);
        for(int i = 0; i < p2.getAnimals().size(); ++i){
            assertThat(p2.getAnimals().get(i).getId()).isEqualTo(i + 1);
        }
        assertThat(p2.getAnimals().get(0).getName()).isEqualTo("Chaminou");
        assertThat(p2.getAnimals().get(0).getColor()).isEqualTo("Orange");
        assertThat(p2.getAnimals().get(1).getName()).isEqualTo("Chaminou Junior");
        assertThat(p2.getAnimals().get(1).getColor()).isEqualTo("Marron");

        Person p3 = results.get(2);
        assertThat(p3.getAge()).isEqualTo(15);
        assertThat(p3.getFirstName()).isEqualTo("Bob");
        assertThat(p3.getLastName()).isEqualTo("SPONGE");
        assertThat(p3.getCity()).isNotNull();
        assertThat(p3.getCity().getId()).isEqualTo(3);
        assertThat(p3.getCity().getName()).isEqualTo("Bikini-Bottom");
        assertThat(p3.getAnimals()).isNullOrEmpty();
    }// all_persons_query_should_success()

    @Test
    public void person_by_id_query_should_success() throws Exception {
        ResultActions resultActions = this.mockMvc.perform(post("/api/public/query")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ personById(id: 1){ id, firstName, lastName, age, city { id, name}, animals { id, name, color} } }"))
                .andDo(print())
                .andExpect(status().isOk());
        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        Map<String, Object> resultBody = this.mapper.readValue(contentAsString, Map.class);
        Person p1 = this.mapper.convertValue(resultBody.get("personById"), Person.class);
        assertThat(p1.getId()).isEqualTo(1);
        assertThat(p1.getAge()).isEqualTo(29);
        assertThat(p1.getFirstName()).isEqualTo("Yassine");
        assertThat(p1.getLastName()).isEqualTo("AZIMANI");
        assertThat(p1.getCity()).isNotNull();
        assertThat(p1.getCity().getId()).isEqualTo(1);
        assertThat(p1.getCity().getName()).isEqualTo("Salon");
        assertThat(p1.getAnimals()).isNullOrEmpty();
    }// person_by_id_query_should_success()

    @Test
    public void person_by_id_query_when_no_id_given_should_fail() throws Exception {
        this.mockMvc.perform(post("/api/public/query")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ personById(id: null){ id, firstName, lastName, age, city { id, name}, animals { id, name, color} } }"))
                .andDo(print())
                .andExpect(status().isBadRequest());
        this.mockMvc.perform(post("/api/public/query")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ personById{ id, firstName, lastName, age, city { id, name}, animals { id, name, color} } }"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }// person_by_id_query_when_no_id_given_should_fail()

    @Test
    public void all_cities_query_should_success() throws Exception {
        ResultActions resultActions = this.mockMvc.perform(post("/api/public/query")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ allCities{ id, name } }"))
                .andDo(print())
                .andExpect(status().isOk());
        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        Map<String, Object> resultBody = this.mapper.readValue(contentAsString, Map.class);
        List<City> results = this.mapper.convertValue(resultBody.get("allCities"), new TypeReference<List<City>>() {});
        assertThat(results).isNotNull();
        assertThat(results).isNotEmpty();
        assertThat(results).hasSize(3);

        for(int i = 0; i < results.size(); ++i){
            assertThat(results.get(i).getId()).isEqualTo(i + 1);
        }

        City c1 = results.get(0);
        assertThat(c1.getName()).isEqualTo("Salon");

        City c2 = results.get(1);
        assertThat(c2.getName()).isEqualTo("Montpellier");

        City c3 = results.get(2);
        assertThat(c3.getName()).isEqualTo("Bikini-Bottom");
    }// all_cities_query_should_success()

    @Test
    public void city_by_id_query_should_success() throws Exception {
        ResultActions resultActions = this.mockMvc.perform(post("/api/public/query")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ cityById(id: 1){id, name} }"))
                .andDo(print())
                .andExpect(status().isOk());
        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        Map<String, Object> resultBody = this.mapper.readValue(contentAsString, Map.class);
        City c1 = this.mapper.convertValue(resultBody.get("cityById"), City.class);

        assertThat(c1.getId()).isEqualTo(1);
        assertThat(c1.getName()).isEqualTo("Salon");
    }// city_by_id_query_should_success()

    @Test
    public void city_by_id_query_when_no_id_given_should_fail() throws Exception {
        this.mockMvc.perform(post("/api/public/query")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ cityById(id: null){id, name} }"))
                .andDo(print())
                .andExpect(status().isBadRequest());
        this.mockMvc.perform(post("/api/public/query")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ cityById{id, name} }"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }// city_by_id_query_when_no_id_given_should_fail()

    @Test
    public void all_animals_query_should_success() throws Exception {
        ResultActions resultActions = this.mockMvc.perform(post("/api/public/query")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ allAnimals{ id, name, color } }"))
                .andDo(print())
                .andExpect(status().isOk());
        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        Map<String, Object> resultBody = this.mapper.readValue(contentAsString, Map.class);
        List<Animal> results = this.mapper.convertValue(resultBody.get("allAnimals"), new TypeReference<List<Animal>>() {});
        assertThat(results).isNotNull();
        assertThat(results).isNotEmpty();
        assertThat(results).hasSize(2);

        for(int i = 0; i < results.size(); ++i){
            assertThat(results.get(i).getId()).isEqualTo(i + 1);
        }

        Animal a1 = results.get(0);
        assertThat(a1.getName()).isEqualTo("Chaminou");
        assertThat(a1.getColor()).isEqualTo("Orange");

        Animal a2 = results.get(1);
        assertThat(a2.getName()).isEqualTo("Chaminou Junior");
        assertThat(a2.getColor()).isEqualTo("Marron");
    }// all_animals_query_should_success()

    @Test
    public void animal_by_id_query_should_success() throws Exception {
        ResultActions resultActions = this.mockMvc.perform(post("/api/public/query")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ animalById(id: 1){id, name, color} }"))
                .andDo(print())
                .andExpect(status().isOk());
        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        Map<String, Object> resultBody = this.mapper.readValue(contentAsString, Map.class);
        Animal a1 = this.mapper.convertValue(resultBody.get("animalById"), Animal.class);

        assertThat(a1.getId()).isEqualTo(1);
        assertThat(a1.getName()).isEqualTo("Chaminou");
        assertThat(a1.getColor()).isEqualTo("Orange");
    }// animal_by_id_query_should_success()

    @Test
    public void animal_by_id_query_when_no_id_given_should_fail() throws Exception {
        this.mockMvc.perform(post("/api/public/query")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ animalById(id: null){id, name, color} }"))
                .andDo(print())
                .andExpect(status().isBadRequest());
        this.mockMvc.perform(post("/api/public/query")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ animalById{id, name, color} }"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }// animal_by_id_query_when_no_id_given_should_fail()

}// EndPointIT
