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
import org.jetbrains.compose.resources.vectorResource
import wheretowatch.composeapp.generated.resources.Res
import wheretowatch.composeapp.generated.resources.error_image

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
                    message = "We couldn't process the request. Please restart and try again."
                )
            }

            ResponseError.UNAUTHORIZED -> {
                ErrorMessage(
                    message = "The API returned an error (Unauthorized). Please restart the app and try again. If the issue persists, contact support."
                )

            }

            ResponseError.FORBIDDEN -> {
                ErrorMessage(
                    message = "The API returned an error (Forbidden). Please restart the app and try again. If the issue persists, contact support."
                )

            }

            ResponseError.NOT_FOUND -> {
                ErrorMessage(message = "Content not found. If the issue persists, please contact support.")
            }

            ResponseError.METHOD_NOT_ALLOWED -> {
                ErrorMessage(
                    message = "The API returned an error (Method not allowed). Please restart the app and try again. If the issue persists, contact support."
                )
            }

            ResponseError.REQUEST_TIMEOUT -> {
                ErrorMessage(
                    message = "The request took too long to respond. Please check your connection and try again."
                )
            }

            ResponseError.TOO_MANY_REQUESTS -> {
                ErrorMessage(message = "You're sending requests too quickly! Please wait a moment and try again.")

            }

            ResponseError.NULL_VALUE -> {
                ErrorMessage(
                    message = "The API returned null values. Please restart the app and try again. If the issue persists, contact support."
                )
            }

            ResponseError.UNRESOLVED_ADDRESS -> {
                ErrorMessage(message = "It looks like you're offline. Please check your internet connection and try again.")
            }

            ResponseError.UNKNOWN -> {

                ErrorMessage(
                    message = "An unknown error occurred. Please restart the app and try again"
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
        text = "Oops! Something Went Wrong",
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