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

import android.content.Context;
import android.text.TextUtils;

import com.raulh82vlc.AvengersComics.R;
import com.raulh82vlc.AvengersComics.domain.models.ComicUI;

import javax.inject.Inject;

/**
 * Implementation of the {@link ComicDetailsPresenter}
 *
 * @author Raul Hernandez Lopez
 */
public class ComicDetailsPresenterImpl implements ComicDetailsPresenter {

    private View view;

    @Inject
    ComicDetailsPresenterImpl() {
    }

    @Override
    public void setView(View view) {
        if (view == null) {
            throw new IllegalArgumentException("The view should be instantiated");
        }
        this.view = view;
    }

    @Override
    public void resetView() {
        view = null;
    }

    @Override
    public void setInfo(ComicUI comic, Context context) {
        if (view.isReady()) {
            if (comic != null) {
                view.showEffect();
                view.showMainImage(comic.getUriRandom());
                if (!TextUtils.isEmpty(comic.getTitle())) {
                    view.showComicTitle(comic.getTitle());
                }
                if (!TextUtils.isEmpty(comic.getDescription())) {
                    view.showDescription(comic.getDescription());
                }
                if (!TextUtils.isEmpty(comic.getCreators())) {
                    view.showCreators(comic.getCreators());
                }
                if (!TextUtils.isEmpty(comic.getCharacters())) {
                    view.showCharacters(comic.getCharacters());
                }
                String comicId = String.valueOf(comic.getComicId());
                if (!TextUtils.isEmpty(comicId)) {
                    view.showComicId(comicId);
                }
            } else {
                view.errorWithComicDetail(context.getResources().getString(R.string.no_comic_info));
            }

        }
    }

    @Override
    public void finishDueToError() {
        if (view != null && view.isReady()) {
            view.finishAfterError();
        }
    }

    @Override
    public void errorGettingInfo(Context context) {
        if (view != null && view.isReady()) {
            view.errorWithComicDetail(context.getResources().getString(R.string.no_comic_info));
        }
    }
}
