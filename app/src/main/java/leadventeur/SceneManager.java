package leadventeur;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class SceneManager 
{
    private final Map<String, GameScene> scenes = new LinkedHashMap<>();
    private final Deque<String> history = new ArrayDeque<>();
    
    private String currentID;
    private Consumer<GameScene> onChange = scene -> {};

    public void register(GameScene scene)
    {
        if(scenes.putIfAbsent(scene.id(), scene) != null)
            throw new IllegalArgumentException("Duplicate scene ID: " + scene.id());
    }

    public GameScene get(String id) {
        return scenes.get(id);
    }

    public GameScene getCurrent() {
        return scenes.get(currentID);
    }

    public String getCurrentID() {
        return currentID;
    }

    public Collection<GameScene> getAll() {
        return List.copyOf(scenes.values());
    }

    public void setOnChange(Consumer<GameScene> callback) {
        onChange = callback;
    }

    public void goTo(String id)
    {
        GameScene next = scenes.get(id);

        if(next == null)
            throw new IllegalArgumentException("Unknown scene: " + id);

        if(id.equals(currentID))
        {
            onChange.accept(next);
            return;
        }

        if(currentID != null)
            history.push(currentID);

        currentID = id;

        onChange.accept(next);
    }

    public void back()
    {
        if(history.isEmpty())
            return;

        currentID = history.pop();
        
        onChange.accept(getCurrent());
    }
}
