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

package com.raulh82vlc.AvengersComics.di.components;

import com.raulh82vlc.AvengersComics.di.modules.ActivityModule;
import com.raulh82vlc.AvengersComics.di.modules.ComicDetailsModule;
import com.raulh82vlc.AvengersComics.di.scopes.ActivityScope;
import com.raulh82vlc.AvengersComics.ui.activities.ComicDetailsActivity;

import dagger.Component;

/**
 * This is the main container of dependencies
 * linked to the activity context. Moreover, this component extends
 * {@link AbstractActivityComponent}, therefore the activity context
 * is provided from the abstract parent component.
 *
 * @author Raul Hernandez Lopez
 */
@ActivityScope
@Component(dependencies = ApplicationComponent.class,
        modules = {
                ActivityModule.class,
                ComicDetailsModule.class
        })
public interface ComicDetailsComponent extends AbstractActivityComponent {
    void inject(ComicDetailsActivity comicDetailsActivity);
}

