package kaiyrzhan.de.empath.core.network.result

import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.converter.Converter
import de.jensklingenberg.ktorfit.converter.KtorfitResult
import de.jensklingenberg.ktorfit.converter.TypeData
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import kaiyrzhan.de.empath.core.utils.result.RequestResult
import kotlin.jvm.JvmStatic

internal class RequestResultConverterFactory private constructor() : Converter.Factory {
    override fun suspendResponseConverter(
        typeData: TypeData,
        ktorfit: Ktorfit,
    ): Converter.SuspendResponseConverter<HttpResponse, RequestResult<Any>>? {
        if (typeData.typeInfo.type == RequestResult::class) {
            return object : Converter.SuspendResponseConverter<HttpResponse, RequestResult<Any>> {
                override suspend fun convert(result: KtorfitResult): RequestResult<Any> {
                    val response: RequestResult<Any> = try {
                        when (result) {
                            is KtorfitResult.Success -> {
                                val statusCode = result.response.getStatusCode()
                                if (statusCode.isSuccess()) {
                                    RequestResult.Success(result.response.body(typeData.typeArgs.first().typeInfo))
                                } else {
                                    RequestResult.Failure.Error(statusCode, result.response)
                                }
                            }

                            is KtorfitResult.Failure -> {
                                RequestResult.Failure.Exception(result.throwable)
                            }

                        }
                    } catch (throwable: Throwable) {
                        RequestResult.Failure.Exception(throwable)
                    }
                    return response
                }
            }
        }
        return null
    }

    internal companion object {
        @JvmStatic
        internal fun create(): RequestResultConverterFactory = RequestResultConverterFactory()
    }
}