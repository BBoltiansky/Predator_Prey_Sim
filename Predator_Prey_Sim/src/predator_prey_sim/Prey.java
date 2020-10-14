package predator_prey_sim;

import util.Helper;
import java.awt.*;
import java.awt.Color;


public class Prey extends Animals{
  private Color color;
  //two constructors and one specific for color
	public Prey(int x, int y){
		super(x, y);
    color = Helper.newRandColor();
	}
  public Prey(int x, int y, Color c){
    super(x, y);
    color = c;
  }
//random movement
	public void moveRandom(World world){
		if(Helper.nextDouble() <= .1){
			this.changeDir();
		}
		super.changePos(1);
	}
 //get color of Prey
   public Color getColor(){
     return color;
   }
   //check to see if prey is dead
   public boolean isDead(World world){
     for(Predator p : world.getPredator()){
       if(this.isAdjacent(p)){
         if(!(color.equals(world.getCanavasColor()))){
           return true;
         }
       }
     }
     return false;
   }
   //prompt prey to run away
   public void runAway(){
     this.reverseDirection();
     super.changePos(2);
   }
   //check to see if prey should run away
   public boolean shouldRun(World w){
     for(Predator p : w.getPredator()){
       if(isNear(p, 10)){
         return true;
       }
     }
     return false;
   }
   //changes direction of prey when encountering predator
   public void reverseDirection(){
     direction = (direction + 2) % 4;
   }
}