package kaiyrzhan.de.empath.features.profile.data.repository

import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import kaiyrzhan.de.empath.core.utils.result.RequestResult
import kaiyrzhan.de.empath.core.utils.result.toDomain
import kaiyrzhan.de.empath.features.profile.data.model.toData
import kaiyrzhan.de.empath.features.profile.data.model.toDomain
import kaiyrzhan.de.empath.features.profile.data.remote.ProfileApi
import kaiyrzhan.de.empath.features.profile.domain.model.User
import kaiyrzhan.de.empath.features.profile.domain.repository.ProfileRepository

internal class ProfileRepositoryImpl(
    private val api: ProfileApi,
) : ProfileRepository {

    override suspend fun getUser(): RequestResult<User> {
        return api
            .getUser()
            .toDomain { user -> user.toDomain() }
    }

    override suspend fun editUser(user: User): RequestResult<Any> {
        return api.editUser(
            userId = user.id,
            body = user.toData(),
        )
    }

    override suspend fun updateUserImage(
        image: ByteArray,
        imageName: String,
        imageType: String,
    ): RequestResult<Any> {
        return api.updateUserImage(
            file = MultiPartFormDataContent(
                formData {
                    append(
                        key = "file",
                        value = image,
                        headers = Headers.build {
                            append(HttpHeaders.ContentType, "image/$imageType")
                            append(
                                HttpHeaders.ContentDisposition,
                                "form-data; filename=\"userImage.$imageType\""
                            )
                        }
                    )
                }
            ),
        )
    }
}