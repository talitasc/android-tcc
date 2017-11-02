package com.example.talit.projetotcc.adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.talit.projetotcc.R;
import com.example.talit.projetotcc.fragments.TabDestaques;
import com.example.talit.projetotcc.logicalView.Marca;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * Created by talit on 30/10/2017.
 */

public class MarcaAdapter  extends RecyclerView.Adapter<MarcaAdapter.MarcaAdapterAdapterHolder>{

    public static class MarcaAdapterAdapterHolder extends RecyclerView.ViewHolder {
        private TextView nomeMarca;
        private ImageView imMarca;
        private View view;

        public MarcaAdapterAdapterHolder(View v) {
            super(v);
            nomeMarca = (TextView) v.findViewById(R.id.txt_itens);
            imMarca = (ImageView) v.findViewById(R.id.img_itens);
            view = v;
        }
    }

    private Activity act;
    private List<Marca> marcaProd;
    private View v;

    public MarcaAdapter(Activity act, List<Marca> marcaProd){
        this.act = act;
        this.marcaProd = marcaProd;
    }
    @Override
    public MarcaAdapter.MarcaAdapterAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_marcas, parent, false);
        v = itemView;
        return new MarcaAdapter.MarcaAdapterAdapterHolder (itemView);
    }

    @Override
    public void onBindViewHolder(MarcaAdapterAdapterHolder holder, int position) {
        final Marca marca = marcaProd.get(position);
        holder.nomeMarca.setText(marca.getDescricao());
        holder.imMarca.setImageBitmap(convert(marca.getImgbase64()));
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TabDestaques.idSubCateg = null;
                TabDestaques.idCateg = null;
                TabDestaques.idEstab = null;
                TabDestaques.idMarca = marca.getIdMarca()+"";
                ((FragmentActivity)TabDestaques.context).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content, new TabDestaques())
                        .commit();
            }
        });
    }


    @Override
    public int getItemCount() {
        return marcaProd.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    public static Bitmap convert(String base64Str) throws IllegalArgumentException {
        byte[] decodedBytes = Base64.decode(
                base64Str.substring(base64Str.indexOf(",") + 1),
                Base64.DEFAULT
        );
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    public static String convert(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
    }
}
