package ninja.bryansills.loudping.network.model.recent

import kotlinx.datetime.Instant
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable
data class PlayHistoryItem(
    val played_at: Instant,
    val context: Context,
    val track: PlayHistoryTrack,
) {
    @Serializable
    data class Context(
        val type: ContextType,
        val href: String,
        val uri: String,
    )
}

@Serializable(with = ContextTypeSerializer::class)
sealed class ContextType {
    @Serializable
    data object Album : ContextType()

    @Serializable
    data object Artist : ContextType()

    @Serializable
    data object Playlist : ContextType()

    @Serializable
    data class Unknown(val rawString: String) : ContextType()

    companion object {
        internal val albumKey = "album"
        internal val artistKey = "artist"
        internal val playlistKey = "playlist"
    }
}

object ContextTypeSerializer : KSerializer<ContextType> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("ContextType", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: ContextType) {
        val string = when (value) {
            ContextType.Album -> ContextType.albumKey
            ContextType.Artist -> ContextType.artistKey
            ContextType.Playlist -> ContextType.playlistKey
            is ContextType.Unknown -> value.rawString
        }
        encoder.encodeString(string)
    }

    override fun deserialize(decoder: Decoder): ContextType {
        return when (val string = decoder.decodeString()) {
            ContextType.albumKey -> ContextType.Album
            ContextType.artistKey -> ContextType.Artist
            ContextType.playlistKey -> ContextType.Playlist
            else -> ContextType.Unknown(string)
        }
    }
}
