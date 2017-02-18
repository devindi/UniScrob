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

package com.devindi.android.uniscrob.service;

import android.app.IntentService;
import android.content.Intent;

import com.devindi.android.uniscrob.UniScrobApp;
import com.devindi.android.uniscrob.inject.ProcessorComponent;
import com.devindi.android.uniscrob.model.Track;
import com.devindi.android.uniscrob.processor.ITrackProcessor;

import java.util.Set;

public class ScrobblerService extends IntentService {

    private Set<ITrackProcessor> processors;

    public ScrobblerService() {
        super(ScrobblerService.class.getName());
        ProcessorComponent processorComponent = UniScrobApp.processorComponent(this);
        processors = processorComponent.processors();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Track track = new Track(intent.getExtras());

        for (ITrackProcessor processor : processors) {
            processor.process(track);
        }
    }
}
