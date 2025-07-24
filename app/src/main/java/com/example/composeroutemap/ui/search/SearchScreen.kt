package com.example.composeroutemap.ui.search

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.composeroutemap.data.Dimens
import com.example.composeroutemap.data.FontSize
import com.example.composeroutemap.data.Weights
import com.example.composeroutemap.ui.customwidget.NextButton
import com.example.composeroutemap.ui.customwidget.ScrollColumnWithEdgeLine
import com.example.composeroutemap.ui.customwidget.rememberHapticClick
import com.example.composeroutemap.ui.navigation.Screen
import com.example.composeroutemap.ui.theme.gray_500
import com.example.composeroutemap.ui.theme.gray_700
import com.example.composeroutemap.ui.theme.gray_800


@Composable
fun SearchScreen(navController: NavController, viewModel: SearchViewModel) {
    val uiPlaces by viewModel.ui.collectAsState()
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars),
    ) {
        MainContent(context, uiPlaces, navController, viewModel, viewModel::removePlace)
    }
}

@Composable
fun MainContent(
    context: Context,
    places: List<PlaceUiModel>,
    navController: NavController,
    viewModel: SearchViewModel,
    onDelete: (PlaceUiModel) -> Unit
) {
    Column {
        TitleView("최적의 경로를 알려드릴게요!")
        SubTitleView("장소를 추가해 주세요.")
        Spacer(Modifier.height(Dimens.LargePadding))
        ScrollColumnWithEdgeLine(
            modifier = Modifier.weight(Weights.Fill),
            contentPadding = PaddingValues(horizontal = Dimens.NormalPadding)
        ) {
            PlaceContent(
                context = context,
                places = places,
                navController = navController,
                onDelete = onDelete
            )
        }
        NextButton(
            onClick = { onClickNextButton(navController, viewModel) },
            context = context,
            modifier = Modifier.padding(
                start = Dimens.NormalPadding,
                end = Dimens.NormalPadding,
                top = Dimens.NormalPadding,
                bottom = Dimens.LargePadding
            ),
            enable = places.isNotEmpty(),
            disableHint = "장소를 한개 이상 추가해 주세요!"
        )
    }
}

@Composable
fun TitleView(text: String) {
    Text(
        modifier = Modifier
            .padding(
                top = Dimens.LargePadding,
                start = Dimens.LargePadding,
                end = Dimens.LargePadding,
            ),
        text = text,
        style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.SemiBold),
        color = Color.Black,
    )
}

@Composable
fun SubTitleView(text: String) {
    Text(
        modifier = Modifier
            .padding(
                top = Dimens.SmallPadding,
                start = Dimens.LargePadding
            ),
        text = text,
        style = MaterialTheme.typography.bodyLarge.copy(fontSize = FontSize.SemiSmallFontSize),
        color = gray_800,
    )
}

@Composable
fun PlaceContent(
    context: Context,
    places: List<PlaceUiModel>,
    navController: NavController,
    onDelete: (PlaceUiModel) -> Unit
) {
    AddedPlaceListView(
        places,
        modifier = Modifier.padding(horizontal = Dimens.NormalPadding),
        onDelete = onDelete
    )
    NextButton(
        onClick = { onClickAddButton(navController) },
        modifier = Modifier.padding(
            horizontal = Dimens.NormalPadding,
            vertical = Dimens.LargePadding
        ),
        text = "추가하기",
        context = context,
        enable = places.size < 10,
        disableHint = "장소는 최대 10개까지만 추가할 수 있습니다."
    )
}

@Composable
fun AddedPlaceListView(
    places: List<PlaceUiModel>,
    modifier: Modifier = Modifier,
    onDelete: (PlaceUiModel) -> Unit
) {
    if (places.isEmpty()) {
        Text(
            "추가한 장소가 없습니다...",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.SemiBold),
            modifier = modifier.padding(start = Dimens.NormalPadding),
            color = gray_500
        )
    } else {

        places.forEach { place ->
            PlaceItem(
                place = place,
                onDelete = rememberHapticClick({ onDelete(place) })
            )
        }
    }
}

@Composable
private fun PlaceItem(place: PlaceUiModel, onDelete: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.NormalPadding, vertical = Dimens.SmallPadding)
    ) {
        Column(modifier = Modifier.weight(Weights.Fill)) {
            Text(place.name, style = MaterialTheme.typography.bodyLarge)
            Spacer(Modifier.height(Dimens.SmallSmallPadding))
            Text(
                place.address,
                style = MaterialTheme.typography.bodyMedium,
                color = gray_700
            )
        }
        IconButton(onClick = onDelete) {
            Icon(Icons.Default.Delete, contentDescription = "Delete Place")
        }
    }
}

private fun onClickAddButton(navController: NavController) {
    navController.navigate(Screen.PlaceSearch.route)
}

private fun onClickNextButton(navController: NavController, viewModel: SearchViewModel) {
    viewModel.calculateOptimalRoute()
    navController.navigateUp()
}


@Preview(showBackground = true)
@Composable
private fun SearchScreenPreview() {
    SearchScreen(
        navController = rememberNavController(),
        viewModel = remember { SearchViewModel() }
    )
}