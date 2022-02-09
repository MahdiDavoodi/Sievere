package davoodi.mahdi.sievere.fragments.tracks;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import davoodi.mahdi.sievere.R;
import davoodi.mahdi.sievere.adapters.TracksArtistsAdapter;
import davoodi.mahdi.sievere.data.DataLoader;

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
        // TODO
    }
}