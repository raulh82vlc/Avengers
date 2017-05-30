/*
 * Copyright (C) 2017 Raul Hernandez Lopez @raulh82vlc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.raulh82vlc.AvengersComics.domain.datasources.net.parameters;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Calculating MD5 is critic, therefore a Unit test is mandatory
 * @author Raul Hernandez Lopez.
 */
public class ComicsAPIVariablesImplTest {

    private ComicsAPIVariables comicsAPIVariables;

    @Before
    public void setUp() throws Exception {
        comicsAPIVariables = new ComicsAPIVariablesImpl();
    }
    @Test
    public void computeMD5() throws Exception {
        final String hashCalculatedFromMD5 = comicsAPIVariables.computeMD5(
                "1469886520"
                + comicsAPIVariables.getPrivateAPIKey()
                + comicsAPIVariables.getPublicAPIKey());
        assertEquals("38c6d6eac62771368344a319f85f735d", hashCalculatedFromMD5);
    }
}