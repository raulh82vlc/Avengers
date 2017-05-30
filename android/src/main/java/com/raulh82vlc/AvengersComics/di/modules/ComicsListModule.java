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

package com.raulh82vlc.AvengersComics.di.modules;

import com.raulh82vlc.AvengersComics.di.scopes.ActivityScope;
import com.raulh82vlc.AvengersComics.domain.interactors.GetComicsInteractor;
import com.raulh82vlc.AvengersComics.domain.interactors.GetComicsInteractorImpl;
import com.raulh82vlc.AvengersComics.ui.presentation.ComicsPresenter;
import com.raulh82vlc.AvengersComics.ui.presentation.ComicsPresenterImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Module which provides all user required artifacts
 * (presenter, interactors)
 * in order to use them in a decoupled way
 *
 * @author Raul Hernandez Lopez
 */
@Module
public class ComicsListModule {

    @Provides
    @ActivityScope
    GetComicsInteractor provideGetComicsInteractor(GetComicsInteractorImpl interactor) {
        return interactor;
    }

    @Provides
    @ActivityScope
    ComicsPresenter provideComicsPresenter(ComicsPresenterImpl presenter) {
        return presenter;
    }
}
