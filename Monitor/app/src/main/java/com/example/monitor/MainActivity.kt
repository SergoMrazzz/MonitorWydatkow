package com.example.monitor

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.monitor.databinding.ActivityMainBinding
import com.example.monitor.viewmodel.ExpenseViewModel
import com.example.monitor.data.model.User
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: ExpenseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[ExpenseViewModel::class.java]

        // Inicjalizacja użytkownika
        initializeUser()

        // Inicjalizacja kategorii i walut
        viewModel.initializeDefaultCategories()
        viewModel.initializeDefaultCurrencies()

        // Nawigacja
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        val bottomNav: BottomNavigationView = binding.bottomNavigation
        bottomNav.setupWithNavController(navController)
    }

    private fun initializeUser() {
        lifecycleScope.launch {
            viewModel.currentUser.observe(this@MainActivity) { user ->
                if (user == null) {
                    val newUser = User(
                        userId = "user_${System.currentTimeMillis()}",
                        defaultCurrency = "PLN",
                        darkMode = false
                    )
                    viewModel.insertUser(newUser)
                }
            }
        }
    }
}