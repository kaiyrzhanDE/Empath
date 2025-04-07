package kaiyrzhan.de.empath.features.articles.ui.article_create

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
import kaiyrzhan.de.empath.features.articles.domain.usecase.CreateArticleUseCase
import kaiyrzhan.de.empath.features.articles.domain.usecase.CreateArticleUseCaseError
import kaiyrzhan.de.empath.features.articles.ui.article_create.model.ArticleCreateAction
import kaiyrzhan.de.empath.features.articles.ui.article_create.model.ArticleCreateEvent
import kaiyrzhan.de.empath.features.articles.ui.article_create.model.ArticleCreateState
import kaiyrzhan.de.empath.features.articles.ui.tags.RealTagsDialogComponent
import kaiyrzhan.de.empath.features.articles.ui.tags.TagsDialogComponent
import kaiyrzhan.de.empath.features.articles.ui.tags.model.TagsState
import kaiyrzhan.de.empath.features.articles.ui.model.ImageUi
import kaiyrzhan.de.empath.features.articles.ui.model.TagUi
import kaiyrzhan.de.empath.features.articles.ui.model.article_create.NewArticleUi
import kaiyrzhan.de.empath.features.articles.ui.model.article_create.NewSubArticleUi
import kaiyrzhan.de.empath.features.articles.ui.model.article_create.toDomain
import kaiyrzhan.de.empath.features.articles.ui.model.article_edit.toDomain
import kaiyrzhan.de.empath.features.articles.ui.model.toUi
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
internal class RealArticleCreateComponent(
    componentContext: ComponentContext,
    private val onBackClick: () -> Unit,
) : BaseComponent(componentContext), ArticleCreateComponent {

    private val uploadFileUseCase: UploadFileUseCase by inject()
    private val createArticleUseCase: CreateArticleUseCase by inject()
    private val getUserUseCase: GetUserUseCase by inject()

    override val state = MutableStateFlow<ArticleCreateState>(
        ArticleCreateState.default()
    )

    private val _action = Channel<ArticleCreateAction>(capacity = Channel.BUFFERED)
    override val action: Flow<ArticleCreateAction> = _action.receiveAsFlow()

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

    override fun onEvent(event: ArticleCreateEvent) {
        logger.d(this.className(), "Event: $event")
        when (event) {
            is ArticleCreateEvent.LoadUser -> loadUser()
            is ArticleCreateEvent.BackClick -> backClick()
            is ArticleCreateEvent.ArticleTitleChange -> changeHeader(event.title)
            is ArticleCreateEvent.ArticleImagesAdd -> addArticleImage(event.files)
            is ArticleCreateEvent.ArticleDescriptionChange -> changeArticleDescription(event.description)
            is ArticleCreateEvent.ArticleImageRemove -> removeArticleImage(event.image)
            is ArticleCreateEvent.SubArticleAdd -> addSubArticle()
            is ArticleCreateEvent.SubArticleRemove -> removeSubArticle(event.id)
            is ArticleCreateEvent.TagAddClick -> showTagsDialog()
            is ArticleCreateEvent.TagRemove -> removeTag(event.tag)
            is ArticleCreateEvent.TagsAdded -> addTags(event.tags)
            is ArticleCreateEvent.ArticleClear -> clear()
            is ArticleCreateEvent.ArticleCreate -> createArticle()

            is ArticleCreateEvent.SubArticleTitleChange ->
                changeSubArticleTitle(
                    id = event.id,
                    title = event.title,
                )

            is ArticleCreateEvent.SubArticleDescriptionChange ->
                changeSubArticleDescription(
                    id = event.id,
                    description = event.description,
                )

            is ArticleCreateEvent.SubArticleImageAdd ->
                addSubArticleImage(
                    id = event.id,
                    selectedFiles = event.files,
                )

            is ArticleCreateEvent.SubArticleImageRemove ->
                removeSubArticleImage(
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
        state.update { ArticleCreateState.Loading }
        coroutineScope.launch {
            getUserUseCase().onSuccess { user ->
                state.update {
                    ArticleCreateState.Success(
                        user = user.toUi(),
                        newArticle = NewArticleUi.default(),
                    )
                }
            }.onFailure { error ->
                when (error) {
                    is Result.Error.DefaultError -> {
                        _action.send(
                            ArticleCreateAction.ShowSnackbar(
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
                is ArticleCreateState.Success -> {
                    if (currentState.newArticle.isChanged()) {
                        showMessageDialog(
                            title = getString(Res.string.abort_article_create_title),
                            description = getString(Res.string.abort_article_create_description),
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
            check(currentState is ArticleCreateState.Success)
            currentState.copy(
                newArticle = currentState.newArticle.copy(
                    title = title,
                ),
            )
        }
    }

    private fun changeArticleDescription(description: String) {
        state.update { currentState ->
            check(currentState is ArticleCreateState.Success)
            currentState.copy(
                newArticle = currentState.newArticle.copy(
                    description = description,
                ),
            )
        }
    }

    private fun updateArticleImage(
        strategy: (List<ImageUi>) -> List<ImageUi>,
    ) {
        state.update { currentState ->
            check(currentState is ArticleCreateState.Success)
            currentState.copy(
                newArticle = currentState.newArticle.copy(
                    images = strategy(currentState.newArticle.images)
                )
            )
        }
    }

    private fun removeArticleImage(selectedImage: ImageUi) {
        updateArticleImage(
            strategy = { images ->
                images.filter { image -> selectedImage.id != image.id }
            }
        )
    }

    private fun addArticleImage(
        selectedFiles: List<PlatformFile>,
    ) {
        val currentState = state.value
        check(currentState is ArticleCreateState.Success)
        repeat(selectedFiles.size) { index ->
            val selectedFile = selectedFiles[index]

            val selectedImage = ImageUi.create(
                platformFile = selectedFile,
                isLoading = true,
            )

            updateArticleImage(
                strategy = { images ->
                    images + selectedImage
                }
            )

            coroutineScope.launch {
                uploadFileUseCase(
                    fileType = FileType.IMAGE,
                    storageName = StorageName.ARTICLE,
                    image = selectedFile.readBytes(),
                    imageType = selectedFile.extension,
                ).onSuccess { uploadedImage ->
                    updateArticleImage(
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
                    updateArticleImage(
                        strategy = { images ->
                            images.filter { image -> image.imageUrl != null }
                        },
                    )

                    when (error) {
                        is UploadFileUseCaseError.FileTooLargeError -> {
                            _action.send(
                                ArticleCreateAction.ShowSnackbar(
                                    message = getString(Res.string.file_too_large),
                                )
                            )
                        }

                        is Result.Error.DefaultError -> {
                            _action.send(
                                ArticleCreateAction.ShowSnackbar(
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
        check(currentState is ArticleCreateState.Success)
        tagsDialogNavigation.activate(
            configuration = TagsState(
                originalTags = currentState.newArticle.tags,
            ),
        )
    }

    private fun removeTag(selectedTag: TagUi) {
        state.update { currentState ->
            check(currentState is ArticleCreateState.Success)
            val newArticle = currentState.newArticle
            currentState.copy(
                newArticle = newArticle.copy(
                    tags = newArticle.tags.filter { tag -> tag.name != selectedTag.name },
                )
            )
        }
    }

    private fun addTags(tags: List<TagUi>) {
        state.update { currentState ->
            check(currentState is ArticleCreateState.Success)
            currentState.copy(
                newArticle = currentState.newArticle.copy(
                    tags = tags,
                )
            )
        }
    }

    private fun addSubArticle() {
        state.update { currentState ->
            check(currentState is ArticleCreateState.Success)
            currentState.copy(
                newArticle = currentState.newArticle.copy(
                    subArticles = currentState.newArticle.subArticles + NewSubArticleUi.create(),
                )
            )
        }
    }

    private fun removeSubArticle(subArticleId: Uuid) {
        state.update { currentState ->
            check(currentState is ArticleCreateState.Success)
            val newArticle = currentState.newArticle
            currentState.copy(
                newArticle = newArticle.copy(
                    subArticles = newArticle.subArticles
                        .filter { subArticle -> subArticle.id != subArticleId },
                )
            )
        }
    }

    private fun changeSubArticleTitle(id: Uuid, title: String) {
        state.update { currentState ->
            check(currentState is ArticleCreateState.Success)
            val newArticle = currentState.newArticle
            currentState.copy(
                newArticle = newArticle.copy(
                    subArticles = newArticle.subArticles.map { subArticle ->
                        if (subArticle.id == id) {
                            subArticle.copy(
                                title = title,
                            )
                        } else {
                            subArticle
                        }
                    }
                )
            )
        }
    }

    private fun changeSubArticleDescription(
        id: Uuid,
        description: String
    ) {
        state.update { currentState ->
            check(currentState is ArticleCreateState.Success)
            val newArticle = currentState.newArticle
            currentState.copy(
                newArticle = newArticle.copy(
                    subArticles = newArticle.subArticles.mapIndexed { index, subArticle ->
                        if (subArticle.id == id) {
                            subArticle.copy(
                                description = description,
                            )
                        } else {
                            subArticle
                        }
                    }
                )
            )
        }
    }

    private fun updateSubArticleImage(
        id: Uuid,
        strategy: (List<ImageUi>) -> List<ImageUi>,
    ) {
        state.update { currentState ->
            check(currentState is ArticleCreateState.Success)
            currentState.copy(
                newArticle = currentState.newArticle.copy(
                    subArticles = currentState.newArticle.subArticles.map { subArticle ->
                        if (subArticle.id == id) {
                            subArticle.copy(
                                images = strategy(subArticle.images)
                            )
                        } else {
                            subArticle
                        }
                    }
                )
            )
        }
    }

    private fun removeSubArticleImage(
        id: Uuid,
        selectedImage: ImageUi,
    ) {
        updateSubArticleImage(
            id = id,
            strategy = { images ->
                images.filter { image -> image.id != selectedImage.id }
            },
        )
    }

    private fun addSubArticleImage(
        id: Uuid,
        selectedFiles: List<PlatformFile>,
    ) {
        val currentState = state.value
        check(currentState is ArticleCreateState.Success)

        repeat(selectedFiles.size) { index ->
            val selectedFile = selectedFiles[index]
            val selectedImage = ImageUi.create(
                platformFile = selectedFile,
                isLoading = true,
            )

            updateSubArticleImage(
                id = id,
                strategy = { images ->
                    images + selectedImage
                },
            )
            coroutineScope.launch {
                uploadFileUseCase(
                    fileType = FileType.IMAGE,
                    storageName = StorageName.ARTICLE,
                    image = selectedFile.readBytes(),
                    imageType = selectedFile.extension,
                ).onSuccess { file ->
                    updateSubArticleImage(
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
                    updateSubArticleImage(
                        id = id,
                        strategy = { images ->
                            images.filter { it.imageUrl != null }
                        },
                    )

                    when (error) {
                        is UploadFileUseCaseError.FileTooLargeError -> {
                            _action.send(
                                ArticleCreateAction.ShowSnackbar(
                                    message = getString(Res.string.file_too_large),
                                )
                            )
                        }

                        is Result.Error.DefaultError -> {
                            _action.send(
                                ArticleCreateAction.ShowSnackbar(
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
            check(currentState is ArticleCreateState.Success)
            currentState.copy(
                newArticle = NewArticleUi.default(),
            )
        }
    }

    private fun createArticle() {
        val currentState = state.value
        check(currentState is ArticleCreateState.Success)
        state.update { ArticleCreateState.Loading }
        coroutineScope.launch {
            createArticleUseCase(
                newArticle = currentState.newArticle.toDomain(),
            ).onSuccess {
                _action.send(
                    ArticleCreateAction.ShowSnackbar(
                        message = getString(Res.string.article_created_successfully),
                    )
                )
                onBackClick()
            }.onFailure { error ->
                state.update { currentState }
                when (error) {
                    is CreateArticleUseCaseError.InvalidArticleContent -> {
                        _action.send(
                            ArticleCreateAction.ShowSnackbar(
                                message = getString(Res.string.invalid_article_content_error),
                            )
                        )
                    }

                    is Result.Error.DefaultError -> {
                        _action.send(
                            ArticleCreateAction.ShowSnackbar(
                                message = getString(Res.string.unknown_error),
                            )
                        )
                    }
                }
            }
        }
    }

}