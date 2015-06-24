
package gameplayModel;

/**
 * This class deals with animation related controller for all of the movable obstacles on the GridMap
 * It involves loading the sprite sheets, images cycling.
 * 
 * @author Olivier Laforest
 */
public abstract class AnimatedObject extends GridObject {
	
	public final int INITIAL_ANIMATION = 0;
	
	protected Animation[] animationList;
	private Animation currentAnimation;

	protected boolean isDead, isObsolete;
	
	private int animationNumber;

	protected int animCycleParam;

	protected int counter;
	
	/**
	 * @param x the x-coordinates of location of the animation taking place
	 * @param y the y-coordinates of location of the animation taking place
	 */
	public AnimatedObject (int x, int y) {
		
		super(x, y);
		
		generateAnimationList();
    	
    	currentAnimation = animationList[INITIAL_ANIMATION];
    	animationNumber = INITIAL_ANIMATION;
    	
    	counter = 0;
    	animCycleParam = 3;
    	
    	isDead = false;
    	isObsolete = false;
	}
	
	/**
	 * Each animation requires cycling through Sprite Sheets to generate on screen
	 * This method controls the timing of each animated event
	 */
	public void cycleAnimation() {
    	
    	if (counter % animCycleParam == 0) {
			
			if (!isDead) {
				currentAnimation.cycleFrame();
			
			} else {
					
				if (currentAnimation.isAnimDone())
					isObsolete = true;
				else
					currentAnimation.cycleFrame();
			}
		}
		counter++;
    }
	
	/**
	 * Triggers when the animated object is dead
	 */
	public void triggerDeath() {
		currentAnimation = animationList[animationList.length - 1];
		isDead = true;
	}
	
	public boolean isDead() {
		return isDead;
	}

	public boolean isObsolete() {
		return isObsolete;
	}
	
	/**
	 * Marked for deletion
	 */
	public abstract void generateAnimationList();
	
	public Animation getCurrentAnimation() {
		return currentAnimation;
	}
	
	public int getAnimationNumber() {
		return animationNumber;
	}
	
	/**
	 * Controls the current frame of the animation
	 * 
	 * @param aT the type of animation desired by the object
	 */
	public void setCurrentAnimation(int aT) {
		currentAnimation = animationList[aT];
		currentAnimation.setToInitialFrame();
		animationNumber = aT;
	}
}
