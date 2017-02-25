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
import android.widget.TextView;

import com.devindi.android.uniscrob.R;
import com.devindi.android.uniscrob.model.Track;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class TrackViewHolder extends RecyclerView.ViewHolder {

    private static final String SUB_TITLE_FORMAT = "%s — %s";
    private static final SimpleDateFormat CREATED_AT_FORMAT;

    static {
        CREATED_AT_FORMAT = new SimpleDateFormat("dd MMM yy, hh:mm", Locale.US);
    }

    private final TextView titleView;
    private final TextView subTitleView;
    private final TextView createdAt;

    public static TrackViewHolder create(ViewGroup parent) {
        return new TrackViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_track, parent, false));
    }

    private TrackViewHolder(View itemView) {
        super(itemView);
        titleView = (TextView) itemView.findViewById(R.id.title);
        createdAt = (TextView) itemView.findViewById(R.id.date_time);
        subTitleView = (TextView) itemView.findViewById(R.id.sub_title);
    }

    void bind(Track track) {
        titleView.setText(track.getTitle());
        createdAt.setText(CREATED_AT_FORMAT.format(track.getCreatedAt()));
        subTitleView.setText(String.format(SUB_TITLE_FORMAT, track.getArtist(), track.getAlbum()));
    }
}
