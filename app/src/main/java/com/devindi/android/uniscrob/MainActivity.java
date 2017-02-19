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

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.devindi.android.uniscrob.model.Track;
import com.devindi.android.uniscrob.storage.TrackStorage;

import java.util.List;

import rx.Subscriber;
import timber.log.Timber;

import static junit.framework.Assert.fail;

public class MainActivity extends AppCompatActivity {

    private TrackStorage trackStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        trackStorage = new TrackStorage();

        trackStorage.getAll().subscribe(new Subscriber<List<Track>>() {
            @Override
            public void onCompleted() {
                Log.d("Test", "Completed");
            }

            @Override
            public void onError(Throwable e) {
                Timber.e(e);
                fail(e.getMessage());
            }

            @Override
            public void onNext(List<Track> tracks) {
                Log.d("Test", "Next");
                Timber.d("new list: %s", tracks);
            }
        });

        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                trackStorage.addTrack(new Track("T2", "A2", "Al"));
            }
        };
        handler.sendEmptyMessageDelayed(0, 3000);
    }
}
