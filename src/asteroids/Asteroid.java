package asteroids;

import processing.core.*;
import java.util.ArrayList;

public class Asteroid{
	public PApplet papp;
	
	public PVector pos, vel;
	public float radius;
	public int total;
	public float[] offset;
	public AABB aabb;
	
	public Asteroid(PApplet pa, AABB ab, float x, float y, float r_) {
		papp = pa;
		aabb = ab;
		
		pos = new PVector(x, y);
    vel = PVector.random2D();
    radius = r_;
    total = PApplet.floor(papp.random(5, 15));
    offset = new float[total];
    for(var i=0;i<total;i++){
      offset[i] = papp.random(-radius/2, radius/2);
    }
	}

  public void update(){
    pos.add(vel);
    edges();
  }

  public void render(){
    papp.push();
    papp.translate(pos.x, pos.y);
    papp.noFill();
    papp.stroke(255);
    papp.beginShape();
    for(var i=0;i<total;i++){
      var angle = (PApplet.map(i, 0, total, 0, PApplet.PI*2));
      var p = PVector.fromAngle(angle).mult(radius+offset[i]);
      papp.vertex(p.x, p.y);
    }
    papp.endShape(PApplet.CLOSE);
    papp.pop();
  }

  public Asteroid[] breakUp(){
    Asteroid[] newA = new Asteroid[2];
    newA[0] = new Asteroid(papp, aabb, pos.x, pos.y, radius/1.3f);
    newA[1] = new Asteroid(papp, aabb, pos.x, pos.y, radius/1.3f);
    return newA;
  }

  public ArrayList<Edge> boundLines(){
    ArrayList<Edge> edges = new ArrayList<Edge>();
    PVector prev = null;
    for(var i=0;i<total;i++){
      float angle = (PApplet.map(i, 0, total, 0, PApplet.PI*2));
      PVector current = PVector.fromAngle(angle).mult(radius+offset[i]).add(pos);
      if(prev!=null){
      	edges.add(new Edge(prev, current));
      }
      prev = current;
    }
    edges.add(new Edge(edges.get(0).p0, edges.get(edges.size()-1).p1));
    return edges;
  }

  public void edges(){
    pos.x=(pos.x)<aabb.min.x?aabb.max.x-1:(pos.x>aabb.max.x)?aabb.min.x:pos.x;
    pos.y=(pos.y)<aabb.min.y?aabb.max.y-1:(pos.y>aabb.max.y)?aabb.min.y:pos.y;
  }
}

