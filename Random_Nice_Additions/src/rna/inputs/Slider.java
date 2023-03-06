package rna.inputs;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

public class Slider extends Input {
    protected final PApplet p;
    int len;
    int hght;
    float min;
    float max;
    float incr;
    float start;
    
    String text = "";
    PVector textOffset = new PVector();
    int textColor;
    int textSize;
    boolean showText;

    float currentVal;
    boolean grabbed = false;

    boolean clicked = false;

    protected Slider(PApplet p, int x, int y, int len, int hght, float min, float max, float incr, float start) {
    	super(x, y);
    	this.p = p;

        this.min = min;
        this.max = max;
        this.incr = incr;
        this.start = start;
        this.len = len;
        this.hght = hght;

        inputs.add(this);

        if (start >= min && start <= max) {
            currentVal = start;
        } else {
            currentVal = min;
        }
    }

    protected Slider(PApplet p, int x, int y, int len, int hght, float min, float max, float incr, float start, boolean showText) {
    	super(x, y);
    	this.p = p;

        this.min = min;
        this.max = max;
        this.incr = incr;
        this.start = start;
        this.len = len;
        this.hght = hght;
        this.showText = showText;

        inputs.add(this);

        if (start >= min && start <= max) {
            currentVal = start;
        } else {
            currentVal = min;
        }
    }

    @Override
    protected void primary() {
        grabCheck();
        showSlider();
    }

    private void showSlider() {
        if (min == max) {
            currentVal = min;
        } else if (start < min && start > max) {
            currentVal = min;
            start = min;
        } else if (grabbed && p.mouseX > x && p.mouseX < x+len) {
            currentVal = roundIncr(PApplet.map(p.mouseX, x, x+len, min, max), incr);
            if (currentVal < PApplet.map(x, x, x+len, min, max)) {
                currentVal = min;
            }
            if (currentVal > PApplet.map(x+len, x, x+len, min, max)) {
                currentVal = max;
            }
        } else if (grabbed && p.mouseX < x) {
            currentVal = min;
        } else if (grabbed && p.mouseX > x+len) {
            currentVal = max;
        }
        p.push();
        p.strokeWeight(hght);
        p.stroke(255);
        p.line(x, y, x+len, y);
        p.stroke(0, 140, 255);
        p.fill(0, 140, 255);
        p.line(x, y, PApplet.map(currentVal, min, max, x, x+len), y);
        p.circle(PApplet.map(currentVal, min, max, x, x+len), y, 15);
        if (text != "") {
            p.fill(textColor);
            p.textSize(textSize);
            p.textAlign(PConstants.CENTER);
            p.text(text, x + len / 2 + textOffset.x, y + textOffset.y);
        }
        p.pop();
    }

    private boolean grabCheck() {
        if (p.mousePressed && p.mouseButton == PConstants.LEFT) {
            if (!clicked) {
                if (PApplet.dist(p.mouseX, p.mouseY, PApplet.map(currentVal, min, max, x, x+len), y) <= 10+hght/2) {
                    //PApplet.print("Grabbed");
                    grabbed = true;
                    return true;
                }
            }
            clicked = true;
        } else {
            grabbed = false;
            clicked = false;
        }

        return false;
    }

    public float value() {
        return currentVal;
    }

    public void setValue(float newVal) {
        currentVal = newVal;
    }

    public void setMin(float newMin) {
        min = newMin;
    }

    public void setMax(float newMax) {
        min = newMax;
    }
    
    public void setText(String newText) {
        text = newText;
      }

    public void setTextOffset(float x, float y) {
        textOffset.x = x;
        textOffset.y = y;
      }
      
    public void editText(String newText, float x, float y, int c, int size) {
    	text = newText;
    	textOffset.x = x;
    	textOffset.y = y;
    	textColor = c;
    	textSize = size;
    }

    float roundIncr(float num, float increment) {
        return PApplet.round(num*(1/increment))/(1/increment);
    }
}