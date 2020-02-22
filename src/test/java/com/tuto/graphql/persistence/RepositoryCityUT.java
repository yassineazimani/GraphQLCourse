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
package com.tuto.graphql.persistence;

import com.tuto.graphql.model.City;
import com.tuto.graphql.persistence.RepositoryCity;
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

/**
 * Tests unitaires {@link RepositoryCity}
 */
@RunWith(MockitoJUnitRunner.class)
public class RepositoryCityUT {

    private RepositoryCity repositoryCity;

    @Mock
    private FakeDataUtils fakeDataUtils;

    private final List<City> allCities = new FakeDataUtils().getAllCities();

    @Before
    public void setUp(){
        this.repositoryCity = Mockito.spy(new RepositoryCity(fakeDataUtils));
        Mockito.when(fakeDataUtils.getAllCities())
                .thenReturn(allCities);
    }// setUp()

    @Test
    public void find_all_should_success(){
        List<City> cities = repositoryCity.findAll();
        assertThat(cities).isNotNull();
        assertThat(cities).isNotEmpty();
        assertThat(cities).hasSize(3);

        for(int i = 0; i < cities.size(); ++i){
            assertThat(cities.get(i).getId()).isEqualTo(allCities.get(i).getId());
            assertThat(cities.get(i).getName()).isEqualTo(allCities.get(i).getName());
        }
    }// find_all_should_success()

    @Test
    public void find_by_id_should_success(){
        Optional<City> optCity = repositoryCity.findById(1);
        assertThat(optCity).isPresent();
        optCity.ifPresent(c -> {
            assertThat(c.getId()).isEqualTo(allCities.get(0).getId());
            assertThat(c.getName()).isEqualTo(allCities.get(0).getName());
        });
    }// find_by_id_should_success()

    @Test
    public void find_by_id_when_id_doesnt_exist_should_return_empty_result(){
        Optional<City> optCity = repositoryCity.findById(9);
        assertThat(optCity).isNotPresent();
    }// find_by_id_when_id_doesnt_exist_should_return_empty_result()

}// RepositoryCityUT
