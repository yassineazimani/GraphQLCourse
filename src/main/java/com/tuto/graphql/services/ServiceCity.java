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
import com.tuto.graphql.utils.CacheUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceCity {

    /**
     * Utilitaire cache
     */
    private CacheUtils cacheUtils;

    /**
     * Accès à la base de données pour la récupération des villes
     */
    private RepositoryCity repositoryCity;

    private final static Logger LOGGER = LogManager.getLogger(ServiceCity.class);

    @Autowired
    public ServiceCity(CacheUtils cacheUtils, RepositoryCity repositoryCity){
        this.cacheUtils = cacheUtils;
        this.repositoryCity = repositoryCity;
    }// ServiceCity()

    /**
     * Récupération de toutes les villes
     * @return Liste de {@link City}
     */
    @Cacheable("cities")
    public List<City> getAllCities(){
        LOGGER.info("Request getAllCities no cached (First request)");
        return this.repositoryCity.findAll();
    }// getAllCities()

    /**
     * Récupération d'une ville en fonction de son identifiant
     * @param id Identifiant de la ville
     * @return {@link City}
     * @throws MissingArgumentException Levée si l'identifiant n'est pas fourni
     */
    @Cacheable("city")
    public Optional<City> getCityById(Integer id) throws MissingArgumentException {
        if(id == null){
            throw new MissingArgumentException("Id is missing");
        }
        LOGGER.info("Request getCityById no cached (First request)");
        return this.repositoryCity.findById(id);
    }// getCityById()

    /**
     * Suppression manuelle du cache
     */
    public void clearCacheCity(){
        this.cacheUtils.clearCacheByName("city");
        this.cacheUtils.clearCacheByName("cities");
    }// clearCacheCity()
    
}// ServiceCity
