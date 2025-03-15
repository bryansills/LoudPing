package ninja.bryansills.loudping.network.model.common

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

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
