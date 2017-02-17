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

public abstract class AbsMusicReceiver extends BroadcastReceiver {

    public static final String EXTRA_TRACK = "EXTRA_TRACK";
    public static final String EXTRA_ARTIST = "EXTRA_ARTIST";
    public static final String EXTRA_ALBUM = "EXTRA_ALBUM";

    @Override
    public void onReceive(Context context, Intent intent) {
        Track track = parseTrack(intent);
        context.startService(createTrackIntent(context, track));
    }

    private Intent createTrackIntent(Context context, Track track) {
        Intent intent = new Intent(context, ScrobblerService.class);
        intent.putExtras(track.toBundle());
        return intent;
    }

    protected Track parseTrack(Intent intent) {
        Bundle rawData = intent.getExtras();
        return new Track.Builder()
                .setTitle(rawData.getString(EXTRA_TRACK))
                .setAlbum(rawData.getString(EXTRA_ALBUM))
                .setArtist(rawData.getString(EXTRA_ARTIST))
                .createTrack();
    }
}
