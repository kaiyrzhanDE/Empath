package kaiyrzhan.de.empath.features.vacancies.data.remote

import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.POST
import de.jensklingenberg.ktorfit.http.Path
import de.jensklingenberg.ktorfit.http.Query
import kaiyrzhan.de.empath.core.network.utils.ApiVersion
import kaiyrzhan.de.empath.core.utils.pagination.ListResultDTO
import kaiyrzhan.de.empath.core.utils.pagination.PaginationUtils
import kaiyrzhan.de.empath.core.utils.result.RequestResult
import kaiyrzhan.de.empath.features.vacancies.data.model.employment.CvDTO
import kaiyrzhan.de.empath.features.vacancies.data.model.employment.ResponseToVacancyRequest
import kaiyrzhan.de.empath.features.vacancies.data.model.employment.VacancyDTO

internal interface EmploymentApi {

    @GET("api/{version}/job/employment/cv")
    suspend fun getCvs(
        @Query("page") page: Int = 1,
        @Query("per_page") pageLimit: Int = PaginationUtils.PAGE_LIMIT_EXTRA_LARGE,
    ): RequestResult<ListResultDTO<CvDTO>>

    @GET("api/{version}/job/employment/vacancies")
    suspend fun getVacancies(
        @Path("version") apiVersion: ApiVersion = ApiVersion.V1,
        @Query("salary_from") salaryFrom: Int?,
        @Query("salary_to") salaryTo: Int?,
        @Query("work_exp") workExperiences: List<String>,
        @Query("work_formats") workFormats: List<String>,
        @Query("education") educations: List<String>,
        @Query("exclude_word") excludeWords: List<String>,
        @Query("include_word") includeWords: List<String>,
        @Query("search") query: String?,
        @Query("page") page: Int = 1,
        @Query("per_page") pageLimit: Int,
    ): RequestResult<ListResultDTO<VacancyDTO>>

    @GET("api/{version}/job/employment/vacancies/responses")
    suspend fun getResponses(
        @Path("version") apiVersion: ApiVersion = ApiVersion.V1,
        @Query("salary_from") salaryFrom: Int?,
        @Query("salary_to") salaryTo: Int?,
        @Query("work_exp") workExperiences: List<String>,
        @Query("work_formats") workFormats: List<String>,
        @Query("education") educations: List<String>,
        @Query("exclude_word") excludeWords: List<String>,
        @Query("include_word") includeWords: List<String>,
        @Query("search") query: String?,
        @Query("page") page: Int = 1,
        @Query("per_page") pageLimit: Int,
    ): RequestResult<ListResultDTO<VacancyDTO>>

    @POST("api/{version}/job/employment/vacancies/{vacancy_id}/response")
    suspend fun responseToVacancy(
        @Path("version") apiVersion: ApiVersion = ApiVersion.V1,
        @Path("vacancy_id") vacancyId: String,
        @Body request: ResponseToVacancyRequest,
    ): RequestResult<Any>

}