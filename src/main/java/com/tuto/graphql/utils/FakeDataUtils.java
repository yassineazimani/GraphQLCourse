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
package com.tuto.graphql.utils;

import com.tuto.graphql.model.Animal;
import com.tuto.graphql.model.City;
import com.tuto.graphql.model.Person;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Mock base de données
 */
@Component
@NoArgsConstructor
public class FakeDataUtils {

    /**
     * Récupération de toutes les villes en base
     * @return Liste de {@link City}
     */
    public List<City> getAllCities(){
        List<City> cities = new ArrayList<>();
        cities.add(new City(1, "Salon"));
        cities.add(new City(2, "Montpellier"));
        cities.add(new City(3, "Bikini-Bottom"));
        return cities;
    }// getAllCities()

    /**
     * Récupération de tous les animaux en base
     * @return Liste de {@link Animal}
     */
    public List<Animal> getAllAnimals(){
        List<Animal> animals = new ArrayList<>();
        animals.add(new Animal(1, "Chaminou", "Orange"));
        animals.add(new Animal(2, "Chaminou Junior", "Marron"));
        return animals;
    }// getAllAnimals()

    /**
     * Récupération de toutes les personnes en base
     * @return Liste de {@link Person}
     */
    public List<Person> getAllPersons(){
        List<City> cities = this.getAllCities();
        List<Person> results = new ArrayList<>();
        results.add(new Person(1, "Yassine", "AZIMANI", 29, cities.get(0), Collections.emptyList()));
        results.add(new Person(2, "Daniel", "PHAN", 35, cities.get(1), this.getAllAnimals()));
        results.add(new Person(3, "Bob", "SPONGE", 15, cities.get(2), Collections.emptyList()));
        return results;
    }// getAllPersons()

}// FakeDataUtils
