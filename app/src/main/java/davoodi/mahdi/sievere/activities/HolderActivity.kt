package davoodi.mahdi.sievere.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import davoodi.mahdi.sievere.R
import davoodi.mahdi.sievere.adapters.TracksAllAdapter
import davoodi.mahdi.sievere.components.Holder
import davoodi.mahdi.sievere.components.Track
import davoodi.mahdi.sievere.data.DataLoader
import davoodi.mahdi.sievere.data.SiQueue
import davoodi.mahdi.sievere.databinding.ActivityHolderBinding
import davoodi.mahdi.sievere.preferences.Finals

class HolderActivity : AppCompatActivity(), TracksAllAdapter.OnTrackListener {
    private var tracks = ArrayList<Track>()
    private lateinit var ui: ActivityHolderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityHolderBinding.inflate(layoutInflater)
        setContentView(ui.root)
        if (SiQueue.holder != null)
            setUpUI(SiQueue.holder)

    }

    private fun findTracks(indices: IntArray): ArrayList<Track> {
        val array = ArrayList<Track>()
        if (DataLoader.tracks.size >= indices.size)
            for (index in indices) array += DataLoader.tracks[index]
        return array
    }

    private fun setUpUI(holder: Holder) {
        if (holder.cover != null)
            ui.haImage.setImageBitmap(holder.cover)

        ui.haNameTv.text = getString(R.string.italicText, holder.name ?: " ")

        if (holder.tracks != null && DataLoader.tracks.isNotEmpty()) {
            tracks = findTracks(holder.tracks)
            ui.haTracksNumberTv.text = if (tracks.size <= 1)
                getString(R.string.tf_artists_track, tracks.size.toString())
            else
                getString(R.string.tf_artists_tracks, tracks.size.toString())
            ui.haTracksList.layoutManager = LinearLayoutManager(this)
            ui.haTracksList.adapter = TracksAllAdapter(this, tracks, this)
            ui.haTracksList.setHasFixedSize(true)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right)
    }

    fun back(view: View) {
        assert(view.id == R.id.ha_back_button)
        onBackPressed()
    }

    override fun onTrackClick(position: Int) {
        if (tracks.isNotEmpty()) {
            SiQueue.position = position
            SiQueue.setQueue(tracks)
            SiQueue.isOnHolder = true
            startActivity(
                Intent(this, NowPlayingActivity::class.java)
                    .putExtra(Finals.PLAY, true)
            )
        }
    }
}