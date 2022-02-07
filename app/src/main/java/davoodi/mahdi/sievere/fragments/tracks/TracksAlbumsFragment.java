package davoodi.mahdi.sievere.fragments.tracks;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import davoodi.mahdi.sievere.R;
import davoodi.mahdi.sievere.adapters.TracksAlbumsAdapter;
import davoodi.mahdi.sievere.data.DataLoader;

public class TracksAlbumsFragment extends Fragment implements TracksAlbumsAdapter.OnAlbumListener {

    static RecyclerView list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tracks_albums, container, false);
        list = view.findViewById(R.id.tf_albums_list);
        showTheList();
        return view;
    }

    public void showTheList() {
        if (getActivity() != null && !DataLoader.albums.isEmpty()) {
            list.setLayoutManager(new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false));
            list.setAdapter(new TracksAlbumsAdapter(getActivity(), DataLoader.albums, this));
            list.setHasFixedSize(true);
        }
    }

    @Override
    public void OnAlbumClick(int position) {
        // TODO
    }
}