package davoodi.mahdi.sievere.activities


import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.widget.ImageButton
import davoodi.mahdi.sievere.players.SiPlayer
import com.masoudss.lib.WaveformSeekBar
import android.graphics.drawable.Drawable
import android.os.Bundle
import davoodi.mahdi.sievere.R
import davoodi.mahdi.sievere.preferences.Finals
import davoodi.mahdi.sievere.data.SiQueue
import androidx.core.content.res.ResourcesCompat
import com.masoudss.lib.SeekBarOnProgressChanged
import android.media.MediaPlayer
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.graphics.BitmapFactory
import android.media.AudioManager
import android.net.Uri
import android.os.Handler
import android.view.View
import android.widget.ImageView
import davoodi.mahdi.sievere.components.Track
import kotlinx.android.synthetic.main.activity_now_playing.*
import java.lang.IllegalStateException

class NowPlayingActivity : AppCompatActivity() {
    // Attributes
    var title: TextView? = null
    var seekbar_duration: TextView? = null
    var seekbar_position: TextView? = null
    var album_art: ImageView? = null
    var play_pause: ImageButton? = null
    var shuffle: ImageButton? = null
    var repeat: ImageButton? = null
    var siPlayer: SiPlayer? = null
    var track: Track? = null
    var seekBar: WaveformSeekBar? = null
    var current_position = 0.0
    var total_duration = 0.0
    var play_song = false
    private val WAVE_PATTERN = intArrayOf(
        0,
        1,
        1,
        0,
        1,
        1,
        2,
        3,
        4,
        2,
        1,
        0,
        1,
        5,
        4,
        6,
        1,
        2,
        8,
        6,
        4,
        3,
        1,
        1,
        1,
        1,
        2,
        3,
        1,
        5,
        4,
        5,
        2,
        8,
        4,
        1,
        1,
        2,
        1,
        5,
        6,
        4,
        5,
        6,
        8,
        9,
        1,
        2,
        5,
        4,
        5,
        6,
        1,
        2,
        1,
        4,
        5,
        5,
        6,
        5,
        4,
        6,
        8,
        9,
        8,
        7,
        5,
        9,
        8,
        7,
        6,
        4,
        0,
        5,
        1,
        9,
        6,
        4,
        5,
        9,
        8,
        4,
        2,
        3,
        1,
        1,
        1,
        0,
        0
    )
    var ic_repeat_one_primary_color: Drawable? = null
    var ic_play_solid: Drawable? = null
    var ic_pause_solid: Drawable? = null
    var ic_shuffle_solid: Drawable? = null
    var ic_shuffle_primary_color: Drawable? = null
    var ic_repeat_primary_color: Drawable? = null
    var ic_repeat_solid: Drawable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_now_playing)
        play_song = if (savedInstanceState == null) {
            val extras = intent.extras
            extras?.getBoolean(Finals.PLAY) ?: false
        } else {
            savedInstanceState.getSerializable(Finals.PLAY) as Boolean
        }
        setUpUI()
        if (SiQueue.isQueueReady()) setUpActivity(play_song)
    }

    private fun setUpUI() {
        ic_repeat_one_primary_color =
            ResourcesCompat.getDrawable(resources, R.drawable.ic_repeat_one_primary_color, theme)
        ic_play_solid = ResourcesCompat.getDrawable(resources, R.drawable.ic_play_solid, theme)
        ic_pause_solid = ResourcesCompat.getDrawable(resources, R.drawable.ic_pause_solid, theme)
        ic_shuffle_solid =
            ResourcesCompat.getDrawable(resources, R.drawable.ic_shuffle_solid, theme)
        ic_shuffle_primary_color =
            ResourcesCompat.getDrawable(resources, R.drawable.ic_shuffle_primary_color, theme)
        ic_repeat_primary_color =
            ResourcesCompat.getDrawable(resources, R.drawable.ic_repeat_primary_color, theme)
        ic_repeat_solid = ResourcesCompat.getDrawable(resources, R.drawable.ic_repeat_solid, theme)
        title = findViewById(R.id.npa_song_tv)
        seekbar_duration = findViewById(R.id.npa_total_tv)
        seekbar_position = findViewById(R.id.npa_current_tv)
        album_art = findViewById(R.id.npa_cover_tv)
        seekBar = findViewById(R.id.npa_sb)
        play_pause = findViewById(R.id.npa_pause_ib)
        shuffle = findViewById(R.id.npa_shuffle_ib)
        repeat = findViewById(R.id.npa_repeat_ib)
    }

    private fun setUpActivity(play: Boolean) {
        siPlayer = SiPlayer.getInstance()
        if (siPlayer == null) siPlayer = SiPlayer()
        setTrack()
        if (track != null) {
            if (play) {
                configMusic()
            } else {
                buildUI()
                configIcons()
            }
        }
        seekBar!!.onProgressChanged =
            object : SeekBarOnProgressChanged {
                override fun onProgressChanged(
                    waveformSeekBar: WaveformSeekBar,
                    progress: Float,
                    fromUser: Boolean
                ) {
                    current_position = waveformSeekBar.progress.toDouble()
                    if (fromUser) siPlayer!!.seekTo(current_position.toInt())
                }
            }
        siPlayer!!.setOnCompletionListener { mediaPlayer: MediaPlayer ->
            if (SiQueue.position == SiQueue.queue.size - 1 && !SiQueue.isOnRepeat) {
                // Queue finished.
                mediaPlayer.pause()
            } else if (!SiQueue.isOnRepeatOne) {
                SiQueue.updatePosition(1)
                configMusic()
            } else configMusic()
        }
    }

    private fun configMusic() {
        setTrack()
        siPlayer!!.playTrack(this, track)
        buildUI()
        configIcons()
    }

    private fun setTrack() {
        if (SiQueue.isQueueReady()) track = SiQueue.getTrackToPlay()
    }

    private fun configIcons() {
        if (siPlayer != null) {
            if (!siPlayer!!.isPlaying) play_pause!!.setImageDrawable(ic_play_solid) else play_pause!!.setImageDrawable(
                ic_pause_solid
            )
            if (SiQueue.isOnShuffle) shuffle!!.setImageDrawable(ic_shuffle_primary_color) else shuffle!!.setImageDrawable(
                ic_shuffle_solid
            )
            if (SiQueue.isOnRepeatOne) repeat!!.setImageDrawable(ic_repeat_one_primary_color) else if (SiQueue.isOnRepeat) repeat!!.setImageDrawable(
                ic_repeat_primary_color
            ) else repeat!!.setImageDrawable(ic_repeat_solid)
        }
    }

    private fun buildUI() {
        if (track != null) {
            title!!.text = resources.getString(R.string.italicText, track!!.title)
            npa_artist_tv!!.text = resources.getString(R.string.italicText, track!!.getArtistName(this))
            if (getAlbumArt(track!!.uri) != null) album_art!!.setImageBitmap(getAlbumArt(track!!.uri)) else album_art!!.setImageDrawable(
                ResourcesCompat.getDrawable(
                    resources, R.drawable.pic_sample_music_art, theme
                )
            )
            current_position = siPlayer!!.currentPosition.toDouble()
            total_duration = siPlayer!!.duration.toDouble()
            seekbar_duration!!.text = getTimes(total_duration.toLong())
            seekbar_position!!.text = getTimes(current_position.toLong())
            seekBar!!.maxProgress = total_duration.toFloat()
            Thread { seekBar!!.setSampleFrom(track!!.path) }.start()
            val handler = Handler()
            runOnUiThread(object : Runnable {
                override fun run() {
                    try {
                        current_position = siPlayer!!.currentPosition.toDouble()
                        seekbar_position!!.text = getTimes(current_position.toLong())
                        seekBar!!.progress = current_position.toFloat()
                        handler.postDelayed(this, 500)
                    } catch (ed: IllegalStateException) {
                        ed.printStackTrace()
                    }
                }
            })
        }
    }

    private fun getTimes(value: Long): String {
        val times = siPlayer!!.convertTime(value)
        val result: String
        result = if (times[0] > 0) {
            resources.getString(R.string.track_time_hour, times[0], times[1], times[2])
        } else {
            resources.getString(R.string.track_time_minutes, times[1], times[2])
        }
        return result
    }

    private fun getAlbumArt(uri: Uri): Bitmap? {
        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(this, uri)
        val data = retriever.embeddedPicture
        return if (data != null) BitmapFactory.decodeByteArray(data, 0, data.size) else null
    }

    // Buttons
    fun back(view: View?) {
        assert(view?.id == R.id.npa_back_ib)
        onBackPressed()
    }

    fun showSystemVolume(view: View?) {
        assert(view?.id == R.id.npa_volume_ib)
        val audioManager = getSystemService(AUDIO_SERVICE) as AudioManager
        val currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume, AudioManager.FLAG_SHOW_UI)
    }

    fun playPause(view: View?) {
        if (siPlayer != null) {
            if (siPlayer!!.isPlaying) siPlayer!!.pause() else siPlayer!!.start()
            configIcons()
        }
    }

    fun next(view: View?) {
        SiQueue.updatePosition(1)
        configMusic()
    }

    fun previous(view: View?) {
        SiQueue.updatePosition(-1)
        configMusic()
    }

    fun shuffle(view: View?) {
        if (SiQueue.isOnShuffle) {
            SiQueue.unShuffle()
            SiQueue.isOnShuffle = false
        } else {
            SiQueue.shuffle()
            SiQueue.isOnShuffle = true
        }
        SiQueue.position = SiQueue.findTrackPosition(track)
        configIcons()
    }

    fun repeat(view: View?) {
        if (SiQueue.isOnRepeat && !SiQueue.isOnRepeatOne) {
            SiQueue.isOnRepeatOne = true
        } else if (SiQueue.isOnRepeatOne) {
            SiQueue.isOnRepeat = false
            SiQueue.isOnRepeatOne = false
        } else {
            SiQueue.isOnRepeat = true
        }
        configIcons()
    }

    fun favorite(view: View?) {
        // TODO: Implement the database.
    }
}