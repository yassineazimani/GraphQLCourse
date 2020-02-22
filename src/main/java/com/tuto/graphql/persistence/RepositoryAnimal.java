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
import com.tuto.graphql.utils.FakeDataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Récupération des animaux provenant de la base de données.
 * Note : Ici, la base de données est simulée. Rien ne vous
 * empêcher de modifier la classe pour y incorporer une connexion
 * avec une base de données.
 */
@Repository
public class RepositoryAnimal {

    /**
     * Mock de la base de données
     */
    private FakeDataUtils fakeDataUtils;

    @Autowired
    public RepositoryAnimal(FakeDataUtils fakeDataUtils){
        this.fakeDataUtils = fakeDataUtils;
    }// RepositoryAnimal()

    public List<Animal> findAll(){
        return fakeDataUtils.getAllAnimals();
    }// findAll()

    public Optional<Animal> findById(Integer id){
        return fakeDataUtils.getAllAnimals()
                .stream()
                .filter(a -> a.getId().equals(id))
                .findFirst();
    }// findById()

}// RepositoryAnimal
