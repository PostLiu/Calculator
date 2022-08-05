package com.postliu.calculator.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.postliu.calculator.model.*


@Composable
fun HomeScreen() {
    val viewModel: MainViewModel = hiltViewModel()
    val scaffoldState = rememberScaffoldState()
    val mainState = rememberMainState(scaffoldState = scaffoldState)
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding(),
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(modifier = Modifier.fillMaxWidth()) {
                IconButton(onClick = { mainState.openDrawer() }) {
                    Icon(imageVector = Icons.Default.Menu, contentDescription = "菜单")
                }
                Text(text = viewModel.viewState.clickCalculator)
                IconButton(onClick = {}) {
                    Icon(imageVector = Icons.Default.Refresh, contentDescription = "切换")
                }
            }
        },
        drawerContent = {
            DrawerContent(
                calculators = viewModel.calculators
            ) {
                viewModel.dispatch(MainViewAction.ClickCalculator(it))
                mainState.closeDrawer()
            }
        },
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
        ) {
            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp, horizontal = 6.dp),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Text(
                            text = viewModel.viewState.inputNumber,
                            fontSize = 50.sp,
                            fontWeight = FontWeight.W900
                        )
                    }
                }
                LazyVerticalGrid(
                    columns = GridCells.Fixed(4),
                    userScrollEnabled = false,
                    content = {
                        items(defaultKeyEvent) {
                            Button(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(1f),
                                onClick = {
                                    when (it) {
                                        is KeyEvent.C -> {
                                            viewModel.dispatch(MainViewAction.ClearAll)
                                        }
                                        in numberInKey -> {
                                            viewModel.dispatch(MainViewAction.InputNumber(it))
                                        }
                                        else -> {

                                        }
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(backgroundColor = if (it is KeyEvent.EQUALS) Color.Green else if (it is KeyEvent.DEL) Color.Red else MaterialTheme.colors.primary)
                            ) {
                                Text(text = decodedKeyName(it), maxLines = 1)
                            }
                        }
                    },
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    verticalArrangement = Arrangement.spacedBy(5.dp),
                    contentPadding = PaddingValues(5.dp)
                )
            }
        }
    }
}

@Composable
private fun DrawerContent(
    calculators: List<Calculator>, clickCalculator: (name: String) -> Unit = {}
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(calculators) {
            val name = when (it) {
                is Calculator.Default -> it.name
                is Calculator.Science -> it.name
            }
            TextButton(
                modifier = Modifier.fillMaxWidth(), onClick = {
                    clickCalculator(name)
                }, colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colors.secondary
                )
            ) {
                Text(text = name)
            }
        }
    }
}