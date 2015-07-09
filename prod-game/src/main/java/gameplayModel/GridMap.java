package gameplayModel;

import gameplayController.GameplayController;

import java.util.ArrayList;

public class GridMap {
	
    public static final int MAPWIDTH = 31;
    public static final int MAPHEIGHT = 13;
    
    public final int SPAWN_TIMEOUT = 10*1000;
    
    private int width = GridObject.EFFECTIVE_PIXEL_WIDTH;
	private int height = GridObject.EFFECTIVE_PIXEL_HEIGHT;
	private int brickExitIndex, spawnTimer;
	
	private int[] levelSpec;
	
	private ArrayList<Concrete> concreteLayout;
	private ArrayList<Brick> bricks;
	private ArrayList<Bomb> bombs;
	private ArrayList<Enemy> enemies;
	private Exitway exitway;
	private PowerUp powerup;
	
	private Bomberman bomberman;
	
	public GridMap(int[] levelSpecification) {
		
		levelSpec = levelSpecification;
		
		spawnTimer = SPAWN_TIMEOUT;
        
        concreteLayout = new ArrayList<Concrete>();
        bricks = new ArrayList<Brick>();
        bombs = new ArrayList<Bomb>();
        enemies = new ArrayList<Enemy>();
        
        generateMap();
        
        populateMap(levelSpec);
	}
	
	/**
	 * Used for GridMap Initialization based on a series of level specification
	 * 
	 * @param spawnTimer sync with the gameplay timer
	 * @param bricks the location and number of bricks on each level
	 * @param bombs the location, number and types of bombs on each level
	 * @param enemies the location, number and types of enemies on each level
	 * @param exitway the location of exitway on each level
	 * @param powerup the location, number and types of PowerUps on each level
	 * @param bomberman the location of bomberman object
	 */
	public GridMap(int spawnTimer, ArrayList<Brick> bricks, ArrayList<Bomb> bombs, ArrayList<Enemy> enemies, Exitway exitway, PowerUp powerup, Bomberman bomberman) {
		
		this.spawnTimer = spawnTimer;
        
        concreteLayout = new ArrayList<Concrete>();
        this.bricks = bricks;
        this.bombs = bombs;
        this.enemies = enemies;
        this.bomberman = bomberman;
        this.exitway = exitway;
        this.powerup = powerup;
        
        generateMap();
	}
	
	private void generateMap() {
		
		int width = GridObject.EFFECTIVE_PIXEL_WIDTH;
		int height = GridObject.EFFECTIVE_PIXEL_HEIGHT;
		
		for (int i = 0 ; i < MAPWIDTH ; i++) {
			concreteLayout.add(new Concrete(i * width, 0));
			concreteLayout.add(new Concrete(i * width, (MAPHEIGHT - 1) * height));
		}
		
		for (int i = 1 ; i < MAPHEIGHT - 1 ; i++){
			concreteLayout.add(new Concrete(0, i * height));
			concreteLayout.add(new Concrete((MAPWIDTH - 1) * width, i * height));
		}
		
		for (int i = 2 ; i < MAPWIDTH - 2 ; i += 2){
			for (int j = 2 ; j < MAPHEIGHT - 2 ; j += 2)
				concreteLayout.add(new Concrete(i * width, j * height));
		}
	}
	
	private void populateMap(int[] levelSpec) {
		
		this.bomberman = new Bomberman(width, height);
		
		if (levelSpec[8] != 0) {
			
			distributeBricks();
			addExitway();
			addPowerup(levelSpec[8]);
			generateEnemies(levelSpec);
		} else {
			spawnMoreEnemies(levelSpec);
		}
		
		
	}
    
	private void distributeBricks () {
		
		double p = 0.225;
		
		for (int i = 1 ; i < MAPHEIGHT ; i += 2) {
			
			for(int j = 1 ; j < MAPWIDTH - 1 ; j++) {
				
				if (Math.random() < p && !(i == 1 && j == 1) && !(i == 1 && j == 2))
					bricks.add(new Brick(width * j, height * i));
			}
		}
		
		for (int i = 2 ; i < MAPHEIGHT - 1 ; i += 2) {
			
			for (int j = 1 ; j < MAPWIDTH - 1 ; j += 2) {
				
				if (Math.random() < p && !(i == 2 && j == 1))
					bricks.add(new Brick(width * j, height * i));
			}
		}
	}
	
	private void addExitway() {
		
		brickExitIndex = (int) (Math.random() * bricks.size());
		
		exitway = new Exitway(bricks.get(brickExitIndex).getXPosition(), bricks.get(brickExitIndex).getYPosition());
	}
	
	private void addPowerup(int type) {
		
		
		int brickPowerupIndex = (int) (Math.random() * bricks.size());
		
		while (brickPowerupIndex == brickExitIndex)
			brickPowerupIndex = (int) (Math.random() * bricks.size());
		
		switch (type) {
		case 1:
			powerup = new BombPU(bricks.get(brickPowerupIndex).getXPosition(), bricks.get(brickPowerupIndex).getYPosition());
			break;
		case 2:
			powerup = new Flames(bricks.get(brickPowerupIndex).getXPosition(), bricks.get(brickPowerupIndex).getYPosition());
			break;
		case 3:
			powerup = new Speed(bricks.get(brickPowerupIndex).getXPosition(), bricks.get(brickPowerupIndex).getYPosition());
			break;
		case 4:
			powerup = new Wallpass(bricks.get(brickPowerupIndex).getXPosition(), bricks.get(brickPowerupIndex).getYPosition());
			break;
		case 5:
			powerup = new Detonator(bricks.get(brickPowerupIndex).getXPosition(), bricks.get(brickPowerupIndex).getYPosition());
			break;
		case 6:
			powerup = new Bombpass(bricks.get(brickPowerupIndex).getXPosition(), bricks.get(brickPowerupIndex).getYPosition());
			break;
		case 7:
			powerup = new Flamepass(bricks.get(brickPowerupIndex).getXPosition(), bricks.get(brickPowerupIndex).getYPosition());
			break;
		case 8:
			powerup = new Mystery(bricks.get(brickPowerupIndex).getXPosition(), bricks.get(brickPowerupIndex).getYPosition());
			break;
		}
	}
	
	/**
	 * generate the enemies on GridMap
	 * 
	 * @param levelSpec level specification regulating the numbers and types of enemies
	 */
	public void generateEnemies(int[] levelSpec) {
		
		int[] position = new int[2];
		
		for (int i = 0 ; i < levelSpec[0] ; i++) {
			position = findEnemyLocation();
			enemies.add(new Balloom(position[0] * width, position[1] * height));
		}
		
		for (int i = 0 ; i < levelSpec[1] ; i++) {
			position = findEnemyLocation();
			enemies.add(new Oneal(position[0] * width, position[1] * height));
		}
		
		for (int i = 0 ; i < levelSpec[2] ; i++) {
			position = findEnemyLocation();
			enemies.add(new Doll(position[0] * width, position[1] * height));
		}
		
		for (int i = 0 ; i < levelSpec[3] ; i++) {
			position = findEnemyLocation();
			enemies.add(new Minvo(position[0] * width, position[1] * height));
		}
		
		for (int i = 0 ; i < levelSpec[4] ; i++) {
			position = findEnemyLocation();
			enemies.add(new Kondoria(position[0] * width, position[1] * height));
		}
		
		for (int i = 0 ; i < levelSpec[5] ; i++) {
			position = findEnemyLocation();
			enemies.add(new Ovapi(position[0] * width, position[1] * height));
		}
		
		for (int i = 0 ; i < levelSpec[6] ; i++) {
			position = findEnemyLocation();
			enemies.add(new Pass(position[0] * width, position[1] * height));
		}
		
		for (int i = 0 ; i < levelSpec[7] ; i++) {
			position = findEnemyLocation();
			enemies.add(new Pontan(position[0] * width, position[1] * height));
		}
			
	}
	
	private void spawnMoreEnemies(int[] levelSpec) {
		
		int i = 0;
		
		while (levelSpec[i] >= 0) {
			i++;
		}
		
		int[] spec = new int[8];
		
		spec[i] = 8;
		
		generateEnemies(spec);
	}
	
	private int[] findEnemyLocation() {
		
		int[] location = new int[2];
		
		location = generateRandomLocation();
		
		int i = 0;
		
		while (i < bricks.size()) {
			
			if (location[0] * width == bricks.get(i).getXPosition() && location[1] * height == bricks.get(i).getYPosition()) {
				location = generateRandomLocation();
				i = 0;
			} else {
				i++;
			}
		}
		return location;
	}
	
	private int[] generateRandomLocation() {
		
		int[] location = new int[2];
		
		double row = Math.random();
		
		if (row >= 0.5) {
			location[0] = ((int) (Math.random() * 29)) + 1;
			location[1] = ((int) (Math.random() * 6))*2 + 1;
		} else {
			location[0] = ((int) (Math.random() * 15))*2 + 1;
			location[1] = ((int) (Math.random() * 5))*2 + 2;
		}
		return location;
	}
	
	/**
	 * Controls the SpawneTimer in sync with gameplay timer
	 */
	
	public void decreaseSpawnTimer() {
		spawnTimer -= GameplayController.TIMEOUT;
		
		if (spawnTimer <= 0) {
			spawnMoreEnemies(levelSpec);
			spawnTimer = SPAWN_TIMEOUT;
		}
	}
	
	public Bomberman getBomberman(){
		return bomberman;
	}
	
	public ArrayList<Concrete> getConcreteLayout(){
		return concreteLayout;
	}
	
	public ArrayList<Brick> getBricks() {
		return bricks;
	}
	
	public PowerUp getPowerUps(){
		return powerup;
	}
	
	public ArrayList<Bomb> getBombs() {
		return bombs;
	}
	
	public ArrayList<Enemy> getEnemies() {
		return enemies;
	}
	
	public Exitway getExitway() {
		return exitway;
	}
	
	/**
	 * Prepare each GridObject for saving into CSV files
	 * 
	 * @return an array list of different array lists of GridObject eg. enemy, powerup, etc... ready for CSV writing
	 */
	
	public ArrayList<String> toCSVEntry() {
		
		ArrayList<String> entryList = new ArrayList<String>();
		
		entryList.add(Integer.toString(spawnTimer));
		entryList.add("Bricks");
		
		for (Brick brick : bricks) {
			
			for (String token : brick.toCSVEntry())
				entryList.add(token);
		}
		
		entryList.add("Bombs");
		entryList.add(Integer.toString(Bomb.getRange()));
		
		for (Bomb bomb : bombs) {
			if (!bomb.isDead()) {
				for (String token : bomb.toCSVEntry())
					entryList.add(token);
			}
		}
		
		entryList.add("Enemies");
		
		for (Enemy enemy : enemies) {
			
			for (String token : enemy.toCSVEntry())
				entryList.add(token);
		}
		
		entryList.add("Exitway");
		
		for (String token : exitway.toCSVEntry())
			entryList.add(token);
		
		entryList.add("PowerUp");
		
		for (String token : powerup.toCSVEntry())
			entryList.add(token);
		
		entryList.add("Bomberman");
		
		for (String token : bomberman.toCSVEntry())
			entryList.add(token);

		return entryList; 
	}
}
