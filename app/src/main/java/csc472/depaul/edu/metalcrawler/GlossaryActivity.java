package csc472.depaul.edu.metalcrawler;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class GlossaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glossary);
        ListView list = findViewById(R.id.list);
        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), getLabels(), getDescriptions());
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_listview, R.id.textView, getGlossary());
        list.setAdapter(customAdapter);
    }

    private String[] getLabels(){
        String[]labels = new String[]{
                "Alchemist:",
                "Bruiser:",
                "Bull:",
                "Class:",
                "Crawler:",
                "Cog: ",
                "DMG:",
                "Door:",
                "Enemy:",
                "Fireball:",
                "Friendly Fire:",
                "Junkie:",
                "Momentum: ",
                "Potion: ",
                "Riposte: ",
                "Strider: ",
                "Waiting:"
        };
        return labels;
    }

    private String[] getDescriptions(){
        String[] descriptions = new String[]{
                "Maniacal enemies that will stop at nothing to throw fireballs at unsuspecting adventurers.",
                "A brute of a man with the a Max Health of 120, and Momentum Scaling of 2. Depressingly low Riposte Scaling of 0.5",
                "Abominations that charge at the player character with relentless vigor. They will continue on a warpath when spotting the character. They foolishly get stuck on walls when charging, use this to your advantage.",
                "Type of character with their own unique attributes.",
                "Standard class with a Riposte Scaling of 1, Momentum Scaling of 1, and Max Health of 100.",
                "An item that increases the Player's score. It is your goal to collect them.",
                "Short for \"Damage\", this is the amount of damage you deal to your opponent.",
                "Further's your crawl through the dungeons depths.",
                "A non-player character that will attack the player.",
                "A hazardous projectile casted by demented Alchemists",
                "Enemies are as crazed as the look, as they have no qualms of attacking their own.",
                "Cannon fodder that will chase you to the ends of the Earth. They are the epitome of clingy.",
                "A mechanic where the Player buffs their attack power by making consecutive moves in the same direction.",
                "An item that heals whoever steps on them.",
                "A damage modifier when you step in the opposite direction of where you were previously going.",
                "A an agile fighter with a high Riposte Scaling of 2. Watchout however, as he is very squishy with a max Health of 80, and Momentum Scaling of 0.5",
                "One may wait for the enemies to move by pressing the middle button. However, they will lose any momentum they accumulated."
        };
        return descriptions;
    }

}
