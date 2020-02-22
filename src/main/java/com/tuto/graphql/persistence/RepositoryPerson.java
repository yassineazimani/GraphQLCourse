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
import com.tuto.graphql.utils.FakeDataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Récupération des personnes provenant de la base de données.
 * Note : Ici, la base de données est simulée. Rien ne vous
 * empêcher de modifier la classe pour y incorporer une connexion
 * avec une base de données.
 */
@Repository
public class RepositoryPerson {

    /**
     * Mock de la base de données
     */
    private FakeDataUtils fakeDataUtils;

    @Autowired
    public RepositoryPerson(FakeDataUtils fakeDataUtils){
        this.fakeDataUtils = fakeDataUtils;
    }// RepositoryPerson()

    /**
     * Récupère toutes les personnes de la base de données
     * @return Liste de {@link Person}
     */
    public List<Person> findAll(){
        return fakeDataUtils.getAllPersons();
    }// findAll()

    /**
     * Récupère une personne en fonction de son identifiant
     * @param id Identifiant de la personne à récupérer
     * @return {@link Person}
     */
    public Optional<Person> findById(Integer id){
        return fakeDataUtils.getAllPersons()
                .stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }// findById()

}// RepositoryPerson
