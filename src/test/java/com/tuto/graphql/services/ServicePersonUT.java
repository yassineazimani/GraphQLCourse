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
import com.tuto.graphql.model.Person;
import com.tuto.graphql.persistence.RepositoryPerson;
import com.tuto.graphql.services.ServicePerson;
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
 * Tests unitaires {@link ServicePerson}
 */
@RunWith(MockitoJUnitRunner.class)
public class ServicePersonUT {

    private ServicePerson servicePerson;

    @Mock
    private RepositoryPerson repositoryPerson;

    @Mock
    private CacheUtils cacheUtils;

    private final List<Person> allPersons = new FakeDataUtils().getAllPersons();

    @Before
    public void setUp(){
        this.servicePerson = Mockito.spy(new ServicePerson(cacheUtils, repositoryPerson));
        Mockito.when(repositoryPerson.findAll()).thenReturn(allPersons);
        Mockito.when(repositoryPerson.findById(1)).thenReturn(Optional.of(allPersons.get(0)));
    }// setUp()

    @Test
    public void get_all_persons_should_success(){
        List<Person> persons = servicePerson.getAllPersons();
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
    }// get_all_persons_should_success()

    @Test
    public void get_person_by_id_when_id_is_1_should_success() throws MissingArgumentException {
        Optional<Person> optPerson = servicePerson.getPersonById(1);
        assertThat(optPerson).isPresent();
        optPerson.ifPresent(person -> {
            assertThat(person.getId()).isEqualTo(allPersons.get(0).getId());
            assertThat(person.getLastName()).isEqualTo(allPersons.get(0).getLastName());
            assertThat(person.getFirstName()).isEqualTo(allPersons.get(0).getFirstName());
            assertThat(person.getCity()).isEqualTo(allPersons.get(0).getCity());
            assertThat(person.getAnimals()).isEqualTo(allPersons.get(0).getAnimals());
        });
    }// get_person_by_id_when_id_is_1_should_success()

    @Test
    public void get_person_by_id_when_id_is_9_should_return_empty_optional() throws MissingArgumentException {
        Optional<Person> optPerson = servicePerson.getPersonById(9);
        assertThat(optPerson).isNotPresent();
    }// get_person_by_id_when_id_is_9_should_return_empty_optional()

    @Test
    public void get_person_by_id_when_id_is_null_should_throw_missing_argument_exception(){
        assertThatThrownBy(() -> servicePerson.getPersonById(null))
                .hasMessage("Id is missing")
                .isInstanceOf(MissingArgumentException.class);
    }// get_person_by_id_when_id_is_null_should_throw_missing_argument_exception()
    
}// ServicePersonUT
