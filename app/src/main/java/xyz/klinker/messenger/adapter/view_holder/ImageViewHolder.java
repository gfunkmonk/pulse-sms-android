/*
 * Copyright (C) 2017 Luke Klinker
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

package xyz.klinker.messenger.adapter.view_holder;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import xyz.klinker.messenger.R;

/**
 * View holder for keeping a single image.
 */
public class ImageViewHolder extends RecyclerView.ViewHolder {

    public ImageView image;
    public ImageView selectedCheckmarkLayout;
    public ImageView playButton;
    public Uri uri;
    public String mimeType;

    public ImageViewHolder(View itemView) {
        super(itemView);
        this.image = (ImageView) itemView.findViewById(R.id.image);
        this.playButton = (ImageView) itemView.findViewById(R.id.play_button);
        this.selectedCheckmarkLayout = (ImageView)
                itemView.findViewById(R.id.selected_checkmark_layout);
    }

}
