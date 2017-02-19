/*
 * Copyright 2016 Andrii Mikhin
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

package com.devindi.android.uniscrob.screen.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devindi.android.uniscrob.model.Track;
import com.devindi.android.uniscrob.screen.BaseFragment;
import com.devindi.android.uniscrob.storage.TrackStorage;

import java.util.List;

import rx.Observer;
import rx.Subscription;
import timber.log.Timber;

public class ListFragment extends BaseFragment implements Observer<List<Track>> {

    private TrackStorage storage;
    private Subscription subscription;
    private TrackAdapter adapter;

    public ListFragment() {
        storage = new TrackStorage();
        adapter = new TrackAdapter();
    }

    @Override
    public void onStart() {
        super.onStart();
        subscription = storage.getAll().subscribe(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return new RecyclerView(container.getContext());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = (RecyclerView) view;
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onNext(List<Track> tracks) {
        Timber.d("Received new list");
        adapter.setTracks(tracks);
    }
}
