package kaiyrzhan.de.empath.features.profile.data.repository

import kaiyrzhan.de.empath.core.utils.result.RequestResult
import kaiyrzhan.de.empath.core.utils.result.Result
import kaiyrzhan.de.empath.core.utils.result.map
import kaiyrzhan.de.empath.features.profile.data.model.toData
import kaiyrzhan.de.empath.features.profile.data.model.toDomain
import kaiyrzhan.de.empath.features.profile.data.remote.ProfileApi
import kaiyrzhan.de.empath.features.profile.domain.model.User
import kaiyrzhan.de.empath.features.profile.domain.repository.ProfileRepository

internal class ProfileRepositoryImpl(
    private val api: ProfileApi
) : ProfileRepository {

    override suspend fun getUser(): RequestResult<User> {
        return api
            .getUser()
            .map { user -> user.toDomain() }
    }

    override suspend fun editUser(user: User): RequestResult<Any> {
        return api.editUser(
            userId = user.id,
            body = user.toData(),
        )
    }
}