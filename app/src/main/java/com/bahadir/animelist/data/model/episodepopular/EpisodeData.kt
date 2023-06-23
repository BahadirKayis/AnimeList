package com.bahadir.animelist.data.model.episodepopular

import com.bahadir.animelist.data.model.common.Entry
import com.google.gson.annotations.SerializedName

data class EpisodeData(
    val entry: Entry,
    val episodes: List<Episode>,
    @SerializedName("region_locked")
    val regionLocked: Boolean
)