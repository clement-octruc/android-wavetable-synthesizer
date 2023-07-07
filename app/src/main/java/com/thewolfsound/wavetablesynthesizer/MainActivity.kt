package com.thewolfsound.wavetablesynthesizer

import android.Manifest
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VolumeMute
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.thewolfsound.wavetablesynthesizer.ui.theme.WavetableSynthesizerTheme
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext


class MainActivity : ComponentActivity() {

  private val synthesizer = NativeWavetableSynthesizer()
  override fun onCreate(savedInstanceState: Bundle?) {

    super.onCreate(savedInstanceState)
    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO), 0)
    lifecycle.addObserver(synthesizer)
    var isStarted = false;

    setContent {
      WavetableSynthesizerTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
          // pass the ViewModel down the composables' hierarchy
          Button(onClick = {
//              withContext(Dispachers.Main) {
              GlobalScope.launch {
                isStarted = !isStarted
                if(isStarted)
                  synthesizer.startRecord(44100)
                else
                  synthesizer.stopRecord()
              }
//          }
          }) {
            Text("heyy")
          }
        }
      }
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    lifecycle.removeObserver(synthesizer)
  }
}
