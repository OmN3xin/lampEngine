package gameLamp;


import java.util.HashMap;
import java.util.Map;

public class Colors {
    public static final Map<String,Vector3> colors = new HashMap<>();
    static {
        colors.put("red", new Vector3(1,0,0));
        colors.put("green", new Vector3(0,1,0));
        colors.put("blue", new Vector3(0,0,1));
        colors.put("white", new Vector3(255,255,255));
        colors.put("black", new Vector3(0,0,0));
    }
}
