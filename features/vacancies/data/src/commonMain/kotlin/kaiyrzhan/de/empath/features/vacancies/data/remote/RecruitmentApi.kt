package kaiyrzhan.de.empath.features.vacancies.data.remote

import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.DELETE
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.PATCH
import de.jensklingenberg.ktorfit.http.POST
import de.jensklingenberg.ktorfit.http.Path
import de.jensklingenberg.ktorfit.http.Query
import kaiyrzhan.de.empath.core.network.utils.ApiVersion
import kaiyrzhan.de.empath.core.utils.pagination.ListResultDTO
import kaiyrzhan.de.empath.core.utils.result.RequestResult
import kaiyrzhan.de.empath.features.vacancies.data.model.recruitment.ChangeResponseStatusRequest
import kaiyrzhan.de.empath.features.vacancies.data.model.recruitment.CreateRecruiterRequest
import kaiyrzhan.de.empath.features.vacancies.data.model.recruitment.VacancyDTO
import kaiyrzhan.de.empath.features.vacancies.data.model.recruitment.VacancyRequest
import kaiyrzhan.de.empath.features.vacancies.data.model.recruitment.ResponseDTO

internal interface RecruitmentApi {

    @POST("api/{version}/job/recruitment/vacancies/author")
    suspend fun createRecruiter(
        @Path("version") apiVersion: ApiVersion = ApiVersion.V1,
        @Body request: CreateRecruiterRequest,
    ): RequestResult<Any>

    @GET("api/{version}/job/recruitment/vacancies")
    suspend fun getVacancies(
        @Path("version") apiVersion: ApiVersion = ApiVersion.V1,
        @Query("salary_from") salaryFrom: Int?,
        @Query("salary_to") salaryTo: Int?,
        @Query("work_exp") workExperiences: List<String>,
        @Query("work_formats") workFormats: List<String>,
        @Query("education") education: List<String>,
        @Query("exclude_word") excludeWords: List<String>,
        @Query("include_word") includeWords: List<String>,
        @Query("search") query: String?,
        @Query("page") page: Int = 1,
        @Query("per_page") pageLimit: Int
    ): RequestResult<ListResultDTO<VacancyDTO>>

    @POST("api/{version}/job/recruitment/vacancies")
    suspend fun createVacancy(
        @Path("version") apiVersion: ApiVersion = ApiVersion.V1,
        @Body request: VacancyRequest,
    ): RequestResult<Any>

    @PATCH("api/{version}/job/recruitment/vacancies/{vacancy_id}")
    suspend fun editVacancy(
        @Path("version") apiVersion: ApiVersion = ApiVersion.V1,
        @Path("vacancy_id") id: String,
        @Body request: VacancyRequest,
    ): RequestResult<Any>

    @DELETE("api/{version}/job/recruitment/vacancies/{vacancy_id}")
    suspend fun deleteVacancy(
        @Path("version") apiVersion: ApiVersion = ApiVersion.V1,
        @Path("vacancy_id") id: String,
    ): RequestResult<Any>

    @GET("api/{version}/job/recruitment/responses")
    suspend fun getResponses(
        @Path("version") apiVersion: ApiVersion = ApiVersion.V1,
        @Query("vacancy_id") vacancyId: String?,
        @Query("page") page: Int = 1,
        @Query("per_page") pageLimit: Int
    ): RequestResult<ListResultDTO<ResponseDTO>>

    @PATCH("api/{version}/job/recruitment/responses")
    suspend fun changeResponseStatus(
        @Path("version") apiVersion: ApiVersion = ApiVersion.V1,
        @Body request: ChangeResponseStatusRequest,
    ): RequestResult<Any>
}