package leadventeur;

import java.util.List;

public record GameScene
(
    String id,
    String title,
    String text,
    List<SceneOption> options
) 
{
    public GameScene {
        options = List.copyOf(options);
    }
}
