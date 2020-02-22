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
package com.tuto.graphql;

import com.tuto.graphql.exceptions.CustomGraphQlException;
import com.tuto.graphql.exceptions.MissingArgumentException;
import com.tuto.graphql.model.Animal;
import com.tuto.graphql.model.City;
import com.tuto.graphql.model.Person;
import com.tuto.graphql.services.ServiceAnimal;
import com.tuto.graphql.services.ServiceCity;
import com.tuto.graphql.services.ServicePerson;
import graphql.schema.DataFetcher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class GraphQLDataFetchers {

    private ServicePerson servicePerson;

    private ServiceAnimal serviceAnimal;

    private ServiceCity serviceCity;

    private final static Logger LOGGER = LogManager.getLogger(GraphQLDataFetchers.class);

    @Autowired
    public GraphQLDataFetchers(ServicePerson servicePerson, ServiceAnimal serviceAnimal, ServiceCity serviceCity){
        this.servicePerson = servicePerson;
        this.serviceAnimal = serviceAnimal;
        this.serviceCity = serviceCity;
    }// GraphQLDataFetchers()

    public Map<String, DataFetcher> getDataFetchersQuery() {
        Map<String, DataFetcher> dataFetchers = new HashMap<>();
        dataFetchers.put("allPersons", this.getAllPersons());
        dataFetchers.put("personById", this.getPersonById());
        dataFetchers.put("allCities", this.getAllCities());
        dataFetchers.put("cityById", this.getCityById());
        dataFetchers.put("allAnimals", this.getAllAnimals());
        dataFetchers.put("animalById", this.getAnimalById());
        return dataFetchers;
    }// getDataFetchersQuery()

    /**************************** DATA FETCHERS **********************************/

    private DataFetcher getAllPersons(){
        return dataFetcherEnvironment -> servicePerson.getAllPersons();
    }// getAllPersons()

    private DataFetcher getPersonById(){
        return dataFetcherEnvironment -> {
            Integer id = dataFetcherEnvironment.getArgument("id");
            try {
                return servicePerson.getPersonById(id).orElse(new Person());
            } catch (MissingArgumentException e) {
                LOGGER.error(e.getMessage(), e);
                throw new CustomGraphQlException(e.getMessage());
            }
        };
    }// getPersonById()

    private DataFetcher getAllAnimals(){
        return dataFetcherEnvironment -> serviceAnimal.getAllAnimals();
    }// getAllAnimals()

    private DataFetcher getAnimalById(){
        return dataFetcherEnvironment -> {
            Integer id = dataFetcherEnvironment.getArgument("id");
            try {
                return serviceAnimal.getAnimalById(id).orElse(new Animal());
            } catch (MissingArgumentException e) {
                LOGGER.error(e.getMessage(), e);
                throw new CustomGraphQlException(e.getMessage());
            }
        };
    }// getAnimalById()

    private DataFetcher getAllCities(){
        return dataFetcherEnvironment -> serviceCity.getAllCities();
    }// getAllCities()

    private DataFetcher getCityById(){
        return dataFetcherEnvironment -> {
            Integer id = dataFetcherEnvironment.getArgument("id");
            try {
                return serviceCity.getCityById(id).orElse(new City());
            } catch (MissingArgumentException e) {
                LOGGER.error(e.getMessage(), e);
                throw new CustomGraphQlException(e.getMessage());
            }
        };
    }// getCityById()

}// GraphQLDataFetchers
