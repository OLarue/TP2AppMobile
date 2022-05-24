package ca.qc.cstj.tp2

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.viewbinding.library.activity.viewBinding
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import ca.qc.cstj.tp2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by viewBinding<ActivityMainBinding>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navFragmentContainerView = supportFragmentManager.findFragmentById(R.id.navFragmentContainerView) as NavHostFragment
        val navController = navFragmentContainerView.navController

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_tickets, R.id.navigation_gateways, R.id.navigation_network
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.bnvMain.setupWithNavController(navController)
    }

    companion object {
        fun newIntent(context: Context) : Intent {
            return Intent(context, MainActivity::class.java)
        }
    }
}