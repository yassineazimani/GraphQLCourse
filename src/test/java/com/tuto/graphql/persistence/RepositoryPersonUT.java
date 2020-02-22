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

import com.tuto.graphql.model.Person;
import com.tuto.graphql.persistence.RepositoryPerson;
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
 * Tests unitaires {@link RepositoryPerson}
 */
@RunWith(MockitoJUnitRunner.class)
public class RepositoryPersonUT {

    private RepositoryPerson repositoryPerson;

    @Mock
    private FakeDataUtils fakeDataUtils;

    private final List<Person> allPersons = new FakeDataUtils().getAllPersons();

    @Before
    public void setUp(){
        this.repositoryPerson = Mockito.spy(new RepositoryPerson(fakeDataUtils));
        Mockito.when(fakeDataUtils.getAllPersons())
                .thenReturn(allPersons);
    }// setUp()

    @Test
    public void find_all_should_success(){
        List<Person> persons = repositoryPerson.findAll();
        assertThat(persons).isNotNull();
        assertThat(persons).isNotEmpty();
        assertThat(persons).hasSize(3);

        for(int i = 0; i < persons.size(); ++i){
            assertThat(persons.get(i).getId()).isEqualTo(allPersons.get(i).getId());
            assertThat(persons.get(i).getLastName()).isEqualTo(allPersons.get(i).getLastName());
            assertThat(persons.get(i).getFirstName()).isEqualTo(allPersons.get(i).getFirstName());
            assertThat(persons.get(i).getCity()).isEqualTo(allPersons.get(i).getCity());
            assertThat(persons.get(i).getAnimals()).isEqualTo(allPersons.get(i).getAnimals());
        }
    }// find_all_should_success()

    @Test
    public void find_by_id_should_success(){
        Optional<Person> optPerson = repositoryPerson.findById(1);
        assertThat(optPerson).isPresent();
        optPerson.ifPresent(p -> {
            assertThat(p.getId()).isEqualTo(allPersons.get(0).getId());
            assertThat(p.getLastName()).isEqualTo(allPersons.get(0).getLastName());
            assertThat(p.getFirstName()).isEqualTo(allPersons.get(0).getFirstName());
            assertThat(p.getCity()).isEqualTo(allPersons.get(0).getCity());
            assertThat(p.getAnimals()).isEqualTo(allPersons.get(0).getAnimals());
        });
    }// find_by_id_should_success()

    @Test
    public void find_by_id_when_id_doesnt_exist_should_return_empty_result(){
        Optional<Person> optPerson = repositoryPerson.findById(10);
        assertThat(optPerson).isNotPresent();
    }// find_by_id_when_id_doesnt_exist_should_return_empty_result()

}// RepositoryPersonUT
