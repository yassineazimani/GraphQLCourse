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
package com.tuto.graphql.endpoints;

import com.tuto.graphql.GraphQlUtility;
import graphql.ExecutionResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/public")
public class EndPoint {

    private GraphQlUtility graphQlUtility;

    @Autowired
    public EndPoint(GraphQlUtility graphQlUtility){
        this.graphQlUtility = graphQlUtility;
    }// EndPoint()

    private static final Logger LOGGER = LogManager.getLogger(EndPoint.class);

    /**
     * Endpoint permettant l'exécution d'une requête GraphQL publique
     *
     * @param query Requête GraphQL
     * @return réponse JSON
     */
    @PostMapping(value = "/query")
    public ResponseEntity<?> publicQuery(@RequestBody String query) {
        ResponseEntity<?> response =
                new ResponseEntity<>("Problème technique", HttpStatus.INTERNAL_SERVER_ERROR);
        try {
            ExecutionResult result = graphQlUtility.createGraphQlObject().execute(query);
            if (result.getErrors() == null || result.getErrors().isEmpty()) {
                return ResponseEntity.ok(result.getData());
            }
            String errors = StringUtils.join(result.getErrors(), ",");
            errors = !errors.isEmpty() && errors.split(":").length > 1 ? errors.split(":")[1] : "";
            if(StringUtils.isNotEmpty(errors)) {
                errors = errors.split("=")[0];
                errors = errors.replace("locations", "");
            }
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }// publicQuery()

}// EndPoint
