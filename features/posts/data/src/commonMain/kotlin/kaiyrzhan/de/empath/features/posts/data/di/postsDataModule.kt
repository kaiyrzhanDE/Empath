package kaiyrzhan.de.empath.features.posts.data.di

import de.jensklingenberg.ktorfit.Ktorfit
import kaiyrzhan.de.empath.features.posts.data.remote.PostsApi
import kaiyrzhan.de.empath.features.posts.data.remote.createPostsApi
import kaiyrzhan.de.empath.features.posts.data.repository.PostsRepositoryImpl
import kaiyrzhan.de.empath.features.posts.domain.repository.PostsRepository
import org.koin.core.module.Module
import org.koin.dsl.module

public val postsDataModule: Module = module {
    single<PostsApi> { get<Ktorfit>().createPostsApi() }

    single<PostsRepository> {
        PostsRepositoryImpl(
            api = get<PostsApi>(),
        )
    }
}