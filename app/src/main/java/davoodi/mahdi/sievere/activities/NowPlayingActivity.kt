package davoodi.mahdi.sievere.activities

import androidx.appcompat.app.AppCompatActivity
import davoodi.mahdi.sievere.players.SiPlayer
import com.masoudss.lib.WaveformSeekBar
import android.os.Bundle
import davoodi.mahdi.sievere.R
import davoodi.mahdi.sievere.preferences.Finals
import davoodi.mahdi.sievere.data.SiQueue
import androidx.core.content.res.ResourcesCompat
import com.masoudss.lib.SeekBarOnProgressChanged
import android.graphics.Bitmap
import android.media.AudioManager
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import davoodi.mahdi.sievere.components.Track
import davoodi.mahdi.sievere.databinding.ActivityNowPlayingBinding
import davoodi.mahdi.sievere.tools.Utilities
import linc.com.amplituda.Amplituda
import java.lang.IllegalStateException

const val TAG = "NowPlayingActivity"

class NowPlayingActivity : AppCompatActivity() {
    val player: SiPlayer = SiPlayer.getInstance() ?: SiPlayer()
    private lateinit var ui: ActivityNowPlayingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityNowPlayingBinding.inflate(layoutInflater)
        setContentView(ui.root)

        val playSong = if (savedInstanceState == null)
            intent.extras?.getBoolean(Finals.PLAY) ?: false
        else false

        if (SiQueue.isQueueReady()) setUpActivity(playSong, SiQueue.getTrackToPlay())
    }

    private fun setUpActivity(play: Boolean, track: Track) {
        if (play) {
            configMusic()
        } else {
            buildUI(track)
            configIcons()
        }
        ui.npaPauseIb.setOnLongClickListener {
            val newWaveForm =
                Amplituda(this)
                    .processAudio(contentResolver.openInputStream(SiQueue.getTrackToPlay().uri))
                    .get()
                    .amplitudesAsList()
                    .toIntArray()
            if (newWaveForm.isNotEmpty()) {
                SiQueue.defaultSamples = newWaveForm
                ui.npaSb.setSampleFrom(SiQueue.defaultSamples)
            } else {
                Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show()
                Log.e(TAG, "The result of Amplituda is empty.")
                Log.d(TAG, SiQueue.getTrackToPlay().toString())
            }
            true
        }
        ui.npaSb.onProgressChanged = object : SeekBarOnProgressChanged {
            override fun onProgressChanged(
                waveformSeekBar: WaveformSeekBar,
                progress: Float,
                fromUser: Boolean
            ) {
                if (fromUser) player.seekTo(waveformSeekBar.progress.toInt())
            }
        }
        player.setOnCompletionListener {
            if (SiQueue.position == SiQueue.queue.size - 1 && !SiQueue.isOnRepeat) {
                player.pause()
                configIcons()
            } else if (!SiQueue.isOnRepeatOne) {
                SiQueue.updatePosition(1)
                configMusic()
            } else configMusic()
        }
    }

    private fun configMusic() {
        player.playTrack(this, SiQueue.getTrackToPlay())
        buildUI(SiQueue.getTrackToPlay())
        configIcons()
    }

    private fun buildUI(track: Track) {
        val cover: Bitmap? = Utilities.getAlbumArt(this, track.uri)
        if (cover != null)
            ui.npaCoverIv.setImageBitmap(cover)
        else ui.npaCoverIv.setImageDrawable(
            ResourcesCompat.getDrawable(
                resources,
                R.drawable.pic_sample_music_art,
                theme
            )
        )

        var currentPosition = player.currentPosition.toDouble()
        val totalDuration = player.duration.toDouble()

        ui.npaSongTv.text = resources.getString(R.string.italicText, track.title)
        ui.npaArtistTv.text = resources.getString(R.string.italicText, track.artist)
        ui.npaTotalTv.text = getTimes(totalDuration.toLong())
        ui.npaCurrentTv.text = getTimes(currentPosition.toLong())
        ui.npaSb.maxProgress = totalDuration.toFloat()

        ui.npaSb.setSampleFrom(
            if (SiQueue.defaultSamples != null) SiQueue.defaultSamples
            else IntArray(60) { x -> if (x >= 30) 60 - x else x }
        )

        val handler = Handler(Looper.getMainLooper())
        runOnUiThread(object : Runnable {
            override fun run() {
                try {
                    currentPosition = player.currentPosition.toDouble()
                    ui.npaCurrentTv.text = getTimes(currentPosition.toLong())
                    ui.npaSb.progress = currentPosition.toFloat()
                    handler.postDelayed(this, 500)
                } catch (exception: IllegalStateException) {
                    exception.printStackTrace()
                }
            }
        })
    }

    private fun getTimes(value: Long): String {
        val times = player.convertTime(value)
        return if (times[0] > 0) resources.getString(
            R.string.track_time_hour,
            times[0],
            times[1],
            times[2]
        )
        else resources.getString(R.string.track_time_minutes, times[1], times[2])
    }

    private fun configIcons() {
        val icons = listOf(
            ResourcesCompat.getDrawable(resources, R.drawable.ic_play_solid, theme),
            ResourcesCompat.getDrawable(resources, R.drawable.ic_pause_solid, theme),
            ResourcesCompat.getDrawable(resources, R.drawable.ic_shuffle_solid, theme),
            ResourcesCompat.getDrawable(resources, R.drawable.ic_shuffle_primary_color, theme),
            ResourcesCompat.getDrawable(resources, R.drawable.ic_repeat_primary_color, theme),
            ResourcesCompat.getDrawable(resources, R.drawable.ic_repeat_one_primary_color, theme),
            ResourcesCompat.getDrawable(resources, R.drawable.ic_repeat_solid, theme)
        )
        ui.npaPauseIb.setImageDrawable(if (player.isPlaying) icons[1] else icons[0])
        ui.npaShuffleIb.setImageDrawable(if (SiQueue.isOnShuffle) icons[3] else icons[2])
        ui.npaRepeatIb.setImageDrawable(
            if (SiQueue.isOnRepeatOne) icons[5]
            else if (SiQueue.isOnRepeat) icons[4]
            else icons[6]
        )
    }

    fun back(view: View) {
        assert(view.id == ui.npaBackIb.id)
        onBackPressed()
        finish()
    }

    fun volume(view: View) {
        assert(view.id == ui.npaVolumeIb.id)
        val audioManager = getSystemService(AUDIO_SERVICE) as AudioManager
        val currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
        audioManager.setStreamVolume(
            AudioManager.STREAM_MUSIC,
            currentVolume,
            AudioManager.FLAG_SHOW_UI
        )
    }

    fun pause(view: View) {
        assert(view.id == ui.npaPauseIb.id)
        if (player.isPlaying)
            player.pause()
        else player.start()
        configIcons()
    }

    fun next(view: View) {
        assert(view.id == ui.npaNextIb.id)
        SiQueue.updatePosition(1)
        configMusic()
    }

    fun previous(view: View) {
        assert(view.id == ui.npaPreviousIb.id)
        SiQueue.updatePosition(-1)
        configMusic()
    }

    fun shuffle(view: View) {
        assert(view.id == ui.npaShuffleIb.id)
        if (SiQueue.isOnShuffle) SiQueue.unShuffle() else SiQueue.shuffle()
        configIcons()
    }

    fun repeat(view: View) {
        assert(view.id == ui.npaRepeatIb.id)
        if (SiQueue.isOnRepeat && !SiQueue.isOnRepeatOne) SiQueue.isOnRepeatOne = true
        else if (SiQueue.isOnRepeatOne) {
            SiQueue.isOnRepeat = false
            SiQueue.isOnRepeatOne = false
        } else SiQueue.isOnRepeat = true
        configIcons()
    }

    fun like(view: View) {
        assert(view.id == ui.npaLikeIb.id)
        // TODO: Implement the database. Version 0.8
    }

    fun options(view: View) = Toast.makeText(this, "Demo v0.4.0", Toast.LENGTH_SHORT).show()
}