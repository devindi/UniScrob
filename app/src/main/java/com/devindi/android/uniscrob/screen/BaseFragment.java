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

package com.devindi.android.uniscrob.screen;

import android.app.Fragment;
import android.widget.Toast;

import rx.subscriptions.CompositeSubscription;

public abstract class BaseFragment extends Fragment {

    protected CompositeSubscription compositeSubscription;

    @Override
    public void onStart() {
        super.onStart();
        compositeSubscription = new CompositeSubscription();
    }

    @Override
    public void onStop() {
        super.onStop();
        compositeSubscription.unsubscribe();
    }

    public void onError(Throwable e) {
        showError(e.getMessage());
    }

    protected void showError(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
}
