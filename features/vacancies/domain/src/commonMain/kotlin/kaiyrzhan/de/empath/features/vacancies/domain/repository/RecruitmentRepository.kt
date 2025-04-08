package kaiyrzhan.de.empath.features.vacancies.domain.repository

import androidx.paging.PagingData
import kaiyrzhan.de.empath.core.utils.pagination.ListResult
import kaiyrzhan.de.empath.core.utils.result.RequestResult
import kaiyrzhan.de.empath.features.vacancies.domain.model.recruitment.NewVacancy
import kaiyrzhan.de.empath.features.vacancies.domain.model.recruitment.Response
import kaiyrzhan.de.empath.features.vacancies.domain.model.recruitment.Vacancy
import kotlinx.coroutines.flow.Flow

public interface RecruitmentRepository {
    public suspend fun createRecruiter(
        companyName: String,
        companyDescription: String,
        email: String,
    ): RequestResult<Any>

    public suspend fun getVacancies(
        query: String?,
        salaryFrom: Int?,
        salaryTo: Int?,
        workExperiences: List<String>,
        workSchedules: List<String>,
        workFormats: List<String>,
        excludeWords: List<String>,
        includeWords: List<String>,
    ): Flow<PagingData<Vacancy>>

    public suspend fun createVacancy(
        vacancy: NewVacancy,
    ): RequestResult<Any>

    public suspend fun deleteVacancy(
        id: String,
    ): RequestResult<Any>

    public suspend fun editVacancy(
        id: String,
        vacancy: NewVacancy,
    ): RequestResult<Any>

    public suspend fun getResponses(
        vacancyId: String?,
    ): Flow<PagingData<Response>>

    public suspend fun changeResponseStatus(
        cvId: String,
        status: String,
        vacancyId: String,
    ): RequestResult<Any>
}