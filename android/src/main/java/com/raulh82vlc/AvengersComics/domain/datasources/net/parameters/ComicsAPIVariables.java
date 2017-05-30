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

/**
 * Comics Extra parameters required for making request contract
 * @author Raul Hernandez Lopez.
 */
public interface ComicsAPIVariables {
    /**
     * <p>This MD5 calculation after seeing other approaches like:
     * https://stackoverflow.com/questions/415953/how-can-i-generate-an-md5-hash
     * </p>
     * I have decided to take this one from Stack Over Flow which seems to work fine:
     * https://stackoverflow.com/questions/4846484/md5-hashing-in-android/4846511#4846511</p>
     * @return md5 String composed by a String combination with "time stamp + privateKey + publicKey"
     *
     */
    String computeMD5(String messageToBecomeMD5);
    String getAPIHashCode();
    String getTimeStamp();
    String getPublicAPIKey();
    String getPrivateAPIKey();
    int getPageLimit();
    int getValidCode();
}
