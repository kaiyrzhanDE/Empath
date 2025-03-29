package kaiyrzhan.de.empath.features.profile.domain.usecase

import kaiyrzhan.de.empath.core.utils.result.Result
import kaiyrzhan.de.empath.core.utils.result.toResult
import kaiyrzhan.de.empath.features.profile.domain.model.User
import kaiyrzhan.de.empath.features.profile.domain.repository.ProfileRepository

public class EditUserUseCase(
    private val repository: ProfileRepository,
) {
    public suspend operator fun invoke(user: User): Result<Any> {
        return repository
            .editUser(user)
            .toResult()
    }
}
