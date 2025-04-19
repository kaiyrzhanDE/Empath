package kaiyrzhan.de.empath.di

import kaiyrzhan.de.empath.core.network.di.networkModule
import kaiyrzhan.de.empath.core.network.di.tokenModule
import kaiyrzhan.de.empath.core.utils.di.dataStoreModule
import kaiyrzhan.de.empath.core.utils.di.utilsModule
import kaiyrzhan.de.empath.features.posts.data.di.postsDataModule
import kaiyrzhan.de.empath.features.posts.domain.di.postsDomainModule
import kaiyrzhan.de.empath.features.auth.data.di.authDataModule
import kaiyrzhan.de.empath.features.auth.domain.di.authDomainModule
import kaiyrzhan.de.empath.features.filestorage.data.di.fileStorageDataModule
import kaiyrzhan.de.empath.features.filestorage.domain.di.fileStorageDomainModule
import kaiyrzhan.de.empath.features.profile.data.di.profileDataModule
import kaiyrzhan.de.empath.features.profile.domain.di.profileDomainModule
import kaiyrzhan.de.empath.features.vacancies.data.di.vacanciesDataModule
import kaiyrzhan.de.empath.features.vacancies.domain.di.vacanciesDomainModule
import org.koin.core.module.Module

internal expect val platformModules: List<Module>

internal val commonModules: List<Module> = listOf(
    tokenModule,
    utilsModule,
    dataStoreModule,
    networkModule,

    authDomainModule,
    authDataModule,

    profileDomainModule,
    profileDataModule,

    fileStorageDomainModule,
    fileStorageDataModule,

    postsDomainModule,
    postsDataModule,

    vacanciesDataModule,
    vacanciesDomainModule,
)



