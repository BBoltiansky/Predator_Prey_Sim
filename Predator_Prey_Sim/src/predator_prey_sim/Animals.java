package predator_prey_sim;

import java.awt.*;
import java.awt.Color;
import util.Helper;
import util.DotPanel;
import java.awt.Container;

public class Animals{
	private int x;
	private int y;
	public int direction;

	public Animals(int xCoord, int yCoord){
		x = xCoord;
		y = yCoord;
		direction = Helper.nextInt(4);
		//updates all 
	}

	public void changeDir(){
		direction = Helper.nextInt(4);
	}

	public void changePos(int numSpaces){
		int xCoord = x;
		int yCoord = y;

		if(direction == 0){//north
			yCoord = y + numSpaces;
		}else if(direction == 1){//east 
			xCoord = x + numSpaces;
		}else if(direction == 2){//south
			yCoord = y - numSpaces;
		}else if(direction == 3){//west
			xCoord = x - numSpaces;
		}

		if(World.isEdge(xCoord, yCoord)){
			this.changeDir();
		}
		else{
			x = xCoord;
			y = yCoord;
		}
	}

	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}

  public boolean isAdjacent(Animals entity){
    int xDist = Math.abs(x - entity.getX());
    int yDist = Math.abs(y - entity.getY());
    
    if(xDist == 0 && yDist <= 1){
      return true;
    }else if(xDist <= 1 && yDist == 0){
      return true;
    }else{
      return false;
    }
  }
  public boolean isNear(Animals entity, int numSpaces){ //numspaces is 15 preynear, 10 when prednear
    int xDist = x - entity.getX();
    int yDist = y - entity.getY();
    
    if(direction == 0 && xDist == 0 && yDist > 0 && yDist < numSpaces){
      for(int i = y; i > entity.getY(); i--){
        if(World.isEdge(x, i)){
          return false;
        }
        return true;
      }
    }
    if(direction == 1 && xDist > (-1 * numSpaces) && xDist > 0 && yDist == 0){
      for(int i = x; i < entity.getX(); i++){
        if(World.isEdge(i, y)){
          return false;
        }
        return true;
      }
    }
    
    if(direction == 2 && xDist == 0 && yDist > (-1 * numSpaces) && yDist < 0){
      for(int i = y; i < entity.getY(); i++){
        if(World.isEdge(x, i)){
          return false;
        }
        return true;
      }
    }
    if(direction == 3 && yDist == 0 && xDist > 0 && xDist < numSpaces){
      for(int i = x; i > entity.getX(); i--){
        if(World.isEdge(i, y)){
          return false;
        }
        return true;
      }
    }
    return false;
    
  }
}