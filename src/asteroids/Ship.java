package asteroids;

import processing.core.*;
import java.util.ArrayList;

public class Ship{
	public PApplet papp;
	
	public PVector pos, vel, acc;
	public AABB aabb;
	public float radius;
	public float rot;
	public ArrayList<Particle> particles;
	
	public Ship(PApplet pa, AABB ab) {
		papp = pa;
		aabb = ab;
    pos = new PVector((aabb.min.x+aabb.max.x)/2, (aabb.min.y+aabb.max.y)/2);
    vel = new PVector();
    acc = new PVector();
    radius = 10;
    rot = 0;
    particles = new ArrayList<Particle>();
  }

  public void update(){
    pos.add(vel);
    vel.mult(0.98f);
    edges();
    for(int i=particles.size()-1;i>=0;i--){
      Particle p = particles.get(i);
      p.update();
      p.show();
      if(p.finished()){
        particles.remove(i);
      }
    }
  }

  public void boost(){
    PVector force = PVector.fromAngle(rot);
    force.mult(0.3f);
    vel.add(force);
    PVector v = PVector.add(pos, PVector.fromAngle(rot+PApplet.PI).mult(5));
    particles.add(new Particle(papp, v.x, v.y, rot+PApplet.PI));
  }

  public void render(){
    papp.push();
    papp.translate(pos.x, pos.y);
    papp.rotate(rot);
    papp.fill(0);
    papp.stroke(255);
    papp.triangle(-radius, -radius, -radius, radius, radius*1.2f, 0);
    papp.pop();
  }

  public void edges(){
    pos.x=(pos.x)<aabb.min.x?aabb.max.x-1:(pos.x>aabb.max.x)?aabb.min.x:pos.x;
    pos.y=(pos.y)<aabb.min.y?aabb.max.y-1:(pos.y>aabb.max.y)?aabb.min.y:pos.y;
  }

  public boolean hits(Asteroid asteroid){
    for(Edge s:boundLines()){
      for(Edge a:asteroid.boundLines()) {
        float den = (s.p0.x-s.p1.x)*(a.p0.y-a.p1.y)-(s.p0.y-s.p1.y)*(a.p0.x-a.p1.x);
        float t = ((s.p0.x-a.p0.x)*(a.p0.y-a.p1.y)-(s.p0.y-a.p0.y)*(a.p0.x-a.p1.x))/den;
        float u = ((s.p1.x-s.p0.x)*(s.p0.y-a.p0.y)-(s.p1.y-s.p0.y)*(s.p0.x-a.p0.x))/den;
        if(t>=0.0&&t<=1.0&u>=0.0&&u<=1.0)return true;
      }
    }
    return false;
  }

  public boolean checkCollisions(ArrayList<Asteroid> asts){
    for(Asteroid a:asts){
      if(hits(a))return true;
    }
    return false;
  }

  public ArrayList<Edge> boundLines(){
  	PVector back = PVector.fromAngle(rot).mult(-radius*1.2f).add(pos);//back
    PVector p1 = PVector.fromAngle(rot).mult(radius*1.2f).add(pos);//front
    PVector p2 = PVector.fromAngle(rot+PApplet.PI/2).mult(radius).add(back);//backleft
    PVector p3 = PVector.fromAngle(rot-PApplet.PI/2).mult(radius).add(back);//backright
    ArrayList<Edge> es = new ArrayList<Edge>();
    es.add(new Edge(p1, p2));
    es.add(new Edge(p2, p3));
    es.add(new Edge(p3, p1));
    return es;
  }

  public void turn(float angle){
    rot += angle;
  }
}
