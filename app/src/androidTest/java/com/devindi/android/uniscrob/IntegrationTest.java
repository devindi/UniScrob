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

package com.devindi.android.uniscrob;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.devindi.android.uniscrob.model.Track;
import com.devindi.android.uniscrob.receiver.AbsMusicReceiver;
import com.devindi.android.uniscrob.storage.TrackStorage;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class IntegrationTest {

    private static class DummyReceiver extends AbsMusicReceiver {

        @Override
        protected Track parseTrack(Intent intent) {
            return new Track.Builder()
                    .setAlbum("Album")
                    .setArtist("Artist")
                    .setTitle("Track 1")
                    .createTrack();
        }
    }

    @Test
    public void processTrack() throws Exception {

        Context appContext = InstrumentationRegistry.getTargetContext();
        Intent trackIntent = new Intent();
        DummyReceiver receiver = new DummyReceiver();
        receiver.onReceive(appContext, trackIntent);

        Thread.sleep(3000);

        Track expectedTrack = receiver.parseTrack(null);
        Track track = new TrackStorage().getAll().get(0);
        Assert.assertEquals(expectedTrack, track);
    }
}
