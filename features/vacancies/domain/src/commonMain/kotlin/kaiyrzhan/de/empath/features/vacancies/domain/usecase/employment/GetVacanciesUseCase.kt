package kaiyrzhan.de.empath.features.vacancies.domain.usecase.employment

import androidx.paging.PagingData
import kaiyrzhan.de.empath.features.vacancies.domain.model.employment.Vacancy
import kaiyrzhan.de.empath.features.vacancies.domain.repository.EmploymentRepository
import kotlinx.coroutines.flow.Flow

public class GetVacanciesUseCase(
    private val repository: EmploymentRepository,
) {
    public suspend operator fun invoke(
        query: String?,
        salaryFrom: Int?,
        salaryTo: Int?,
        workExperiences: List<String>,
        workSchedules: List<String>,
        educations: List<String>,
        workFormats: List<String>,
        excludeWords: List<String>,
        includeWords: List<String>,
    ): Flow<PagingData<Vacancy>> {
        return repository
            .getVacancies(
                query = query,
                salaryFrom = salaryFrom,
                salaryTo = salaryTo,
                workExperiences = workExperiences,
                workSchedules = workSchedules,
                educations = educations,
                workFormats = workFormats,
                excludeWords = excludeWords,
                includeWords = includeWords,
            )
    }
}