package davoodi.mahdi.sievere.activities

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
import kotlinx.android.synthetic.main.activity_holder.*

class HolderActivity : AppCompatActivity(), TracksAllAdapter.OnTrackListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_holder)
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
            ha_image.setImageBitmap(holder.cover)

        ha_name_tv.text = getString(R.string.italicText, holder.name ?: " ")

        if (holder.tracks != null && DataLoader.tracks.isNotEmpty()) {
            val tracks = findTracks(holder.tracks)
            ha_tracks_number_tv.text = if (tracks.size <= 1)
                getString(R.string.tf_artists_track, tracks.size.toString())
            else
                getString(R.string.tf_artists_tracks, tracks.size.toString())
            ha_tracks_list.layoutManager = LinearLayoutManager(this)
            ha_tracks_list.adapter = TracksAllAdapter(this, tracks, this)
            ha_tracks_list.setHasFixedSize(true)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right)
    }

    fun back(view: View) {
        assert(view.id == ha_back_button.id)
        onBackPressed()
    }

    override fun onTrackClick(position: Int) {
        TODO("Not yet implemented")
    }
}