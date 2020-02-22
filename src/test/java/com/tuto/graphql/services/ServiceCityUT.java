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
package com.tuto.graphql.services;

import com.tuto.graphql.exceptions.MissingArgumentException;
import com.tuto.graphql.model.City;
import com.tuto.graphql.persistence.RepositoryCity;
import com.tuto.graphql.services.ServiceCity;
import com.tuto.graphql.utils.CacheUtils;
import com.tuto.graphql.utils.FakeDataUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests unitaires {@link ServiceCity}
 */
@RunWith(MockitoJUnitRunner.class)
public class ServiceCityUT {

    private ServiceCity serviceCity;

    @Mock
    private RepositoryCity repositoryCity;

    @Mock
    private CacheUtils cacheUtils;

    private final List<City> allCities = new FakeDataUtils().getAllCities();

    @Before
    public void setUp(){
        this.serviceCity = Mockito.spy(new ServiceCity(cacheUtils, repositoryCity));
        Mockito.when(repositoryCity.findAll()).thenReturn(allCities);
        Mockito.when(repositoryCity.findById(1)).thenReturn(Optional.of(allCities.get(0)));
    }// setUp()

    @Test
    public void get_all_cities_should_success(){
        List<City> cities = serviceCity.getAllCities();
        assertThat(cities).isNotNull();
        assertThat(cities).isNotEmpty();
        assertThat(cities).hasSize(3);

        for(int i = 0; i < cities.size(); ++i){
            assertThat(cities.get(i).getId()).isEqualTo(allCities.get(i).getId());
            assertThat(cities.get(i).getName()).isEqualTo(allCities.get(i).getName());
        }
    }// get_all_cities_should_success()

    @Test
    public void get_city_by_id_when_id_is_1_should_success() throws MissingArgumentException {
        Optional<City> optCity = serviceCity.getCityById(1);
        assertThat(optCity).isPresent();
        optCity.ifPresent(c -> {
            assertThat(c.getId()).isEqualTo(allCities.get(0).getId());
            assertThat(c.getName()).isEqualTo(allCities.get(0).getName());
        });
    }// get_city_by_id_when_id_is_1_should_success()

    @Test
    public void get_city_by_id_when_id_is_9_should_return_empty_optional() throws MissingArgumentException {
        Optional<City> optCity = serviceCity.getCityById(9);
        assertThat(optCity).isNotPresent();
    }// get_city_by_id_when_id_is_9_should_return_empty_optional()

    @Test
    public void get_city_by_id_when_id_is_null_should_throw_missing_argument_exception(){
        assertThatThrownBy(() -> serviceCity.getCityById(null))
                .hasMessage("Id is missing")
                .isInstanceOf(MissingArgumentException.class);
    }// get_city_by_id_when_id_is_null_should_throw_missing_argument_exception()

}// ServiceCityUT
