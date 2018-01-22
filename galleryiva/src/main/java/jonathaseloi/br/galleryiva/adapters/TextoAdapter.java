package jonathaseloi.br.galleryiva.adapters;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Vibrator;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jonathaseloi.br.galleryiva.R;

import static android.view.View.GONE;

public class TextoAdapter extends RecyclerView.Adapter<TextoAdapter.MyViewHolder> {

    private List<Boolean> selected = new ArrayList<>();
    private int oldpos = -1;
    private Vibrator vibe;
    private ItemCaminho itemCaminho;
    private List<String> outropath = new ArrayList<>();

    public void setOutros(List<String> videopath) {
        this.outropath = videopath;
    }

    public void addOutros(List<String> imagespath) {
        for (String path : imagespath) {
            this.outropath.add(path);
            this.selected.add(false);
            notifyItemInserted(getItemCount());
        }
    }

    public void addOutro(String path) {
        this.outropath.add(path);
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

        @Override
        public void onClick(View v) {
            Log.e("TAG","click " + outropath.get(getAdapterPosition()));

            String filename = outropath.get(getAdapterPosition()).substring(outropath.get(getAdapterPosition()).lastIndexOf(".") + 1);

            if(filename.equals("pdf")){
                File file = new File(outropath.get(getAdapterPosition()));
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(file), "application/pdf");
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                Intent intent1 = Intent.createChooser(intent, "Open With");
                try {
                    getContext().startActivity(intent1);
                } catch (ActivityNotFoundException e) {
                    // Instruct the user to install a PDF reader here
                }
            }
        }

        //Seleciona o arquivo de texto
        @Override
        public boolean onLongClick(View view) {
            // Handle long click
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
//                itemCaminho.getItemCaminho(outropath.get(getAdapterPosition()));
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

        holder.tvNomeArquivo.setVisibility(View.VISIBLE);
        holder.ivIconeVideo.setVisibility(View.GONE);

        String outropat = outropath.get(position);
        String filename = outropat.substring(outropat.lastIndexOf("/") + 1);

        Picasso.with(holder.ivImagemArquivo.getContext()).load(R.drawable.ic_file).resize(120, 120).centerCrop().into(holder.ivImagemArquivo);
        if(filename.length() > 24)
            holder.tvNomeArquivo.setText(filename.substring(0, 21) + " ... " + filename.substring(filename.length() - 4));
        else{
            holder.tvNomeArquivo.setText(filename);
        }

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
        return outropath.size();
    }

    public void clear() {
        outropath.clear();
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
