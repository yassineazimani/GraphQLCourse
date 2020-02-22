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
import com.tuto.graphql.utils.CacheUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceAnimal {

    /**
     * Utilitaire cache
     */
    private CacheUtils cacheUtils;

    /**
     * Accès à la base de données pour la récupération des animaux
     */
    private RepositoryAnimal repositoryAnimal;

    private final static Logger LOGGER = LogManager.getLogger(ServiceAnimal.class);

    @Autowired
    public ServiceAnimal(CacheUtils cacheUtils, RepositoryAnimal repositoryAnimal){
        this.cacheUtils = cacheUtils;
        this.repositoryAnimal = repositoryAnimal;
    }// ServiceAnimal()

    /**
     * Récupération de tous les animaux
     * @return Liste de {@link com.tuto.graphql.model.Animal}
     */
    @Cacheable("animals")
    public List<Animal> getAllAnimals(){
        LOGGER.info("Request getAllAnimals no cached (First request)");
        return this.repositoryAnimal.findAll();
    }// getAllAnimals()

    /**
     * Récupération d'un animal en fonction de son identifiant
     * @param id Identifiant de l'animal
     * @return {@link com.tuto.graphql.model.Animal}
     * @throws MissingArgumentException Levée si l'identifiant n'est pas fourni
     */
    @Cacheable("animal")
    public Optional<Animal> getAnimalById(Integer id) throws MissingArgumentException {
        if(id == null){
            throw new MissingArgumentException("Id is missing");
        }
        LOGGER.info("Request getAnimalById no cached (First request)");
        return this.repositoryAnimal.findById(id);
    }// getAnimalById()

    /**
     * Suppression manuelle du cache
     */
    public void clearCacheAnimal(){
        this.cacheUtils.clearCacheByName("animal");
        this.cacheUtils.clearCacheByName("animals");
    }// clearCacheAnimal()

}// ServiceAnimal
