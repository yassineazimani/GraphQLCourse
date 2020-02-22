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

import com.tuto.graphql.model.Animal;
import com.tuto.graphql.persistence.RepositoryAnimal;
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
 * Tests unitaires {@link RepositoryAnimal}
 */
@RunWith(MockitoJUnitRunner.class)
public class RepositoryAnimalUT {

    private RepositoryAnimal repositoryAnimal;

    @Mock
    private FakeDataUtils fakeDataUtils;

    private final List<Animal> allAnimals = new FakeDataUtils().getAllAnimals();

    @Before
    public void setUp(){
        this.repositoryAnimal = Mockito.spy(new RepositoryAnimal(fakeDataUtils));
        Mockito.when(fakeDataUtils.getAllAnimals())
                .thenReturn(allAnimals);
    }// setUp()

    @Test
    public void find_all_should_success(){
        List<Animal> animals = repositoryAnimal.findAll();
        assertThat(animals).isNotNull();
        assertThat(animals).isNotEmpty();
        assertThat(animals).hasSize(2);

        for(int i = 0; i < animals.size(); ++i){
            assertThat(animals.get(i).getId()).isEqualTo(allAnimals.get(i).getId());
            assertThat(animals.get(i).getName()).isEqualTo(allAnimals.get(i).getName());
            assertThat(animals.get(i).getColor()).isEqualTo(allAnimals.get(i).getColor());
        }
    }// find_all_should_success()

    @Test
    public void find_by_id_should_success(){
        Optional<Animal> optAnimal = repositoryAnimal.findById(1);
        assertThat(optAnimal).isPresent();
        optAnimal.ifPresent(c -> {
            assertThat(c.getId()).isEqualTo(allAnimals.get(0).getId());
            assertThat(c.getName()).isEqualTo(allAnimals.get(0).getName());
            assertThat(c.getColor()).isEqualTo(allAnimals.get(0).getColor());
        });
    }// find_by_id_should_success()

    @Test
    public void find_by_id_when_id_doesnt_exist_should_return_empty_result(){
        Optional<Animal> optAnimal = repositoryAnimal.findById(9);
        assertThat(optAnimal).isNotPresent();
    }// find_by_id_when_id_doesnt_exist_should_return_empty_result()

}// RepositoryAnimalUT
