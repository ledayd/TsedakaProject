package com.example.apple.tsedaka;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/**
 * Created by apple on 20/04/2017.
 */

public class Piece {

    private Float Xinit;
    private Float Yinit;
    private Float Valeur;
    private ImageView imageView;
    public int tag;

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public Piece(Float xinit, Float yinit, Float valeur, ImageView imageView) {
        Xinit = xinit;
        Yinit = yinit;
        Valeur = valeur;
        this.imageView = imageView;
    }

    public Piece(Float valeur, ImageView imageView) {
        Valeur = valeur;
        this.imageView = imageView;
    }
    public Piece() {
    }

    public Piece(Float valeur) {
        Valeur = valeur;
    }

    public Float getXinit() {
        return Xinit;
    }

    public void setXinit(Float xinit) {
        Xinit = xinit;
    }

    public Float getYinit() {
        return Yinit;
    }

    public void setYinit(Float yinit) {
        Yinit = yinit;
    }

    public Float getValeur() {
        return Valeur;
    }

    public void setValeur(Float valeur) {
        Valeur = valeur;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public void setValeurNDrawable(float valeur, int drwble){
        this.setValeur(valeur);
        this.getImageView().setImageResource(drwble);

    }
}
