
package utility;

/**
 *
 * @author Jonti
 */
public class Node {

	public double fCost;
	public double hCost;
	public double gCost;
        public int xPosition;
        public int yPosition;
	
	public Node parent;
	
	public Node(int x, int y, Node p, double HCost, double GCost){
		
		parent = p;
		hCost = HCost;
		gCost = GCost;
		fCost = hCost + gCost;
                xPosition = x;
                yPosition = y;
		
	}
}