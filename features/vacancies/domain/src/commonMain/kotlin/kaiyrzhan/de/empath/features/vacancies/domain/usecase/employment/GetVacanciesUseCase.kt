package kaiyrzhan.de.empath.features.vacancies.domain.usecase.employment

import androidx.paging.PagingData
import kaiyrzhan.de.empath.features.vacancies.domain.model.employment.Vacancy
import kaiyrzhan.de.empath.features.vacancies.domain.repository.EmploymentRepository
import kotlinx.coroutines.flow.Flow

public class GetVacanciesUseCase(
    private val repository: EmploymentRepository,
) {
    private val whiteSpaceRegex = Regex("\\s+")
    public suspend operator fun invoke(
        query: String?,
        salaryFrom: Int?,
        salaryTo: Int?,
        workExperiences: List<String>,
        educations: List<String>,
        workFormats: List<String>,
        excludeWords: String,
        includeWords: String,
    ): Flow<PagingData<Vacancy>> {
        return repository
            .getVacancies(
                query = query,
                salaryFrom = salaryFrom,
                salaryTo = salaryTo,
                workExperiences = workExperiences,
                educations = educations,
                workFormats = workFormats,
                excludeWords = excludeWords
                    .replace(whiteSpaceRegex, "")
                    .split(",")
                    .filter { word -> word.isNotBlank() },
                includeWords = includeWords
                    .replace(whiteSpaceRegex, "")
                    .split(",")
                    .filter { word -> word.isNotBlank() },
            )
    }
}