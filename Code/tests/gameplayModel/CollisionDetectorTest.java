/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameplayModel;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jonti
 */
public class CollisionDetectorTest {
    
private static CollisionDetector detector;

    @BeforeClass
    public static void setUpClass() {
        
        GameContext gC = new GameContext();
        detector = new CollisionDetector(new GameContext());
        
    
    }
 
    /**
     * Test of checkUpCollision method, of class CollisionDetector.
     */
    @Test
    public void testCheckUpCollision() {
        
        GridObject a = null ;
        GridObject b = null ;
        
        assertFalse(detector.checkUpCollision(a,b));
        
        a = new GridObject(0,100);
        b = new GridObject(0,150);
        
        assertFalse(detector.checkUpCollision(a,b));
        
        a.xPosition = 0;
        a.yPosition = 100;
        
        b.xPosition = 0;
        b.yPosition = 80;
                
        assertTrue(detector.checkUpCollision(a,b));
        
        a.xPosition = 0;
        a.yPosition = 100;
        
        b.xPosition = 0;
        b.yPosition = 60;
        
        assertFalse(detector.checkUpCollision(a,b));
        
        
    }

    /**
     * Test of checkDownCollision method, of class CollisionDetector.
     */
    @Test
    public void testCheckDownCollision() {
        
        GridObject a = null ;
        GridObject b = null ;
        
        assertFalse(detector.checkDownCollision(a,b));
        
        a = new GridObject(0,100);
        b = new GridObject(0,150);
        
        assertFalse(detector.checkDownCollision(a,b));
        
        a.xPosition = 0;
        a.yPosition = 100;
        
        b.xPosition = 0;
        b.yPosition = 80;
                
        assertFalse(detector.checkDownCollision(a,b));
        
        a.xPosition = 0;
        a.yPosition = 100;
        
        b.xPosition = 0;
        b.yPosition = 120;
        
        assertTrue(detector.checkDownCollision(a,b));
         a.xPosition = 0;
        a.yPosition = 100;
        
        b.xPosition = 0;
        b.yPosition = 150;
        
        assertFalse(detector.checkDownCollision(a,b));
        
    }

    /**
     * Test of checkLeftCollision method, of class CollisionDetector.
     */
    @Test
    public void testCheckLeftCollision() {
        
        GridObject a = null ;
        GridObject b = null ;
        
        assertFalse(detector.checkLeftCollision(a,b));
        
        a = new GridObject(100,0);
        b = new GridObject(150,0);
        
        assertFalse(detector.checkLeftCollision(a,b));
        
        a.xPosition = 100;
        a.yPosition = 0;
        
        b.xPosition = 80;
        b.yPosition = 0;
                
        assertTrue(detector.checkLeftCollision(a,b));
        
        a.xPosition = 100;
        a.yPosition = 0;
        
        b.xPosition = 60;
        b.yPosition = 0;
        
        assertFalse(detector.checkUpCollision(a,b));
        
        a.xPosition = 100;
        a.yPosition = 0;
        
        b.xPosition = 120;
        b.yPosition = 0;
        
        assertFalse(detector.checkUpCollision(a,b));
        
    }

    /**
     * Test of checkRightCollision method, of class CollisionDetector.
     */
    @Test
    public void testCheckRightCollision() {
        
        GridObject a = null ;
        GridObject b = null ;
        
        assertFalse(detector.checkLeftCollision(a,b));
        
        a = new GridObject(100,0);
        b = new GridObject(150,0);
        
        assertFalse(detector.checkLeftCollision(a,b));
        
        a.xPosition = 100;
        a.yPosition = 0;
        
        b.xPosition = 80;
        b.yPosition = 0;
                
        assertTrue(detector.checkLeftCollision(a,b));
        
        a.xPosition = 100;
        a.yPosition = 0;
        
        b.xPosition = 60;
        b.yPosition = 0;
        
        assertFalse(detector.checkUpCollision(a,b));
        
        a.xPosition = 100;
        a.yPosition = 0;
        
        b.xPosition = 120;
        b.yPosition = 0;
        
        assertFalse(detector.checkUpCollision(a,b));
    }

    /**
     * Test of checkExplGridObject method, of class CollisionDetector.
     */
    @Test
    public void testCheckExplGridObject() {
       
    }

    /**
     * Test of checkExplEnemies method, of class CollisionDetector.
     */
    @Test
    public void testCheckExplEnemies() {
        
    }

    /**
     * Test of checkExplBricks method, of class CollisionDetector.
     */
    @Test
    public void testCheckExplBricks() {
       Bomb bomb = new Bomb(GridObject.EFFECTIVE_PIXEL_WIDTH * 7,GridObject.EFFECTIVE_PIXEL_HEIGHT * 7);
       
       assertEquals((detector.checkExplBricks(bomb).indexOf(0)),-1);
       
       assertEquals((detector.checkExplBricks(bomb).indexOf(1)),-1);
       
       assertEquals((detector.checkExplBricks(bomb).indexOf(2)),-1);
       
       assertEquals((detector.checkExplBricks(bomb).indexOf(3)),-1);
       
       //int range = bomb.getRightRange();
       
       Brick brick = new Brick(GridObject.EFFECTIVE_PIXEL_WIDTH * 8,GridObject.EFFECTIVE_PIXEL_HEIGHT * 7);
       
       assertEquals((detector.checkExplBricks(bomb).indexOf(0)),-1);
       
       assertEquals(bomb.getRightRange(),0);
        
    }

    /**
     * Test of checkExactCollision method, of class CollisionDetector.
     */
    @Test
    public void testCheckExactCollision() {
      
        
        Bomberman bomberman = new Bomberman(0,0);
        GridObject a = new GridObject(0,0);
        
        assertTrue(detector.checkExactCollision(bomberman, a));
        
        GridObject b = new GridObject(2,2);
        
         assertTrue(detector.checkExactCollision(bomberman, a));
         
           
        Bomberman testNull = null;
        GridObject c = null;
        
        assertFalse(detector.checkExactCollision(testNull, c));
        
    }
    
}
