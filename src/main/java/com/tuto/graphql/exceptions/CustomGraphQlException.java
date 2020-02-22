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
package com.tuto.graphql.exceptions;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Surcharge des exceptions générés par GraphQL
 *
 * @author Yassine AZIMANI
 */
public class CustomGraphQlException extends RuntimeException implements GraphQLError {

    public CustomGraphQlException(String errorMessage) {
        super(errorMessage);
    }// CustomGraphQlException()

    @Override
    public Map<String, Object> getExtensions() {
        Map<String, Object> customAttributes = new LinkedHashMap<>();
        customAttributes.put("errorMessage", this.getMessage());
        return customAttributes;
    }// getExtensions()

    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }// getLocations()

    @Override
    public ErrorType getErrorType() {
        return null;
    }// getErrorType()
}// CustomGraphQlException
