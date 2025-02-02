package com.guilherme.wheretowatch.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.guilherme.wheretowatch.domain.ResponseError
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import wheretowatch.composeapp.generated.resources.Res
import wheretowatch.composeapp.generated.resources.error_display_forbidden
import wheretowatch.composeapp.generated.resources.error_display_method_not_allowed
import wheretowatch.composeapp.generated.resources.error_display_not_found
import wheretowatch.composeapp.generated.resources.error_display_null_value
import wheretowatch.composeapp.generated.resources.error_display_request_timeout
import wheretowatch.composeapp.generated.resources.error_display_too_many_requests
import wheretowatch.composeapp.generated.resources.error_display_unauthorized
import wheretowatch.composeapp.generated.resources.error_display_unknown
import wheretowatch.composeapp.generated.resources.error_display_unresolved_address
import wheretowatch.composeapp.generated.resources.error_image
import wheretowatch.composeapp.generated.resources.oops_something_went_wrong
import wheretowatch.composeapp.generated.resources.we_couldnt_process_the_request_please_restart_and_try_again

@Composable
fun ErrorDisplay(errorState: ResponseError?) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            modifier = Modifier.fillMaxWidth(),
            imageVector = vectorResource(Res.drawable.error_image),
            contentDescription = "",
            contentScale = ContentScale.FillWidth
        )

        when (errorState) {
            ResponseError.BAD_REQUEST -> {
                ErrorMessage(
                    message = stringResource(Res.string.we_couldnt_process_the_request_please_restart_and_try_again)
                )
            }

            ResponseError.UNAUTHORIZED -> {
                ErrorMessage(
                    message = stringResource(Res.string.error_display_unauthorized)
                )

            }

            ResponseError.FORBIDDEN -> {
                ErrorMessage(
                    message = stringResource(Res.string.error_display_forbidden)
                )

            }

            ResponseError.NOT_FOUND -> {
                ErrorMessage(message = stringResource(Res.string.error_display_not_found))
            }

            ResponseError.METHOD_NOT_ALLOWED -> {
                ErrorMessage(
                    message = stringResource(Res.string.error_display_method_not_allowed)
                )
            }

            ResponseError.REQUEST_TIMEOUT -> {
                ErrorMessage(
                    message = stringResource(Res.string.error_display_request_timeout)
                )
            }

            ResponseError.TOO_MANY_REQUESTS -> {
                ErrorMessage(message = stringResource(Res.string.error_display_too_many_requests))

            }

            ResponseError.NULL_VALUE -> {
                ErrorMessage(
                    message = stringResource(Res.string.error_display_null_value)
                )
            }

            ResponseError.UNRESOLVED_ADDRESS -> {
                ErrorMessage(message = stringResource(Res.string.error_display_unresolved_address))
            }

            ResponseError.UNKNOWN -> {

                ErrorMessage(
                    message = stringResource(Res.string.error_display_unknown)
                )
            }

            null -> {
                ErrorMessage(message = "")
            }

        }
    }
}

@Composable
private fun ErrorMessage(message: String) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(Res.string.oops_something_went_wrong),
        fontSize = MaterialTheme.typography.headlineLarge.fontSize,
        fontWeight = FontWeight.ExtraBold,
        textAlign = TextAlign.Center,
        lineHeight = MaterialTheme.typography.headlineLarge.lineHeight
    )

    Text(
        modifier = Modifier.fillMaxWidth(),
        text = message,
        fontSize = 24.sp,
        textAlign = TextAlign.Center
    )
}