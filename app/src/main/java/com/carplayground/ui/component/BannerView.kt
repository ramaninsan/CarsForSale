package com.carplayground.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.carplayground.R

@Preview
@Composable
fun BannerView() {
    Box(
        Modifier
            .fillMaxWidth()
            .background(Color.Black)
            .height(260.dp)
    ) {

        Image(
            painter = painterResource(id = R.drawable.ic_tacoma), contentDescription = "",
            modifier = Modifier.fillMaxWidth()
        )

        Column(
            Modifier
                .padding(20.dp)
                .align(Alignment.BottomStart)
        ) {
            Text(
                text = stringResource(R.string.tacomo_2021),
                style = TextStyle(
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.White
                )
            )
            Text(
                text = stringResource(R.string.get_yours_now),
                style = TextStyle(
                    fontSize = 20.sp,
                    color = Color.White,
                    fontWeight = FontWeight.W400
                )
            )

        }


    }

}
