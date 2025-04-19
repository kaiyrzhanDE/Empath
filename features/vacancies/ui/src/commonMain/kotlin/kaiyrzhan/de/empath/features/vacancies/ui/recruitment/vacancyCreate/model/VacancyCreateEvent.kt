package kaiyrzhan.de.empath.features.vacancies.ui.recruitment.vacancyCreate.model

import kaiyrzhan.de.empath.features.vacancies.ui.model.EducationUi
import kaiyrzhan.de.empath.features.vacancies.ui.model.SkillUi
import kaiyrzhan.de.empath.features.vacancies.ui.model.WorkExperienceUi

internal sealed interface VacancyCreateEvent {
    data object BackClick : VacancyCreateEvent
    data object VacancyCreateClick : VacancyCreateEvent
    data class TitleChange(val title: String) : VacancyCreateEvent
    data class SalaryToChange(val salaryTo: Int?) : VacancyCreateEvent
    data class SalaryFromChange(val salaryFrom: Int?) : VacancyCreateEvent
    data class AddressChange(val address: String) : VacancyCreateEvent
    data class AdditionalDescriptionChange(val description: String) : VacancyCreateEvent
    data class EmailChange(val email: String) : VacancyCreateEvent
    data class WorkExperienceSelect(val workExperience: WorkExperienceUi) : VacancyCreateEvent
    data class WorkFormatSelect(val workFormat: SkillUi) : VacancyCreateEvent
    data class WorkScheduleSelect(val workSchedule: SkillUi) : VacancyCreateEvent
    data class EmploymentTypeSelect(val employmentType: SkillUi) : VacancyCreateEvent
    data class EducationSelect(val education: EducationUi) : VacancyCreateEvent
    data class ResponsibilitiesChange(val responsibilities: String) : VacancyCreateEvent
    data class RequirementsChange(val requirements: String) : VacancyCreateEvent
    data class KeySkillsAdd(val skills: List<SkillUi>) : VacancyCreateEvent
    data class AdditionalSkillsAdd(val skills: List<SkillUi>) : VacancyCreateEvent
    data class RemoveKeySkill(val skill: SkillUi) : VacancyCreateEvent
    data class RemoveAdditionalSkill(val skill: SkillUi) : VacancyCreateEvent
    data object AddKeySkillsClick : VacancyCreateEvent
    data object AddAdditionalSkillsClick : VacancyCreateEvent
    data object ChangeVisibility : VacancyCreateEvent
    data object CreateVacancyClick : VacancyCreateEvent
}