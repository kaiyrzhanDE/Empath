package kaiyrzhan.de.empath.features.profile.domain.repository

import kaiyrzhan.de.empath.core.utils.result.RequestResult
import kaiyrzhan.de.empath.features.profile.domain.model.User

public interface ProfileRepository {

    public suspend fun getUser(): RequestResult<User>

    public suspend fun editUser(user: User): RequestResult<Any>

    public suspend fun updateUserImage(
        image: ByteArray,
        imageName: String,
        imageType: String,
    ): RequestResult<Any>

}