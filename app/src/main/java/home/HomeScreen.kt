package com.slangmap.app.presentation.home

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.slangmap.app.presentation.home.model.HomeUiState

private object Dimens {

    val SheetPeekHeight = 280.dp
    val SheetCornerRadius = 28.dp

    val SheetDragHandleWidth = 32.dp
    val SheetDragHandleHeight = 4.dp
    val SheetDragHandlePadding = 12.dp

    val ScreenPadding = 16.dp
    val SectionSpacing = 16.dp
    val ListItemSpacing = 12.dp

    val StateContentHeight = 160.dp
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onStoreClick: (Long) -> Unit,
    viewModel: HomeViewModel = viewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->

        val hasFine =
            permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true

        val hasCoarse =
            permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true

        viewModel.onLocationPermissionResult(
            hasFine || hasCoarse
        )
    }

    // 화면 최초 진입 시 1회만 권한 상태를 확인하고 필요하면 요청함
    LaunchedEffect(Unit) {

        val hasFine =
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

        val hasCoarse =
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

        if (hasFine || hasCoarse) {
            viewModel.onLocationPermissionResult(true)
        } else {
            permissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    val scaffoldState = rememberBottomSheetScaffoldState()

    Scaffold(
        bottomBar = {
            BottomNavigationBar()
        }
    ) { scaffoldPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding)
        ) {

            BottomSheetScaffold(
                scaffoldState = scaffoldState,
                sheetPeekHeight = Dimens.SheetPeekHeight,
                sheetShape = RoundedCornerShape(
                    topStart = Dimens.SheetCornerRadius,
                    topEnd = Dimens.SheetCornerRadius
                ),
                sheetDragHandle = {
                    SheetDragHandle()
                },
                sheetContent = {
                    StoreListSheetContent(
                        uiState = uiState,
                        onStoreClick = onStoreClick
                    )
                }
            ) { innerPadding ->

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(Dimens.ScreenPadding)
                ) {

                    SearchBar()

                    Spacer(
                        modifier = Modifier.height(
                            Dimens.SectionSpacing
                        )
                    )

                    NaverMapView(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    )

                    Spacer(
                        modifier = Modifier.height(
                            Dimens.SectionSpacing
                        )
                    )

                    CategoryChipRow()
                }
            }
        }
    }
}

@Composable
private fun SheetDragHandle() {

    HorizontalDivider(
        modifier = Modifier
            .padding(vertical = Dimens.SheetDragHandlePadding)
            .width(Dimens.SheetDragHandleWidth),
        thickness = Dimens.SheetDragHandleHeight
    )
}

@Composable
private fun StoreListSheetContent(
    uiState: HomeUiState,
    onStoreClick: (Long) -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.ScreenPadding)
    ) {

        when {

            uiState.isLoading -> {
                LoadingContent()
            }

            uiState.stores.isEmpty() -> {
                EmptyContent()
            }

            else -> {

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(Dimens.ListItemSpacing),
                    contentPadding = PaddingValues(bottom = Dimens.ScreenPadding)
                ) {

                    items(
                        items = uiState.stores,
                        key = { it.id }
                    ) { store ->

                        StoreCard(
                            store = store,
                            onClick = { onStoreClick(store.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun LoadingContent() {
    StateContentContainer {
        CircularProgressIndicator()
    }
}

@Composable
private fun EmptyContent() {
    StateContentContainer {
        Text(
            text = "주변에 등록된 가게가 없어요",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun StateContentContainer(
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(Dimens.StateContentHeight),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}