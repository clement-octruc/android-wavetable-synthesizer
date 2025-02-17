package com.thewolfsound.wavetablesynthesizer

import androidx.annotation.StringRes

enum class Wavetable {
  SINE {
    @StringRes
    override fun toResourceString(): Int {
      return R.string.sine
    }
  };

  @StringRes
  abstract fun toResourceString(): Int
}

interface WavetableSynthesizer {
  suspend fun play()
  suspend fun stop()
  suspend fun isPlaying() : Boolean
  suspend fun setFrequency(frequencyInHz: Float)
  suspend fun setVolume(volumeInDb: Float)
  suspend fun setWavetable(wavetable: Wavetable)
}
