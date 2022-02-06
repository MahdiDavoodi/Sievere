package davoodi.mahdi.sievere.fragments.tracks;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import davoodi.mahdi.sievere.R;
import davoodi.mahdi.sievere.adapters.TracksAlbumsAdapter;
import davoodi.mahdi.sievere.components.Album;
import davoodi.mahdi.sievere.data.DataLoader;

public class TracksAlbumsFragment extends Fragment implements TracksAlbumsAdapter.OnAlbumListener {

    static RecyclerView list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tracks_albums, container, false);
        list = view.findViewById(R.id.tf_albums_list);
        Handler handler = new Handler(requireActivity().getMainLooper());
        Runnable runnable = this::showTheList;
        handler.postDelayed(runnable, 500);
        return view;
    }

    public void showTheList() {
        if (getActivity() != null) {
            ArrayList<Album> albums;
            if (DataLoader.albums.isEmpty())
                albums = DataLoader.INSTANCE.updateAlbums(getActivity());
            else albums = DataLoader.albums;
            list.setLayoutManager(new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false));
            list.setAdapter(new TracksAlbumsAdapter(getActivity(), albums, this));
            list.setHasFixedSize(true);
        }
    }

    @Override
    public void OnAlbumClick(int position) {
        // TODO
    }
}