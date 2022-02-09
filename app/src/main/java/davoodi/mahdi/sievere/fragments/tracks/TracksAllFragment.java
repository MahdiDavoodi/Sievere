package davoodi.mahdi.sievere.fragments.tracks;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import davoodi.mahdi.sievere.R;
import davoodi.mahdi.sievere.activities.NowPlayingActivity;
import davoodi.mahdi.sievere.adapters.TracksAllAdapter;
import davoodi.mahdi.sievere.components.Track;
import davoodi.mahdi.sievere.data.DataLoader;
import davoodi.mahdi.sievere.data.SiQueue;
import davoodi.mahdi.sievere.preferences.Finals;


public class TracksAllFragment extends Fragment implements TracksAllAdapter.OnTrackListener {

    static RecyclerView list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tracks_all, container, false);
        list = view.findViewById(R.id.tf_all_list);
        showTheList();
        return view;
    }

    public void showTheList() {
        if (getActivity() != null && !DataLoader.tracks.isEmpty()) {
            ArrayList<Track> tracks = DataLoader.tracks;
            list.setLayoutManager(new LinearLayoutManager(getActivity()));
            list.setAdapter(new TracksAllAdapter(getActivity(), tracks, this));
            list.setHasFixedSize(true);
        }
    }

    @Override
    public void onTrackClick(int position) {
        if (!DataLoader.tracks.isEmpty()) {

            if (SiQueue.isOnShuffle)
                SiQueue.position = SiQueue.findTrackPosition(SiQueue.initialQueue.get(position));
            else {
                SiQueue.position = position;
                SiQueue.setQueue(DataLoader.tracks);
            }
            startActivity(new Intent(getActivity(), NowPlayingActivity.class)
                    .putExtra(Finals.PLAY, true));
        }
    }
}