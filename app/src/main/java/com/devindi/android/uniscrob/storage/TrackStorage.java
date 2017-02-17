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

    public List<Track> getAll() {
        Realm realm = Realm.getDefaultInstance();
        List<Track> tracks = new ArrayList<>();
        RealmResults<TrackDTO> all = realm.where(TrackDTO.class).findAll();
        for (TrackDTO dto : all) {
            tracks.add(new Track.Builder()
                    .setAlbum(dto.getAlbum())
                    .setArtist(dto.getArtist())
                    .setTitle(dto.getTitle())
                    .createTrack());
        }
        realm.close();
        return tracks;
    }
}
