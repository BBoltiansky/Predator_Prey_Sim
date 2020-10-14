package predator_prey_sim;

import util.Helper;

import java.awt.*;
import java.util.ArrayList;


public class World {

	private static int width, height;
	protected Color canavasColor;
	private static boolean edges[][];
	private ArrayList<Prey> allPrey = new ArrayList<Prey>();
	private ArrayList<Predator> allPredator = new ArrayList<Predator>();

	/**
	 * Create a new City and fill it with buildings and people.
	 * @param w width of city
	 * @param h height of city
	 * @param numPrey number of prey
	 * @param numPredator number of predators
	 */
	public World(int w, int h, int numPrey, int numPredator) {
		width = w;
		height = h;
		canavasColor = Color.BLACK;
		//holds all coordinates of edges of screen
		edges = new boolean[w][h];
		
		// Add Prey and Predators to the world.
		populate(numPrey, numPredator);
	}


	/**
	 * Generates numPrey random prey and numPredator random predators 
	 * distributed throughout the world.
	 * Prey must not be placed outside canavas!
	 *
	 * @param numPrey the number of prey to generate
	 * @param numPredator the number of predators to generate
	 */
	private void populate(int numPrey, int numPredators)
	{
		// Generates numPrey prey and numPredator predators 
		// randomly placed around the world.
		for(int i = 0; i < numPredators; i++){
			int x = Helper.nextInt(width);
			int y = Helper.nextInt(height);

			while(edges[x][y]){
				x = Helper.nextInt(width);
				y = Helper.nextInt(height);
			}
			allPredator.add(new Predator(x, y));
		}
   
   for(int i = 0; i < numPrey; i++){
			int x = Helper.nextInt(width);
			int y = Helper.nextInt(height);

			while(edges[x][y]){
				x = Helper.nextInt(width);
				y = Helper.nextInt(height);
			}
      allPrey.add(new Prey(x, y));
		}
	}
	
	/**
	 * Updates the state of the world for a time step.
	 */
	public void update() {
		// Move predators, prey, etc
    ArrayList<Predator> reproPred = new ArrayList<Predator>();
    ArrayList<Predator> deadPred = new ArrayList<Predator>();
		for(Predator predator : allPredator){
			//moverandomly predators
			if(predator.shouldFollow(this)){
        predator.follow();
      }else{
        predator.moveRandom(this);
      }
      
      
      
      if(reproduce(false)){
        reproPred.add(new Predator(predator.getX(), predator.getY()));
      }
      
      if(randDeath()){
        deadPred.add(predator);
      }
      
		}
   for(Predator p : reproPred){
     allPredator.add(p);
   }
   allPredator.removeAll(deadPred);
   
   
    ArrayList<Prey> reproPrey = new ArrayList<Prey>();
    for(Prey prey : allPrey){
      if(prey.shouldRun(this)){
        prey.runAway();
      }else{
        prey.moveRandom(this);
      } 
      if(reproduce(true)){
        if(reproduce(true)){//if true, will be a different color than parent
          reproPrey.add(new Prey(prey.getX(), prey.getY()));
        }else{
          reproPrey.add(new Prey(prey.getX(), prey.getY(), prey.getColor()));
        }
      }
      
    }
    for(Prey prey : reproPrey){
        allPrey.add(prey);
    }
    this.toPredator();

	}

	/**
	 * Draw all the predators and prey.
	 */
	public void draw(){
		/* Clear the screen */
		PPSim.dp.clear(canavasColor);
		// Draw predators and pray
		
		for(Prey prey : allPrey){
			PPSim.dp.drawCircle(prey.getX(), prey.getY(), prey.getColor());
		}
		
		for(Predator predator : allPredator){
			PPSim.dp.drawSquare(predator.getX(), predator.getY(), Color.RED);
		}
	}
	
	public ArrayList<Prey> getPrey(){
		return this.allPrey;
	}
	
	public ArrayList<Predator> getPredator(){
		return this.allPredator;
	}

	public static boolean isEdge(int x, int y){
		if((x < 0) || (x >= width - 1) || (y < 0) || (y >= height - 1)){
			return true;
		}else{
			return edges[x][y];
		}
	}
 public void toPredator(){
   ArrayList<Prey> deadPrey = new ArrayList<Prey>();
   
   for(Prey p : allPrey){
     if(p.isDead(this)){
       deadPrey.add(p);
     }
   }
   allPrey.removeAll(deadPrey);
   
   for(Prey prey : deadPrey){
     allPredator.add(new Predator(prey.getX(), prey.getY()));
   }
 }
 public void addPredator(int x, int y){
   if(!isEdge(x, y)){
     allPredator.add(new Predator(x, y));
   }
 }
 public boolean reproduce(boolean bl){// true for prey, false for predator
   double chance = Helper.nextDouble();
   if(bl){
     if(chance <= .01){
       return true;
     }else{
       return false;
     }
   }else{
     if(chance <= .001){
       return true;
     }else{
       return false;
     }
   }
 }
 public boolean randDeath(){
   double chance = Helper.nextDouble();
   if(chance <= .005){
     return true;
   }else{
     return false;
   }
 }
public Color getCanavasColor(){
  return canavasColor;
}


}
