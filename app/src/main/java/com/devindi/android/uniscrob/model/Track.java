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

package com.devindi.android.uniscrob.model;

import android.os.Bundle;

public class Track {

    private final String title;
    private final String artist;
    private final String album;

    public Track(String title, String artist, String album) {
        this.title = title;
        this.artist = artist;
        this.album = album;
    }

    public Track(Bundle bundle) {
        this.title = bundle.getString("title", "");
        this.artist = bundle.getString("artist", "");
        this.album = bundle.getString("album", "");
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("artist", artist);
        bundle.putString("album", album);
        return bundle;
    }

    @Override
    public String toString() {
        return "Track{" +
                "title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", album='" + album + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Track track = (Track) o;

        if (title != null ? !title.equals(track.title) : track.title != null) return false;
        if (artist != null ? !artist.equals(track.artist) : track.artist != null) return false;
        return album != null ? album.equals(track.album) : track.album == null;
    }

    public static class Builder {

        private String title;
        private String artist;
        private String album;

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setArtist(String artist) {
            this.artist = artist;
            return this;
        }

        public Builder setAlbum(String album) {
            this.album = album;
            return this;
        }

        public Track createTrack() {
            return new Track(title, artist, album);
        }
    }
}
