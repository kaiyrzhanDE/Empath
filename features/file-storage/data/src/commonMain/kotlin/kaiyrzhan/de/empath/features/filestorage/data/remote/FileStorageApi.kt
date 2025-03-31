package kaiyrzhan.de.empath.features.filestorage.data.remote

import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.POST
import de.jensklingenberg.ktorfit.http.Path
import de.jensklingenberg.ktorfit.http.Query
import de.jensklingenberg.ktorfit.http.Streaming
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.statement.HttpStatement
import kaiyrzhan.de.empath.core.network.utils.ApiVersion
import kaiyrzhan.de.empath.core.utils.result.RequestResult
import kaiyrzhan.de.empath.features.filestorage.data.model.FileDTO

internal interface FileStorageApi {

    @Streaming
    @GET("api/{version}/file-storage/{filepath}")
    suspend fun downloadFile(
        @Path("version") apiVersion: ApiVersion = ApiVersion.V1,
        @Path("filepath") filePath: String,
    ): HttpStatement

    @POST("api/{version}/file-storage")
    suspend fun uploadFile(
        @Path("version") apiVersion: ApiVersion = ApiVersion.V1,
        @Query("file_type") fileType: String,
        @Query("storage_name") storageName: String,
        @Body file: MultiPartFormDataContent,
    ): RequestResult<FileDTO>

}