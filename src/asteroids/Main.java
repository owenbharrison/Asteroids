package asteroids;

import processing.core.*;
import java.util.ArrayList;

public class Main extends PApplet{
	public Ship ship;
	public ArrayList<Asteroid> asteroids;
	public ArrayList<Laser> lasers;
	public AABB BOUNDS;

	public int score = 0;
	public int stage = 0;

	public boolean firing = false;
	public boolean movingForward = false;
	public boolean movingLeft = false;
	public boolean movingRight = false;
	
	public int frameLastShot = 0;
	
	public static void main(String[] args) {
		PApplet.main(new String[] {"asteroids.Main"});
	}
	
	public void settings() {
		fullScreen();
	}

	public void setup(){
		asteroids = new ArrayList<Asteroid>();
		lasers = new ArrayList<Laser>();
		BOUNDS = new AABB(0, 0, width, height);
	  ship = new Ship(this, BOUNDS);
	  addRandomAsteroidOnEdge();
	}
	
	public void keyPressed() {
		if(key==' ') firing = true;
		if(key==CODED) {
			if(keyCode==UP) movingForward = true;
			if(keyCode==LEFT) movingLeft = true;
      if(keyCode==RIGHT) movingRight = true;
		}
	}
	
  public void keyReleased() {
  	if(key==' ') firing = false;
		if(key==CODED) {
			if(keyCode==UP) movingForward = false;
			if(keyCode==LEFT) movingLeft = false;
      if(keyCode==RIGHT) movingRight = false;
		}
	}

	public void draw(){
	  background(0);
	  stroke(4);
	  if(firing&&frameCount-frameLastShot>7){
	    lasers.add(new Laser(this, BOUNDS, ship.pos.x, ship.pos.y, ship.rot));
	    frameLastShot = frameCount;
	  }

	  for(Asteroid a:asteroids) {
	    a.render();
	    a.update();
	  }

	  for(int i=0;i<lasers.size();i++){
	  	Laser l= lasers.get(i);
	    l.render();
	    l.update();
	    for(int j=0;j<asteroids.size();j++){
	    	Asteroid a = asteroids.get(j);	
	      if(l.hits(a)){
	        Asteroid[] newAsteroids = asteroids.get(j).breakUp();
	        if(a.radius>20){
	          asteroids.add(newAsteroids[0]);
	          asteroids.add(newAsteroids[1]);
	          score+=4;
	        }
	        else{
	          score+=24;
	        }
	        asteroids.remove(j);
	        lasers.remove(i);
	        i--;
	        j--;
	        break;
	      }
	    }
	  }
	  for(int i=lasers.size()-1;i>=0;i--){
	    if(lasers.get(i).isOffscreen()){
	      lasers.remove(i);
	    }
	  }
	  if(frameCount%48==0){
	    score+=2;
	  }
	  ship.update();
	  if(ship.checkCollisions(asteroids)){
	    push();
	    textAlign(CENTER, CENTER);
	    textSize(96);
	    stroke(255);
	    strokeWeight(0.5f);
	    noFill();
	    text("you are die", width/2, height/2);
	    pop();
	    noLoop();
	  }

	  ship.turn(movingLeft?-.1f:0);
	  ship.turn(movingRight?.1f:0);
	  if(movingForward)ship.boost();

	  ship.render();

	  push();
	  fill(255);
	  text("stage "+stage, 4, height-16);
	  text("score: "+score, 4, height-6);
	  pop();
	  if(asteroids.size()==0) {
	  	int numToAdd = 0;
	  	switch(stage) {
	  		case 0:
	  			numToAdd = 3;
	  			break;
	  		case 1:
	  			numToAdd = 5;
	  			break;
	  		case 2:
	  			numToAdd = 7;
	  			break;
	  		case 3:
	  			numToAdd = 10;
	  			break;
	  		case 4:
	  			push();
	  			noFill();
	  			stroke(255);
	  	    textSize(102);
	  	    strokeWeight(0.5f);
	  	    textAlign(CENTER, CENTER);
	  	    text("you win!", width/2, height/2);
	  	    pop();
	  	    noLoop();
	  			break;
	  	}
	  	for(int i=0;i<1;i++) {
	  		addRandomAsteroidOnEdge();
	  	}
	  	stage++;
	  }
	}
	
	public void addRandomAsteroidOnEdge() {
		PVector p = new PVector();
		if(random(1)>0.5) {
			p = new PVector(random(width), round(random(1))*height);
		}
		else{
			p = new PVector(round(random(1))*width, random(height));
		}
		asteroids.add(new Asteroid(this, BOUNDS, p.x, p.y, random(35, 50)));
	}
}
