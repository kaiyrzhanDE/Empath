package kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancyEdit.model

import kaiyrzhan.de.empath.features.vacancies.ui.model.EducationUi
import kaiyrzhan.de.empath.features.vacancies.ui.model.SkillUi
import kaiyrzhan.de.empath.features.vacancies.ui.model.WorkExperienceUi

internal sealed interface VacancyEditEvent {
    data object BackClick : VacancyEditEvent
    data object VacancyEditClick : VacancyEditEvent
    data object LoadVacancy : VacancyEditEvent
    data object LoadWorkFormats : VacancyEditEvent
    data object LoadWorkSchedules : VacancyEditEvent
    data object LoadEmploymentTypes : VacancyEditEvent
    data class TitleChange(val title: String) : VacancyEditEvent
    data class SalaryToChange(val salaryTo: Int?) : VacancyEditEvent
    data class SalaryFromChange(val salaryFrom: Int?) : VacancyEditEvent
    data class AddressChange(val address: String) : VacancyEditEvent
    data class AdditionalDescriptionChange(val description: String) : VacancyEditEvent
    data class EmailChange(val email: String) : VacancyEditEvent
    data class WorkExperienceSelect(val workExperience: WorkExperienceUi) : VacancyEditEvent
    data class WorkFormatSelect(val workFormat: SkillUi) : VacancyEditEvent
    data class WorkScheduleSelect(val workSchedule: SkillUi) : VacancyEditEvent
    data class EmploymentTypeSelect(val employmentType: SkillUi) : VacancyEditEvent
    data class EducationSelect(val education: EducationUi) : VacancyEditEvent
    data class ResponsibilitiesChange(val responsibilities: String) : VacancyEditEvent
    data class RequirementsChange(val requirements: String) : VacancyEditEvent
    data class KeySkillsAdd(val skills: List<SkillUi>) : VacancyEditEvent
    data class AdditionalSkillsAdd(val skills: List<SkillUi>) : VacancyEditEvent
    data class KeySkillRemove(val skill: SkillUi) : VacancyEditEvent
    data class AdditionalSkillRemove(val skill: SkillUi) : VacancyEditEvent
    data object KeySkillsAddClick : VacancyEditEvent
    data object AdditionalSkillsAddClick : VacancyEditEvent
    data object VisibilityChange : VacancyEditEvent
}