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
import android.media.MediaPlayer
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.graphics.BitmapFactory
import android.media.AudioManager
import android.net.Uri
import android.os.Handler
import android.view.View
import davoodi.mahdi.sievere.components.Track
import kotlinx.android.synthetic.main.activity_now_playing.*
import java.lang.IllegalStateException

class NowPlayingActivity : AppCompatActivity() {
    val player: SiPlayer = SiPlayer.getInstance() ?: SiPlayer()
    var track: Track? = null
    var currentPosition = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_now_playing)
        val playSong = if (savedInstanceState == null)
            intent.extras?.getBoolean(Finals.PLAY) ?: false
        else
            savedInstanceState.getSerializable(Finals.PLAY) as Boolean

        if (SiQueue.isQueueReady()) setUpActivity(playSong)
    }

    private fun setUpActivity(play: Boolean) {
        setTrack()
        if (track != null) {
            if (play) {
                configMusic()
            } else {
                buildUI(track!!)
                configIcons()
            }
        }
        npa_sb!!.onProgressChanged =
            object : SeekBarOnProgressChanged {
                override fun onProgressChanged(
                    waveformSeekBar: WaveformSeekBar,
                    progress: Float,
                    fromUser: Boolean
                ) {
                    currentPosition = waveformSeekBar.progress.toDouble()
                    if (fromUser) player!!.seekTo(currentPosition.toInt())
                }
            }
        player!!.setOnCompletionListener { mediaPlayer: MediaPlayer ->
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
        player!!.playTrack(this, track)
        buildUI(track!!)
        configIcons()
    }

    private fun setTrack() {
        if (SiQueue.isQueueReady()) track = SiQueue.getTrackToPlay()
    }

    private fun buildUI(track: Track) {
        npa_song_tv.text = resources.getString(R.string.italicText, track.title)
        npa_artist_tv.text =
            resources.getString(R.string.italicText, track.getArtistName(this))
        if (getAlbumArt(track.uri) != null) npa_cover_iv.setImageBitmap(getAlbumArt(track.uri)) else npa_cover_iv!!.setImageDrawable(
            ResourcesCompat.getDrawable(
                resources, R.drawable.pic_sample_music_art, theme
            )
        )
        currentPosition = player!!.currentPosition.toDouble()
        val totalDuration = player!!.duration.toDouble()
        npa_total_tv.text = getTimes(totalDuration.toLong())
        npa_current_tv.text = getTimes(currentPosition.toLong())
        npa_sb.maxProgress = totalDuration.toFloat()
        Thread { npa_sb.setSampleFrom(track.path) }.start()
        val handler = Handler()
        runOnUiThread(object : Runnable {
            override fun run() {
                try {
                    currentPosition = player!!.currentPosition.toDouble()
                    npa_current_tv.text = getTimes(currentPosition.toLong())
                    npa_sb.progress = currentPosition.toFloat()
                    handler.postDelayed(this, 500)
                } catch (ed: IllegalStateException) {
                    ed.printStackTrace()
                }
            }
        })
    }

    // REFACTORING

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
        npa_pause_ib.setImageDrawable(if (player.isPlaying) icons[1] else icons[0])
        npa_shuffle_ib.setImageDrawable(if (SiQueue.isOnShuffle) icons[3] else icons[2])
        npa_repeat_ib.setImageDrawable(
            if (SiQueue.isOnRepeatOne) icons[5]
            else if (SiQueue.isOnRepeat) icons[4]
            else icons[6]
        )
    }

    private fun getAlbumArt(uri: Uri): Bitmap? {
        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(this, uri)
        val data = retriever.embeddedPicture
        return if (data != null) BitmapFactory.decodeByteArray(data, 0, data.size) else null
    }

    fun back(view: View) {
        assert(view.id == npa_back_ib.id)
        onBackPressed()
    }

    fun volume(view: View) {
        assert(view.id == npa_volume_ib.id)
        val audioManager = getSystemService(AUDIO_SERVICE) as AudioManager
        val currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
        audioManager.setStreamVolume(
            AudioManager.STREAM_MUSIC,
            currentVolume,
            AudioManager.FLAG_SHOW_UI
        )
    }

    fun pause(view: View) {
        assert(view.id == npa_pause_ib.id)
        if (player.isPlaying)
            player.pause()
        else player.start()
        configIcons()
    }

    fun next(view: View) {
        assert(view.id == npa_next_ib.id)
        SiQueue.updatePosition(1)
        configMusic()
    }

    fun previous(view: View) {
        assert(view.id == npa_previous_ib.id)
        SiQueue.updatePosition(-1)
        configMusic()
    }

    fun shuffle(view: View) {
        assert(view.id == npa_shuffle_ib.id)
        if (SiQueue.isOnShuffle) SiQueue.unShuffle() else SiQueue.shuffle()
        SiQueue.position = SiQueue.findTrackPosition(track)
        configIcons()
    }

    fun repeat(view: View) {
        assert(view.id == npa_repeat_ib.id)
        if (SiQueue.isOnRepeat && !SiQueue.isOnRepeatOne) SiQueue.isOnRepeatOne = true
        else if (SiQueue.isOnRepeatOne) {
            SiQueue.isOnRepeat = false
            SiQueue.isOnRepeatOne = false
        } else SiQueue.isOnRepeat = true
        configIcons()
    }

    fun like(view: View) {
        assert(view.id == npa_like_ib.id)
        // TODO: Implement the database. Version 0.8
    }
}