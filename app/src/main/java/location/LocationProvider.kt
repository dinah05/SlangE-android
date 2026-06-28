package com.slangmap.app.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.util.Log
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeoutOrNull

class LocationProvider(
    context: Context
) {

    private val fusedLocationClient =
        LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    suspend fun getCurrentLocation(): Location? {
        return try {
            withTimeoutOrNull(LOCATION_TIMEOUT_MS) {
                val cancellationTokenSource = CancellationTokenSource()
                fusedLocationClient.getCurrentLocation(
                    Priority.PRIORITY_BALANCED_POWER_ACCURACY,
                    cancellationTokenSource.token
                ).await()
            }
        } catch (e: SecurityException) {
            logError("위치 권한 없이 호출됨", e)
            null
        } catch (e: Exception) {
            logError("위치 조회 실패", e)
            null
        }
    }

    private fun logError(
        message: String,
        e: Exception
    ) {
        Log.e(TAG, message, e)
    }

    companion object {
        private const val TAG = "LocationProvider"
        private const val LOCATION_TIMEOUT_MS = 5_000L
    }
}