package rna.inputs;

import processing.core.PApplet;

public class Toggle extends Input{
    protected final PApplet p;
    int scale = 80;
    float bigScale, smallScale;

    boolean state = false;

    int colorOn;
    int colorOff;
    int greyOff;

    boolean switchingState = false;
    boolean clicked = false;

    protected Toggle(PApplet p, int x, int y) {
    	super(x, y);
    	this.p = p;

        bigScale = (float) (scale * 1.1);
        smallScale = (float) (scale * 0.8);
        
        colorOn = this.p.color(0, 140, 255);
        colorOff = this.p.color(245, 245, 245);
        greyOff = this.p.color(175, 175, 175);

        inputs.add(this);
    }

    protected Toggle(PApplet p, int x, int y, int scale) {
    	super(x, y);
    	this.p = p;

        this.scale = scale;

        bigScale = (float) (scale * 1.1);
        smallScale = (float) (scale * 0.8);
        
        colorOn = this.p.color(0, 140, 255);
        colorOff = this.p.color(245, 245, 245);
        greyOff = this.p.color(175, 175, 175);

        inputs.add(this);
    }

    protected Toggle(PApplet p, int x, int y, boolean startState) {
    	super(x, y);
    	this.p = p;
        this.state = startState;

        bigScale = (float) (scale * 1.1);
        smallScale = (float) (scale * 0.8);
        
        colorOn = this.p.color(0, 140, 255);
        colorOff = this.p.color(245, 245, 245);
        greyOff = this.p.color(175, 175, 175);

        inputs.add(this);
    }

    protected Toggle(PApplet p, int x, int y, int scale, boolean startState) {
    	super(x, y);
    	this.p = p;
        this.scale = scale;
        this.state = startState;

        bigScale = (float) (scale * 1.1);
        smallScale = (float) (scale * 0.8);
        
        colorOn = this.p.color(0, 140, 255);
        colorOff = this.p.color(245, 245, 245);
        greyOff = this.p.color(175, 175, 175);

        inputs.add(this);
    }

    protected Toggle(PApplet p, int x, int y, int scale, boolean startState, int Color) {
    	super(x, y);
    	this.p = p;
        this.scale = scale;
        this.state = startState;
        this.colorOn = Color;
        //this.colorOff = colorOff;
        
        colorOff = this.p.color(245, 245, 245);
        greyOff = this.p.color(175, 175, 175);

        bigScale = (float) (scale * 1.1);
        smallScale = (float) (scale * 0.8);

        inputs.add(this);
    }
    
    @Override
    protected void primary() {
        mousePressed();

        show();
    }

    private void show() {
        p.push();
        if (state) {
            p.fill(colorOn);
            pill(x, y, bigScale, smallScale, false);
            p.fill(255);
            p.noStroke();
            p.circle(x + smallScale, y, scale);
        } else {
            p.fill(greyOff);
            pill(x, y, bigScale, smallScale, false);
            p.fill(colorOff);
            pill(x, y, scale, smallScale, true);
            p.fill(255);
            p.stroke(greyOff);
            p.strokeWeight(2);
            p.circle(x, y, scale);
        }
        p.pop();
    }

    private void pill(int x, int y, float size, float len, boolean inner) {
        p.push();
        p.noStroke();
        p.push();
        if (inner) {
            p.fill(colorOn);
        }
        p.circle(x, y, size);
        p.pop();
        p.circle(x + len, y, size);
        p.rect(x, y - size / 2, len, size);
        p.pop();
    }

    private void mousePressed() {
        if (p.mousePressed && p.mouseButton == PApplet.LEFT) {
            //PApplet.println(boundCheck(p.mouseX, p.mouseY));
            if (boundCheck(p.mouseX, p.mouseY)) {
                if (!clicked) {
                    state = !state;
                }
            }
            clicked = true;
        } else {
            clicked = false;
        }
    }

    private boolean boundCheck(int x, int y) {
        return (PApplet.dist(x, y, this.x, this.y) <= bigScale / 2 || PApplet.dist(x, y, this.x + smallScale, this.y) <= bigScale / 2 || (x >= this.x && x <= this.x + smallScale && y >= this.y - bigScale / 2 && y <= this.y + bigScale / 2));
    }

    public boolean value() {
        return state;
    }

    public void setValue(boolean newState) {
        state = newState;
    }
}