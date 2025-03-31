package kaiyrzhan.de.empath.features.filestorage.domain.di

import kaiyrzhan.de.empath.features.filestorage.domain.repository.FileStorageRepository
import kaiyrzhan.de.empath.features.filestorage.domain.usecase.DownloadFileUseCase
import kaiyrzhan.de.empath.features.filestorage.domain.usecase.UploadFileUseCase
import org.koin.core.module.Module
import org.koin.dsl.module

public val fileStorageDomainModule: Module = module {
    factory {
        DownloadFileUseCase(
            repository = get<FileStorageRepository>(),
        )
    }
    factory {
        UploadFileUseCase(
            repository = get<FileStorageRepository>(),
        )
    }
}