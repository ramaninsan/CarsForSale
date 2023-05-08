package com.carplayground.ui.cars

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.viewModels
import com.carplayground.R
import com.carplayground.dataSource.filesystem.FetchDataFromFile
import com.carplayground.model.CarsInfoModel
import com.carplayground.room.tables.Car
import com.carplayground.ui.component.BannerView
import com.carplayground.ui.component.BoxWithDropdowns
import com.carplayground.ui.component.DividerViewColor
import com.carplayground.ui.component.DotWithTextView
import com.carplayground.utils.AppOrangeColor
import com.carplayground.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class CarListFragment : Fragment() {
    private val myViewModel: CarViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        ViewCompositionStrategy.DisposeOnLifecycleDestroyed(viewLifecycleOwner)
        return ComposeView(requireContext()).apply {
            setContent {
                InitializeView()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeView()
    }

    private fun subscribeView() {
        // If user coming to app first time than fetch data from file and save it to local
        myViewModel.isDataNeedToDownload.observe(viewLifecycleOwner) {
            if (it) {
                myViewModel.saveDb(FetchDataFromFile.fetchAndSaveDataToLocalDb(requireContext()))
            }
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun InitializeView() {
        val list = myViewModel.carDetailsList.observeAsState().value
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(onClick = { myViewModel.resetFilter() }) {
                    Text(modifier = Modifier.padding(16.dp), text = stringResource(R.string.reset))
                }
            }

        ) { padding ->
            Box(Modifier.padding(padding)) {
                list?.let { carList ->
                    Column(Modifier.verticalScroll(rememberScrollState())) {
                        BannerView()
                        BoxWithDropdowns(carList) {
                            myViewModel.filterCarList(it)
                        }
                        FilteredListView()
                    }
                }
            }
        }
    }

    /**
     *   Car Info ListView
     * */
    @Composable
    fun FilteredListView() {
        val filteredList = myViewModel.carDetailsFilteredList.observeAsState().value
        filteredList?.let { carList ->
            repeat(carList.size) {
                CarItemView(carItem = carList[it])

            }
        }
    }


    /**
     *  Single Car Info item
     * */
    @Composable
    fun CarItemView(carItem: CarsInfoModel) {
        Column {
            Box(Modifier.padding(16.dp).background(Color(0xF0D1D1D1)).fillMaxWidth()
                .clickable { myViewModel.expandSelectedCarInfo(carItem) })
            {
                Column(Modifier.padding(16.dp)) {
                    CollapseItemView(carItem)
                    Spacer(modifier = Modifier.height(20.dp))
                    if (carItem.isExpanded) {
                        ExpandedProsAndCons(carItem = carItem.carInfo)
                    }
                }
            }
            DividerViewColor()
        }
    }


    @Composable
    fun CollapseItemView(carItem: CarsInfoModel) {
        Row {
            Image(
                painter = painterResource(id = Utils.getCarImageByModel(carID = carItem.carInfo.make)),
                contentDescription = ""
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = carItem.carInfo.make,
                    style = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.W600)
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = stringResource(id = R.string.price).plus(
                        Utils.formatNumberToReadableAmount(carItem.carInfo.customerPrice.toInt())
                    )
                )
                Spacer(modifier = Modifier.height(2.dp))
                Row {
                    repeat(carItem.carInfo.rating) {
                        Icon(
                            Icons.Default.Star, "", tint = Color(
                                AppOrangeColor
                            )
                        )
                    }
                }

            }

        }
    }


    /**
     *  Dynamic View for
     *  Pros and Cons
     * */
    @Composable
    fun ExpandedProsAndCons(carItem: Car) {
        val cons = carItem.consList.filter { it.isNotEmpty() }
        val pros = carItem.prosList.filter { it.isNotEmpty() }
        Column {
            if (pros.isNotEmpty()) {
                PreFixTextView(text = stringResource(R.string.pros))
                Spacer(modifier = Modifier.height(20.dp))
                repeat(pros.size) {
                    DotWithTextView(value = pros[it])
                }
            }
            Spacer(modifier = Modifier.height(20.dp))

            if (cons.isNotEmpty()) {
                PreFixTextView(text = stringResource(R.string.cons))
                Spacer(modifier = Modifier.height(20.dp))
                repeat(cons.size) {
                    DotWithTextView(value = cons[it])
                }
            }

        }

    }


    @Composable
    fun PreFixTextView(text: String) {
        Text(
            text = text, style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.W700, color = Color(0xFF707070)
            )
        )
    }

}
