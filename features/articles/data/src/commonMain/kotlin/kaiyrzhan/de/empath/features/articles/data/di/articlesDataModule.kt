package kaiyrzhan.de.empath.features.articles.data.di

import de.jensklingenberg.ktorfit.Ktorfit
import kaiyrzhan.de.empath.features.articles.data.remote.ArticlesApi
import kaiyrzhan.de.empath.features.articles.data.remote.createArticlesApi
import kaiyrzhan.de.empath.features.articles.data.repository.ArticlesRepositoryImpl
import kaiyrzhan.de.empath.features.articles.domain.repository.ArticlesRepository
import org.koin.core.module.Module
import org.koin.dsl.module

public val articlesDataModule: Module = module {
    single<ArticlesApi> { get<Ktorfit>().createArticlesApi() }

    single<ArticlesRepository> {
        ArticlesRepositoryImpl(
            api = get<ArticlesApi>(),
        )
    }
}