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
import com.example.talit.projetotcc.logicalView.Subcategoria;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * Created by talit on 31/10/2017.
 */

public class SubcategoriaAdapter extends RecyclerView.Adapter<SubcategoriaAdapter.SubcategoriadapterAdapterHolder> {

    public static class SubcategoriadapterAdapterHolder extends RecyclerView.ViewHolder {
        private TextView nomeMarca;
        private ImageView imMarca;
        private View view;

        public SubcategoriadapterAdapterHolder(View v) {
            super(v);
            nomeMarca = (TextView) v.findViewById(R.id.txt_itens);
            imMarca = (ImageView) v.findViewById(R.id.img_itens);
            view = v;
        }
    }

    private Activity act;
    private List<Subcategoria> subcategorias;
    private View v;

    public SubcategoriaAdapter(Activity act, List<Subcategoria> subcategorias){
        this.act = act;
        this.subcategorias = subcategorias;
    }
    @Override
    public SubcategoriaAdapter.SubcategoriadapterAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_marcas, parent, false);
        v = itemView;
        return new SubcategoriaAdapter.SubcategoriadapterAdapterHolder (itemView);
    }

    @Override
    public void onBindViewHolder(SubcategoriadapterAdapterHolder holder, int position) {
        final Subcategoria subcategoria = subcategorias.get(position);
        holder.nomeMarca.setText(subcategoria.getSub_categoria_descricao());
        holder.imMarca.setVisibility(View.INVISIBLE);
        //holder.imMarca.setImageBitmap(convert(marca.getImgbase64()));
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TabDestaques.idSubCateg = subcategoria.getSub_categoria_id()+"";
                ((FragmentActivity)TabDestaques.context).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content, new TabDestaques())
                        .commit();
            }
        });
    }


    @Override
    public int getItemCount() {
        return subcategorias.size();
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
