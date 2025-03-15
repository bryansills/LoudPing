package ninja.bryansills.loudping.network.model.album

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable(with = AlbumTypeSerializer::class)
sealed class AlbumType {
    @Serializable
    data object Album : AlbumType()

    @Serializable
    data object Single : AlbumType()

    @Serializable
    data object Compilation : AlbumType()

    @Serializable
    data class Unknown(val rawString: String) : AlbumType()

    companion object {
        internal val albumKey = "album"
        internal val singleKey = "single"
        internal val compilationKey = "compilation"
    }
}

object AlbumTypeSerializer : KSerializer<AlbumType> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("AlbumType", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: AlbumType) {
        val string = when (value) {
            AlbumType.Album -> AlbumType.albumKey
            AlbumType.Single -> AlbumType.singleKey
            AlbumType.Compilation -> AlbumType.compilationKey
            is AlbumType.Unknown -> value.rawString
        }
        encoder.encodeString(string)
    }

    override fun deserialize(decoder: Decoder): AlbumType {
        return when (val string = decoder.decodeString()) {
            AlbumType.albumKey -> AlbumType.Album
            AlbumType.singleKey -> AlbumType.Single
            AlbumType.compilationKey -> AlbumType.Compilation
            else -> AlbumType.Unknown(string)
        }
    }
}
