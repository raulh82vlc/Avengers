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

import android.support.annotation.NonNull;

import com.raulh82vlc.AvengersComics.BuildConfig;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import timber.log.Timber;

/**
 * Comics API variables and its methods to create them, required to make requests
 *
 * @author Raul Hernandez Lopez.
 */

public class ComicsAPIVariablesImpl implements ComicsAPIVariables {

    @Override
    public String computeMD5(String messageToBecomeMD5) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(messageToBecomeMD5.getBytes());
            byte messageDigest[] = digest.digest();
            return getHexString(messageDigest);
        } catch (NoSuchAlgorithmException e) {
            Timber.e(e.getMessage());
            e.printStackTrace();
        }
        return "";
    }

    @NonNull
    private String getHexString(byte[] messageDigest) {
        // Create Hex String
        StringBuilder hexString = new StringBuilder();
        for (byte aMessageDigest : messageDigest) {
            String h = Integer.toHexString(0xFF & aMessageDigest);
            while (h.length() < 2)
                h = "0" + h;
            hexString.append(h);
        }
        return hexString.toString();
    }

    @Override
    public String getAPIHashCode() {
        return computeMD5(getTimeStamp() + getPrivateAPIKey() + getPublicAPIKey());
    }

    @Override
    public String getTimeStamp() {
        return String.valueOf(System.currentTimeMillis());
    }

    @Override
    public String getPublicAPIKey() {
        return BuildConfig.API_PUBLIC_KEY;
    }

    @Override
    public String getPrivateAPIKey() {
        return BuildConfig.API_PRIVATE_KEY;
    }

    @Override
    public int getPageLimit() {
        return ComicsAPIConstants.PAGE_LIMIT;
    }

    @Override
    public int getValidCode() {
        return ComicsAPIConstants.HTTP_OK_CODE;
    }
}
