package kaiyrzhan.de.empath.features.posts.domain.di

import kaiyrzhan.de.empath.features.posts.domain.repository.PostsRepository
import kaiyrzhan.de.empath.features.posts.domain.usecase.CancelDislikeCommentUseCase
import kaiyrzhan.de.empath.features.posts.domain.usecase.CancelDislikePostUseCase
import kaiyrzhan.de.empath.features.posts.domain.usecase.CancelLikeCommentUseCase
import kaiyrzhan.de.empath.features.posts.domain.usecase.CancelLikePostUseCase
import kaiyrzhan.de.empath.features.posts.domain.usecase.CreatePostUseCase
import kaiyrzhan.de.empath.features.posts.domain.usecase.CreateCommentUseCase
import kaiyrzhan.de.empath.features.posts.domain.usecase.DeletePostUseCase
import kaiyrzhan.de.empath.features.posts.domain.usecase.DeleteCommentUseCase
import kaiyrzhan.de.empath.features.posts.domain.usecase.DislikeCommentUseCase
import kaiyrzhan.de.empath.features.posts.domain.usecase.DislikePostUseCase
import kaiyrzhan.de.empath.features.posts.domain.usecase.EditPostUseCase
import kaiyrzhan.de.empath.features.posts.domain.usecase.EditCommentUseCase
import kaiyrzhan.de.empath.features.posts.domain.usecase.GetPostUseCase
import kaiyrzhan.de.empath.features.posts.domain.usecase.GetPostsUseCase
import kaiyrzhan.de.empath.features.posts.domain.usecase.GetCommentsUseCase
import kaiyrzhan.de.empath.features.posts.domain.usecase.GetSpecializationsUseCase
import kaiyrzhan.de.empath.features.posts.domain.usecase.GetTagsUseCase
import kaiyrzhan.de.empath.features.posts.domain.usecase.LikeCommentUseCase
import kaiyrzhan.de.empath.features.posts.domain.usecase.LikePostUseCase
import kaiyrzhan.de.empath.features.posts.domain.usecase.ViewPostUseCase
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
    factory {
        LikePostUseCase(
            repository = get<PostsRepository>(),
        )
    }
    factory {
        DislikePostUseCase(
            repository = get<PostsRepository>(),
        )
    }
    factory {
        CancelLikePostUseCase(
            repository = get<PostsRepository>(),
        )
    }
    factory {
        CancelDislikePostUseCase(
            repository = get<PostsRepository>(),
        )
    }
    factory {
        LikeCommentUseCase(
            repository = get<PostsRepository>(),
        )
    }
    factory {
        DislikeCommentUseCase(
            repository = get<PostsRepository>(),
        )
    }
    factory {
        CancelLikeCommentUseCase(
            repository = get<PostsRepository>(),
        )
    }
    factory {
        CancelDislikeCommentUseCase(
            repository = get<PostsRepository>(),
        )
    }
    factory {
        ViewPostUseCase(
            repository = get<PostsRepository>(),
        )
    }
    factory {
        GetSpecializationsUseCase(
            repository = get<PostsRepository>(),
        )
    }
}