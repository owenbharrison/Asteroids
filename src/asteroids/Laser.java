package asteroids;

import processing.core.*;

public class Laser{
	public PApplet papp;
	
	public PVector pos, vel;
	public AABB aabb;
	
	public Laser(PApplet pa, AABB ab, float x, float y, float rot) {
    papp = pa;
		pos = new PVector(x, y);
    aabb = ab;
    vel = PVector.fromAngle(rot).mult(5);
  }

  public void update(){
    pos.add(vel);
  }

  public void render(){
    papp.push();
    papp.stroke(255);
    papp.strokeWeight(4);
    papp.point(pos.x, pos.y);
    papp.pop();
  }

  public boolean isOffscreen(){
    return !aabb.inside(pos.x, pos.y);
  }

  public boolean hits(Asteroid asteroid){
    return (PApplet.dist(pos.x, pos.y, asteroid.pos.x, asteroid.pos.y)<asteroid.radius);
  }
}

