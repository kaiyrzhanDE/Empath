package kaiyrzhan.de.empath.features.vacancies.domain.repository

import androidx.paging.PagingData
import kaiyrzhan.de.empath.core.utils.result.RequestResult
import kaiyrzhan.de.empath.features.vacancies.domain.model.Skill
import kaiyrzhan.de.empath.features.vacancies.domain.model.job.VacancyDetail
import kotlinx.coroutines.flow.Flow

public interface JobRepository {

    public suspend fun getVacancyDetail(id: String): RequestResult<VacancyDetail>

    public suspend fun getWorkFormats(): RequestResult<List<Skill>>

    public suspend fun getWorkSchedules(): RequestResult<List<Skill>>

    public suspend fun getEmploymentTypes(): RequestResult<List<Skill>>

    public suspend fun getSkills(query: String?): Flow<PagingData<Skill>>

}