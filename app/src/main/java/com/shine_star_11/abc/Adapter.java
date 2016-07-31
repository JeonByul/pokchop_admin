package com.shine_star_11.abc;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by shine_star_11 on 7/30/16.
 */
public class Adapter extends ArrayAdapter <String>{
    Context c;
    String[] pokemonNames;
    int[] pokemonImages;
    String[] pokemonNum;

    public Adapter(Context ctx, String[] pokemonNames, int[] pokemonImages){
        super(ctx,R.layout.pokemon_dbrow,pokemonNames);
        this.c = ctx;
        this.pokemonNames = pokemonNames;
        this.pokemonImages = pokemonImages;
        pokemonNum = new String[152];
        pokemonNum[0]="";
        for(int i=1; i<152; i++){
            pokemonNum[i] = Integer.toString(i);
        }
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.pokemon_dbrow, null);
        }

        TextView name= (TextView) convertView.findViewById(R.id.pokemon_dbname);
        ImageView img = (ImageView) convertView.findViewById(R.id.pokemon_icon);
        //TextView num = (TextView) convertView.findViewById(R.id.pokemon_num);

        // SET DATA
        name.setText(pokemonNames[position]);
        img.setImageResource(pokemonImages[position]);
        //num.setText(pokemonNum[position]);

        return convertView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.pokemon_dbrow, null);
        }

        TextView name= (TextView) convertView.findViewById(R.id.pokemon_dbname);
        ImageView img = (ImageView) convertView.findViewById(R.id.pokemon_icon);
        //TextView num = (TextView) convertView.findViewById(R.id.pokemon_num);

        // SET DATA
        name.setText(pokemonNames[position]);
        img.setImageResource(pokemonImages[position]);
        //num.setText(pokemonNum[position]);

        return convertView;
    }
}
