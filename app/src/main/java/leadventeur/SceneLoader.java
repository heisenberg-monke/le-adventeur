package leadventeur;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SceneLoader 
{
    private String value(String line) {
        return line.substring(line.indexOf('=') + 1).trim();
    }

    public List<GameScene> loadResources(String filename) throws IOException
    {
        var resource = filename.startsWith("/") ? filename.substring(1) : filename;
        var stream = SceneLoader.class.getClassLoader().getResourceAsStream(resource);

        if(stream == null)
            throw new IOException("Resource not found: " + resource);

        String content;

        try(var in = stream) {
            content = new String(in.readAllBytes(), StandardCharsets.UTF_8);
        }

        return load(content);
    }

    private List<GameScene> load(String content) throws IOException
    {
        List<GameScene> result = new ArrayList<>();

        String id = null;
        String title = "";
        String text = "";
        List<SceneOption> options = new ArrayList<>();

        for(String original : content.lines().toList())
        {
            String line = original.trim();

            if(line.isEmpty() || line.startsWith("#"))
                continue;

            if(line.startsWith("scene="))
            {
                if(id != null)
                    throw new IOException("Missing `end` before " + line);

                id = value(line);
                title = id;
                text = "";
                options = new ArrayList<>();
            }

            else if(line.equals("end"))
            {
                if(id == null)
                    throw new IOException("`end` without a scene");

                result.add(new GameScene(id, title, text, options));
                id = null;
            }

            else {
                if(id == null)
                    throw new IOException("Property outside a scene: `" + line + "`");

                if(line.startsWith("title="))
                    title = value(line);

                else if(line.startsWith("text="))
                    text = value(line).replace("\\n", "\n");

                else if(line.startsWith("option="))
                {
                    String parts[] = value(line).split("\\|", -1);

                    if(parts.length != 3)
                        throw new IOException("Expected label|target|action: " + line);

                    options.add(new SceneOption(parts[0], parts[1], parts[2]));
                }

                else
                    throw new IOException("Unknown property: " + line);
            }
        }

        if(id != null)
            throw new IOException("Scene missing `end`: " + id);

        validate(result);

        return result;
    }

    private void validate(List<GameScene> scenes) throws IOException
    {
        Map<String, GameScene> unique = new LinkedHashMap<>();

        for(var scene : scenes)
        {
            if(unique.putIfAbsent(scene.id(), scene) != null)
                throw new IOException("Duplicate scene ID: " + scene.id());
        }

        for(var scene : scenes)
        {
            for(var option : scene.options())
            {
                if(!unique.containsKey(option.target()))
                    throw new IOException("Scene `" + scene.id() + "` points to missing scene `" + option.target() + "`");
            }
        }
    }
}
