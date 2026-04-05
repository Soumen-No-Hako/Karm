package com.nemuos.karm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.nemuos.karm.ui.screens.DashboardScreen
import com.nemuos.karm.ui.screens.ProjectDetailScreen
import com.nemuos.karm.ui.screens.WorkItemDetailScreen
import com.nemuos.karm.ui.theme.KarmTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KarmTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "dashboard"
                    ) {
                        composable("dashboard") {
                            DashboardScreen(
                                onNavigateToProject = { projectId ->
                                    navController.navigate("project/$projectId")
                                }
                            )
                        }

                        composable(
                            route = "project/{projectId}",
                            arguments = listOf(navArgument("projectId") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val projectId = backStackEntry.arguments?.getString("projectId") ?: ""
                            ProjectDetailScreen(
                                projectId = projectId,
                                onNavigateBack = { navController.popBackStack() },
                                onNavigateToWorkItem = { workItemId ->
                                    navController.navigate("workitem/$workItemId")
                                }
                            )
                        }

                        composable(
                            route = "workitem/{workItemId}",
                            arguments = listOf(navArgument("workItemId") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val workItemId = backStackEntry.arguments?.getString("workItemId") ?: ""
                            WorkItemDetailScreen(
                                workItemId = workItemId,
                                onNavigateBack = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}
