package gameplayModel.gridObjects;

public interface HiddenObject {

	static int generateIndex(int size) {
		return (int) (Math.random() * size);
	}
}
