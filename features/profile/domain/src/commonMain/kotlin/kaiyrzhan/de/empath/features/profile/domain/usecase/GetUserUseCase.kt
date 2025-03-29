package kaiyrzhan.de.empath.features.profile.domain.usecase

import kaiyrzhan.de.empath.features.profile.domain.model.User
import kaiyrzhan.de.empath.features.profile.domain.repository.ProfileRepository
import kaiyrzhan.de.empath.core.utils.result.Result
import kaiyrzhan.de.empath.core.utils.result.toResult

public class GetUserUseCase(
    private val repository: ProfileRepository,
) {
    public suspend operator fun invoke(): Result<User> {
        return repository
            .getUser()
            .toResult()
    }
}

