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
import com.tuto.graphql.utils.CacheUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicePerson {

    /**
     * Utilitaire cache
     */
    private CacheUtils cacheUtils;

    /**
     * Accès à la base de données pour la récupération des personnes
     */
    private RepositoryPerson repositoryPerson;

    private final static Logger LOGGER = LogManager.getLogger(ServicePerson.class);

    @Autowired
    public ServicePerson(CacheUtils cacheUtils, RepositoryPerson repositoryPerson){
        this.cacheUtils = cacheUtils;
        this.repositoryPerson = repositoryPerson;
    }// ServicePerson()

    /**
     * Récupération de toutes les personnes
     * @return Liste de {@link Person}
     */
    @Cacheable("persons")
    public List<Person> getAllPersons(){
        LOGGER.info("Request getAllPersons no cached (First request)");
        return this.repositoryPerson.findAll();
    }// getAllPersons()

    /**
     * Récupération d'une personne en fonction de son identifiant
     * @param id Identifiant de la personne
     * @return {@link Person}
     * @throws MissingArgumentException Levée si l'identifiant n'est pas fourni
     */
    @Cacheable("person")
    public Optional<Person> getPersonById(Integer id) throws MissingArgumentException {
        if(id == null){
            throw new MissingArgumentException("Id is missing");
        }
        LOGGER.info("Request getPersonById no cached (First request)");
        return this.repositoryPerson.findById(id);
    }// getPersonById()

    /**
     * Suppression manuelle du cache
     */
    public void clearCachePerson(){
        this.cacheUtils.clearCacheByName("persons");
        this.cacheUtils.clearCacheByName("person");
    }// clearCachePerson()

}// ServicePerson
