package csc472.depaul.edu.metalcrawler.GameComponents;

import android.graphics.Bitmap;

public class Blurb {
    private Bitmap sprite;
    private String text;

    public Blurb()
    {

    }

    public void SetSprite(Bitmap bitmap) {this.sprite = bitmap;}
    public void SetText(String text) {this.text = text;}
    public Bitmap GetSprite(){return sprite;}
    public String GetString() {return text;}

}
