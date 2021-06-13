package davoodi.mahdi.sievere.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import davoodi.mahdi.sievere.R;
import davoodi.mahdi.sievere.adapters.TracksViewPagerAdapter;
import davoodi.mahdi.sievere.fragments.tracks.TracksAlbumsFragment;
import davoodi.mahdi.sievere.fragments.tracks.TracksAllFragment;
import davoodi.mahdi.sievere.fragments.tracks.TracksArtistsFragment;

public class TracksFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tracks, container, false);

        // Pass the fragments tu adapter.
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new TracksAllFragment());
        fragments.add(new TracksAlbumsFragment());
        fragments.add(new TracksArtistsFragment());

        TracksViewPagerAdapter adapter = new TracksViewPagerAdapter(fragments,
                requireActivity().getSupportFragmentManager(),
                getLifecycle());

        ViewPager2 viewPager = view.findViewById(R.id.tf_view_pager);
        viewPager.setAdapter(adapter);

        return view;
    }
}