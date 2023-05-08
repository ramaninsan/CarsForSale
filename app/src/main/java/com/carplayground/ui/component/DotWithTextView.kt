package com.carplayground.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.carplayground.utils.AppOrangeColor

@Composable
fun DotWithTextView(value : String) {
    Row(modifier = Modifier.padding(start = 10.dp),
        verticalAlignment = Alignment.CenterVertically){
        Box(
            Modifier
                .size(10.dp)
                .background(Color(AppOrangeColor),shape = RoundedCornerShape(5.dp))
                .padding(end = 10.dp)){}
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = value)
    }
}

