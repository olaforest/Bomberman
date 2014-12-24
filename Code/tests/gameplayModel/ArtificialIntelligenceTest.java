/*
    This is a class to test the ArtificialIntelligence class

       !!! PLEASE NOTE : DUE TO THE RANDOM NATURE OF !!!
       THE BEHAVIOUR OF ENEMIES WITH INTELLIGENCE OF
       ABOVE 1, THE TESTS MAY FAIL OCCASIONALLY. 

       PLEASE RUN THEM AGAIN ONE OR TWO TIMES IF THEY FAIL, 
        AND THEY WILL WORK. 

       
*/
package gameplayModel;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import gameplayModel.Balloom;

/**
 *
 * @author Jonti
 */
public class ArtificialIntelligenceTest {
    private ArtificialIntelligence AI;
    private Balloom balloom;
    private ArrayList<Enemy> e;
    private ArrayList<Brick> br;
    private ArrayList<Bomb> bo;
    private CollisionDetector cD;
    private Bomberman b;
    public ArtificialIntelligenceTest() {
    }
 
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Before
    public void setUp() {
        e = new ArrayList();
        br = new ArrayList();
        bo = new ArrayList();
        cD = new CollisionDetector(new GameContext());
        b = new Bomberman(31 * GridObject.EFFECTIVE_PIXEL_WIDTH, 13 * GridObject.EFFECTIVE_PIXEL_HEIGHT );
       
        AI = new ArtificialIntelligence(b, e, br, bo ,cD);
        
    }
    
    /**
     * Test of updateEnemiesPosition method, of class ArtificialIntelligence.
     */
    @Test 
     public void testMoveUnobstructedUp() { // tests whether, without an obstacle an enemy with direction up will move up
        
       
         balloom = new Balloom(GridObject.EFFECTIVE_PIXEL_WIDTH,GridObject.EFFECTIVE_PIXEL_HEIGHT*2,0);  
            
            e.clear();
            e.add(balloom);
            
            AI.updateEnemiesPosition();
            assertEquals(balloom.getDirection(),0);
            assertEquals(balloom.getXPosition(),GridObject.EFFECTIVE_PIXEL_WIDTH);
            assertEquals(balloom.getYPosition(),GridObject.EFFECTIVE_PIXEL_HEIGHT*2-balloom.getSpeed());       
    }
     
    @Test
    public void testMoveUnobstructedDown() {// tests whether, without an obstacle an enemy with direction down will move down
        
       
            balloom = new Balloom(GridObject.EFFECTIVE_PIXEL_WIDTH,GridObject.EFFECTIVE_PIXEL_HEIGHT,1);  
            
            e.clear();
            e.add(balloom);
            
            AI.updateEnemiesPosition();
            assertEquals(balloom.getDirection(),1);
            assertEquals(balloom.getXPosition(),GridObject.EFFECTIVE_PIXEL_WIDTH);
            assertEquals(balloom.getYPosition(),GridObject.EFFECTIVE_PIXEL_HEIGHT + balloom.getSpeed());
            
           
            
                
                
    }
    @Test 
     public void testMoveUnobstructedLeft() { // tests whether, without an obstacle an enemy with direction left will move left
        
            balloom = new Balloom(GridObject.EFFECTIVE_PIXEL_WIDTH * 2,GridObject.EFFECTIVE_PIXEL_HEIGHT,2);  
            
            e.clear();
            e.add(balloom);
            
            
            AI.updateEnemiesPosition();
            assertEquals(balloom.getDirection(),2);
            assertEquals(balloom.getXPosition(),GridObject.EFFECTIVE_PIXEL_WIDTH * 2 - balloom.getSpeed());
            
            assertEquals(balloom.getYPosition(),GridObject.EFFECTIVE_PIXEL_HEIGHT);
            
           
            
                
                
    }
     @Test 
     public void testMoveUnobstructedRight() { // tests whether, without an obstacle an enemy with direction right will move right
        
            balloom = new Balloom(GridObject.EFFECTIVE_PIXEL_WIDTH,GridObject.EFFECTIVE_PIXEL_HEIGHT,3);  
            
            e.clear();
            e.add(balloom);
            
            AI.updateEnemiesPosition();
            assertEquals(balloom.getDirection(),3);
            assertEquals(balloom.getXPosition(),GridObject.EFFECTIVE_PIXEL_WIDTH + balloom.getSpeed());
            assertEquals(balloom.getYPosition(),GridObject.EFFECTIVE_PIXEL_HEIGHT);
            
           
            
                
                
    }
    
    @Test
    public void testMoveUpperBounds(){//tests whether an enemy going up into the boundary will turn around
        
         balloom = new Balloom(GridObject.EFFECTIVE_PIXEL_WIDTH,GridObject.EFFECTIVE_PIXEL_HEIGHT,0);  
            
            e.clear();
            e.add(balloom);
            
            AI.updateEnemiesPosition();
            
            
            
            assertEquals(balloom.getDirection(),1);
            assertEquals(balloom.getXPosition(),GridObject.EFFECTIVE_PIXEL_WIDTH);
            
            //the extra + speed here has to do with the way GridObjects handle the setXPosition/setYPosition method.
            //because the true position of the enemy is in the top left corner, when told to move into concrete it will not.
            //combine that with the turn around method telling it to move back where it came, and you have an extra addition of +speed
            assertEquals(balloom.getYPosition(),GridObject.EFFECTIVE_PIXEL_HEIGHT + balloom.getSpeed() );
    }
    
    @Test
    public void testMoveLowerBounds(){//tests whether an enemy going down into the boundary will turn around
        
         balloom = new Balloom(GridObject.EFFECTIVE_PIXEL_WIDTH,GridObject.EFFECTIVE_PIXEL_HEIGHT * 13,1);  
            
            e.clear();
            e.add(balloom);
            
            
            
            AI.updateEnemiesPosition();
            
            
            assertEquals(balloom.getDirection(),0);
            assertEquals(balloom.getXPosition(),GridObject.EFFECTIVE_PIXEL_WIDTH);
            assertEquals(balloom.getYPosition(),GridObject.EFFECTIVE_PIXEL_HEIGHT * 13);
    }
    
    @Test
    public void testMoveLeftBounds(){ //tests whether an enemy going left into the boundary will turn around
        
         balloom = new Balloom(GridObject.EFFECTIVE_PIXEL_WIDTH,GridObject.EFFECTIVE_PIXEL_HEIGHT,2);  
            
            e.clear();
            e.add(balloom);
            
            AI.updateEnemiesPosition();
            
            
            assertEquals(balloom.getDirection(),3);
            //the extra + speed here has to do with the way GridObjects handle the setXPosition/setYPosition method.
            //because the true position of the enemy is in the top left corner, when told to move into concrete it will not.
            //combine that with the turn around method telling it to move back where it came, and you have an extra addition of +speed
            assertEquals(balloom.getXPosition(),GridObject.EFFECTIVE_PIXEL_WIDTH + balloom.getSpeed());
            assertEquals(balloom.getYPosition(),GridObject.EFFECTIVE_PIXEL_HEIGHT);
    }
    
    @Test
    public void testMoveRightBounds(){ //tests whether an enemy going right into the boundary will turn around
        
         balloom = new Balloom(GridObject.EFFECTIVE_PIXEL_WIDTH*31,GridObject.EFFECTIVE_PIXEL_HEIGHT,3);  
            
            e.clear();
            e.add(balloom);
            
            AI.updateEnemiesPosition();
            
            assertEquals(balloom.getDirection(),2);
            assertEquals(balloom.getXPosition(),GridObject.EFFECTIVE_PIXEL_WIDTH * 31);
            assertEquals(balloom.getYPosition(),GridObject.EFFECTIVE_PIXEL_HEIGHT);
    }
    
    @Test
    public void testRightCollision(){ //tests what happens when a brick or bomb is in the path of the enemy going right
         
        

        //when a brick is to the right of the enemy and it is going right
        
        balloom = new Balloom(GridObject.EFFECTIVE_PIXEL_WIDTH,GridObject.EFFECTIVE_PIXEL_HEIGHT,3);  
         
        br.clear();
        e.clear();
        
        e.add(balloom);
        br.add(new Brick(GridObject.EFFECTIVE_PIXEL_WIDTH + balloom.getSpeed(),GridObject.EFFECTIVE_PIXEL_HEIGHT));
            
        AI.updateEnemiesPosition();
            
        assertEquals(balloom.getDirection(),2);
        assertEquals(balloom.getXPosition(),GridObject.EFFECTIVE_PIXEL_WIDTH);
        assertEquals(balloom.getYPosition(),GridObject.EFFECTIVE_PIXEL_HEIGHT);
        
        
        
        //when a bomb is to the right of the enemy and it is going right
        
        balloom = new Balloom(GridObject.EFFECTIVE_PIXEL_WIDTH,GridObject.EFFECTIVE_PIXEL_HEIGHT,3);
        
        e.clear();
        e.add(balloom);
        bo.clear();
        bo.add(new Bomb(GridObject.EFFECTIVE_PIXEL_WIDTH + balloom.getSpeed(),GridObject.EFFECTIVE_PIXEL_HEIGHT));
        
        AI.updateEnemiesPosition();
            
        assertEquals(balloom.getDirection(),2);
        assertEquals(balloom.getXPosition(),GridObject.EFFECTIVE_PIXEL_WIDTH);
        assertEquals(balloom.getYPosition(),GridObject.EFFECTIVE_PIXEL_HEIGHT);
    }
    @Test
    public void testLeftCollision(){ //tests what happens when a brick or bomb is in the path of the enemy going left
        
        //when a brick is to the left of the enemy and it is going left
        
        balloom = new Balloom(GridObject.EFFECTIVE_PIXEL_WIDTH * 6,GridObject.EFFECTIVE_PIXEL_HEIGHT,2);  
         
        br.clear();
        e.clear();
        
        e.add(balloom);
        br.add(new Brick(GridObject.EFFECTIVE_PIXEL_WIDTH * 6 - balloom.getSpeed(),GridObject.EFFECTIVE_PIXEL_HEIGHT));
            
        AI.updateEnemiesPosition();
            
        assertEquals(balloom.getDirection(),3);
        assertEquals(balloom.getXPosition(),GridObject.EFFECTIVE_PIXEL_WIDTH * 6);
        assertEquals(balloom.getYPosition(),GridObject.EFFECTIVE_PIXEL_HEIGHT);
        
        
        //when a bomb is to the left of the enemy and it is going left
        
        balloom = new Balloom(GridObject.EFFECTIVE_PIXEL_WIDTH * 6,GridObject.EFFECTIVE_PIXEL_HEIGHT,2);
        
        e.clear();
        e.add(balloom);
        bo.clear();
        bo.add(new Bomb(GridObject.EFFECTIVE_PIXEL_WIDTH * 6 - balloom.getSpeed(),GridObject.EFFECTIVE_PIXEL_HEIGHT));
        
        AI.updateEnemiesPosition();
            
        assertEquals(balloom.getDirection(),3);
        assertEquals(balloom.getXPosition(),GridObject.EFFECTIVE_PIXEL_WIDTH * 6);
        assertEquals(balloom.getYPosition(),GridObject.EFFECTIVE_PIXEL_HEIGHT);
    }
    @Test
    public void testUpCollision(){ //tests what happens when a brick or bomb is in the path of the enemy going up
         
        //when a brick is above the enemy and it is going up
        
        balloom = new Balloom(GridObject.EFFECTIVE_PIXEL_WIDTH,GridObject.EFFECTIVE_PIXEL_HEIGHT * 6,0);  
         
        br.clear();
        e.clear();
        
        e.add(balloom);
        br.add(new Brick(GridObject.EFFECTIVE_PIXEL_WIDTH ,GridObject.EFFECTIVE_PIXEL_HEIGHT * 6 - balloom.getSpeed()));
            
        AI.updateEnemiesPosition();
            
        assertEquals(balloom.getDirection(),1);
        assertEquals(balloom.getXPosition(),GridObject.EFFECTIVE_PIXEL_WIDTH);
        assertEquals(balloom.getYPosition(),GridObject.EFFECTIVE_PIXEL_HEIGHT * 6);
        
             
        //when a bomb is above the enemy and it is going up
        
        balloom = new Balloom(GridObject.EFFECTIVE_PIXEL_WIDTH,GridObject.EFFECTIVE_PIXEL_HEIGHT * 6,0);
        
        e.clear();
        e.add(balloom);
        bo.clear();
        bo.add(new Bomb(GridObject.EFFECTIVE_PIXEL_WIDTH ,GridObject.EFFECTIVE_PIXEL_HEIGHT * 6 -  balloom.getSpeed()));
        
        AI.updateEnemiesPosition();
            
        assertEquals(balloom.getDirection(),1);
        assertEquals(balloom.getXPosition(),GridObject.EFFECTIVE_PIXEL_WIDTH);
        assertEquals(balloom.getYPosition(),GridObject.EFFECTIVE_PIXEL_HEIGHT * 6 );
    }
    @Test
    public void testDownCollision(){ //tests what happens when a brick or bomb is in the path of the enemy going down
         
        //when a brick is below the enemy and it is going down
        balloom = new Balloom(GridObject.EFFECTIVE_PIXEL_WIDTH,GridObject.EFFECTIVE_PIXEL_HEIGHT * 6,1);  
         
        br.clear();
        e.clear();
        
        e.add(balloom);
        br.add(new Brick(GridObject.EFFECTIVE_PIXEL_WIDTH ,GridObject.EFFECTIVE_PIXEL_HEIGHT * 6 + balloom.getSpeed()));
            
        AI.updateEnemiesPosition();
            
        assertEquals(balloom.getDirection(),0);
        assertEquals(balloom.getXPosition(),GridObject.EFFECTIVE_PIXEL_WIDTH);
        assertEquals(balloom.getYPosition(),GridObject.EFFECTIVE_PIXEL_HEIGHT * 6);
        
        //when a bomb is below the enemy and it is going down
        balloom = new Balloom(GridObject.EFFECTIVE_PIXEL_WIDTH,GridObject.EFFECTIVE_PIXEL_HEIGHT * 6,1);
        
        e.clear();
        e.add(balloom);
        bo.clear();
        bo.add(new Bomb(GridObject.EFFECTIVE_PIXEL_WIDTH ,GridObject.EFFECTIVE_PIXEL_HEIGHT * 6 + balloom.getSpeed()));
        
        AI.updateEnemiesPosition();
            
        assertEquals(balloom.getDirection(),0);
        assertEquals(balloom.getXPosition(),GridObject.EFFECTIVE_PIXEL_WIDTH);
        assertEquals(balloom.getYPosition(),GridObject.EFFECTIVE_PIXEL_HEIGHT * 6 );
    }
    
    @Test 
    
    public void testFindBombermanDown(){
        
        //going right with bomberman 2 squares below
        
        balloom = new Balloom(GridObject.EFFECTIVE_PIXEL_WIDTH,GridObject.EFFECTIVE_PIXEL_HEIGHT ,3);
        balloom.smartness = 2;
        
        e.clear();
        bo.clear();
        br.clear();
        e.add(balloom);
        
        b.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH);
        b.setYPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT*3);
        
        
        AI.updateEnemiesPosition();

        assertEquals(balloom.getDirection(),3);
        assertEquals(balloom.getXPosition(),GridObject.EFFECTIVE_PIXEL_WIDTH + balloom.getSpeed());
        assertEquals(balloom.getYPosition(),GridObject.EFFECTIVE_PIXEL_HEIGHT );
        
        
        
        //going right with bomberman 1 squares below
        
        balloom = new Balloom(GridObject.EFFECTIVE_PIXEL_WIDTH,GridObject.EFFECTIVE_PIXEL_HEIGHT ,3);
        balloom.smartness = 2;
        
        e.clear();
        bo.clear();
        br.clear();
        e.add(balloom);
        
        b.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH);
        b.setYPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT*2);
        
        AI.updateEnemiesPosition();
        
        assertEquals(balloom.getDirection(),1);
        assertEquals(balloom.getXPosition(),GridObject.EFFECTIVE_PIXEL_WIDTH );
        assertEquals(balloom.getYPosition(),GridObject.EFFECTIVE_PIXEL_HEIGHT + balloom.getSpeed());
        
        
        
        //going left with bomberman 2 squares below
        
        balloom = new Balloom(GridObject.EFFECTIVE_PIXEL_WIDTH*5,GridObject.EFFECTIVE_PIXEL_HEIGHT ,2);
        balloom.smartness = 2;
        
        e.clear();
        bo.clear();
        br.clear();
        e.add(balloom);
        
        b.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH*5);
        b.setYPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT*3);
        
        AI.updateEnemiesPosition();
        
        assertEquals(balloom.getDirection(),2);
        assertEquals(balloom.getXPosition(),GridObject.EFFECTIVE_PIXEL_WIDTH *5 - balloom.getSpeed());
        assertEquals(balloom.getYPosition(),GridObject.EFFECTIVE_PIXEL_HEIGHT );
        
        
        
        //going left with bomberman 1 square below
        
        balloom = new Balloom(GridObject.EFFECTIVE_PIXEL_WIDTH*5,GridObject.EFFECTIVE_PIXEL_HEIGHT ,2);
        balloom.smartness = 2;
        
        e.clear();
        bo.clear();
        br.clear();
        e.add(balloom);
        
        b.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH*5);
        b.setYPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT*2);
        
        AI.updateEnemiesPosition();
        
        assertEquals(balloom.getDirection(),1);
        assertEquals(balloom.getXPosition(),GridObject.EFFECTIVE_PIXEL_WIDTH *5);
        assertEquals(balloom.getYPosition(),GridObject.EFFECTIVE_PIXEL_HEIGHT + balloom.getSpeed() );
        
        
        
        //going up with bomberman 2 squares below
        
        balloom = new Balloom(GridObject.EFFECTIVE_PIXEL_WIDTH * 5,GridObject.EFFECTIVE_PIXEL_HEIGHT * 5 ,0);
        balloom.smartness = 2;
        
        e.clear();
        bo.clear();
        br.clear();
        e.add(balloom);
        
        b.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 5);
        b.setYPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 7);
        
        AI.updateEnemiesPosition();
        
        assertEquals(balloom.getDirection(),0);
        assertEquals(balloom.getXPosition(),GridObject.EFFECTIVE_PIXEL_WIDTH * 5 );
        assertEquals(balloom.getYPosition(),GridObject.EFFECTIVE_PIXEL_HEIGHT * 5 - balloom.getSpeed() );
        
        
        
        //going up with bomberman 1 square below
        
        balloom = new Balloom(GridObject.EFFECTIVE_PIXEL_WIDTH * 5,GridObject.EFFECTIVE_PIXEL_HEIGHT * 5,0);
        balloom.smartness = 2;
        
        e.clear();
        bo.clear();
        br.clear();
        e.add(balloom);
        
        b.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 5);
        b.setYPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 6);
        
        AI.updateEnemiesPosition();
        
        assertEquals(balloom.getDirection(),1);
        assertEquals(balloom.getXPosition(),GridObject.EFFECTIVE_PIXEL_WIDTH * 5);
        assertEquals(balloom.getYPosition(),GridObject.EFFECTIVE_PIXEL_HEIGHT * 5 + balloom.getSpeed() );
        
        
        
        //going down with bomberman 2 squares below
        
        balloom = new Balloom(GridObject.EFFECTIVE_PIXEL_WIDTH * 5,GridObject.EFFECTIVE_PIXEL_HEIGHT * 5 ,1);
        balloom.smartness = 2;
        
        e.clear();
        bo.clear();
        br.clear();
        e.add(balloom);
        
        b.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 5);
        b.setYPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 7);
        
        AI.updateEnemiesPosition();
        
        assertEquals(balloom.getDirection(),1);
        assertEquals(balloom.getXPosition(),GridObject.EFFECTIVE_PIXEL_WIDTH * 5 );
        assertEquals(balloom.getYPosition(),GridObject.EFFECTIVE_PIXEL_HEIGHT * 5 + balloom.getSpeed() );
        
        
        
        //going down with bomberman 1 square below
        
        balloom = new Balloom(GridObject.EFFECTIVE_PIXEL_WIDTH * 5,GridObject.EFFECTIVE_PIXEL_HEIGHT * 5,1);
        balloom.smartness = 2;
        
        e.clear();
        bo.clear();
        br.clear();
        e.add(balloom);
        
        b.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 5);
        b.setYPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 6);
        
        AI.updateEnemiesPosition();
        
        assertEquals(balloom.getDirection(),1);
        assertEquals(balloom.getXPosition(),GridObject.EFFECTIVE_PIXEL_WIDTH * 5);
        assertEquals(balloom.getYPosition(),GridObject.EFFECTIVE_PIXEL_HEIGHT * 5 + balloom.getSpeed() );
        
    }
   
    
    @Test
     public void testFindBombermanUp(){
        
         
        //going right with bomberman 2 squares above
         
        balloom = new Balloom(GridObject.EFFECTIVE_PIXEL_WIDTH,GridObject.EFFECTIVE_PIXEL_HEIGHT*7 ,3);
        balloom.smartness = 2;
        
        e.clear();
        bo.clear();
        br.clear();
        e.add(balloom);
        
        b.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH);
        b.setYPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT*5);
        
        
        AI.updateEnemiesPosition();

        assertEquals(balloom.getDirection(),3);
        assertEquals(balloom.getXPosition(),GridObject.EFFECTIVE_PIXEL_WIDTH + balloom.getSpeed());
        assertEquals(balloom.getYPosition(),GridObject.EFFECTIVE_PIXEL_HEIGHT * 7 );
        
        
        
        //going right with bomberman 1 square above
        
        balloom = new Balloom(GridObject.EFFECTIVE_PIXEL_WIDTH,GridObject.EFFECTIVE_PIXEL_HEIGHT * 7 ,3);
        balloom.smartness = 2;
        
        e.clear();
        bo.clear();
        br.clear();
        e.add(balloom);
        
        b.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH);
        b.setYPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT*6);
        
        AI.updateEnemiesPosition();
        
        assertEquals(balloom.getDirection(),0);
        assertEquals(balloom.getXPosition(),GridObject.EFFECTIVE_PIXEL_WIDTH );
        assertEquals(balloom.getYPosition(),GridObject.EFFECTIVE_PIXEL_HEIGHT * 7 - balloom.getSpeed());
        
        
        
        //going left with bomberman 2 squares above
        
        balloom = new Balloom(GridObject.EFFECTIVE_PIXEL_WIDTH*5,GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 ,2);
        balloom.smartness = 2;
        
        e.clear();
        bo.clear();
        br.clear();
        e.add(balloom);
        
        b.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH*5);
        b.setYPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT);
        
        AI.updateEnemiesPosition();
        
        assertEquals(balloom.getDirection(),2);
        assertEquals(balloom.getXPosition(),GridObject.EFFECTIVE_PIXEL_WIDTH * 5 - balloom.getSpeed());
        assertEquals(balloom.getYPosition(),GridObject.EFFECTIVE_PIXEL_HEIGHT * 3);
        
        
        //going left with bomberman 1 square above
        
        balloom = new Balloom(GridObject.EFFECTIVE_PIXEL_WIDTH * 5,GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 ,2);
        balloom.smartness = 2;
        
        e.clear();
        bo.clear();
        br.clear();
        e.add(balloom);
        
        b.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH*5);
        b.setYPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT*2);
        
        AI.updateEnemiesPosition();
        
        assertEquals(balloom.getDirection(),0);
        assertEquals(balloom.getXPosition(),GridObject.EFFECTIVE_PIXEL_WIDTH * 5);
        assertEquals(balloom.getYPosition(),GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - balloom.getSpeed() );
        
        
        
        //going down with bomberman 2 squares above
        
        balloom = new Balloom(GridObject.EFFECTIVE_PIXEL_WIDTH * 5,GridObject.EFFECTIVE_PIXEL_HEIGHT * 7 ,1);
        balloom.smartness = 2;
        
        e.clear();
        bo.clear();
        br.clear();
        e.add(balloom);
        
        b.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 5);
        b.setYPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 5);
        
        AI.updateEnemiesPosition();
        
        assertEquals(balloom.getDirection(),1);
        assertEquals(balloom.getXPosition(),GridObject.EFFECTIVE_PIXEL_WIDTH * 5 );
        assertEquals(balloom.getYPosition(),GridObject.EFFECTIVE_PIXEL_HEIGHT * 7 + balloom.getSpeed() );
        
        
        
        //going down with bomberman 1 squares above
        
        balloom = new Balloom(GridObject.EFFECTIVE_PIXEL_WIDTH * 5,GridObject.EFFECTIVE_PIXEL_HEIGHT * 7 ,1);
        balloom.smartness = 2;
        
        e.clear();
        bo.clear();
        br.clear();
        e.add(balloom);
        
        b.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 5);
        b.setYPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 6);
        
        AI.updateEnemiesPosition();
        
        assertEquals(balloom.getDirection(),0);
        assertEquals(balloom.getXPosition(),GridObject.EFFECTIVE_PIXEL_WIDTH * 5);
        assertEquals(balloom.getYPosition(),GridObject.EFFECTIVE_PIXEL_HEIGHT * 7 - balloom.getSpeed() );
        
        
        
        //going up with bomberman 2 squares above
        
        balloom = new Balloom(GridObject.EFFECTIVE_PIXEL_WIDTH * 5,GridObject.EFFECTIVE_PIXEL_HEIGHT * 7 ,0);
        balloom.smartness = 2;
        
        e.clear();
        bo.clear();
        br.clear();
        e.add(balloom);
        
        b.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 5);
        b.setYPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 5);
        
        AI.updateEnemiesPosition();
        
        assertEquals(balloom.getDirection(),0);
        assertEquals(balloom.getXPosition(),GridObject.EFFECTIVE_PIXEL_WIDTH * 5 );
        assertEquals(balloom.getYPosition(),GridObject.EFFECTIVE_PIXEL_HEIGHT * 7 - balloom.getSpeed() );
        
        
        //going up with bomberman 1 square above
        
        balloom = new Balloom(GridObject.EFFECTIVE_PIXEL_WIDTH * 5,GridObject.EFFECTIVE_PIXEL_HEIGHT * 7 ,0);
        balloom.smartness = 2;
        
        e.clear();
        bo.clear();
        br.clear();
        e.add(balloom);
        
        b.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 5);
        b.setYPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 6);
        
        AI.updateEnemiesPosition();
        
        assertEquals(balloom.getDirection(),0);
        assertEquals(balloom.getXPosition(),GridObject.EFFECTIVE_PIXEL_WIDTH * 5);
        assertEquals(balloom.getYPosition(),GridObject.EFFECTIVE_PIXEL_HEIGHT * 7 - balloom.getSpeed() );
        
    }
     
     @Test
     public void testFindBombermanLeft(){ //tests whether an enemy with intelligence 2 will move towards bomberman if he is 1 square left of it.
        
        
        //going right with bomberman 2 squares to the left
         
        balloom = new Balloom(GridObject.EFFECTIVE_PIXEL_WIDTH * 11,GridObject.EFFECTIVE_PIXEL_HEIGHT * 9 ,3);
        balloom.smartness = 2;
        
        e.clear();
        bo.clear();
        br.clear();
        e.add(balloom);
        
        b.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 9);
        b.setYPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 9);
        
        
        AI.updateEnemiesPosition();

        assertEquals(balloom.getDirection(),3);
        assertEquals(balloom.getXPosition(),GridObject.EFFECTIVE_PIXEL_WIDTH * 11 + balloom.getSpeed());
        assertEquals(balloom.getYPosition(),GridObject.EFFECTIVE_PIXEL_HEIGHT * 9 );
        
        
        
        
        //going right with bomberman 1 square to the left
        
        balloom = new Balloom(GridObject.EFFECTIVE_PIXEL_WIDTH * 11,GridObject.EFFECTIVE_PIXEL_HEIGHT * 9 ,3);
        balloom.smartness = 2;
        
        e.clear();
        bo.clear();
        br.clear();
        e.add(balloom);
        
        b.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 10);
        b.setYPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 9);
        
        AI.updateEnemiesPosition();
        
        assertEquals(balloom.getDirection(),2);
        assertEquals(balloom.getXPosition(),GridObject.EFFECTIVE_PIXEL_WIDTH * 11 - balloom.getSpeed() );
        assertEquals(balloom.getYPosition(),GridObject.EFFECTIVE_PIXEL_HEIGHT * 9);
        
        
        
        
        //going left with bomberman 2 squares to the left
        
        balloom = new Balloom(GridObject.EFFECTIVE_PIXEL_WIDTH * 11,GridObject.EFFECTIVE_PIXEL_HEIGHT * 9 ,2);
        balloom.smartness = 2;
        
        e.clear();
        bo.clear();
        br.clear();
        e.add(balloom);
        
        b.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 9);
        b.setYPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 9);
        
        
        AI.updateEnemiesPosition();

        assertEquals(balloom.getDirection(),2);
        assertEquals(balloom.getXPosition(),GridObject.EFFECTIVE_PIXEL_WIDTH * 11 - balloom.getSpeed());
        assertEquals(balloom.getYPosition(),GridObject.EFFECTIVE_PIXEL_HEIGHT * 9 );
        
        
        
        //going left with bomberman 1 square to the left
        
        balloom = new Balloom(GridObject.EFFECTIVE_PIXEL_WIDTH * 11,GridObject.EFFECTIVE_PIXEL_HEIGHT * 9 ,2);
        balloom.smartness = 2;
        
        e.clear();
        bo.clear();
        br.clear();
        e.add(balloom);
        
        b.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 10);
        b.setYPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 9);
        
        AI.updateEnemiesPosition();
        
        assertEquals(balloom.getDirection(),2);
        assertEquals(balloom.getXPosition(),GridObject.EFFECTIVE_PIXEL_WIDTH * 11 - balloom.getSpeed() );
        assertEquals(balloom.getYPosition(),GridObject.EFFECTIVE_PIXEL_HEIGHT * 9);
        
        
        
        //going down with bomberman 2 squares to the left
        
        balloom = new Balloom(GridObject.EFFECTIVE_PIXEL_WIDTH * 11,GridObject.EFFECTIVE_PIXEL_HEIGHT * 9 ,1);
        balloom.smartness = 2;
        
        e.clear();
        bo.clear();
        br.clear();
        e.add(balloom);
        
        b.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 9);
        b.setYPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 9);
        
        AI.updateEnemiesPosition();
        
        assertEquals(balloom.getDirection(),1);
        assertEquals(balloom.getXPosition(),GridObject.EFFECTIVE_PIXEL_WIDTH * 11 );
        assertEquals(balloom.getYPosition(),GridObject.EFFECTIVE_PIXEL_HEIGHT * 9 + balloom.getSpeed() );
        
        
        
        //going down with bomberman 1 square to the left
        
        balloom = new Balloom(GridObject.EFFECTIVE_PIXEL_WIDTH * 11,GridObject.EFFECTIVE_PIXEL_HEIGHT * 9 ,1);
        balloom.smartness = 2;
        
        e.clear();
        bo.clear();
        br.clear();
        e.add(balloom);
        
        b.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 10);
        b.setYPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 9);
        
        AI.updateEnemiesPosition();
        
        assertEquals(balloom.getDirection(),2);
        assertEquals(balloom.getXPosition(),GridObject.EFFECTIVE_PIXEL_WIDTH * 11 - balloom.getSpeed());
        assertEquals(balloom.getYPosition(),GridObject.EFFECTIVE_PIXEL_HEIGHT * 9  );
        
        
        
        //going up with bomberman 2 squares to the left
        
        balloom = new Balloom(GridObject.EFFECTIVE_PIXEL_WIDTH * 11,GridObject.EFFECTIVE_PIXEL_HEIGHT * 9 ,0);
        balloom.smartness = 2;
        
        e.clear();
        bo.clear();
        br.clear();
        e.add(balloom);
        
        b.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 9);
        b.setYPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 9);
        
        AI.updateEnemiesPosition();
        
        assertEquals(balloom.getDirection(),0);
        assertEquals(balloom.getXPosition(),GridObject.EFFECTIVE_PIXEL_WIDTH * 11 );
        assertEquals(balloom.getYPosition(),GridObject.EFFECTIVE_PIXEL_HEIGHT * 9 - balloom.getSpeed() );
        
        
        
        //going up with bomberman 1 square to the left
        
        balloom = new Balloom(GridObject.EFFECTIVE_PIXEL_WIDTH * 11,GridObject.EFFECTIVE_PIXEL_HEIGHT * 9 ,0);
        balloom.smartness = 2;
        
        e.clear();
        bo.clear();
        br.clear();
        e.add(balloom);
        
        b.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 10);
        b.setYPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 9);
        
        AI.updateEnemiesPosition();
        
        assertEquals(balloom.getDirection(),2);
        assertEquals(balloom.getXPosition(),GridObject.EFFECTIVE_PIXEL_WIDTH * 11 - balloom.getSpeed());
        assertEquals(balloom.getYPosition(),GridObject.EFFECTIVE_PIXEL_HEIGHT * 9  );
        
    }
    
     @Test
     
      public void testFindBombermanRight(){
        
        //going right with bomberman 2 squares to the right  
          
        balloom = new Balloom(GridObject.EFFECTIVE_PIXEL_WIDTH * 21,GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 ,3);
        balloom.smartness = 2;
        
        e.clear();
        bo.clear();
        br.clear();
        e.add(balloom);
        
        b.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 23);
        b.setYPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3);
        
        
        AI.updateEnemiesPosition();

        assertEquals(balloom.getDirection(),3);
        assertEquals(balloom.getXPosition(),GridObject.EFFECTIVE_PIXEL_WIDTH * 21 + balloom.getSpeed());
        assertEquals(balloom.getYPosition(),GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 );
        
        
        
        //going right with bomberman 1 square to the right 
        
        balloom = new Balloom(GridObject.EFFECTIVE_PIXEL_WIDTH * 21,GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 ,3);
        balloom.smartness = 2;
        
        e.clear();
        bo.clear();
        br.clear();
        e.add(balloom);
        
        b.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 22);
        b.setYPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3);
        
        AI.updateEnemiesPosition();
        
        assertEquals(balloom.getDirection(),3);
        assertEquals(balloom.getXPosition(),GridObject.EFFECTIVE_PIXEL_WIDTH * 21 + balloom.getSpeed() );
        assertEquals(balloom.getYPosition(),GridObject.EFFECTIVE_PIXEL_HEIGHT * 3);
        
        
        
        //going left with bomberman 2 squares to the right 
        
        balloom = new Balloom(GridObject.EFFECTIVE_PIXEL_WIDTH * 21,GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 ,2);
        balloom.smartness = 2;
        
        e.clear();
        bo.clear();
        br.clear();
        e.add(balloom);
        
        b.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 23);
        b.setYPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3);
        
        
        AI.updateEnemiesPosition();

        assertEquals(balloom.getDirection(),2);
        assertEquals(balloom.getXPosition(),GridObject.EFFECTIVE_PIXEL_WIDTH * 21 - balloom.getSpeed());
        assertEquals(balloom.getYPosition(),GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 );
        
        
        //going left with bomberman 1 square to the right
        
        balloom = new Balloom(GridObject.EFFECTIVE_PIXEL_WIDTH * 21,GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 ,2);
        balloom.smartness = 2;
        
        e.clear();
        bo.clear();
        br.clear();
        e.add(balloom);
        
        b.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 22);
        b.setYPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3);
        
        AI.updateEnemiesPosition();
        
        assertEquals(balloom.getDirection(),3);
        assertEquals(balloom.getXPosition(),GridObject.EFFECTIVE_PIXEL_WIDTH * 21 + balloom.getSpeed() );
        assertEquals(balloom.getYPosition(),GridObject.EFFECTIVE_PIXEL_HEIGHT * 3);
        
        
        
        //going down with bomberman 2 squares to the right 
        
        balloom = new Balloom(GridObject.EFFECTIVE_PIXEL_WIDTH * 21,GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 ,1);
        balloom.smartness = 2;
        
        e.clear();
        bo.clear();
        br.clear();
        e.add(balloom);
        
        b.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 23);
        b.setYPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3);
        
        AI.updateEnemiesPosition();
        
        assertEquals(balloom.getDirection(),1);
        assertEquals(balloom.getXPosition(),GridObject.EFFECTIVE_PIXEL_WIDTH * 21 );
        assertEquals(balloom.getYPosition(),GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 + balloom.getSpeed() );
        
        
        
        //going down with bomberman 1 square to the right
        
        balloom = new Balloom(GridObject.EFFECTIVE_PIXEL_WIDTH * 21,GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 ,1);
        balloom.smartness = 2;
        
        e.clear();
        bo.clear();
        br.clear();
        e.add(balloom);
        
        b.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 22);
        b.setYPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3);
        
        AI.updateEnemiesPosition();
        
        assertEquals(balloom.getDirection(),3);
        assertEquals(balloom.getXPosition(),GridObject.EFFECTIVE_PIXEL_WIDTH * 21 + balloom.getSpeed());
        assertEquals(balloom.getYPosition(),GridObject.EFFECTIVE_PIXEL_HEIGHT * 3  );
        
        
        
        //going up with bomberman 2 squares to the right 
        
        balloom = new Balloom(GridObject.EFFECTIVE_PIXEL_WIDTH * 21,GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 ,0);
        balloom.smartness = 2;
        
        e.clear();
        bo.clear();
        br.clear();
        e.add(balloom);
        
        b.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 23);
        b.setYPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3);
        
        AI.updateEnemiesPosition();
        
        assertEquals(balloom.getDirection(),0);
        assertEquals(balloom.getXPosition(),GridObject.EFFECTIVE_PIXEL_WIDTH * 21 );
        assertEquals(balloom.getYPosition(),GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 - balloom.getSpeed() );
        
        
        
        //going up with bomberman 1 square to the right
        
        balloom = new Balloom(GridObject.EFFECTIVE_PIXEL_WIDTH * 21,GridObject.EFFECTIVE_PIXEL_HEIGHT * 3 ,0);
        balloom.smartness = 2;
        
        e.clear();
        bo.clear();
        br.clear();
        e.add(balloom);
        
        b.setXPosition(GridObject.EFFECTIVE_PIXEL_WIDTH * 22);
        b.setYPosition(GridObject.EFFECTIVE_PIXEL_HEIGHT * 3);
        
        AI.updateEnemiesPosition();
        
        assertEquals(balloom.getDirection(),3);
        assertEquals(balloom.getXPosition(),GridObject.EFFECTIVE_PIXEL_WIDTH * 21 + balloom.getSpeed());
        assertEquals(balloom.getYPosition(),GridObject.EFFECTIVE_PIXEL_HEIGHT * 3  );
        
    }
}
