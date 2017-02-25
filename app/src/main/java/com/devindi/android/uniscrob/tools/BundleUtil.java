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

package com.devindi.android.uniscrob.tools;

import android.os.Bundle;

public class BundleUtil {

    private BundleUtil() {
        //no instance
    }

    public static String bundleToString(Bundle bundle) {
        StringBuilder builder = new StringBuilder();
        for (String key : bundle.keySet()) {
            builder.append(key).append(':').append(bundle.get(key)).append('\n');
        }
        return builder.toString();
    }

    public static Boolean getBoolOrNumberAsBoolExtra(Bundle bundle, Boolean defaultValue, String... possibleExtraNames) {
        if (possibleExtraNames == null || possibleExtraNames.length == 0) return defaultValue;

        if (bundle == null || bundle.isEmpty()) return defaultValue;

        for (String possibleExtraName : possibleExtraNames) {
            if (bundle.containsKey(possibleExtraName)) {
                Object object = bundle.get(possibleExtraName);

                if (object instanceof Boolean) {
                    return (Boolean) object;
                } else if (object instanceof Integer) {
                    return (Integer) object > 0;
                } else if (object instanceof Long){
                    return (Long) object > 0;
                } else if (object instanceof Short) {
                    return (Short) object > 0;
                } else if (object instanceof Byte) {
                    return (Byte) object > 0;
                }
            }
        }

        return defaultValue;
    }
}
