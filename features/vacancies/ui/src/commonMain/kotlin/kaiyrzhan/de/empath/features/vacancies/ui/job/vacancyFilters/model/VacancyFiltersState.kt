package kaiyrzhan.de.empath.features.vacancies.ui.job.vacancyFilters.model

import kaiyrzhan.de.empath.features.vacancies.ui.model.Education
import kaiyrzhan.de.empath.features.vacancies.ui.model.EducationUi
import kaiyrzhan.de.empath.features.vacancies.ui.model.VacancyFiltersUi
import kaiyrzhan.de.empath.features.vacancies.ui.model.WorkExperience
import kaiyrzhan.de.empath.features.vacancies.ui.model.WorkExperienceUi
import kaiyrzhan.de.empath.features.vacancies.ui.model.WorkFormat
import kaiyrzhan.de.empath.features.vacancies.ui.model.WorkFormatUi

internal data class VacancyFiltersState(
    val original: VacancyFiltersUi,
    val query: String,
    val salaryTo: Int?,
    val salaryFrom: Int?,
    val includeWords: String,
    val excludeWords: String,
    val workExperiences: List<WorkExperienceUi>,
    val workFormats: List<WorkFormatUi>,
    val educations: List<EducationUi>,
) {
    fun isChanged(): Boolean {
        return query.isNotBlank() ||
                salaryTo != null ||
                salaryFrom != null ||
                includeWords.isNotBlank() ||
                excludeWords.isNotBlank() ||
                workExperiences.any { workExperience -> workExperience.isSelected } ||
                workFormats.any { workFormat -> workFormat.isSelected } ||
                educations.any { education -> education.isSelected }

    }

    companion object {
        fun default(
            vacancyFilters: VacancyFiltersUi,
        ): VacancyFiltersState {
            val workExperiences = WorkExperience.getWorkExperiences()
            val workFormats = WorkFormat.getWorkFormats()
            val educations = Education.getEducations()
            return VacancyFiltersState(
                query = vacancyFilters.query,
                salaryTo = vacancyFilters.salaryTo,
                salaryFrom = vacancyFilters.salaryFrom,
                original = vacancyFilters,
                includeWords = vacancyFilters.includeWords,
                excludeWords = vacancyFilters.excludeWords,
                workExperiences = workExperiences.map { workExperience ->
                    workExperience.copy(isSelected = workExperience.type.value in vacancyFilters.selectedWorkExperienceTypes)
                },
                workFormats = workFormats.map { workFormat ->
                    workFormat.copy(isSelected = workFormat.type.value in vacancyFilters.selectedWorkFormatTypes)
                },
                educations = educations.map { education ->
                    education.copy(isSelected = education.type.value in vacancyFilters.selectedEducationTypes)
                },
            )
        }
    }
}