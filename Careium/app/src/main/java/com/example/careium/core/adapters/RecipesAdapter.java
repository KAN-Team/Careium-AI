package com.example.careium.core.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.careium.R;

public class RecipesAdapter extends BaseAdapter {

    Context context;
    String[] imagesText;
    int[] images;
    LayoutInflater inflater;

    public RecipesAdapter(Context context, String[] imagesText, int[] images) {
        this.context = context;
        this.imagesText = imagesText;
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(inflater==null)
            inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView == null)
            convertView = inflater.inflate(R.layout.layout_recipe_item,null);

        ImageView imageView = convertView.findViewById(R.id.recipe_image);
        TextView textView = convertView.findViewById(R.id.recipe_text);
        imageView.setImageResource(images[position]);
        textView.setText(imagesText[position]);

        return convertView;
    }
}