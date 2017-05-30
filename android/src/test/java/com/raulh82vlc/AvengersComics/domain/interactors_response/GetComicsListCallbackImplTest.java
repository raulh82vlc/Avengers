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

package com.raulh82vlc.AvengersComics.domain.interactors_response;

import android.support.annotation.NonNull;

import com.raulh82vlc.AvengersComics.domain.interactors.GetComicsInteractor;
import com.raulh82vlc.AvengersComics.domain.interactors.mappers.ComicsListModelDataMapper;
import com.raulh82vlc.AvengersComics.domain.models.ComicUI;
import com.raulh82vlc.AvengersComics.domain.models.ComicsResponse;
import com.raulh82vlc.AvengersComics.ui.presentation.ComicsPresenter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.verification.VerificationMode;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Interactions within our callback is critic to have our results back
 * @author Raul Hernandez Lopez.
 */
public class GetComicsListCallbackImplTest {

    private final static String ERROR = "ERROR";

    @Mock
    private ComicsPresenter.View view;
    @Mock
    private ComicsListModelDataMapper mapper;
    @Mock
    private ComicsResponse response;
    private List<ComicUI> comics = new ArrayList<>();
    private GetComicsInteractor.GetComicsListCallback callbackToTest;

    @Before
    public void setup() throws Exception {
        initMocks(this);
        callbackToTest = spy(new GetComicsListCallbackImpl(view, mapper));
    }

    @After
    public void tearDown() throws Exception {
        comics.clear();
        callbackToTest = null;
    }

    @Test
    public void callbackIsWorkingForOKAndReady() throws Exception {
        // when it is returned true for the isReady method by default,
        // then we could check 1 iteration appears on each method
        when(view.isReady()).thenReturn(true);
        comics.add(mock(ComicUI.class));
        when(mapper.transform(response)).thenReturn(comics);
        callbackToTest.onGetComicsListOK(response);
        verify(view, getTimes()).isReady();
        verify(view, getTimes()).hideLoader();
        verify(view, getTimes()).showList();
        verify(view, getTimes()).updateComicsList(anyList());
        verify(view, getTimes()).showAnimation();
    }

    @Test
    public void callbackIsWorkingForKOAndReady() throws Exception {
        // when it is returned true for the isReady method by default,
        // then we could check 1 iteration appears on each method
        when(view.isReady()).thenReturn(true);
        when(mapper.transform(response)).thenReturn(comics);
        callbackToTest.onGetComicsListKO(ERROR);
        verify(view, getTimes()).isReady();
        verify(view, getTimes()).hideLoader();
        verify(view, getTimes()).showEmptyState();
        verify(view, getTimes()).errorGettingComics(anyString());
    }

    @Test
    public void callbackIsWorkingKOForNotReady() throws Exception {
        // when it is returned true for the isReady method by default,
        // then we could check 1 iteration appears on each method
        when(view.isReady()).thenReturn(false);
        when(mapper.transform(response)).thenReturn(comics);
        callbackToTest.onGetComicsListKO(ERROR);
        verify(view, getTimes()).isReady();
        verify(view, getNoTime()).hideLoader();
        verify(view, getNoTime()).showEmptyState();
        verify(view, getNoTime()).errorGettingComics(anyString());
    }

    @Test
    public void callbackIsWorkingForOKForNotReady() throws Exception {
        // when it is returned true for the isReady method by default,
        // then we could check 1 iteration appears on each method
        when(view.isReady()).thenReturn(false);
        comics.add(mock(ComicUI.class));
        when(mapper.transform(response)).thenReturn(comics);
        callbackToTest.onGetComicsListOK(response);
        verify(view, getTimes()).isReady();
        verify(view, getNoTime()).hideLoader();
        verify(view, getNoTime()).showList();
        verify(view, getNoTime()).updateComicsList(anyList());
        verify(view, getNoTime()).showAnimation();
    }

    @NonNull
    private VerificationMode getTimes() {
        return times(1);
    }

    @NonNull
    private VerificationMode getNoTime() {
        return times(0);
    }
}