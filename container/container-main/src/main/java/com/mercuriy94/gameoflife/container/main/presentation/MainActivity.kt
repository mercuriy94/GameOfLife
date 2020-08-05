package com.mercuriy94.gameoflife.container.main.presentation

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.mercuriy94.gameoflife.container.main.R
import com.mercuriy94.gameoflife.container.main.databinding.MainContainerLayoutBinding
import com.mercuriy94.gameoflife.container.main.di.MainContainerComponent
import com.mercuriy94.gameoflife.core.di.util.ComponentDependenciesProvider
import com.mercuriy94.gameoflife.core.di.util.HasComponentDependencies
import com.mercuriy94.gameoflife.core.ui.base.BaseActivity
import com.mercuriy94.gameoflife.core.ui.base.NavigationHost
import dev.chrisbanes.insetter.setEdgeToEdgeSystemUiFlags
import javax.inject.Inject

/**
 * @author Nikita Marsyukov
 */
class MainActivity : BaseActivity<MainContainerViewModel, MainContainerLayoutBinding>(),
    NavigationHost,
    HasComponentDependencies {

    @Inject
    override lateinit var dependencies: ComponentDependenciesProvider

    override val viewModelClass: Class<MainContainerViewModel> = MainContainerViewModel::class.java

    override fun inject() = MainContainerComponent.inject(this)

    override fun createViewBinding(): MainContainerLayoutBinding? =
        MainContainerLayoutBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding.root.setEdgeToEdgeSystemUiFlags(true)
    }

    override fun getNavController(): NavController = findNavController(R.id.nav_host_fragment)

    override fun isTopDestination(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        val config = AppBarConfiguration.Builder(navController.graph).build()
        return config.topLevelDestinations.contains(navController.currentDestination?.id)
    }

    override fun registerToolbarWithNavigation(toolbar: Toolbar) {
        NavigationUI.setupWithNavController(toolbar, findNavController(R.id.nav_host_fragment))
    }

}
