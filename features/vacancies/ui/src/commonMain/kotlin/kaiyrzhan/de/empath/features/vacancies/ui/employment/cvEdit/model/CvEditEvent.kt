@file:OptIn(ExperimentalUuidApi::class)

package kaiyrzhan.de.empath.features.vacancies.ui.employment.cvEdit.model

import io.github.vinceglb.filekit.PlatformFile
import kaiyrzhan.de.empath.features.vacancies.ui.model.EducationUi
import kaiyrzhan.de.empath.features.vacancies.ui.model.SkillUi
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

internal sealed interface CvEditEvent {
    data class TitleChange(val title: String) : CvEditEvent
    data object VisibilityChange : CvEditEvent
    data class SalaryFromChange(val salaryFrom: Int?) : CvEditEvent
    data class SalaryToChange(val salaryTo: Int?) : CvEditEvent
    data class WorkFormatSelect(val workFormat: SkillUi) : CvEditEvent
    data class WorkScheduleSelect(val workSchedule: SkillUi) : CvEditEvent
    data class EmploymentTypeSelect(val employmentType: SkillUi) : CvEditEvent
    data class EducationSelect(val education: EducationUi) : CvEditEvent
    data class AddressChange(val address: String) : CvEditEvent
    data class AboutMeChange(val aboutMe: String) : CvEditEvent
    data class CvFileAdd(val file: PlatformFile) : CvEditEvent
    data object CvFileRemove : CvEditEvent
    data class EmailChange(val email: String) : CvEditEvent
    data object LoadWorkFormats : CvEditEvent
    data object LoadWorkSchedules : CvEditEvent
    data object LoadEmploymentTypes : CvEditEvent
    data object AddKeySkillsClick : CvEditEvent
    data object AddAdditionalSkillsClick : CvEditEvent
    data class KeySkillsAdd(val skills: List<SkillUi>) : CvEditEvent
    data class AdditionalSkillsAdd(val skills: List<SkillUi>) : CvEditEvent
    data class RemoveKeySkill(val skill: SkillUi) : CvEditEvent
    data class RemoveAdditionalSkill(val skill: SkillUi) : CvEditEvent
    data object BackClick : CvEditEvent
    data object CvEditClick : CvEditEvent
    data object LoadCv : CvEditEvent
    data object WorkExperienceAdd : CvEditEvent
    data class WorkExperienceRemove(val id: Uuid) : CvEditEvent
    data class WorkExperienceStartDateChange(val id: Uuid) : CvEditEvent
    data class WorkExperienceEndDateChange(val id: Uuid) : CvEditEvent
    data class WorkExperienceTitleChange(val id: Uuid, val title: String) : CvEditEvent
    data class WorkExperienceCompanyChange(val id: Uuid, val company: String) : CvEditEvent
    data class WorkExperienceIsRelevantChange(val id: Uuid) : CvEditEvent
    data class WorkExperienceDescriptionChange(val id: Uuid, val description: String) : CvEditEvent
}
