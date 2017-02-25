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

package com.devindi.android.uniscrob.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.devindi.android.uniscrob.model.Track;
import com.devindi.android.uniscrob.service.ScrobblerService;
import com.devindi.android.uniscrob.tools.BundleUtil;

import java.util.Date;

import timber.log.Timber;

public abstract class AbsMusicReceiver extends BroadcastReceiver {

    private static final String EXTRA_TRACK = "track";
    private static final String EXTRA_ARTIST = "artist";
    private static final String EXTRA_ALBUM = "album";

    @Override
    public void onReceive(Context context, Intent intent) {
        Timber.d("Received new track\n %s from %s", BundleUtil.bundleToString(intent.getExtras()), intent.getAction());
        if (!shouldTrack(intent)) return;
        Track track = parseTrack(intent);
        context.startService(createTrackIntent(context, track));
    }

    private Intent createTrackIntent(Context context, Track track) {
        Intent intent = new Intent(context, ScrobblerService.class);
        intent.putExtras(track.toBundle());
        return intent;
    }

    private Track parseTrack(Intent intent) {
        Bundle rawData = intent.getExtras();
        return new Track.Builder()
                .setTitle(rawData.getString(EXTRA_TRACK))
                .setAlbum(rawData.getString(EXTRA_ALBUM))
                .setArtist(rawData.getString(EXTRA_ARTIST))
                .setCreatedAt(new Date())
                .createTrack();
    }

    // TODO: 25.02.17 refactor
    @SuppressWarnings("WeakerAccess")
    protected boolean shouldTrack(Intent intent) {
        Boolean isPlaying = BundleUtil.getBoolOrNumberAsBoolExtra(intent.getExtras(), null, "playing", "playstate", "isPlaying", "isplaying", "is_playing");
        return isPlaying == null ? false : isPlaying;
    }
}
