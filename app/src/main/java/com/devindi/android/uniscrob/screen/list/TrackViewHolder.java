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

package com.devindi.android.uniscrob.screen.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.devindi.android.uniscrob.R;
import com.devindi.android.uniscrob.model.Track;

public class TrackViewHolder extends RecyclerView.ViewHolder {

    private TextView titleView;
    private ImageView artView;
    private ImageView statusView;
    private TextView subTitleView;
    private TextView scrobbledAtView;

    public static TrackViewHolder create(ViewGroup parent) {
        return new TrackViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_track, parent, false));
    }

    public TrackViewHolder(View itemView) {
        super(itemView);
        titleView = (TextView) itemView.findViewById(R.id.title);
    }

    public void bind(Track track) {
        titleView.setText(track.getTitle());
    }
}
