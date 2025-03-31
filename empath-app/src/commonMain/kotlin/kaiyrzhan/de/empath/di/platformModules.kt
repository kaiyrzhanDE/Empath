package kaiyrzhan.de.empath.di

import kaiyrzhan.de.empath.core.network.di.networkModule
import kaiyrzhan.de.empath.core.network.di.tokenModule
import kaiyrzhan.de.empath.core.utils.di.dataStoreModule
import kaiyrzhan.de.empath.core.utils.di.utilsModule
import kaiyrzhan.de.empath.features.articles.data.di.articlesDataModule
import kaiyrzhan.de.empath.features.articles.domain.di.articlesDomainModule
import kaiyrzhan.de.empath.features.auth.data.di.authDataModule
import kaiyrzhan.de.empath.features.auth.domain.di.authDomainModule
import kaiyrzhan.de.empath.features.filestorage.data.di.fileStorageDataModule
import kaiyrzhan.de.empath.features.filestorage.domain.di.fileStorageDomainModule
import kaiyrzhan.de.empath.features.profile.data.di.profileDataModule
import kaiyrzhan.de.empath.features.profile.domain.di.profileDomainModule
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

    articlesDomainModule,
    articlesDataModule,
)



