package jp.cordea.flatten.data

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Serializable
data class Score(
    val id: String,
    val title: String,
    val subtitle: String = "",
    val user: User,
    val htmlUrl: String,
    @Serializable(with = DateTimeSerializer::class)
    val publicationDate: LocalDateTime
)

class DateTimeSerializer : KSerializer<LocalDateTime> {
    private val serializer = String.serializer()

    override val descriptor: SerialDescriptor
        get() = serializer.descriptor

    override fun deserialize(decoder: Decoder): LocalDateTime {
        return LocalDateTime.parse(
            decoder.decodeString(),
            DateTimeFormatter.ISO_OFFSET_DATE_TIME
        )
    }

    override fun serialize(encoder: Encoder, value: LocalDateTime) {
        throw NotImplementedError()
    }
}
