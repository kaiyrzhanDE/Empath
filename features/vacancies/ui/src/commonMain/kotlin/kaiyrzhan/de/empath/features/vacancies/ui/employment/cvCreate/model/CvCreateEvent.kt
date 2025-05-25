@file:OptIn(ExperimentalUuidApi::class)

package kaiyrzhan.de.empath.features.vacancies.ui.employment.cvCreate.model

import io.github.vinceglb.filekit.PlatformFile
import kaiyrzhan.de.empath.features.vacancies.ui.employment.model.FileUi
import kaiyrzhan.de.empath.features.vacancies.ui.model.EducationUi
import kaiyrzhan.de.empath.features.vacancies.ui.model.SkillUi
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

internal sealed interface CvCreateEvent {
    data class TitleChange(val title: String) : CvCreateEvent
    data object VisibilityChange : CvCreateEvent
    data class SalaryFromChange(val salaryFrom: Int?) : CvCreateEvent
    data class SalaryToChange(val salaryTo: Int?) : CvCreateEvent
    data class WorkFormatSelect(val workFormat: SkillUi) : CvCreateEvent
    data class WorkScheduleSelect(val workSchedule: SkillUi) : CvCreateEvent
    data class EmploymentTypeSelect(val employmentType: SkillUi) : CvCreateEvent
    data class EducationSelect(val education: EducationUi) : CvCreateEvent
    data class AddressChange(val address: String) : CvCreateEvent
    data class AboutMeChange(val aboutMe: String) : CvCreateEvent
    data class CvFileAdd(val file: PlatformFile) : CvCreateEvent
    data object CvFileRemove : CvCreateEvent
    data class EmailChange(val email: String) : CvCreateEvent
    data object LoadWorkFormats : CvCreateEvent
    data object LoadWorkSchedules : CvCreateEvent
    data object LoadEmploymentTypes : CvCreateEvent
    data object AddKeySkillsClick : CvCreateEvent
    data object AddAdditionalSkillsClick : CvCreateEvent
    data class KeySkillsAdd(val skills: List<SkillUi>) : CvCreateEvent
    data class AdditionalSkillsAdd(val skills: List<SkillUi>) : CvCreateEvent
    data class RemoveKeySkill(val skill: SkillUi) : CvCreateEvent
    data class RemoveAdditionalSkill(val skill: SkillUi) : CvCreateEvent
    data object BackClick : CvCreateEvent
    data object CvCreateClick : CvCreateEvent
    data object WorkExperienceAdd : CvCreateEvent
    data class WorkExperienceRemove(val id: Uuid) : CvCreateEvent
    data class WorkExperienceStartDateChange(val id: Uuid) : CvCreateEvent
    data class WorkExperienceEndDateChange(val id: Uuid) : CvCreateEvent
    data class WorkExperienceTitleChange(val id: Uuid, val title: String) : CvCreateEvent
    data class WorkExperienceCompanyChange(val id: Uuid, val company: String) : CvCreateEvent
    data class WorkExperienceIsRelevantChange(val id: Uuid) : CvCreateEvent
    data class WorkExperienceDescriptionChange(val id: Uuid, val description: String) :
        CvCreateEvent
}
