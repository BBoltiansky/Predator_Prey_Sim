package predator_prey_sim;

import util.Helper;
import java.awt.*;
import java.awt.Color;

public class Predator extends Animals{
	public Predator(int x, int y){
		super(x, y);
	}

	public void moveRandom(World world){
		if(Helper.nextDouble() <= .05){
			this.changeDir();
		}
		this.changePos(1);
	}
   public void follow(){
     this.changePos(1);
   }
   public boolean shouldFollow(World w){
     for(Prey p : w.getPrey()){
       if(isNear(p, 15)){
         return true;
       }
     }
     return false;
   }
   public boolean isDead(){
     if(Helper.nextDouble() <= .05){
       return true;
     }else{
       return false;
     }
   }
}