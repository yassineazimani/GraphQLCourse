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
package com.tuto.graphql;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static graphql.GraphQL.newGraphQL;

/**
 * Cette classe fait le pont entre les requêtes clientes et
 * les services. Elle gère donc les requêtes GraphQL.
 *
 * @author Yassine AZIMANI
 */
@Component
public class GraphQlUtility {

    /**
     * Schéma directeur / Contrat graphQL déterminant les relations et
     * les données à fournir au client.
     */
    @Value("classpath:schemas.graphqls")
    private Resource schemaResource;

    /**
     * DataFetchers réalisant le lien entre le moteur GraphQL et les couches métiers (services).
     */
    @Autowired
    private GraphQLDataFetchers graphQLDataFetchers;

    /**
     * Création d'une instance GraphQL
     * @return instance GraphQL
     * @throws IOException
     */
    @PostConstruct
    public GraphQL createGraphQlObject() throws IOException {
        InputStream is = schemaResource.getInputStream();
        TypeDefinitionRegistry typeRegistry = new SchemaParser()
                .parse(new InputStreamReader(is));
        RuntimeWiring wiring = buildRuntimeWiring();
        GraphQLSchema schema = new SchemaGenerator()
                .makeExecutableSchema(typeRegistry, wiring);
        return newGraphQL(schema).build();
    }// createGraphQlObject()

    private RuntimeWiring buildRuntimeWiring() {
        RuntimeWiring.Builder builder = RuntimeWiring.newRuntimeWiring();
        buildQueries(builder);
        return builder.build();
    }// buildRuntimeWiring()

    private void buildQueries(RuntimeWiring.Builder builder){
        builder.type("Query", typeWiring -> typeWiring
                .dataFetchers(graphQLDataFetchers.getDataFetchersQuery()));
    }// buildQueries()

}// GraphQlUtility