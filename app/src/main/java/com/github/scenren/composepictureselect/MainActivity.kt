package com.github.scenren.composepictureselect

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.github.sceneren.pictureselector.Media
import com.github.sceneren.pictureselector.PictureSelectParams
import com.github.sceneren.pictureselector.compose.rememberPictureSelect
import com.github.scenren.composepictureselect.engine.CoilEngine
import com.github.scenren.composepictureselect.engine.UCropEngine
import com.github.scenren.composepictureselect.ui.theme.ComposePictureSelectTheme
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposePictureSelectTheme {
                val scope = rememberCoroutineScope()
                val pictureSelector = rememberPictureSelect()
                var pictureMedia by remember {
                    mutableStateOf(listOf<Media>())
                }
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column {
                        Greeting(
                            name = "Android",
                            modifier = Modifier
                                .padding(innerPadding)
                                .clickable {
                                    scope.launch {
                                        pictureMedia =
                                            pictureSelector
                                                .selectPhoto(
                                                    params = PictureSelectParams(
                                                        maxImageNum = 1,
                                                        isCrop = true,
                                                        isCompress = true,
                                                        imageEngine = CoilEngine(),
                                                        cropEngine = UCropEngine(1F,1F)
                                                    )
                                                )
                                                .firstOrNull()
                                                ?.getOrNull() ?: listOf()

                                        Log.e("pictureMedia", pictureMedia.toString())

                                    }
                                }
                        )

                        AsyncImage(
                            modifier = Modifier.size(120.dp),
                            model = pictureMedia.firstOrNull()?.path,
                            contentDescription = null,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposePictureSelectTheme {
        Greeting("Android")
    }
}