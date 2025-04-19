package kaiyrzhan.de.empath.features.posts.domain.di

import kaiyrzhan.de.empath.features.posts.domain.repository.PostsRepository
import kaiyrzhan.de.empath.features.posts.domain.usecase.CreatePostUseCase
import kaiyrzhan.de.empath.features.posts.domain.usecase.CreateCommentUseCase
import kaiyrzhan.de.empath.features.posts.domain.usecase.DeletePostUseCase
import kaiyrzhan.de.empath.features.posts.domain.usecase.DeleteCommentUseCase
import kaiyrzhan.de.empath.features.posts.domain.usecase.EditPostUseCase
import kaiyrzhan.de.empath.features.posts.domain.usecase.EditCommentUseCase
import kaiyrzhan.de.empath.features.posts.domain.usecase.GetPostUseCase
import kaiyrzhan.de.empath.features.posts.domain.usecase.GetPostsUseCase
import kaiyrzhan.de.empath.features.posts.domain.usecase.GetCommentsUseCase
import kaiyrzhan.de.empath.features.posts.domain.usecase.GetTagsUseCase
import org.koin.core.module.Module
import org.koin.dsl.module

public val postsDomainModule: Module = module {
    factory {
        CreatePostUseCase(
            repository = get<PostsRepository>(),
        )
    }
    factory {
        DeletePostUseCase(
            repository = get<PostsRepository>(),
        )
    }
    factory {
        EditPostUseCase(
            repository = get<PostsRepository>(),
        )
    }
    factory {
        GetPostsUseCase(
            repository = get<PostsRepository>(),
        )
    }
    factory {
        CreateCommentUseCase(
            repository = get<PostsRepository>(),
        )
    }
    factory {
        DeleteCommentUseCase(
            repository = get<PostsRepository>(),
        )
    }
    factory {
        GetCommentsUseCase(
            repository = get<PostsRepository>(),
        )
    }
    factory {
        EditCommentUseCase(
            repository = get<PostsRepository>(),
        )
    }
    factory {
        GetTagsUseCase(
            repository = get<PostsRepository>(),
        )
    }
    factory {
        GetPostUseCase(
            repository = get<PostsRepository>(),
        )
    }
}