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

package com.raulh82vlc.AvengersComics.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;

import com.raulh82vlc.AvengersComics.AvengersComicsApp;
import com.raulh82vlc.AvengersComics.R;
import com.raulh82vlc.AvengersComics.di.components.ComicsListComponent;
import com.raulh82vlc.AvengersComics.di.components.DaggerComicsListComponent;
import com.raulh82vlc.AvengersComics.di.modules.ActivityModule;

/**
 * <p>Comics List Activity</p>
 *
 * @author Raul Hernandez Lopez
 */
public class ComicsListActivity extends BaseActivity {

    private ComicsListComponent comicsListComponent;

    public ComicsListComponent component() {
        if (comicsListComponent == null) {
            comicsListComponent = DaggerComicsListComponent.builder()
                    .applicationComponent(((AvengersComicsApp) getApplication()).component())
                    .activityModule(new ActivityModule(this))
                    .build();
        }
        return comicsListComponent;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        component().inject(this);
        setToolbarInitialisation();
        setInitialTitle();
    }

    @Override
    protected void setInitialTitle() {
        toolbar.setTitle(getString(R.string.main_screen));
    }

    @Override
    protected void setToolbarInitialisation() {
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_comics_list;
    }

    @Override
    protected ComicsListActivity getActivity() {
        return this;
    }
}
