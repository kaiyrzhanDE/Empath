package kaiyrzhan.de.empath.features.articles.domain.di

import kaiyrzhan.de.empath.features.articles.domain.repository.ArticlesRepository
import kaiyrzhan.de.empath.features.articles.domain.usecase.CreateArticleUseCase
import kaiyrzhan.de.empath.features.articles.domain.usecase.CreateCommentUseCase
import kaiyrzhan.de.empath.features.articles.domain.usecase.DeleteArticleUseCase
import kaiyrzhan.de.empath.features.articles.domain.usecase.DeleteCommentUseCase
import kaiyrzhan.de.empath.features.articles.domain.usecase.EditArticleUseCase
import kaiyrzhan.de.empath.features.articles.domain.usecase.EditCommentUseCase
import kaiyrzhan.de.empath.features.articles.domain.usecase.GetArticleUseCase
import kaiyrzhan.de.empath.features.articles.domain.usecase.GetArticlesUseCase
import kaiyrzhan.de.empath.features.articles.domain.usecase.GetCommentsUseCase
import kaiyrzhan.de.empath.features.articles.domain.usecase.GetTagsUseCase
import org.koin.core.module.Module
import org.koin.dsl.module

public val articlesDomainModule: Module = module {
    factory {
        CreateArticleUseCase(
            repository = get<ArticlesRepository>(),
        )
    }
    factory {
        DeleteArticleUseCase(
            repository = get<ArticlesRepository>(),
        )
    }
    factory {
        EditArticleUseCase(
            repository = get<ArticlesRepository>(),
        )
    }
    factory {
        GetArticlesUseCase(
            repository = get<ArticlesRepository>(),
        )
    }
    factory {
        CreateCommentUseCase(
            repository = get<ArticlesRepository>(),
        )
    }
    factory {
        DeleteCommentUseCase(
            repository = get<ArticlesRepository>(),
        )
    }
    factory {
        GetCommentsUseCase(
            repository = get<ArticlesRepository>(),
        )
    }
    factory {
        EditCommentUseCase(
            repository = get<ArticlesRepository>(),
        )
    }
    factory {
        GetTagsUseCase(
            repository = get<ArticlesRepository>(),
        )
    }
    factory {
        GetArticleUseCase(
            repository = get<ArticlesRepository>(),
        )
    }
}