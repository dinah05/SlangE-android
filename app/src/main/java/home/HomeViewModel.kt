package com.slangmap.app.presentation.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.slangmap.app.location.LocationProvider
import com.slangmap.app.presentation.home.model.HomeUiState
import com.slangmap.app.presentation.home.model.StoreUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    application: Application,
) : AndroidViewModel(application) {

    private val locationProvider = LocationProvider(application)

    private val _uiState = MutableStateFlow(HomeUiState(isLoading = true))
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadStores()
    }

    private fun loadStores() {
        // TODO: 실제 레포지토리/API 연동으로 교체할 Mock 데이터
        _uiState.update {
            it.copy(
                isLoading = false,
                stores = listOf(
                    StoreUiModel(
                        id = 1,
                        name = "슬라임 팩토리",
                        category = "슬라임",
                        distance = "320m",
                        rating = 4.8
                    ),
                    StoreUiModel(
                        id = 2,
                        name = "문구좋아 목동점",
                        category = "말랑이",
                        distance = "890m",
                        rating = 4.6
                    )
                )
            )
        }
    }

    /** 권한 요청 결과를 화면으로부터 전달받아 처리 */
    fun onLocationPermissionResult(granted: Boolean) {
        _uiState.update { it.copy(hasLocationPermission = granted) }

        if (granted) {
            fetchCurrentLocation()
        }
    }

    private fun fetchCurrentLocation() {
        viewModelScope.launch {
            val location = locationProvider.getCurrentLocation()
            _uiState.update { it.copy(currentLocation = location) }
            // TODO: 지도 카메라 이동 / 거리 기반 필터링은 아직 미구현
            // 현재는 상태에 보관만 하고 화면에는 별도 반영하지 않음
        }
    }
}