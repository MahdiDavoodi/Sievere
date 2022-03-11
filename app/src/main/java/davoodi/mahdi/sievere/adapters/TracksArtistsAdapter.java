package davoodi.mahdi.sievere.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

import davoodi.mahdi.sievere.R;
import davoodi.mahdi.sievere.components.Artist;

public class TracksArtistsAdapter extends RecyclerView.Adapter<TracksArtistsAdapter.ViewHolder> {

    Context context;
    LayoutInflater inflater;
    ArrayList<Artist> artists;
    OnArtistListener listener;

    public TracksArtistsAdapter(Context context, ArrayList<Artist> artists, OnArtistListener listener) {
        this.context = context;
        this.artists = artists;
        this.listener = listener;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_row_item_artist, parent, false);
        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Artist artist = artists.get(position);
        holder.name.setText(context.getResources().getString(R.string.italicText, artist.getName()));

        int size = Objects.requireNonNull(artist.getTracks()).length;
        if (size <= 1)
            holder.tracks.setText(context.getResources().getString(R.string.tf_artists_track, String.valueOf(size)));
        else
            holder.tracks.setText(context.getResources().getString(R.string.tf_artists_tracks, String.valueOf(size)));
    }

    @Override
    public int getItemCount() {
        return artists.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name, tracks;
        ImageView image;
        ImageButton options;
        OnArtistListener listener;

        public ViewHolder(@NonNull @NotNull View itemView, OnArtistListener listener) {
            super(itemView);
            name = itemView.findViewById(R.id.tf_artist_name);
            tracks = itemView.findViewById(R.id.tf_artist_tracks);
            image = itemView.findViewById(R.id.tf_artist_icon);
            //options = itemView.findViewById(R.id.tf_artist_more_option);
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onArtistClick(getAdapterPosition());
        }
    }

    public interface OnArtistListener {
        void onArtistClick(int position);
    }
}
