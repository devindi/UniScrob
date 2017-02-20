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
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.crashlytics.android.Crashlytics;
import com.devindi.android.uniscrob.inject.DaggerProcessorComponent;
import com.devindi.android.uniscrob.inject.ProcessorComponent;
import com.squareup.leakcanary.LeakCanary;

import io.fabric.sdk.android.Fabric;
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
        if (shouldInitFabric()) {
            Fabric.with(this, new Crashlytics());
            Timber.plant(new FabricTree());
        }
    }

    private boolean shouldInitFabric() {
        try {
            ApplicationInfo info = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            Bundle meta = info.metaData;
            return !meta.getString("io.fabric.ApiKey", "").isEmpty();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ProcessorComponent processorComponent(Context context) {
        return ((UniScrobApp) context.getApplicationContext()).processorComponent;
    }

    private static class FabricTree extends Timber.Tree {

        @Override
        protected void log(int priority, String tag, String message, Throwable t) {
            if (t != null) {
                Crashlytics.logException(t);
            }
        }
    }
}
