package kaiyrzhan.de.empath.features.profile.ui.profile_edit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import kaiyrzhan.de.empath.core.ui.components.CircularLoadingScreen
import kaiyrzhan.de.empath.core.ui.components.ErrorScreen
import kaiyrzhan.de.empath.core.ui.dialog.date_picker.DatePickerDialog
import kaiyrzhan.de.empath.core.ui.effects.SingleEventEffect
import kaiyrzhan.de.empath.core.ui.extensions.isPhone
import kaiyrzhan.de.empath.core.ui.modifiers.PaddingType
import kaiyrzhan.de.empath.core.ui.modifiers.defaultMaxWidth
import kaiyrzhan.de.empath.core.ui.modifiers.screenHorizontalPadding
import kaiyrzhan.de.empath.core.ui.modifiers.screenPadding
import kaiyrzhan.de.empath.core.ui.navigation.BackHandler
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kaiyrzhan.de.empath.core.ui.uikit.LocalSnackbarHostState
import kaiyrzhan.de.empath.features.profile.ui.profile_edit.components.ProfileCard
import kaiyrzhan.de.empath.features.profile.ui.profile_edit.components.ProfileEditActions
import kaiyrzhan.de.empath.features.profile.ui.profile_edit.components.ProfileEditTextFields
import kaiyrzhan.de.empath.features.profile.ui.profile_edit.components.TopBar
import kaiyrzhan.de.empath.features.profile.ui.profile_edit.model.ProfileEditAction
import kaiyrzhan.de.empath.features.profile.ui.profile_edit.model.ProfileEditEvent
import kaiyrzhan.de.empath.features.profile.ui.profile_edit.model.ProfileEditState
import kotlinx.coroutines.launch

@Composable
internal fun ProfileEditScreen(
    component: ProfileEditComponent,
    modifier: Modifier = Modifier,
) {
    val profileEditState = component.state.collectAsState()

    val snackbarHostState = LocalSnackbarHostState.current
    val coroutineScope = rememberCoroutineScope()

    val datePickerSlot by component.datePicker.subscribeAsState()
    datePickerSlot.child?.instance?.also { datePickerComponent ->
        DatePickerDialog(
            component = datePickerComponent,
        )
    }

    SingleEventEffect(component.action) { action ->
        when (action) {
            is ProfileEditAction.ShowSnackbar -> {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(action.message)
                }
            }
        }
    }

    BackHandler(component.backHandler) {
        component.onEvent(ProfileEditEvent.BackClick)
    }

    ProfileEditScreen(
        modifier = modifier,
        state = profileEditState.value,
        onEvent = component::onEvent,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProfileEditScreen(
    modifier: Modifier = Modifier,
    state: ProfileEditState,
    onEvent: (ProfileEditEvent) -> Unit,
) {
    val windowAdaptiveInfo = currentWindowAdaptiveInfo()
    val scrollState = rememberScrollState()

    when (state) {
        is ProfileEditState.Success -> {
            Scaffold(
                modifier = modifier,
                topBar = {
                    TopBar(
                        modifier = Modifier.fillMaxWidth(),
                        onBackClick = { onEvent(ProfileEditEvent.BackClick) },
                    )
                },
                contentColor = EmpathTheme.colors.onSurface,
                containerColor = EmpathTheme.colors.surface,
            ) { contentPadding ->
                if (windowAdaptiveInfo.isPhone()) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(contentPadding)
                            .screenHorizontalPadding()
                            .verticalScroll(scrollState),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Spacer(modifier = Modifier.height(16.dp))
                        ProfileCard(
                            modifier = Modifier.defaultMaxWidth(),
                            state = state,
                            onEvent = onEvent,
                            imageSize = 160.dp,
                        )
                        Spacer(modifier = Modifier.height(32.dp))
                        ProfileEditTextFields(
                            modifier = Modifier.defaultMaxWidth(),
                            state = state,
                            onEvent = onEvent,
                        )
                        Spacer(modifier = Modifier.height(32.dp))
                        ProfileEditActions(
                            modifier = Modifier.defaultMaxWidth(),
                            state = state,
                            onEvent = onEvent,
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                } else {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(contentPadding)
                            .screenHorizontalPadding()
                            .verticalScroll(scrollState),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        BoxWithConstraints(
                            modifier = Modifier.weight(1f),
                            contentAlignment = Alignment.Center,
                        ) {
                            val imageSize = maxWidth.value * 0.5f
                            ProfileCard(
                                modifier = Modifier.defaultMaxWidth(),
                                state = state,
                                onEvent = onEvent,
                                imageSize = imageSize.dp,
                            )
                        }
                        Spacer(modifier = Modifier.width(60.dp))
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Spacer(modifier = Modifier.height(16.dp))
                            ProfileEditTextFields(
                                modifier = Modifier.defaultMaxWidth(),
                                state = state,
                                onEvent = onEvent,
                            )
                            Spacer(modifier = Modifier.height(32.dp))
                            ProfileEditActions(
                                modifier = Modifier.defaultMaxWidth(),
                                state = state,
                                onEvent = onEvent,
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }
        }

        is ProfileEditState.Loading -> {
            CircularLoadingScreen(
                modifier = Modifier.fillMaxSize(),
            )
        }

        is ProfileEditState.Error -> {
            ErrorScreen(
                modifier = Modifier.fillMaxSize(),
                message = state.message,
                onTryAgainClick = { onEvent(ProfileEditEvent.LoadProfile) },
            )
        }

        is ProfileEditState.Initial -> Unit
    }
}

