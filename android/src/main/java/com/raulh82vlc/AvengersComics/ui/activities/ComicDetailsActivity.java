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

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.raulh82vlc.AvengersComics.AvengersComicsApp;
import com.raulh82vlc.AvengersComics.R;
import com.raulh82vlc.AvengersComics.di.components.ComicDetailsComponent;
import com.raulh82vlc.AvengersComics.di.components.DaggerComicDetailsComponent;
import com.raulh82vlc.AvengersComics.di.modules.ActivityModule;
import com.raulh82vlc.AvengersComics.domain.models.ComicUI;
import com.raulh82vlc.AvengersComics.ui.presentation.ComicDetailsPresenter;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.InjectView;
import timber.log.Timber;

/**
 * <p>Comic Details UI</p>
 *
 * @author Raul Hernandez Lopez
 */
public class ComicDetailsActivity extends BaseActivity implements ComicDetailsPresenter.View {

    private final static String IMG_TRANSITION_TAG = "activity_compat_transition_by_img";
    // UI Injections
    @InjectView(R.id.title_txt)
    TextView title;

    @InjectView(R.id.description_txt)
    TextView description;

    @InjectView(R.id.id_txt)
    TextView idComic;

    @InjectView(R.id.creators_txt)
    TextView creators;

    @InjectView(R.id.characters_txt)
    TextView characters;

    @InjectView(R.id.main_image)
    ImageView mainImage;

    @InjectView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;

    public static final String KEY_COMIC = "ComicDetailsActivity.KEY_COMIC";

    // Comic Component for dagger
    private ComicDetailsComponent comicDetailsComponent;

    private ComicUI comic;
    @Inject
    ComicDetailsPresenter comicDetailsPresenter;

    /**
     * Navigate To details of the comic using different animations depending on the Android version
     */
    public static void navigateToDetailsActivity(AppCompatActivity activity, ComicUI comic, View view) {
        Intent intent = new Intent(activity, ComicDetailsActivity.class);
        Bundle data = new Bundle();
        data.putParcelable(KEY_COMIC, comic);
        intent.putExtras(data);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,
                    view, IMG_TRANSITION_TAG);
            ActivityCompat.startActivity(activity, intent, optionsCompat.toBundle());
        } else {
            activity.startActivity(intent);
            activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }

    @Override
    public boolean onNavigateUp() {
        onBackPressed();
        return true;
    }

    public ComicDetailsComponent component() {
        if (comicDetailsComponent == null) {
            comicDetailsComponent = DaggerComicDetailsComponent.builder()
                    .applicationComponent(((AvengersComicsApp) getApplication()).component())
                    .activityModule(new ActivityModule(this))
                    .build();
        }
        return comicDetailsComponent;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityCompat.postponeEnterTransition(this);
        }
        component().inject(this);
        setToolbarInitialisation();
        comicDetailsPresenter.setView(this);
        if (getIntent() != null) {
            setIntentParameters();
            if (comic != null) {
                setInitialTitle();
                comicDetailsPresenter.setInfo(comic, this);
            } else {
                comicDetailsPresenter.errorGettingInfo(this);
            }
        } else {
            comicDetailsPresenter.errorGettingInfo(this);
        }
    }

    private void setIntentParameters() {
        comic = getIntent().getParcelableExtra(KEY_COMIC);
    }

    @Override
    protected void setInitialTitle() {
        toolbar.setTitle(comic.getTitle());
        collapsingToolbar.setExpandedTitleColor(ContextCompat.getColor(this, android.R.color.transparent));
        collapsingToolbar.setCollapsedTitleTextColor(ContextCompat.getColor(this, android.R.color.white));
        collapsingToolbar.setTitle(comic.getTitle());
    }

    @Override
    protected void setToolbarInitialisation() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_comic_details;
    }

    @Override
    protected ComicDetailsActivity getActivity() {
        return this;
    }


    @Override
    protected void onDestroy() {
        comicDetailsPresenter.resetView();
        super.onDestroy();
    }

    @Override
    public void errorWithComicDetail(String error) {
        Timber.e(error);
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        comicDetailsPresenter.finishDueToError();
    }

    @Override
    public boolean isReady() {
        return !isFinishing();
    }

    @Override
    public void finishAfterError() {
        finish();
    }

    @Override
    public void showDescription(String comicDescription) {
        description.setText(comicDescription);
    }

    @Override
    public void showCreators(String comicCreators) {
        creators.setText(getString(R.string.creators, comicCreators));
    }

    @Override
    public void showCharacters(String comicCharacters) {
        characters.setText(getString(R.string.characters, comicCharacters));
    }

    @Override
    public void showMainImage(String uriRandom) {
        Picasso.with(this)
                .load(uriRandom)
                .placeholder(R.drawable.view_holder)
                .into(mainImage);
    }

    @Override
    public void showComicId(String comicId) {
        idComic.setText(getString(R.string.comid_id, comicId));
    }

    @Override
    public void showEffect() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ViewCompat.setTransitionName(mainImage, IMG_TRANSITION_TAG);
            ActivityCompat.startPostponedEnterTransition(this);
        }
    }

    @Override
    public void showComicTitle(String comicTitle) {
        title.setText(comicTitle);
    }
}
