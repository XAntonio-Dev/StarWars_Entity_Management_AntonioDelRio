package com.example.starwarsentitymanagementsystem.home

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.starwarsentitymanagementsystem.ui.components.BaseFabState
import com.example.starwarsentitymanagementsystem.ui.components.BaseTopAppBar
import com.example.starwarsentitymanagementsystem.ui.components.BaseTopAppBarState
import com.example.starwarsentitymanagementsystem.ui.screens.AboutScreen
import com.example.starwarsentitymanagementsystem.ui.screens.add.AddSpeciesScreen
import com.example.starwarsentitymanagementsystem.ui.screens.add.AddSpeciesViewModel
import com.example.starwarsentitymanagementsystem.ui.screens.edit.EditSpeciesScreen
import com.example.starwarsentitymanagementsystem.ui.screens.list.SpeciesListScreen
import com.example.starwarsentitymanagementsystem.ui.screens.list.SpeciesListViewModel
import kotlinx.coroutines.launch

@Composable
fun NavHostScreen(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val speciesListViewModel: SpeciesListViewModel = hiltViewModel()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: "list"

    var topBarState by remember { mutableStateOf(BaseTopAppBarState()) }
    var showFab by remember { mutableStateOf(true) }

    val onConfigureTopBar: (BaseTopAppBarState) -> Unit = { newState ->
        val isRootScreen = currentRoute == "list" || currentRoute == "about"

        val navIcon = if (isRootScreen) Icons.Default.Menu else Icons.AutoMirrored.Filled.ArrowBack
        val navClick: () -> Unit = {
            if (isRootScreen) scope.launch { drawerState.open() } else navController.popBackStack()
        }

        topBarState = newState.copy(
            navigationIcon = navIcon,
            onNavigationClick = navClick
        )
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text(
                    text = "Menú Star Wars",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.titleLarge
                )
                HorizontalDivider()
                NavigationDrawerItem(
                    label = { Text("Listado Especies") },
                    selected = currentRoute == "list",
                    onClick = {
                        navController.navigate("list") { popUpTo("list") { inclusive = true } }
                        scope.launch { drawerState.close() }
                    }
                )
                NavigationDrawerItem(
                    label = { Text("Sobre Nosotros") },
                    selected = currentRoute == "about",
                    onClick = {
                        navController.navigate("about")
                        scope.launch { drawerState.close() }
                    }
                )
            }
        }
    ) {
        Scaffold(
            topBar = { BaseTopAppBar(state = topBarState) },
            snackbarHost = { SnackbarHost(snackbarHostState) },
            floatingActionButton = {
                if (showFab) {
                    FloatingActionButton(onClick = { navController.navigate("add") }) {
                        Icon(Icons.Default.Add, contentDescription = "Añadir")
                    }
                }
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "list",
                modifier = modifier.padding(innerPadding)
            ) {
                // LISTA
                composable(
                    route = "list",
                    enterTransition = { fadeIn(animationSpec = tween(400)) },
                    exitTransition = { fadeOut(animationSpec = tween(400)) }
                ) {
                    LaunchedEffect(Unit) { showFab = true }

                    val state by speciesListViewModel.uiState.collectAsState()

                    SpeciesListScreen(
                        modifier = Modifier,
                        speciesList = state,
                        speciesToDelete = speciesListViewModel.speciesToDelete,
                        onConfigureTopBar = onConfigureTopBar,
                        onEdit = { species ->
                            speciesListViewModel.onEditAccount(species)
                            navController.navigate("edit")
                        },
                        onRequestDelete = { species -> speciesListViewModel.onRequestDelete(species) },
                        onDismissDelete = { speciesListViewModel.onDismissDeleteDialog() },
                        onConfirmDelete = {
                            val name = speciesListViewModel.speciesToDelete?.name ?: ""
                            speciesListViewModel.onConfirmDelete()
                            scope.launch { snackbarHostState.showSnackbar("Especie $name eliminada") }
                        },
                        onSort = { speciesListViewModel.onSortByName() },
                        onAbout = { navController.navigate("about") }
                    )
                }

                // AÑADIR
                composable(
                    route = "add",
                    enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
                    exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) }
                ) {
                    LaunchedEffect(Unit) {
                        showFab = false
                        onConfigureTopBar(BaseTopAppBarState(title = "Nueva Especie"))
                    }
                    val addViewModel: AddSpeciesViewModel = hiltViewModel()
                    AddSpeciesScreen(
                        viewModel = addViewModel,
                        onCreate = { navController.popBackStack() },
                        onCancel = { navController.popBackStack() }
                    )
                }

                // EDITAR
                composable(
                    route = "edit",
                    enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
                    exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) }
                ) {
                    LaunchedEffect(Unit) { showFab = false }
                    val speciesToEdit = speciesListViewModel.speciesToEdit

                    if (speciesToEdit != null) {
                        EditSpeciesScreen(
                            species = speciesToEdit,
                            onBack = { navController.popBackStack() },
                            onShowSnackbar = { msg -> scope.launch { snackbarHostState.showSnackbar(msg) } },
                            onConfigureTopBar = onConfigureTopBar
                        )
                    } else {
                        LaunchedEffect(Unit) { navController.popBackStack() }
                    }
                }

                // ABOUT
                composable(
                    route = "about",
                    enterTransition = { fadeIn(animationSpec = tween(400)) },
                    exitTransition = { fadeOut(animationSpec = tween(400)) }
                ) {
                    LaunchedEffect(Unit) {
                        showFab = false
                        onConfigureTopBar(BaseTopAppBarState(title = "Sobre Nosotros"))
                    }
                    AboutScreen()
                }
            }
        }
    }
}