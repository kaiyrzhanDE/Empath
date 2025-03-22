package kaiyrzhan.de.empath.features.profile.domain.repository

import kaiyrzhan.de.empath.core.utils.result.RequestResult
import kaiyrzhan.de.empath.features.profile.domain.model.User

public interface ProfileRepository {

    public suspend fun getUser(): RequestResult<User>

}