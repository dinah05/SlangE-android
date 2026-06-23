package com.slangmap.app.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.slangmap.app.R

/**
 * 로그인 화면.
 *
 * - 콘텐츠 높이가 화면보다 커지는 경우(큰 폰트 스케일, 작은 화면, 긴 다국어 문자열 등)를
 *   대비해 verticalScroll을 기본으로 깔아 잘림을 방지한다.
 * - weight 기반 Spacer는 "여유 공간이 있을 때" 비율 배치를 위한 것이며,
 *   콘텐츠 자체의 오버플로를 막아주지 않으므로 스크롤과 함께 사용한다.
 * - edge-to-edge 대응을 위해 상/하단 안전 영역 padding을 적용한다.
 */

// 화면 영역 배치 비율 (의도 명확화를 위한 명명 상수)
private const val WEIGHT_TOP_SPACE = 0.12f
private const val WEIGHT_SUBTITLE_SPACE = 0.12f
private const val WEIGHT_ACTIONS_SPACE = 0.08f
private const val WEIGHT_BEFORE_LOGIN_BUTTON = 0.18f
private const val WEIGHT_BEFORE_TERMS = 0.1f

@Composable
fun LoginScreen(
    isLoginInProgress: Boolean = false,
    onLoginClick: () -> Unit,
    onSearchNearbyClick: () -> Unit,
    onReviewClick: () -> Unit,
    onReportClick: () -> Unit,
    onTermsClick: () -> Unit,
    onPrivacyPolicyClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding() // edge-to-edge 대응: 상태바/내비게이션 바 영역 보호
            .verticalScroll(rememberScrollState()) // 콘텐츠 오버플로 시 잘림 방지
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.weight(WEIGHT_TOP_SPACE))

        Image(
            painter = painterResource(id = R.drawable.slangmap_logo),
            contentDescription = stringResource(R.string.login_logo_content_description),
            modifier = Modifier.size(width = 120.dp, height = 120.dp),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.weight(WEIGHT_SUBTITLE_SPACE))

        Text(
            text = stringResource(R.string.login_subtitle),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.weight(WEIGHT_ACTIONS_SPACE))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                onClick = onSearchNearbyClick,
                modifier = Modifier
                    .weight(1f)
                    .testTag("login_search_nearby_button")
            ) {
                Text(stringResource(R.string.login_action_search_nearby))
            }

            OutlinedButton(
                onClick = onReviewClick,
                modifier = Modifier
                    .weight(1f)
                    .testTag("login_review_button")
            ) {
                Text(stringResource(R.string.login_action_review))
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = onReportClick,
            modifier = Modifier
                .fillMaxWidth()
                .testTag("login_report_button")
        ) {
            Text(stringResource(R.string.login_action_report))
        }

        Spacer(
            modifier = Modifier
                .weight(WEIGHT_BEFORE_LOGIN_BUTTON)
                .heightIn(min = 24.dp)
        )

        Button(
            onClick = onLoginClick,
            enabled = !isLoginInProgress, // 로딩 중 중복 클릭을 방지
            modifier = Modifier
                .fillMaxWidth()
                .testTag("login_google_button")
        ) {
            if (isLoginInProgress) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = MaterialTheme.colorScheme.onPrimary,
                    strokeWidth = 2.dp
                )
            } else {
                Text(stringResource(R.string.login_google_cta))
            }
        }

        Spacer(
            modifier = Modifier
                .weight(WEIGHT_BEFORE_TERMS)
                .heightIn(min = 16.dp)
        )

        TermsSection(
            onTermsClick = onTermsClick,
            onPrivacyPolicyClick = onPrivacyPolicyClick
        )

        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
private fun TermsSection(
    onTermsClick: () -> Unit,
    onPrivacyPolicyClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.login_terms_notice),
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(
                onClick = onTermsClick,
                modifier = Modifier
                    .minimumInteractiveComponentSize() // 48dp 최소 터치 영역 보장
                    .testTag("login_terms_button"),
                contentPadding = PaddingValues(horizontal = 4.dp)
            ) {
                Text(
                    text = stringResource(R.string.login_terms_of_service),
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Text(
                text = stringResource(R.string.login_terms_divider), // 하드코딩 "|" 제거, i18n 처리
                style = MaterialTheme.typography.bodySmall,
            )

            TextButton(
                onClick = onPrivacyPolicyClick,
                modifier = Modifier
                    .minimumInteractiveComponentSize()
                    .testTag("login_privacy_policy_button"),
                contentPadding = PaddingValues(horizontal = 4.dp)
            ) {
                Text(
                    text = stringResource(R.string.login_privacy_policy),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(
    showBackground = true,
    fontScale = 1.5f,
    name = "Large font scale"
)
@Preview(
    showBackground = true,
    device = "spec:width=360dp,height=480dp",
    name = "Small screen"
)
@Composable
private fun LoginScreenPreview() {
    LoginScreen(
        isLoginInProgress = false,
        onLoginClick = {},
        onSearchNearbyClick = {},
        onReviewClick = {},
        onReportClick = {},
        onTermsClick = {},
        onPrivacyPolicyClick = {}
    )
}

@Preview(showBackground = true, name = "Login in progress")
@Composable
private fun LoginScreenLoadingPreview() {
    LoginScreen(
        isLoginInProgress = true,
        onLoginClick = {},
        onSearchNearbyClick = {},
        onReviewClick = {},
        onReportClick = {},
        onTermsClick = {},
        onPrivacyPolicyClick = {}
    )
}