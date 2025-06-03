package kaiyrzhan.de.empath.features.profile.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.key.Key.Companion.R
import androidx.compose.ui.unit.dp
import empath.core.uikit.generated.resources.Res
import empath.core.uikit.generated.resources.ic_ac_unit
import empath.core.uikit.generated.resources.rating
import kaiyrzhan.de.empath.core.ui.animations.CollapseAnimatedVisibility
import kaiyrzhan.de.empath.core.ui.components.CircularLoadingScreen
import kaiyrzhan.de.empath.core.ui.effects.SingleEventEffect
import kaiyrzhan.de.empath.core.ui.extensions.appendColon
import kaiyrzhan.de.empath.core.ui.extensions.appendSpace
import kaiyrzhan.de.empath.core.ui.extensions.isPhone
import kaiyrzhan.de.empath.core.ui.modifiers.PaddingType
import kaiyrzhan.de.empath.core.ui.modifiers.screenPadding
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kaiyrzhan.de.empath.core.ui.uikit.LocalSnackbarHostState
import kaiyrzhan.de.empath.core.utils.currentPlatform
import kaiyrzhan.de.empath.core.utils.toGroupedString
import kaiyrzhan.de.empath.features.profile.ui.profile.components.ProfileErrorCard
import kaiyrzhan.de.empath.features.profile.ui.profile.components.ProfileCard
import kaiyrzhan.de.empath.features.profile.ui.profile.components.AccountProperties
import kaiyrzhan.de.empath.features.profile.ui.profile.components.GeneralProperties
import kaiyrzhan.de.empath.features.profile.ui.profile.components.StudyProperties
import kaiyrzhan.de.empath.features.profile.ui.profile.components.WorkProperties
import kaiyrzhan.de.empath.features.profile.ui.profile.model.ProfileAction
import kaiyrzhan.de.empath.features.profile.ui.profile.model.ProfileEvent
import kaiyrzhan.de.empath.features.profile.ui.profile.model.ProfileState
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun ProfileScreen(
    component: ProfileComponent,
    modifier: Modifier = Modifier,
) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = LocalSnackbarHostState.current

    val profileState = component.state.collectAsState()

    SingleEventEffect(component.action) { action ->
        when (action) {
            is ProfileAction.ShowSnackBar -> {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(action.message)
                }
            }
        }
    }

    ProfileScreen(
        modifier = modifier
            .fillMaxSize(),
        state = profileState.value,
        onEvent = component::onEvent,
    )
}

@Composable
private fun ProfileScreen(
    modifier: Modifier = Modifier,
    state: ProfileState,
    onEvent: (ProfileEvent) -> Unit,
) {
    val scrollState = rememberScrollState()
    val windowAdaptiveInfo = currentWindowAdaptiveInfo()

    Column(
        modifier = modifier
            .verticalScroll(scrollState)
            .background(color = EmpathTheme.colors.surfaceDim)
            .screenPadding(PaddingType.MAIN),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        BoxWithConstraints(
            modifier = Modifier.fillMaxWidth(),
        ) {
            val maxHeight = maxWidth.value * 0.15f
            when (state) {
                is ProfileState.Success -> {
                    ProfileCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 88.dp, max = 200.dp)
                            .height(maxHeight.dp),
                        name = state.user.nickname,
                        email = state.user.email,
                        imageUrl = state.user.imageUrl,
                        rating = state.user.rating,
                        onUserPageClick = { onEvent(ProfileEvent.UserPageClick) },
                    )
                }

                is ProfileState.Error -> {
                    ProfileErrorCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 88.dp, max = 200.dp)
                            .height(maxHeight.dp),
                        imageSize = maxHeight.dp,
                        onReloadClick = { onEvent(ProfileEvent.Reload) },
                        onUserPageClick = { onEvent(ProfileEvent.UserPageClick) },
                    )
                }

                is ProfileState.Loading -> {
                    CircularLoadingScreen(
                        modifier = Modifier
                            .clip(EmpathTheme.shapes.small)
                            .fillMaxWidth()
                            .heightIn(min = 88.dp, max = 200.dp)
                            .height(maxHeight.dp),

                        )
                }

                is ProfileState.Initial -> Unit
            }
        }
        if (windowAdaptiveInfo.isPhone()) {
            CollapseAnimatedVisibility(state is ProfileState.Success) {
                if (state is ProfileState.Success) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(EmpathTheme.shapes.small)
                            .background(EmpathTheme.colors.primaryContainer)
                            .padding(vertical = 18.dp, horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(Res.drawable.ic_ac_unit),
                            contentDescription = null,
                            tint = EmpathTheme.colors.onPrimaryContainer,
                        )
                        Text(
                            text = buildString {
                                append(stringResource(Res.string.rating))
                                appendColon()
                                appendSpace()
                                append(state.user.rating.toGroupedString())
                            },
                            style = EmpathTheme.typography.titleSmall,
                            color = EmpathTheme.colors.onPrimaryContainer,
                        )
                    }
                }
            }
        }
        AccountProperties(
            onEditProfileClick = { onEvent(ProfileEvent.EditProfileClick) },
        )
        StudyProperties()
        WorkProperties()
        GeneralProperties(
            onLogOutClick = { onEvent(ProfileEvent.LogOut) }
        )
    }
}


@Preview
@Composable
private fun Preview() {
    ProfileScreen(
        component = FakeProfileComponent(),
        modifier = Modifier.fillMaxSize(),
    )
}