package kaiyrzhan.de.empath.features.posts.ui.postCreate

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.value.Value
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.*
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.extension
import io.github.vinceglb.filekit.readBytes
import kaiyrzhan.de.empath.core.ui.dialog.message.MessageDialogComponent
import kaiyrzhan.de.empath.core.ui.dialog.message.RealMessageDialogComponent
import kaiyrzhan.de.empath.core.ui.dialog.message.model.MessageDialogState
import kaiyrzhan.de.empath.core.ui.dialog.model.DialogActionConfig
import kaiyrzhan.de.empath.core.ui.navigation.BaseComponent
import kaiyrzhan.de.empath.core.utils.logger.className
import kaiyrzhan.de.empath.core.utils.result.Result
import kaiyrzhan.de.empath.core.utils.result.addBaseUrl
import kaiyrzhan.de.empath.core.utils.result.onFailure
import kaiyrzhan.de.empath.core.utils.result.onSuccess
import kaiyrzhan.de.empath.features.posts.domain.usecase.CreatePostUseCase
import kaiyrzhan.de.empath.features.posts.domain.usecase.CreatePostUseCaseError
import kaiyrzhan.de.empath.features.posts.ui.postCreate.model.PostCreateAction
import kaiyrzhan.de.empath.features.posts.ui.postCreate.model.PostCreateEvent
import kaiyrzhan.de.empath.features.posts.ui.postCreate.model.PostCreateState
import kaiyrzhan.de.empath.features.posts.ui.tags.RealTagsDialogComponent
import kaiyrzhan.de.empath.features.posts.ui.tags.TagsDialogComponent
import kaiyrzhan.de.empath.features.posts.ui.tags.model.TagsState
import kaiyrzhan.de.empath.features.posts.ui.model.ImageUi
import kaiyrzhan.de.empath.features.posts.ui.model.TagUi
import kaiyrzhan.de.empath.features.posts.ui.model.postCreate.NewPostUi
import kaiyrzhan.de.empath.features.posts.ui.model.postCreate.NewSubPostUi
import kaiyrzhan.de.empath.features.posts.ui.model.postCreate.toDomain
import kaiyrzhan.de.empath.features.posts.ui.model.toUi
import kaiyrzhan.de.empath.features.filestorage.domain.model.FileType
import kaiyrzhan.de.empath.features.filestorage.domain.model.StorageName
import kaiyrzhan.de.empath.features.filestorage.domain.usecase.UploadFileUseCase
import kaiyrzhan.de.empath.features.filestorage.domain.usecase.UploadFileUseCaseError
import kaiyrzhan.de.empath.features.profile.domain.usecase.GetUserUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString
import org.koin.core.component.inject
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
internal class RealPostCreateComponent(
    componentContext: ComponentContext,
    private val onBackClick: () -> Unit,
) : BaseComponent(componentContext), PostCreateComponent {

    private val uploadFileUseCase: UploadFileUseCase by inject()
    private val createPostUseCase: CreatePostUseCase by inject()
    private val getUserUseCase: GetUserUseCase by inject()

    override val state = MutableStateFlow<PostCreateState>(
        PostCreateState.default()
    )

    private val _action = Channel<PostCreateAction>(capacity = Channel.BUFFERED)
    override val action: Flow<PostCreateAction> = _action.receiveAsFlow()

    private val messageDialogNavigation = SlotNavigation<MessageDialogState>()
    override val messageDialog: Value<ChildSlot<*, MessageDialogComponent>> = childSlot(
        source = messageDialogNavigation,
        key = MessageDialogComponent.DEFAULT_KEY,
        serializer = MessageDialogState.serializer(),
        childFactory = ::createMessageDialog,
    )

    private val tagsDialogNavigation = SlotNavigation<TagsState>()
    override val tagsDialog: Value<ChildSlot<*, TagsDialogComponent>> = childSlot(
        source = tagsDialogNavigation,
        key = TagsDialogComponent.DEFAULT_KEY,
        serializer = TagsState.serializer(),
        childFactory = ::createTagsDialog,
    )


    init {
        loadUser()
    }

    override fun onEvent(event: PostCreateEvent) {
        logger.d(this.className(), "Event: $event")
        when (event) {
            is PostCreateEvent.LoadUser -> loadUser()
            is PostCreateEvent.BackClick -> backClick()
            is PostCreateEvent.PostTitleChange -> changeHeader(event.title)
            is PostCreateEvent.PostImagesAdd -> addPostImage(event.files)
            is PostCreateEvent.PostDescriptionChange -> changePostDescription(event.description)
            is PostCreateEvent.PostImageRemove -> removePostImage(event.image)
            is PostCreateEvent.SubPostAdd -> addSubPost()
            is PostCreateEvent.SubPostRemove -> removeSubPost(event.id)
            is PostCreateEvent.TagAddClick -> showTagsDialog()
            is PostCreateEvent.TagRemove -> removeTag(event.tag)
            is PostCreateEvent.TagsAdded -> addTags(event.tags)
            is PostCreateEvent.PostClear -> clear()
            is PostCreateEvent.PostCreate -> createPost()

            is PostCreateEvent.SubPostTitleChange ->
                changeSubPostTitle(
                    id = event.id,
                    title = event.title,
                )

            is PostCreateEvent.SubPostDescriptionChange ->
                changeSubPostDescription(
                    id = event.id,
                    description = event.description,
                )

            is PostCreateEvent.SubPostImageAdd ->
                addSubPostImage(
                    id = event.id,
                    selectedFiles = event.files,
                )

            is PostCreateEvent.SubPostImageRemove ->
                removeSubPostImage(
                    id = event.id,
                    selectedImage = event.image,
                )
        }
    }

    private fun createMessageDialog(
        state: MessageDialogState,
        childComponentContext: ComponentContext,
    ): MessageDialogComponent {
        return RealMessageDialogComponent(
            componentContext = childComponentContext,
            messageDialogState = state,
        )
    }

    private fun showMessageDialog(
        title: String,
        description: String,
        dismissActionConfig: DialogActionConfig,
        confirmActionConfig: DialogActionConfig? = null,
        onDismissClick: (() -> Unit)? = null,
        onConfirmClick: (() -> Unit)? = null,
    ) {
        messageDialogNavigation.activate(
            configuration = MessageDialogState(
                title = title,
                description = description,
                dismissActionConfig = dismissActionConfig,
                onDismissClick = {
                    messageDialogNavigation
                        .dismiss()
                        .also { onDismissClick?.invoke() }
                },
                confirmActionConfig = confirmActionConfig,
                onConfirmClick = {
                    messageDialogNavigation
                        .dismiss()
                        .also { onConfirmClick?.invoke() }
                },
            ),
        )
    }

    private fun createTagsDialog(
        state: TagsState,
        childComponentContext: ComponentContext,
    ): TagsDialogComponent {
        return RealTagsDialogComponent(
            componentContext = childComponentContext,
            tagsDialogState = state,
            onDismissClick = { selectedTags ->
                addTags(selectedTags)
                    .also { tagsDialogNavigation.dismiss() }
            },
        )
    }

    private fun loadUser() {
        state.update { PostCreateState.Loading }
        coroutineScope.launch {
            getUserUseCase().onSuccess { user ->
                state.update {
                    PostCreateState.Success(
                        user = user.toUi(),
                        newPost = NewPostUi.default(),
                    )
                }
            }.onFailure { error ->
                when (error) {
                    is Result.Error.DefaultError -> {
                        _action.send(
                            PostCreateAction.ShowSnackbar(
                                message = getString(Res.string.unknown_error),
                            )
                        )
                    }
                }
            }
        }
    }

    private fun backClick() {
        coroutineScope.launch {
            when (val currentState = state.value) {
                is PostCreateState.Success -> {
                    if (currentState.newPost.isChanged()) {
                        showMessageDialog(
                            title = getString(Res.string.abort_post_create_title),
                            description = getString(Res.string.abort_post_create_description),
                            dismissActionConfig = DialogActionConfig(
                                text = getString(Res.string.close),
                            ),
                            confirmActionConfig = DialogActionConfig(
                                text = getString(Res.string.stay_here),
                                isPrimary = true,
                            ),
                            onDismissClick = onBackClick,
                        )
                    } else {
                        onBackClick()
                    }
                }

                else -> onBackClick()
            }
        }
    }

    private fun changeHeader(title: String) {
        state.update { currentState ->
            check(currentState is PostCreateState.Success)
            currentState.copy(
                newPost = currentState.newPost.copy(
                    title = title,
                ),
            )
        }
    }

    private fun changePostDescription(description: String) {
        state.update { currentState ->
            check(currentState is PostCreateState.Success)
            currentState.copy(
                newPost = currentState.newPost.copy(
                    description = description,
                ),
            )
        }
    }

    private fun updatePostImage(
        strategy: (List<ImageUi>) -> List<ImageUi>,
    ) {
        state.update { currentState ->
            check(currentState is PostCreateState.Success)
            currentState.copy(
                newPost = currentState.newPost.copy(
                    images = strategy(currentState.newPost.images)
                )
            )
        }
    }

    private fun removePostImage(selectedImage: ImageUi) {
        updatePostImage(
            strategy = { images ->
                images.filter { image -> selectedImage.id != image.id }
            }
        )
    }

    private fun addPostImage(
        selectedFiles: List<PlatformFile>,
    ) {
        val currentState = state.value
        check(currentState is PostCreateState.Success)
        repeat(selectedFiles.size) { index ->
            val selectedFile = selectedFiles[index]

            val selectedImage = ImageUi.create(
                platformFile = selectedFile,
                isLoading = true,
            )

            updatePostImage(
                strategy = { images ->
                    images + selectedImage
                }
            )

            coroutineScope.launch {
                uploadFileUseCase(
                    fileType = FileType.IMAGE,
                    storageName = StorageName.POST,
                    image = selectedFile.readBytes(),
                    imageType = selectedFile.extension,
                ).onSuccess { uploadedImage ->
                    updatePostImage(
                        strategy = { images ->
                            images.map { image ->
                                if (image.id == selectedImage.id) {
                                    image.copy(
                                        imageUrl = uploadedImage.url.addBaseUrl(),
                                        isLoading = false,
                                    )
                                } else {
                                    image
                                }
                            }
                        },
                    )
                }.onFailure { error ->
                    updatePostImage(
                        strategy = { images ->
                            images.filter { image -> image.imageUrl != null }
                        },
                    )

                    when (error) {
                        is UploadFileUseCaseError.FileTooLargeError -> {
                            _action.send(
                                PostCreateAction.ShowSnackbar(
                                    message = getString(Res.string.file_too_large),
                                )
                            )
                        }

                        is Result.Error.DefaultError -> {
                            _action.send(
                                PostCreateAction.ShowSnackbar(
                                    message = getString(Res.string.unknown_error),
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    private fun showTagsDialog() {
        val currentState = state.value
        check(currentState is PostCreateState.Success)
        tagsDialogNavigation.activate(
            configuration = TagsState(
                originalTags = currentState.newPost.tags,
            ),
        )
    }

    private fun removeTag(selectedTag: TagUi) {
        state.update { currentState ->
            check(currentState is PostCreateState.Success)
            val newPost = currentState.newPost
            currentState.copy(
                newPost = newPost.copy(
                    tags = newPost.tags.filter { tag -> tag.name != selectedTag.name },
                )
            )
        }
    }

    private fun addTags(tags: List<TagUi>) {
        state.update { currentState ->
            check(currentState is PostCreateState.Success)
            currentState.copy(
                newPost = currentState.newPost.copy(
                    tags = tags,
                )
            )
        }
    }

    private fun addSubPost() {
        state.update { currentState ->
            check(currentState is PostCreateState.Success)
            currentState.copy(
                newPost = currentState.newPost.copy(
                    subPosts = currentState.newPost.subPosts + NewSubPostUi.create(),
                )
            )
        }
    }

    private fun removeSubPost(subPostId: Uuid) {
        state.update { currentState ->
            check(currentState is PostCreateState.Success)
            val newPost = currentState.newPost
            currentState.copy(
                newPost = newPost.copy(
                    subPosts = newPost.subPosts
                        .filter { subPost -> subPost.id != subPostId },
                )
            )
        }
    }

    private fun changeSubPostTitle(id: Uuid, title: String) {
        state.update { currentState ->
            check(currentState is PostCreateState.Success)
            val newPost = currentState.newPost
            currentState.copy(
                newPost = newPost.copy(
                    subPosts = newPost.subPosts.map { subPost ->
                        if (subPost.id == id) {
                            subPost.copy(
                                title = title,
                            )
                        } else {
                            subPost
                        }
                    }
                )
            )
        }
    }

    private fun changeSubPostDescription(
        id: Uuid,
        description: String
    ) {
        state.update { currentState ->
            check(currentState is PostCreateState.Success)
            val newPost = currentState.newPost
            currentState.copy(
                newPost = newPost.copy(
                    subPosts = newPost.subPosts.mapIndexed { index, subPost ->
                        if (subPost.id == id) {
                            subPost.copy(
                                description = description,
                            )
                        } else {
                            subPost
                        }
                    }
                )
            )
        }
    }

    private fun updateSubPostImage(
        id: Uuid,
        strategy: (List<ImageUi>) -> List<ImageUi>,
    ) {
        state.update { currentState ->
            check(currentState is PostCreateState.Success)
            currentState.copy(
                newPost = currentState.newPost.copy(
                    subPosts = currentState.newPost.subPosts.map { subPost ->
                        if (subPost.id == id) {
                            subPost.copy(
                                images = strategy(subPost.images)
                            )
                        } else {
                            subPost
                        }
                    }
                )
            )
        }
    }

    private fun removeSubPostImage(
        id: Uuid,
        selectedImage: ImageUi,
    ) {
        updateSubPostImage(
            id = id,
            strategy = { images ->
                images.filter { image -> image.id != selectedImage.id }
            },
        )
    }

    private fun addSubPostImage(
        id: Uuid,
        selectedFiles: List<PlatformFile>,
    ) {
        val currentState = state.value
        check(currentState is PostCreateState.Success)

        repeat(selectedFiles.size) { index ->
            val selectedFile = selectedFiles[index]
            val selectedImage = ImageUi.create(
                platformFile = selectedFile,
                isLoading = true,
            )

            updateSubPostImage(
                id = id,
                strategy = { images ->
                    images + selectedImage
                },
            )
            coroutineScope.launch {
                uploadFileUseCase(
                    fileType = FileType.IMAGE,
                    storageName = StorageName.POST,
                    image = selectedFile.readBytes(),
                    imageType = selectedFile.extension,
                ).onSuccess { file ->
                    updateSubPostImage(
                        id = id,
                        strategy = { images ->
                            images.map { image ->
                                if (selectedImage.id == image.id) {
                                    image.copy(
                                        imageUrl = file.url.addBaseUrl(),
                                        isLoading = false,
                                    )
                                } else {
                                    image
                                }
                            }
                        },
                    )
                }.onFailure { error ->
                    updateSubPostImage(
                        id = id,
                        strategy = { images ->
                            images.filter { it.imageUrl != null }
                        },
                    )

                    when (error) {
                        is UploadFileUseCaseError.FileTooLargeError -> {
                            _action.send(
                                PostCreateAction.ShowSnackbar(
                                    message = getString(Res.string.file_too_large),
                                )
                            )
                        }

                        is Result.Error.DefaultError -> {
                            _action.send(
                                PostCreateAction.ShowSnackbar(
                                    message = getString(Res.string.unknown_error),
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    private fun clear() {
        state.update { currentState ->
            check(currentState is PostCreateState.Success)
            currentState.copy(
                newPost = NewPostUi.default(),
            )
        }
    }

    private fun createPost() {
        val currentState = state.value
        check(currentState is PostCreateState.Success)
        state.update { PostCreateState.Loading }
        coroutineScope.launch {
            createPostUseCase(
                newPost = currentState.newPost.toDomain(),
            ).onSuccess {
                _action.send(
                    PostCreateAction.ShowSnackbar(
                        message = getString(Res.string.post_created_successfully),
                    )
                )
                onBackClick()
            }.onFailure { error ->
                state.update { currentState }
                when (error) {
                    is CreatePostUseCaseError.InvalidPostContent -> {
                        _action.send(
                            PostCreateAction.ShowSnackbar(
                                message = getString(Res.string.invalid_post_content_error),
                            )
                        )
                    }

                    is Result.Error.DefaultError -> {
                        _action.send(
                            PostCreateAction.ShowSnackbar(
                                message = getString(Res.string.unknown_error),
                            )
                        )
                    }
                }
            }
        }
    }

}