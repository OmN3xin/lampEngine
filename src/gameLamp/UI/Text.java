package gameLamp.UI;


import org.newdawn.slick.TrueTypeFont;

import java.awt.*;

public class Text {
    private Font awtFont;
    private TrueTypeFont font;
    public Text(){
        awtFont = new Font("Times New Roman", Font.BOLD, 24);
        font = new TrueTypeFont(awtFont, false);
    }
    public void Draw(float x, float y, String text){
        font.drawString(x,y,text);
    }
}
