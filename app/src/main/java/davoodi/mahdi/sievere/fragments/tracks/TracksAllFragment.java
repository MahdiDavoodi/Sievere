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
import davoodi.mahdi.sievere.adapters.TAFLinearListAdapter;
import davoodi.mahdi.sievere.components.Track;
import davoodi.mahdi.sievere.data.DataLoader;


public class TracksAllFragment extends Fragment implements TAFLinearListAdapter.OnTrackListener {

    static RecyclerView list;
    private static TracksAllFragment instance;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tracks_all, container, false);
        list = view.findViewById(R.id.tf_all_list);
        instance = this;

        // Get the data ready for list and pass to it.
        if (DataLoader.isAllReady)
            showTheList();
        else
            new Thread(() -> DataLoader.allTracksList(getActivity(),
                    null, null, null, null)).start();
        //
        return view;
    }

    public static TracksAllFragment getInstance() {
        return instance;
    }

    public void showTheList() {
        if (getActivity() != null && DataLoader.tracks.size() != 0) {
            ArrayList<Track> tracks = DataLoader.tracks;
            list.setLayoutManager(new LinearLayoutManager(getActivity()));
            list.setAdapter(new TAFLinearListAdapter(getActivity(), tracks, this));
            list.setHasFixedSize(true);
        }
    }

    @Override
    public void onTrackClick(int position) {
        Track nowPlaying;
        if (DataLoader.tracks != null && DataLoader.tracks.size() > 0)
            nowPlaying = DataLoader.tracks.get(position);
        startActivity(new Intent(getActivity(), NowPlayingActivity.class));
    }
}