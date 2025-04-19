package kaiyrzhan.de.empath.features.vacancies.domain.usecase.recruitment

import androidx.paging.PagingData
import kaiyrzhan.de.empath.features.vacancies.domain.model.recruitment.Vacancy
import kaiyrzhan.de.empath.features.vacancies.domain.repository.RecruitmentRepository
import kotlinx.coroutines.flow.Flow

public class GetVacanciesUseCase(
    private val repository: RecruitmentRepository,
) {
    private val whiteSpaceRegex = Regex("\\s+")
    public suspend operator fun invoke(
        salaryFrom: Int?,
        salaryTo: Int?,
        workExperiences: List<String>,
        workFormats: List<String>,
        excludeWords: String,
        includeWords: String,
        query: String?,
        educations: List<String>,
    ): Flow<PagingData<Vacancy>> {
        return repository
            .getVacancies(
                salaryFrom = salaryFrom,
                salaryTo = salaryTo,
                workExperiences = workExperiences,
                workFormats = workFormats,
                excludeWords = excludeWords
                    .replace(whiteSpaceRegex, "")
                    .split(",")
                    .filter { word -> word.isNotBlank() },
                includeWords = includeWords
                    .replace(whiteSpaceRegex, "")
                    .split(",")
                    .filter { word -> word.isNotBlank() },
                query = query,
                educations = educations,
            )
    }
}