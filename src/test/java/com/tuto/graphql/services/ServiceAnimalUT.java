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
import com.tuto.graphql.model.Animal;
import com.tuto.graphql.persistence.RepositoryAnimal;
import com.tuto.graphql.services.ServiceAnimal;
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
 * Tests unitaires {@link ServiceAnimal}
 */
@RunWith(MockitoJUnitRunner.class)
public class ServiceAnimalUT {

    private ServiceAnimal serviceAnimal;

    @Mock
    private RepositoryAnimal repositoryAnimal;

    @Mock
    private CacheUtils cacheUtils;

    private final List<Animal> allAnimals = new FakeDataUtils().getAllAnimals();

    @Before
    public void setUp(){
        this.serviceAnimal = Mockito.spy(new ServiceAnimal(cacheUtils, repositoryAnimal));
        Mockito.when(repositoryAnimal.findAll()).thenReturn(allAnimals);
        Mockito.when(repositoryAnimal.findById(1)).thenReturn(Optional.of(allAnimals.get(0)));
    }// setUp()

    @Test
    public void get_all_animals_should_success(){
        List<Animal> animals = serviceAnimal.getAllAnimals();
        assertThat(animals).isNotNull();
        assertThat(animals).isNotEmpty();
        assertThat(animals).hasSize(2);

        for(int i = 0; i < animals.size(); ++i){
            assertThat(animals.get(i).getId()).isEqualTo(allAnimals.get(i).getId());
            assertThat(animals.get(i).getName()).isEqualTo(allAnimals.get(i).getName());
            assertThat(animals.get(i).getColor()).isEqualTo(allAnimals.get(i).getColor());
        }
    }// get_all_animals_should_success()

    @Test
    public void get_animal_by_id_when_id_is_1_should_success() throws MissingArgumentException {
        Optional<Animal> optAnimal = serviceAnimal.getAnimalById(1);
        assertThat(optAnimal).isPresent();
        optAnimal.ifPresent(c -> {
            assertThat(c.getId()).isEqualTo(allAnimals.get(0).getId());
            assertThat(c.getName()).isEqualTo(allAnimals.get(0).getName());
            assertThat(c.getColor()).isEqualTo(allAnimals.get(0).getColor());
        });
    }// get_animal_by_id_when_id_is_1_should_success()

    @Test
    public void get_animal_by_id_when_id_is_9_should_return_empty_optional() throws MissingArgumentException {
        Optional<Animal> optAnimal = serviceAnimal.getAnimalById(9);
        assertThat(optAnimal).isNotPresent();
    }// get_animal_by_id_when_id_is_9_should_return_empty_optional()

    @Test
    public void get_animal_by_id_when_id_is_null_should_throw_missing_argument_exception(){
        assertThatThrownBy(() -> serviceAnimal.getAnimalById(null))
                .hasMessage("Id is missing")
                .isInstanceOf(MissingArgumentException.class);
    }// get_animal_by_id_when_id_is_null_should_throw_missing_argument_exception()

}// ServiceAnimalUT
