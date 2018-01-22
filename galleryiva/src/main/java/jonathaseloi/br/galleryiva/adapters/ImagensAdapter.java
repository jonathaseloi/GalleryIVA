package jonathaseloi.br.galleryiva.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jonathaseloi.br.galleryiva.GaleriaImagemActivity;
import jonathaseloi.br.galleryiva.R;

import static android.view.View.GONE;

public class ImagensAdapter extends RecyclerView.Adapter<ImagensAdapter.MyViewHolder> {

    private List<Boolean> selected = new ArrayList<>();
    private int oldpos = -1;
    private Vibrator vibe;
    private ItemCaminho itemCaminho;

    private List<String> imagespath = new ArrayList<>();

    public void setImagens(List<String> imagespath) {
        this.imagespath = imagespath;
    }

    public void addImagens(List<String> imagespath) {
        for (String path : imagespath) {
            this.imagespath.add(path);
            this.selected.add(false);
            notifyItemInserted(getItemCount());
        }
    }

    public void addImagem(String path) {
        this.imagespath.add(path);
        this.selected.add(false);
        notifyItemInserted(getItemCount());
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

        //Abre a imagem em tela cheia
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getContext(), GaleriaImagemActivity.class);
            intent.putExtra("caminho", imagespath.get(getAdapterPosition()));
            getContext().startActivity(intent);
        }

        //Seleciona a imagem
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
//                itemCaminho.getItemCaminho(imagespath.get(getAdapterPosition()));
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

        String imagepath = imagespath.get(position);

        holder.ivIconeVideo.setVisibility(GONE);
        holder.tvNomeArquivo.setVisibility(GONE);

        Picasso.with(holder.ivImagemArquivo.getContext()).load(new File(imagepath)).resize(120, 120).centerCrop().into(holder.ivImagemArquivo);

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
        return imagespath.size();
    }

    public void clear() {
        imagespath.clear();
        notifyDataSetChanged();
    }

    public void setItemCaminho(ItemCaminho itemCaminho){
        this.itemCaminho = itemCaminho;
    }

    public interface ItemCaminho{
        void getItemCaminho(String caminho);
        void EsconderIcone();
    }
}
