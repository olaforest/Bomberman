//*****************************************************************************************************
//  Enemy.java
//
//*****************************************************************************************************

package gameplayModel;

import java.util.ArrayList;

/**
 * This class contains the methods and attributes that are common to all enemies in Bomberman.
 * 
 * @author Olivier Laforest
 */
public abstract class Enemy extends AnimatedObject {
	
	public static enum AnimationType {right, left, death};
	
	public final int SPEED_MULTIPLIER = 1;
	
	protected int points;
	protected int speed;
	protected int smartness;
	protected boolean wallpass;
	protected int direction;
	
    public Enemy(int x, int y) {
		
    	super(x, y);
		direction = (int) (Math.random() * 3);
    }
    
    public Enemy(int x, int y,int dir) {
		
    	super(x, y);
        direction = dir;
    }
    
    public int getDirection() {
        return direction;
    }
        
        //gotta watch that the input is 0-3
    public void setDirection(int direction) { 
        this.direction = direction;
    }
	
	public abstract void generateAnimationList();
	
	public int getPoints() {
		return points;
	}
	
	public int getSpeed() {
		return speed;
	}
	
	public int getSmartness() {
		return smartness;
	}
	
	public ArrayList<String> toCSVEntry() {
		
		ArrayList<String> entryList = new ArrayList<String>();
		
		entryList.add(this.getClass().toString());
		entryList.add(Integer.toString(xPosition));
		entryList.add(Integer.toString(yPosition));
		entryList.add(Integer.toString(direction));

		return entryList; 
	}
}
