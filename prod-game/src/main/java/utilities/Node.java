package utilities;

public class Node {

	public final double fCost;
	public final double gCost;
	public final int xPosition;
	public final int yPosition;
	public final Node parent;

	public Node(int x, int y, Node p, double HCost, double GCost) {
		parent = p;
		gCost = GCost;
		fCost = HCost + gCost;
		xPosition = x;
		yPosition = y;
	}
}