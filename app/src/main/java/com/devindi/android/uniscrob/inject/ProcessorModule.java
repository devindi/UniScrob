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

package com.devindi.android.uniscrob.inject;

import com.devindi.android.uniscrob.processor.ITrackProcessor;
import com.devindi.android.uniscrob.processor.Logger;
import com.devindi.android.uniscrob.processor.StorageProcessor;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;

@Module
public class ProcessorModule {

    @Provides
    @IntoSet
    public ITrackProcessor provideLogger() {
        return new Logger();
    }

    @Provides
    @IntoSet
    public ITrackProcessor provideStorage() {
        return new StorageProcessor();
    }


}