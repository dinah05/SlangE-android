package com.slangmap.app.presentation.home.model

data class HomeUiState(
    val stores: List<StoreUiModel> = emptyList(),
    val isLoading: Boolean = false,
)