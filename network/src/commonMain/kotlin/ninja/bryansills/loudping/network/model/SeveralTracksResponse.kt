package ninja.bryansills.loudping.network.model

import kotlinx.serialization.Serializable
import ninja.bryansills.loudping.network.model.track.Track

@Serializable
data class SeveralTracksResponse(val tracks: List<Track>)
