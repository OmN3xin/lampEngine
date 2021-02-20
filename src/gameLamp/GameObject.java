package gameLamp;

public class GameObject {
    public String name = "";
    public Vector3 position = new Vector3(0,0,0);
    public Vector3 scale = new Vector3(1,1,1);
    public Color color = new Color();
    float right = position.x * scale.x;
    float left = position.x / scale.x;
    float top = position.y * scale.y;
    float bottom = position.y / scale.y;
    float back = position.z * scale.z;
    float front = position.z / scale.z;

}
