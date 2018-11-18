package csc472.depaul.edu.metalcrawler.GameComponents;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import csc472.depaul.edu.metalcrawler.GameComponents.CellularAutomata.CellularAutomata;
import csc472.depaul.edu.metalcrawler.MainActivity;
import csc472.depaul.edu.metalcrawler.R;

public class GameManager implements Parcelable {
    // Android Objects
    Context context;
    View view;

    // Map Objects
    CellularAutomata cellAuto;
    int w;
    int h;
    List<Environment> environmentList;
    int currentEnvironment = -1;

    List<List<Sprite>> sprites;
    List<Actor> actors;
    List<Entity> entities;

    //used for keeping track of current score
    int score = 0;

    Player player;
    // Singleton
    private static GameManager instance;

    protected GameManager(Parcel in) {
        w = in.readInt();
        h = in.readInt();
        currentEnvironment = in.readInt();
    }

    public static final Creator<GameManager> CREATOR = new Creator<GameManager>() {
        @Override
        public GameManager createFromParcel(Parcel in) {
            return new GameManager(in);
        }

        @Override
        public GameManager[] newArray(int size) {
            return new GameManager[size];
        }
    };

    private GameManager() {

    }

    public static GameManager Instance()
    {
        if (instance == null) {
            instance = new GameManager();
            instance.environmentList = new ArrayList<Environment>();
            instance.cellAuto = new CellularAutomata();
            instance.sprites = new ArrayList<List<Sprite>>();
            instance.actors = new ArrayList<Actor>();
            instance.entities = new ArrayList<Entity>();
        }

        return instance;
    }

    // GameStart
    public void GameStart(Context context, View view)
    {
        this.context = context;
        this.view = view;
        w = 15;
        h = 15;

        environmentList.add(new Environment(view,w,h));
        currentEnvironment++;


        player =  new Player(view);
        environmentList.get(currentEnvironment).PopulateTiles(cellAuto,w,h);
        // environmentList.get(currentEnvironment).PopulateEnemies(0,null);

        view.invalidate();
    }

    public void GameEnd()
    {
        // TODO
        saveHighScorePreference();
        instance = new GameManager();
        instance.environmentList = new ArrayList<Environment>();
        instance.cellAuto = new CellularAutomata();
        instance.sprites = new ArrayList<List<Sprite>>();
        instance.actors = new ArrayList<Actor>();
    }

    public void PerformTurn()
    {
        for (int i=0; i<actors.size(); i++)
        {
            actors.get(i).Update();
        }
        view.invalidate();
    }

    public void Draw(Canvas canvas)
    {
        for (List<Sprite> sprite : sprites)
        {
            for (Sprite s : sprite) {
                s.draw(canvas);
            }
        }
    }

    public void GenerateNewMap()
    {
        for (int i=0; i< actors.size(); i++)
        {
            if (actors.get(i).GetType() == EntityType.ENEMY)
            {
                actors.get(i).Recycle();
            }
        }

        for (int i=0; i< entities.size(); i++)
        {
            entities.get(i).Recycle();
        }
        environmentList.get(currentEnvironment).PopulateTiles(cellAuto,w,h);
    }


    public Context GetContext()
    {
        return context;
    }

    public View GetView()
    {
        return view;
    }


    public void AddSprite(Sprite sprite)
    {
        while (sprites.size() <= sprite.GetDrawLayer())
        {
            sprites.add(new ArrayList<Sprite>());
        }

        sprites.get(sprite.GetDrawLayer()).add(sprite);
    }

    public void RemoveSprite(Sprite sprite)
    {
        sprites.get(sprite.GetDrawLayer()).remove(sprite);
    }

    public boolean IsValidTile(int x, int y)
    {
        return environmentList.get(currentEnvironment).GetTiles()[x][y] != null;
    }

    public Tile GetTile(int x, int y)
    {
        return environmentList.get(currentEnvironment).GetTile(x,y);
    }

    public void AddActor(Actor actor)
    {
        actors.add(actor);
    }

    public void RemoveActor(Actor actor)
    {
        actors.remove(actor);
    }

    public void SetPlayer(Player player)
    {
        this.player = player;
    }

    public Player GetPlayer()
    {
        return player;
    }

    public Environment GetCurrentEnvironment()
    {
        return environmentList.get(currentEnvironment);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(w);
        dest.writeInt(h);
        dest.writeInt(currentEnvironment);
    }

    public List<Actor> GetActors() {return actors;}

    public void AddEntity(Entity entity)
    {
        entities.add(entity);
    }

    public void RemoveEntity(Entity entity)
    {
        entities.remove(entity);
    }

    public List<Entity> GetEntities()
    {
        return entities;
    }
    public int getScore(){
        return this.score;
    }

    public void addToScore(int num){
        this.score += num;
    }

    public void saveHighScorePreference(){
        SharedPreferences sp = context.getSharedPreferences("HIGH_SCORE", Activity.MODE_PRIVATE);
        if (sp != null){
            //get the saved highscore, default will be 0
            int savedHighScore = sp.getInt("highscore", 0);
            SharedPreferences.Editor editor = sp.edit();
            if (editor != null){
                if (savedHighScore < score) {
                    //put the score in the preferences
                    editor.putInt("highscore", score);
                    editor.commit();

                    try {
                        File sdCard = android.os.Environment.getExternalStorageDirectory();
                        //Create a directory to store the file
                        File dir = new File(sdCard.getAbsolutePath() + "/csc472/MetalCrawler");
                        dir.mkdirs();

                        File file = new File(dir, "/highscore.txt");
                        file.createNewFile();

                        FileOutputStream outputFile = new FileOutputStream(file);
                        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputFile);
                        outputStreamWriter.append(score + "");
                        outputStreamWriter.close();
                        outputFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
