/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 * (c) 2015 - Interseroh
 */
package de.interseroh.report.server.birt;

public enum BirtDataType {

    TYPE_ANY(0),
    TYPE_STRING(1),
    TYPE_FLOAT(2),
    TYPE_DECIMAL(3),
    TYPE_DATE_TIME(4),
    TYPE_BOOLEAN(5),
    TYPE_INTEGER(6),
    TYPE_DATE(7),
    TYPE_TIME(8);

    private int dataType;

    BirtDataType(int dataType) {
        this.dataType = dataType;
    }

    public int getType() {
        return dataType;
    }

    public static BirtDataType valueOf(int dataType) {
        for (BirtDataType type : BirtDataType.values()) {
            if (type.dataType == dataType)
                return type;

        }
        throw new UnknownDataTypeException(dataType);
    }

}
