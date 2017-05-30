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

package com.raulh82vlc.AvengersComics.ui.presentation;

import android.support.annotation.NonNull;

import com.raulh82vlc.AvengersComics.domain.interactors.GetComicsInteractor;
import com.raulh82vlc.AvengersComics.domain.interactors.mappers.ComicsListModelDataMapper;
import com.raulh82vlc.AvengersComics.domain.interactors_response.GetComicsListCallbackImpl;
import com.raulh82vlc.AvengersComics.domain.interactors_response.GetMoreComicsListCallbackImpl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.verification.VerificationMode;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Interactions within our presenter is critic to handle our actions
 * @author Raul Hernandez Lopez.
 */
public class ComicsPresenterImplTest {


    private static final String KEY_CAPTAIN = "123345556";
    @Mock
    private ComicsListModelDataMapper mapper;
    @Mock
    private GetComicsInteractor interactor;
    @Mock
    private ComicsPresenter.View view;
    private ComicsPresenterImpl callbackToTest;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        callbackToTest = spy(new ComicsPresenterImpl(interactor, mapper));
        callbackToTest.setView(view);
        callbackToTest.addAvenger(KEY_CAPTAIN, "Captain America");
    }

    @After
    public void tearDown() throws Exception {
        callbackToTest = null;
    }

    @Test
    public void raiseException() throws Exception {
        when(view.isReady()).thenReturn(true);
        callbackToTest.raiseException("error");
        verify(view, getTime()).isReady();
        verify(view, getTime()).showErrorMessage(anyString());
    }

    @Test
    public void initInitialVisibility() throws Exception {
        when(view.isReady()).thenReturn(true);
        callbackToTest.initInitialVisibility();
        verify(view, getTime()).isReady();
        verify(view, getTime()).showInitialVisibility();
    }

    @Test
    public void loadMoreComics() throws Exception {
        when(view.isReady()).thenReturn(true);
        callbackToTest.getComics(KEY_CAPTAIN);
        callbackToTest.loadMoreComics();
        verify(view, getTimes()).isReady();
        verify(view, getTimes()).showLoader(anyString());
        verify(interactor, getTime()).executeLoadMore(anyString(), anyMap(), any(GetMoreComicsListCallbackImpl.class));
    }

    @Test
    public void getComics() throws Exception {
        when(view.isReady()).thenReturn(true);
        callbackToTest.getComics(KEY_CAPTAIN);
        verify(view, getTime()).isReady();
        verify(view, getTime()).showLoader(anyString());
        verify(interactor, getTime()).execute(anyString(), anyMap(), any(GetComicsListCallbackImpl.class));
    }

    @NonNull
    private VerificationMode getTime() {
        return times(1);
    }

    @NonNull
    private VerificationMode getTimes() {
        return times(2);
    }
}