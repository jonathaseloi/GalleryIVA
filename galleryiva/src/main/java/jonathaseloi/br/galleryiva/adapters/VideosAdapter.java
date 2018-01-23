package jonathaseloi.br.galleryiva.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Vibrator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import jonathaseloi.br.galleryiva.R;
import jonathaseloi.br.galleryiva.utils.VideoRequestHandlerUtils;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;

public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.MyViewHolder> {

    private List<Boolean> selected = new ArrayList<>();
    private int oldpos = -1;
    private Vibrator vibe;
    private ItemCaminho itemCaminho;
    private List<String> videopath = new ArrayList<>();
    private int numItens = 3;

    public void setVideos(List<String> videopath) {
        this.videopath = videopath;
    }

    public void addVideos(List<String> imagespath) {
        for (String path : imagespath) {
            this.videopath.add(path);
            notifyItemInserted(getItemCount());
        }
    }

    public void addVideo(String path) {
        this.videopath.add(path);
        notifyItemInserted(getItemCount());
    }

    public void redImages(int numItens){
        this.numItens = numItens;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        ImageView ivImagemArquivo;
        ImageView ivIconeVideo;
        TextView tvNomeArquivo;
        Context context;
        ImageView itemselected;

        public MyViewHolder(Context context, View view) {
            super(view);
            this.context = context;
            ivImagemArquivo = (ImageView) view.findViewById(R.id.ivImagemArquivo);
            itemselected = (ImageView) view.findViewById(R.id.image_selected);
            ivIconeVideo = (ImageView) view.findViewById(R.id.ivIconeVideo);
            tvNomeArquivo = (TextView) view.findViewById(R.id.tvNomeArquivo);
            itemView.setClickable(true);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(new File(videopath.get(getAdapterPosition()))), "video/*");
            getContext().startActivity(intent);
        }

        //Seleciona o video
        @Override
        public boolean onLongClick(View view) {

//            FIXME : SHARE
//            if(!view.isSelected()){
//                vibe.vibrate(50);
//
//                if(oldpos!= -1 && oldpos!=getAdapterPosition()){
//                    selected.set(oldpos, false);
//                    notifyItemChanged(oldpos);
//                }
//
//                selected.set(getAdapterPosition(), true);
//                oldpos = getAdapterPosition();
//                itemCaminho.getItemCaminho(videopath.get(getAdapterPosition()));
//            }
//            else{
//                selected.set(getAdapterPosition(), false);
//                oldpos = -1;
//                itemCaminho.EsconderIcone();
//            }
//            notifyItemChanged(getAdapterPosition());

            return true;
        }

        public Context getContext() {
            return context;
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_imagens, null);
        return new MyViewHolder(parent.getContext(), itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        vibe = (Vibrator) holder.itemView.getContext().getSystemService(Context.VIBRATOR_SERVICE);

        String videopat = videopath.get(position);

        VideoRequestHandlerUtils videoRequestHandlerUtils;
        Picasso picassoInstance;

        videoRequestHandlerUtils = new VideoRequestHandlerUtils();
        picassoInstance = new Picasso.Builder(holder.context.getApplicationContext())
                .addRequestHandler(videoRequestHandlerUtils)
                .build();
        holder.ivIconeVideo.setVisibility(View.VISIBLE);
        holder.tvNomeArquivo.setVisibility(GONE);

        picassoInstance.load(videoRequestHandlerUtils.SCHEME_VIDEO+":"+videopat).fit().centerCrop().centerCrop().into(holder.ivImagemArquivo);
        FrameLayout.LayoutParams parms = new FrameLayout.LayoutParams(holder.context.getResources().getDisplayMetrics().widthPixels/numItens,holder.context.getResources().getDisplayMetrics().widthPixels/numItens);
        holder.ivImagemArquivo.setLayoutParams(parms);
        if(selected.size() > 0 && selected.get(position)){
            holder.itemselected.setVisibility(View.VISIBLE);
            holder.itemView.setSelected(true);
        }
        else{
            holder.itemView.setSelected(false);
            holder.itemselected.setVisibility(GONE);
        }
}

    @Override
    public int getItemCount() {
        return videopath.size();
    }

    public void clear() {
        videopath.clear();
        notifyDataSetChanged();
    }

    public void setItemCaminho(ItemCaminho itemCaminho) {
        this.itemCaminho = itemCaminho;
    }

    public interface ItemCaminho {
        void getItemCaminho(String caminho);

        void EsconderIcone();
    }
}
