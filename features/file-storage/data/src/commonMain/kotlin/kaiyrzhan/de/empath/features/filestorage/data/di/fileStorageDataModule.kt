package kaiyrzhan.de.empath.features.filestorage.data.di

import de.jensklingenberg.ktorfit.Ktorfit
import kaiyrzhan.de.empath.features.filestorage.data.remote.FileStorageApi
import kaiyrzhan.de.empath.features.filestorage.data.remote.createFileStorageApi
import kaiyrzhan.de.empath.features.filestorage.data.repository.FileStorageRepositoryImpl
import kaiyrzhan.de.empath.features.filestorage.domain.repository.FileStorageRepository
import org.koin.core.module.Module
import org.koin.dsl.module

public val fileStorageDataModule: Module = module {
    single<FileStorageApi> { get<Ktorfit>().createFileStorageApi()}

    single<FileStorageRepository> {
        FileStorageRepositoryImpl(
            api = get<FileStorageApi>(),
        )
    }
}