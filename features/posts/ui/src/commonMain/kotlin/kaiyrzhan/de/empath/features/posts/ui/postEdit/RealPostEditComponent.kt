package kaiyrzhan.de.empath.features.posts.ui.postEdit

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
import kaiyrzhan.de.empath.features.posts.domain.usecase.CreatePostUseCaseError
import kaiyrzhan.de.empath.features.posts.domain.usecase.EditPostUseCase
import kaiyrzhan.de.empath.features.posts.domain.usecase.GetPostUseCase
import kaiyrzhan.de.empath.features.posts.ui.postEdit.model.PostEditAction
import kaiyrzhan.de.empath.features.posts.ui.postEdit.model.PostEditEvent
import kaiyrzhan.de.empath.features.posts.ui.postEdit.model.PostEditState
import kaiyrzhan.de.empath.features.posts.ui.tags.RealTagsDialogComponent
import kaiyrzhan.de.empath.features.posts.ui.tags.TagsDialogComponent
import kaiyrzhan.de.empath.features.posts.ui.tags.model.TagsState
import kaiyrzhan.de.empath.features.posts.ui.model.ImageUi
import kaiyrzhan.de.empath.features.posts.ui.model.TagUi
import kaiyrzhan.de.empath.features.posts.ui.model.postEdit.EditedSubPostUi
import kaiyrzhan.de.empath.features.posts.ui.model.postEdit.toDomain
import kaiyrzhan.de.empath.features.posts.ui.model.postEdit.toEdit
import kaiyrzhan.de.empath.features.filestorage.domain.model.FileType
import kaiyrzhan.de.empath.features.filestorage.domain.model.StorageName
import kaiyrzhan.de.empath.features.filestorage.domain.usecase.UploadFileUseCase
import kaiyrzhan.de.empath.features.filestorage.domain.usecase.UploadFileUseCaseError
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
internal class RealPostEditComponent(
    componentContext: ComponentContext,
    private val postId: String,
    private val onBackClick: () -> Unit,
) : BaseComponent(componentContext), PostEditComponent {

    private val uploadFileUseCase: UploadFileUseCase by inject()
    private val editPostUseCase: EditPostUseCase by inject()
    private val getPostUseCase: GetPostUseCase by inject()

    override val state = MutableStateFlow<PostEditState>(
        PostEditState.default()
    )

    private val _action = Channel<PostEditAction>(capacity = Channel.BUFFERED)
    override val action: Flow<PostEditAction> = _action.receiveAsFlow()

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
        loadPost(postId)
    }

    override fun onEvent(event: PostEditEvent) {
        logger.d(this.className(), "Event: $event")
        when (event) {
            is PostEditEvent.BackClick -> backClick()
            is PostEditEvent.PostTitleChange -> changeHeader(event.title)
            is PostEditEvent.PostImagesAdd -> addPostImage(event.files)
            is PostEditEvent.PostDescriptionChange -> changePostDescription(event.description)
            is PostEditEvent.PostImageRemove -> removePostImage(event.image)
            is PostEditEvent.SubPostAdd -> addSubPost()
            is PostEditEvent.SubPostRemove -> removeSubPost(event.id)
            is PostEditEvent.TagAddClick -> showTagsDialog()
            is PostEditEvent.TagRemove -> removeTag(event.tag)
            is PostEditEvent.TagsAdded -> addTags(event.tags)
            is PostEditEvent.PostEdit -> editPost()
            is PostEditEvent.LoadPost -> loadPost(postId)
            is PostEditEvent.PostRevert -> revertPost()

            is PostEditEvent.SubPostTitleChange ->
                changeSubPostTitle(
                    id = event.id,
                    title = event.title,
                )

            is PostEditEvent.SubPostDescriptionChange ->
                changeSubPostDescription(
                    id = event.id,
                    description = event.description,
                )

            is PostEditEvent.SubPostImageAdd ->
                addSubPostImage(
                    id = event.id,
                    selectedFiles = event.files,
                )

            is PostEditEvent.SubPostImageRemove ->
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

    private fun backClick() {
        coroutineScope.launch {
            when (val currentState = state.value) {
                is PostEditState.Success -> {
                    if (currentState.editablePost.isChanged()) {
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
            check(currentState is PostEditState.Success)
            currentState.copy(
                editablePost = currentState.editablePost.copy(
                    title = title,
                ),
            )
        }
    }

    private fun changePostDescription(description: String) {
        state.update { currentState ->
            check(currentState is PostEditState.Success)
            currentState.copy(
                editablePost = currentState.editablePost.copy(
                    description = description,
                ),
            )
        }
    }

    private fun updatePostImage(
        strategy: (List<ImageUi>) -> List<ImageUi>,
    ) {
        state.update { currentState ->
            check(currentState is PostEditState.Success)
            currentState.copy(
                editablePost = currentState.editablePost.copy(
                    images = strategy(currentState.editablePost.images)
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
        check(currentState is PostEditState.Success)
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
                                PostEditAction.ShowSnackbar(
                                    message = getString(Res.string.file_too_large),
                                )
                            )
                        }

                        is Result.Error.DefaultError -> {
                            _action.send(
                                PostEditAction.ShowSnackbar(
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
        check(currentState is PostEditState.Success)
        tagsDialogNavigation.activate(
            configuration = TagsState(
                originalTags = currentState.editablePost.tags,
            ),
        )
    }

    private fun removeTag(selectedTag: TagUi) {
        state.update { currentState ->
            check(currentState is PostEditState.Success)
            val newPost = currentState.editablePost
            currentState.copy(
                editablePost = newPost.copy(
                    tags = newPost.tags.filter { tag -> tag.name != selectedTag.name },
                )
            )
        }
    }

    private fun addTags(tags: List<TagUi>) {
        state.update { currentState ->
            check(currentState is PostEditState.Success)
            currentState.copy(
                editablePost = currentState.editablePost.copy(
                    tags = tags,
                )
            )
        }
    }

    private fun addSubPost() {
        state.update { currentState ->
            check(currentState is PostEditState.Success)
            currentState.copy(
                editablePost = currentState.editablePost.copy(
                    subPosts = currentState.editablePost.subPosts + EditedSubPostUi.create(),
                )
            )
        }
    }

    private fun removeSubPost(subpostId: Uuid) {
        state.update { currentState ->
            check(currentState is PostEditState.Success)
            val newPost = currentState.editablePost
            currentState.copy(
                editablePost = newPost.copy(
                    subPosts = newPost.subPosts
                        .filter { subPost -> subPost.id != subpostId },
                )
            )
        }
    }

    private fun changeSubPostTitle(id: Uuid, title: String) {
        state.update { currentState ->
            check(currentState is PostEditState.Success)
            val newPost = currentState.editablePost
            currentState.copy(
                editablePost = newPost.copy(
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
            check(currentState is PostEditState.Success)
            val newPost = currentState.editablePost
            currentState.copy(
                editablePost = newPost.copy(
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
            check(currentState is PostEditState.Success)
            currentState.copy(
                editablePost = currentState.editablePost.copy(
                    subPosts = currentState.editablePost.subPosts.map { subPost ->
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
        check(currentState is PostEditState.Success)

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
                                PostEditAction.ShowSnackbar(
                                    message = getString(Res.string.file_too_large),
                                )
                            )
                        }

                        is Result.Error.DefaultError -> {
                            _action.send(
                                PostEditAction.ShowSnackbar(
                                    message = getString(Res.string.unknown_error),
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    private fun revertPost() {
        state.update { currentState ->
            check(currentState is PostEditState.Success)
            currentState.copy(
                editablePost = currentState.originalPost,
            )
        }
    }

    private fun loadPost(postId: String) {
        state.update { PostEditState.Loading }
        coroutineScope.launch {
            getPostUseCase(postId).onSuccess { post ->
                val editablePost = post.toEdit()
                state.update {
                    PostEditState.Success(
                        editablePost = editablePost,
                        originalPost = editablePost,
                    )
                }
            }.onFailure { error ->
                when (error) {
                    is Result.Error.DefaultError -> {
                        state.update {
                            PostEditState.Error(error.toString())
                        }
                    }
                }
            }
        }
    }

    private fun editPost() {
        val currentState = state.value
        check(currentState is PostEditState.Success)
        state.update { PostEditState.Loading }
        coroutineScope.launch {
            editPostUseCase(
                id = postId,
                post = currentState.editablePost.toDomain(),
            ).onSuccess {
                _action.send(
                    PostEditAction.ShowSnackbar(
                        message = getString(Res.string.post_created_successfully),
                    )
                )
                onBackClick()
            }.onFailure { error ->
                state.update { currentState }
                when (error) {
                    is CreatePostUseCaseError.InvalidPostContent -> {
                        _action.send(
                            PostEditAction.ShowSnackbar(
                                message = getString(Res.string.invalid_post_content_error),
                            )
                        )
                    }

                    is Result.Error.DefaultError -> {
                        _action.send(
                            PostEditAction.ShowSnackbar(
                                message = getString(Res.string.unknown_error),
                            )
                        )
                    }
                }
            }
        }
    }
}