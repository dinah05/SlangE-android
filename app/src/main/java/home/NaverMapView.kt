package com.slangmap.app.presentation.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.naver.maps.map.MapView

/**
 * 네이버 지도 Composable
 *
 * [수정 이유]
 *
 * - 기존 코드는 onCreate/onStart/onResume만 호출하고 종료 생명주기를 처리하지 않아
 *   MapView 내부 GL 컨텍스트, 네트워크 연결 등이 해제되지 않아 메모리 누수가 발생했음.
 *
 * [해결 방법]
 *
 * - remember로 MapView 인스턴스를 단일 생성 (recomposition 시 재생성 방지)
 *
 * - DisposableEffect + LifecycleEventObserver로 전체 생명주기 위임
 *
 * - onDispose에서 observer 제거 및 MapView.onDestroy() 보장
 */
@Composable
fun NaverMapView(
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    // recomposition이 발생해도 MapView 인스턴스는 하나만 유지
    val mapView = remember {
        MapView(context).apply {

            // Lifecycle ON_CREATE를 놓칠 수 있으므로
            // 생성 시점에 직접 호출
            onCreate(null)
        }
    }

    DisposableEffect(
        mapView,
        lifecycleOwner
    ) {

        val observer = LifecycleEventObserver { _, event ->

            when (event) {

                Lifecycle.Event.ON_START -> {
                    mapView.onStart()
                }

                Lifecycle.Event.ON_RESUME -> {
                    mapView.onResume()
                }

                Lifecycle.Event.ON_PAUSE -> {
                    mapView.onPause()
                }

                Lifecycle.Event.ON_STOP -> {
                    mapView.onStop()
                }

                // onDispose에서 처리
                Lifecycle.Event.ON_DESTROY -> Unit

                else -> Unit
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {

            lifecycleOwner.lifecycle.removeObserver(observer)

            mapView.onDestroy()
        }
    }

    AndroidView(
        modifier = modifier,
        factory = {
            mapView
        }
    )
}