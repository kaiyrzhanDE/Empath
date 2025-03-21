package kaiyrzhan.de.empath.core.ui.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import kaiyrzhan.de.empath.core.utils.dispatchers.AppDispatchers
import kaiyrzhan.de.empath.core.utils.logger.BaseLogger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

public abstract class BaseComponent(
    private val componentContext: ComponentContext,
) : ComponentContext by componentContext, KoinComponent {
    protected val appDispatchers: AppDispatchers by inject()
    protected val coroutineScope: CoroutineScope =
        coroutineScope(appDispatchers.main + SupervisorJob())

    protected val logger: BaseLogger by inject()
}

