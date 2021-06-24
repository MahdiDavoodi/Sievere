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

/*Tracks All Fragment Linear recyclerview list adapter.*/
public class TAFLinearListAdapter extends RecyclerView.Adapter<TAFLinearListAdapter.ViewHolder> {

    Context context;
    LayoutInflater inflater;
    ArrayList<Track> tracks;

    public TAFLinearListAdapter(Context context, ArrayList<Track> tracks) {
        this.context = context;
        this.tracks = tracks;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_row_item_track, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Track track = tracks.get(position);

        holder.title.setText(context.getResources().getString(R.string.italicText, track.getTitle()));
        holder.artist.setText(context.getResources().getString(R.string.italicText, track.getArtistName()));
    }

    @Override
    public int getItemCount() {
        return tracks.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, artist;
        ImageView status;
        ImageButton options;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tf_song_title);
            artist = itemView.findViewById(R.id.tf_song_artist);
            status = itemView.findViewById(R.id.tf_song_status_image);
            options = itemView.findViewById(R.id.tf_song_more_option);
        }
    }
}
