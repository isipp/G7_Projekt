package client.main;

import java.util.Random;

public class Terrain {
	boolean castle;
	TerrainEnum type;
	
	
	public Terrain(TerrainEnum type,boolean castle) {
		this.type = type;
		this.castle = castle;
	}
	public Terrain(TerrainEnum type) {
		this.type = type;
		this.castle = false;
	}
	public Terrain() {
		this.type = randomTer();
	}


	public boolean isCastle() {
		return castle;
	}


	public TerrainEnum getType() {
		return type;
	}
	
	private TerrainEnum randomTer() {
		
		int pick = new Random().nextInt(TerrainEnum.values().length);
		
		
		return TerrainEnum.values()[pick];
	}
	
}
