package com.carplayground.ui.component


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.carplayground.R
import com.carplayground.model.CarsInfoModel

@Composable
fun BoxWithDropdowns(carList: List<CarsInfoModel>, onSelectedFilter: (String) -> Unit) {
    var selectedMake by remember { mutableStateOf("Any Make") }
    var selectedModel by remember { mutableStateOf("Any Model") }

    var makeExpandableState by remember { mutableStateOf(false) }
    var modelExpandableState by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .background(Color.Gray,shape = RoundedCornerShape(5.dp))
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(text = stringResource(R.string.filters), style = TextStyle(color = Color.White, fontSize = 16.sp))
            Spacer(modifier = Modifier.height(10.dp))
            Card(
                Modifier
                    .clickable(onClick = { makeExpandableState = true })
                    .fillMaxWidth()
                    .height(40.dp),
                shape = RoundedCornerShape(5.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Text(
                    text = selectedMake,
                    Modifier.padding(8.dp).align(Alignment.Start).height(40.dp)
                )
                DropdownMenu(
                    expanded = makeExpandableState,
                    onDismissRequest = { makeExpandableState = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    carList.map { it.carInfo.make }.forEachIndexed() { index , it ->
                        DropdownMenuItem(text = { Text(text = it) }, onClick = {
                            selectedMake = it
                            onSelectedFilter.invoke(carList[index].carInfo.model)
                            selectedModel = "Any Model"
                            makeExpandableState = false
                        })
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            Card(
                Modifier
                    .fillMaxWidth()
                    .clickable(onClick = { modelExpandableState = true })
                    .height(40.dp),
                shape = RoundedCornerShape(5.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                DropdownMenu(
                    expanded = modelExpandableState,
                    onDismissRequest = { modelExpandableState = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    carList.filter { it.carInfo.make == selectedMake }.forEach {
                        DropdownMenuItem(text = { Text(text = it.carInfo.model) }, onClick = {
                            selectedModel = it.carInfo.model
                            modelExpandableState = false
                            onSelectedFilter.invoke(selectedModel)
                        })
                    }
                }
                Text(
                    text = selectedModel,
                    Modifier.padding(8.dp).align(Alignment.Start).height(40.dp)
                )

            }
        }
    }
}

