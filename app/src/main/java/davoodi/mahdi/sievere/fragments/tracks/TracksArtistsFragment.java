package davoodi.mahdi.sievere.fragments.tracks;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import davoodi.mahdi.sievere.R;
import davoodi.mahdi.sievere.activities.HolderActivity;
import davoodi.mahdi.sievere.adapters.TracksArtistsAdapter;
import davoodi.mahdi.sievere.data.DataLoader;
import davoodi.mahdi.sievere.data.SiQueue;

public class TracksArtistsFragment extends Fragment implements TracksArtistsAdapter.OnArtistListener {

    static RecyclerView list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tracks_artists, container, false);
        list = view.findViewById(R.id.tf_artists_list);
        showTheList();
        return view;
    }

    public void showTheList() {
        if (getActivity() != null && !DataLoader.artists.isEmpty()) {
            list.setLayoutManager(new LinearLayoutManager(getActivity()));
            list.setAdapter(new TracksArtistsAdapter(getActivity(), DataLoader.artists, this));
            list.setHasFixedSize(true);
        }
    }

    @Override
    public void onArtistClick(int position) {
        SiQueue.holder = DataLoader.artists.get(position);
        startActivity(new Intent(getActivity(), HolderActivity.class));
        requireActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }
}