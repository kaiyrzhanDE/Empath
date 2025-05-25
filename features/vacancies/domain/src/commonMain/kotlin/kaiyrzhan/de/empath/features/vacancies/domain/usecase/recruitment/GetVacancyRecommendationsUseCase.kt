package kaiyrzhan.de.empath.features.vacancies.domain.usecase.recruitment

import kaiyrzhan.de.empath.core.utils.result.Result
import kaiyrzhan.de.empath.core.utils.result.toResult
import kaiyrzhan.de.empath.features.vacancies.domain.model.recruitment.VacancyRecommendations
import kaiyrzhan.de.empath.features.vacancies.domain.repository.RecruitmentRepository

public class GetVacancyRecommendationsUseCase(
    private val repository: RecruitmentRepository,
) {
    public suspend operator fun invoke(
        vacancyId: String,
    ): Result<VacancyRecommendations> {
        return repository
            .getVacancyRecommendations(vacancyId)
            .toResult()
    }
}