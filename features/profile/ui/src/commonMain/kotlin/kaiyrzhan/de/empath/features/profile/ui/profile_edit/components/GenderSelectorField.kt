package kaiyrzhan.de.empath.features.profile.ui.profile_edit.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import empath.core.uikit.generated.resources.ic_arrow_forward
import empath.core.uikit.generated.resources.ic_wc
import empath.features.profile.ui.generated.resources.select_gender
import kaiyrzhan.de.empath.core.ui.modifiers.defaultMaxWidth
import kaiyrzhan.de.empath.core.ui.uikit.EmpathTheme
import kaiyrzhan.de.empath.features.profile.domain.model.User
import empath.features.profile.ui.generated.resources.Res as FeatureRes
import empath.core.uikit.generated.resources.Res as CoreRes
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun GenderSelectorField(
    modifier: Modifier = Modifier,
    gender: String,
    onGenderSelect: (User.Gender) -> Unit,
) {
    var showGenderPopUp by rememberSaveable { mutableStateOf(false) }
    val genders = User.Gender.entries

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        PickerField(
            title = stringResource(FeatureRes.string.select_gender),
            selected = gender,
            onClick = { showGenderPopUp = showGenderPopUp.not() },
            leadingPainter = painterResource(CoreRes.drawable.ic_wc),
            trailingPainter = painterResource(CoreRes.drawable.ic_arrow_forward),
        )
        DropdownMenu(
            modifier = Modifier.defaultMaxWidth(),
            expanded = showGenderPopUp,
            onDismissRequest = { showGenderPopUp = false },
            shape = EmpathTheme.shapes.extraSmall,
            containerColor = EmpathTheme.colors.surface,
            border = BorderStroke(width = 1.dp, color = EmpathTheme.colors.outline),
        ) {
            genders.forEachIndexed { index, gender ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = gender.toString(),
                            style = EmpathTheme.typography.bodyLarge,
                            color = EmpathTheme.colors.onSurface,
                        )
                    },
                    onClick = {
                        showGenderPopUp = false
                        onGenderSelect(gender)
                    }
                )
            }
        }
    }
}