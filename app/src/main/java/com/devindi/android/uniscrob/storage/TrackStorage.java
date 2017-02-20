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

package com.devindi.android.uniscrob.storage;

import com.devindi.android.uniscrob.model.Track;
import com.devindi.android.uniscrob.storage.dto.TrackDTO;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;
import rx.functions.Action0;
import rx.functions.Func1;
import timber.log.Timber;

public class TrackStorage {

    public TrackStorage() {

    }

    public void addTrack(final Track track) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                TrackDTO trackDTO = realm.createObject(TrackDTO.class);
                trackDTO.setAlbum(track.getAlbum());
                trackDTO.setArtist(track.getArtist());
                trackDTO.setTitle(track.getTitle());
                Timber.d("Saved track to DB");
            }
        });
        realm.close();
    }

    public Observable<List<Track>> getAll() {
        Timber.d("Realm opened");
        final Realm realm = Realm.getDefaultInstance();
        return realm
                .asObservable()
                .map(new Func1<Realm, RealmResults<TrackDTO>>() {
                    @Override
                    public RealmResults<TrackDTO> call(Realm realm) {
                        return realm.where(TrackDTO.class).findAll();
                    }
                })
                .map(new Func1<RealmResults<TrackDTO>, List<Track>>() {
                    @Override
                    public List<Track> call(RealmResults<TrackDTO> dtos) {
                        List<Track> list = new ArrayList<>();
                        for (TrackDTO dto : dtos) {
                            list.add(new Track.Builder()
                                    .setAlbum(dto.getAlbum())
                                    .setArtist(dto.getArtist())
                                    .setTitle(dto.getTitle())
                                    .createTrack());
                        }
                        return list;
                    }
                })
                .doOnUnsubscribe(new Action0() {
                    @Override
                    public void call() {
                        Timber.d("Realm closed");
                        realm.close();
                    }
                });
    }

    public void clear() {
        Realm realm = Realm.getDefaultInstance();
        realm.delete(TrackDTO.class);
        realm.close();
    }
}
