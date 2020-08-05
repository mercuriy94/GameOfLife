package com.mercuriy94.gameoflife.flow.main.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mercuriy94.gameoflife.core.di.util.ComponentDependenciesProvider
import com.mercuriy94.gameoflife.core.di.util.HasComponentDependencies
import com.mercuriy94.gameoflife.core.flow.BaseFlowFragment
import com.mercuriy94.gameoflife.core.navigation.NavigationViewModel
import com.mercuriy94.gameoflife.core.ui.base.BaseFragment
import com.mercuriy94.gameoflife.flow.main.R
import com.mercuriy94.gameoflife.flow.main.databinding.MainFlowLayoutBinding
import com.mercuriy94.gameoflife.flow.main.di.MainFlowComponent
import javax.inject.Inject

/**
 * @author Nikita Marsyukov
 */
class MainFlowFragment :
    BaseFlowFragment<NavigationViewModel, MainFlowLayoutBinding>(R.id.main_nav_host),
    HasComponentDependencies {

    @Inject
    override lateinit var dependencies: ComponentDependenciesProvider
    override val viewModelClass: Class<NavigationViewModel> = NavigationViewModel::class.java

    override fun inject() = MainFlowComponent.inject(this)

    override fun createViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): MainFlowLayoutBinding? = MainFlowLayoutBinding.inflate(inflater)

}
