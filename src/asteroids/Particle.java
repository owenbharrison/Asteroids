package asteroids;

import processing.core.*;

public class Particle{
	public PApplet papp;
	
	public PVector pos, vel;
	public int alpha;
	
	public Particle(PApplet pa, float x, float y, float r) {
		papp = pa;
    pos = new PVector(x, y);
    vel = PVector.fromAngle(r+papp.random(-PApplet.PI/6, PApplet.PI/6)).mult(papp.random(1, 2));
    alpha = 255;
	}

	public void update(){
    pos.add(vel);
    alpha -= 5;
  }

  public boolean finished(){
    return (alpha<0);
  }

  public void show(){
    papp.push();
    papp.translate(pos.x, pos.y);
    papp.noStroke();
    papp.fill(255, alpha);
    papp.circle(0, 0, 7);
    papp.pop();
  }
}
