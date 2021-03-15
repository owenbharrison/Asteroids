package asteroids;

import processing.core.*;

public class AABB{
	public PVector min, max;
  
  public AABB(float x1, float y1, float x2, float y2){
    min = new PVector(x1, y1);
    max = new PVector(x2, y2);
  }
  
  public boolean inside(float x, float y) {
  	return (x>min.x&&x<max.x&&y>min.y&&y<max.y);
  }
}
