package com.slangmap.app.presentation.home.model

import android.location.Location

data class HomeUiState(
    val stores: List<StoreUiModel> = emptyList(),
    val isLoading: Boolean = false,
    val hasLocationPermission: Boolean = false,
    // TODO: 카메라 이동 / 주변 가게 거리 필터링에 사용할 예정 (미구현, 현재는 보관만 함)
    val currentLocation: Location? = null,
)