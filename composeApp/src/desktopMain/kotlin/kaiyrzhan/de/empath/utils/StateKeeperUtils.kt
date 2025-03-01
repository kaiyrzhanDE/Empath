package kaiyrzhan.de.empath.utils

import com.arkivanov.essenty.statekeeper.SerializableContainer
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.encodeToStream
import kotlinx.serialization.json.decodeFromStream
import java.io.File


@OptIn(ExperimentalSerializationApi::class)
public fun SerializableContainer.writeToFile(file: File) {
    file.outputStream().use { output ->
        json.encodeToStream(SerializableContainer.serializer(), this, output)
    }
}

@OptIn(ExperimentalSerializationApi::class)
public fun File.readSerializableContainer(): SerializableContainer? =
    takeIf(File::exists)?.inputStream()?.use { input ->
        try {
            json.decodeFromStream(SerializableContainer.serializer(), input)
        } catch (e: Exception) {
            null
        }
    }