package gameLamp;


import gameLamp.Color.Color;
import gameLamp.Shape.Shape;
import gameLamp.Shape.Shapes;

public class GameObject {
    public String name = "";
    public Vector3 position = new Vector3(0,0,0);
    public Vector3 scale = new Vector3(1,1,1);
    public Vector3 rotation = new Vector3(0,0,0);
    public String ImagePath = "";
    public Color color = new Color();
    public Shape s = Shapes.rect;
    public boolean clicked = false;
    public boolean visable = true;
    float right = position.x * scale.x;
    float left = position.x / scale.x;
    float top = position.y * scale.y;
    float bottom = position.y / scale.y;
    float back = position.z * scale.z;
    float front = position.z / scale.z;

}
