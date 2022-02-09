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

import davoodi.mahdi.sievere.R;
import davoodi.mahdi.sievere.components.Track;

public class TracksAllAdapter extends RecyclerView.Adapter<TracksAllAdapter.ViewHolder> {

    Context context;
    LayoutInflater inflater;
    ArrayList<Track> tracks;
    OnTrackListener listener;

    public TracksAllAdapter(Context context, ArrayList<Track> tracks, OnTrackListener listener) {
        this.context = context;
        this.tracks = tracks;
        this.listener = listener;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_row_item_track, parent, false);
        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Track track = tracks.get(position);

        holder.title.setText(context.getResources().getString(R.string.italicText, track.getTitle()));
        holder.artist.setText(context.getResources().getString(R.string.italicText, track.getArtist()));
    }

    @Override
    public int getItemCount() {
        return tracks.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title, artist;
        ImageView status;
        ImageButton options;
        OnTrackListener listener;

        public ViewHolder(@NonNull @NotNull View itemView, OnTrackListener listener) {
            super(itemView);
            title = itemView.findViewById(R.id.tf_song_title);
            artist = itemView.findViewById(R.id.tf_song_artist);
            status = itemView.findViewById(R.id.tf_song_status_image);
            options = itemView.findViewById(R.id.tf_song_more_option);
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onTrackClick(getAdapterPosition());
        }
    }

    public interface OnTrackListener {
        void onTrackClick(int position);
    }
}
