package ninja.bryansills.loudping.network.model.track

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import ninja.bryansills.loudping.network.model.artist.SimplifiedArtist

@Serializable
data class TrackAlbum(
    val id: String,
    val name: String,
    val album_type: AlbumType,
    val total_tracks: Int,
    val release_date: String,
    val release_date_precision: ReleaseDatePrecision,
    val images: List<AlbumImage>,
    val artists: List<SimplifiedArtist>,
) {
    @Serializable
    data class AlbumImage(
        val url: String,
        val height: Int? = -1,
        val width: Int? = -1,
    )
}

val TrackAlbum.coverImageUrl: String?
    get() = this.images.maxByOrNull { (it.height ?: 0) * (it.width ?: 0) }?.url

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

@Serializable(with = ReleaseDatePrecisionSerializer::class)
sealed class ReleaseDatePrecision {
    @Serializable
    data object Year : ReleaseDatePrecision()

    @Serializable
    data object Month : ReleaseDatePrecision()

    @Serializable
    data object Day : ReleaseDatePrecision()

    @Serializable
    data class Unknown(val rawString: String) : ReleaseDatePrecision()

    companion object {
        internal val yearKey = "year"
        internal val monthKey = "month"
        internal val dayKey = "day"
    }
}

object ReleaseDatePrecisionSerializer : KSerializer<ReleaseDatePrecision> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("ReleaseDatePrecision", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: ReleaseDatePrecision) {
        val string = when (value) {
            ReleaseDatePrecision.Day -> ReleaseDatePrecision.dayKey
            ReleaseDatePrecision.Month -> ReleaseDatePrecision.monthKey
            ReleaseDatePrecision.Year -> ReleaseDatePrecision.yearKey
            is ReleaseDatePrecision.Unknown -> value.rawString
        }
        encoder.encodeString(string)
    }

    override fun deserialize(decoder: Decoder): ReleaseDatePrecision {
        return when (val string = decoder.decodeString()) {
            ReleaseDatePrecision.dayKey -> ReleaseDatePrecision.Day
            ReleaseDatePrecision.monthKey -> ReleaseDatePrecision.Month
            ReleaseDatePrecision.yearKey -> ReleaseDatePrecision.Year
            else -> ReleaseDatePrecision.Unknown(string)
        }
    }
}
