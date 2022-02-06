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
import davoodi.mahdi.sievere.components.Album;
import davoodi.mahdi.sievere.components.Track;

public class TracksAlbumsAdapter extends RecyclerView.Adapter<TracksAlbumsAdapter.ViewHolder> {

    Context context;
    LayoutInflater inflater;
    ArrayList<Album> albums;
    OnAlbumListener listener;

    public TracksAlbumsAdapter(Context context, ArrayList<Album> albums, OnAlbumListener listener) {
        this.context = context;
        this.albums = albums;
        this.listener = listener;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_row_item_album, parent, false);
        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Album album = albums.get(position);

        holder.name.setText(context.getResources().getString(R.string.italicText, album.getName()));
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name;
        ImageView albumCover;
        OnAlbumListener listener;

        public ViewHolder(@NonNull @NotNull View itemView, OnAlbumListener listener) {
            super(itemView);
            name = itemView.findViewById(R.id.tf_album_name);
            albumCover = itemView.findViewById(R.id.tf_album_cover);
            this.listener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.OnAlbumClick(getAdapterPosition());
        }
    }

    public interface OnAlbumListener {
        void OnAlbumClick(int position);
    }
}
