package kaiyrzhan.de.empath.features.vacancies.data.remote

import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.POST
import de.jensklingenberg.ktorfit.http.Path
import de.jensklingenberg.ktorfit.http.Query
import kaiyrzhan.de.empath.core.network.utils.ApiVersion
import kaiyrzhan.de.empath.core.utils.pagination.ListResultDTO
import kaiyrzhan.de.empath.core.utils.result.RequestResult
import kaiyrzhan.de.empath.features.vacancies.data.model.SkillDTO
import kaiyrzhan.de.empath.features.vacancies.data.model.job.VacancyDetailDTO

internal interface JobApi {

    @GET("api/{version}/job/vacancies/{vacancy_id}")
    suspend fun getVacancyDetail(
        @Path("version") apiVersion: ApiVersion = ApiVersion.V1,
        @Path("vacancy_id") id: String,
    ): RequestResult<VacancyDetailDTO>

    @GET("api/{version}/job/employment-types")
    suspend fun getEmploymentTypes(
        @Path("version") apiVersion: ApiVersion = ApiVersion.V1,
    ): RequestResult<List<SkillDTO>>

    @GET("api/{version}/job/work-formats")
    suspend fun getWorkFormats(
        @Path("version") apiVersion: ApiVersion = ApiVersion.V1,
    ): RequestResult<List<SkillDTO>>

    @GET("api/{version}/job/work-schedules")
    suspend fun getWorkSchedules(
        @Path("version") apiVersion: ApiVersion = ApiVersion.V1,
    ): RequestResult<List<SkillDTO>>

    @GET("api/{version}/job/skills")
    suspend fun getSkills(
        @Path("version") apiVersion: ApiVersion = ApiVersion.V1,
        @Query("search") query: String?,
        @Query("page") page: Int = 1,
        @Query("per_page") pageLimit: Int
    ): RequestResult<ListResultDTO<SkillDTO>>

}