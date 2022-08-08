package com.postliu.calculator.ui.home

import android.content.res.Configuration
import android.graphics.Typeface
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.postliu.calculator.R
import com.postliu.calculator.model.KeyEvent
import com.postliu.calculator.model.decodedKeyName
import com.postliu.calculator.model.defaultKeyEvent
import com.postliu.calculator.model.numberInKey
import kotlin.collections.contains


@Composable
fun HomeScreen(viewModel: MainViewModel = hiltViewModel()) {
    val assetsManager = LocalContext.current.assets
    val orientation = viewModel.viewState.orientation
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding(),
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = { Text(text = "简易计算器") })
        },
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
        ) {
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Column(
                        modifier = Modifier.weight(1f), verticalArrangement = Arrangement.Bottom
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
                            items(defaultKeyEvent.filterNot { it is KeyEvent.ZERO || it is KeyEvent.PLUS }) {
                                Button(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .aspectRatio(1f),
                                    onClick = {
                                        buttonClick(it, viewModel)
                                    },
                                    colors = ButtonDefaults.buttonColors(backgroundColor = if (it is KeyEvent.EQUALS) Color.Green else if (it is KeyEvent.DEL) Color.Red else MaterialTheme.colors.primary)
                                ) {
                                    Text(
                                        text = if (it is KeyEvent.Orientation) stringResource(id = R.string.orientation_str) else decodedKeyName(
                                            it
                                        ),
                                        maxLines = 1,
                                        fontFamily = FontFamily(
                                            Typeface.createFromAsset(
                                                assetsManager,
                                                "iconfont.ttf"
                                            )
                                        ),
                                        fontSize = if (it is KeyEvent.Orientation) 24.sp else 14.sp
                                    )
                                }
                            }
                            item(span = { GridItemSpan(3) }) {
                                Button(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(80.dp),
                                    onClick = {
                                        buttonClick(KeyEvent.ZERO(), viewModel)
                                    },
                                ) {
                                    Text(
                                        text = decodedKeyName(KeyEvent.ZERO()),
                                        maxLines = 1,
                                        fontSize = 14.sp
                                    )
                                }
                            }
                            item(span = { GridItemSpan(1) }) {
                                Button(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(80.dp),
                                    onClick = {
                                        buttonClick(KeyEvent.PLUS(), viewModel)
                                    },
                                ) {
                                    Text(
                                        text = decodedKeyName(KeyEvent.PLUS()),
                                        maxLines = 1,
                                        fontSize = 14.sp
                                    )
                                }
                            }
                        },
                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                        verticalArrangement = Arrangement.spacedBy(5.dp),
                        contentPadding = PaddingValues(5.dp)
                    )
                }
            } else {
                Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.Top) {
                    Column(
                        modifier = Modifier.weight(1f), verticalArrangement = Arrangement.Top
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp, horizontal = 6.dp),
                            contentAlignment = Alignment.TopEnd
                        ) {
                            Text(
                                text = viewModel.viewState.inputNumber,
                                fontSize = 50.sp,
                                fontWeight = FontWeight.W900
                            )
                        }
                    }
                    LazyVerticalGrid(
                        modifier = Modifier.weight(3f),
                        columns = GridCells.Fixed(4),
                        userScrollEnabled = false,
                        content = {
                            items(defaultKeyEvent.filterNot { it is KeyEvent.ZERO || it is KeyEvent.PLUS }) {
                                Button(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    onClick = {
                                        buttonClick(it, viewModel)
                                    },
                                    colors = ButtonDefaults.buttonColors(backgroundColor = if (it is KeyEvent.EQUALS) Color.Green else if (it is KeyEvent.DEL) Color.Red else MaterialTheme.colors.primary)
                                ) {
                                    Text(
                                        text = if (it is KeyEvent.Orientation) stringResource(id = R.string.orientation_str) else decodedKeyName(
                                            it
                                        ),
                                        maxLines = 1,
                                        fontFamily = FontFamily(
                                            Typeface.createFromAsset(
                                                assetsManager,
                                                "iconfont.ttf"
                                            )
                                        ),
                                        fontSize = if (it is KeyEvent.Orientation) 24.sp else 14.sp
                                    )
                                }
                            }
                            item(span = { GridItemSpan(3) }) {
                                Button(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    onClick = {
                                        buttonClick(KeyEvent.ZERO(), viewModel)
                                    },
                                ) {
                                    Text(
                                        text = decodedKeyName(KeyEvent.ZERO()),
                                        maxLines = 1,
                                        fontSize = 14.sp
                                    )
                                }
                            }
                            item(span = { GridItemSpan(1) }) {
                                Button(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    onClick = {
                                        buttonClick(KeyEvent.PLUS(), viewModel)
                                    },
                                ) {
                                    Text(
                                        text = decodedKeyName(KeyEvent.PLUS()),
                                        maxLines = 1,
                                        fontSize = 14.sp
                                    )
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
}

private fun buttonClick(
    it: KeyEvent,
    viewModel: MainViewModel
) {
    when (it) {
        is KeyEvent.Orientation -> {
            viewModel.dispatch(MainViewAction.Orientation)
        }
        is KeyEvent.C -> {
            viewModel.dispatch(MainViewAction.ClearAll)
        }
        in numberInKey -> {
            viewModel.dispatch(MainViewAction.InputNumber(it))
        }
        is KeyEvent.DEL -> {
            viewModel.dispatch(MainViewAction.DeleteLast)
        }
        is KeyEvent.PLUS -> {
            viewModel.dispatch(MainViewAction.Plus)
        }
        is KeyEvent.MINUS -> {
            viewModel.dispatch(MainViewAction.Minus)
        }
        is KeyEvent.TIMES -> {
            viewModel.dispatch(MainViewAction.Times)
        }
        is KeyEvent.DIV -> {
            viewModel.dispatch(MainViewAction.Div)
        }
        is KeyEvent.EQUALS -> {
            viewModel.dispatch(MainViewAction.Equals)
        }
        else -> {

        }
    }
}