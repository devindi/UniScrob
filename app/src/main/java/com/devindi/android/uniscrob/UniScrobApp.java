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

import android.app.Application;
import android.content.Context;

import com.devindi.android.uniscrob.inject.DaggerProcessorComponent;
import com.devindi.android.uniscrob.inject.ProcessorComponent;
import com.squareup.leakcanary.LeakCanary;

import io.realm.Realm;
import timber.log.Timber;

public class UniScrobApp extends Application {

    private ProcessorComponent processorComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        Timber.d("************** App started **************");
        Realm.init(this);
        processorComponent = DaggerProcessorComponent.create();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
    }

    public static ProcessorComponent processorComponent(Context context) {
        return ((UniScrobApp) context.getApplicationContext()).processorComponent;
    }
}
