package kaiyrzhan.de.empath.features.vacancies.domain.usecase

import kaiyrzhan.de.empath.core.utils.result.Result
import kaiyrzhan.de.empath.core.utils.result.toResult
import kaiyrzhan.de.empath.features.vacancies.domain.repository.RecruitmentRepository

public class GetVacanciesUseCase(
    private val repository: RecruitmentRepository,
) {
    public suspend operator fun invoke(
        salaryFrom: Int?,
        salaryTo: Int?,
        workExperiences: List<String>,
        workSchedules: List<String>,
        workFormats: List<String>,
        excludeWords: List<String>,
        includeWords: List<String>,
        query: String?,
    ): Result<Any> {
        return repository
            .getVacancies(
                salaryFrom = salaryFrom,
                salaryTo = salaryTo,
                workExperiences = workExperiences,
                workSchedules = workSchedules,
                workFormats = workFormats,
                excludeWords = excludeWords,
                includeWords = includeWords,
                query = query,
            )
            .toResult()
    }
}