package kaiyrzhan.de.empath.features.vacancies.domain.usecase.recruitment

import kaiyrzhan.de.empath.core.utils.result.Result
import kaiyrzhan.de.empath.core.utils.result.toResult
import kaiyrzhan.de.empath.features.vacancies.domain.model.recruitment.NewVacancy
import kaiyrzhan.de.empath.features.vacancies.domain.repository.RecruitmentRepository

public class CreateVacancyUseCase(
    private val repository: RecruitmentRepository,
) {
    public suspend operator fun invoke(
        vacancy: NewVacancy,
    ): Result<Any> {
        return repository
            .createVacancy(
                vacancy = vacancy,
            )
            .toResult()
    }
}