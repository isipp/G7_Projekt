package client.main;



public class HalfMapGen {
	Terrain[][] table;
	
	public HalfMapGen() {
		this.table = halfgen();
	}


	private Terrain[][] halfgen(){
		Terrain[][] half = rawgen();

		while((checkAmountTerrain(half)==false)||(borderCheck(half)==false)||(numIslands(half)>1)){

			half = rawgen();
			System.out.println("Again");
		}


		return half;
	}
	
	private Terrain[][] rawgen(){
		Terrain[][] half = new Terrain[4][8];

		for(int i = 0 ; i< half.length;i++){
			for(int l = 0; l<half[i].length;l++){
				half[i][l] = new Terrain();
			}
		}
		return half;
	}


	public String printHalf(){
		String out = "";

		for(int i = 0 ; i< table.length;i++){
			for(int l = 0; l<table[i].length;l++){
				String buffString = "";
				if(table[i][l].type==TerrainEnum.Wasser){
					buffString = "W,";
				}
				if(table[i][l].type==TerrainEnum.Berg){
					buffString = "B,";
				}
				if(table[i][l].type==TerrainEnum.Wiesen){
					buffString = "V,";
				}
				out = out + buffString;
			}
			out = out + "\n";
		}
		return out;
	}

	private boolean checkAmountTerrain(Terrain[][] half){
		boolean check;
		int water = 0;
		int wiesen = 0;
		int berg = 0;
		for(int i = 0; i<half.length; i++){
			for(int l = 0; l< half[i].length;l++){
				if(half[i][l].type==TerrainEnum.Wasser){
					water++;
				}
				if(half[i][l].type==TerrainEnum.Berg){
					berg++;
				}
				if(half[i][l].type==TerrainEnum.Wiesen){
					wiesen++;
				}
			}
		}
		if((berg>=3)&&(wiesen>=15)&&(water>=4)){
			check = true;
		}else{
			check = false;
		}
		System.out.println("water " + water);
		System.out.println("wiesen " + wiesen);
		System.out.println("berg " + berg);

		return check;
	}

	private boolean borderCheck(Terrain[][] half){
		boolean check;
		int countShortLeft = 0;
		int countShortRight = 0;

		for(int i= 0; i< half.length; i++){
			if(half[i][0].getType()==TerrainEnum.Wasser){
				countShortLeft++;
			}
			if(half[i][7].getType()==TerrainEnum.Wasser){
				countShortRight++;
			}
		}
		int countUp = 0;
		int countDown = 0;

		for(int i = 0 ; i<half[3].length;i++){
			if(half[0][i].getType()==TerrainEnum.Wasser){
				countUp++;
			}
			if(half[3][i].getType()==TerrainEnum.Wasser){
				countDown++;
			}
		}


		if((countShortLeft<=1)&&(countShortRight<=1)&&(countUp<=3)&&(countDown<=3)){
			check = true;
		}else {
			check = false;
		}
		System.out.println("Left " + countShortLeft);
		System.out.println("Right  " + countShortRight);
		System.out.println("Up  " + countUp);
		System.out.println("Down  " + countDown);

		return  check;
	}

	private char[][] terConvert(Terrain[][] grid){
		char[][] gridDone = new char[4][8];
		for(int i = 0; i<grid.length;i++){
			for(int l = 0; l < grid[i].length;l++){
				if(grid[i][l].getType()==TerrainEnum.Wasser){
					gridDone[i][l] = '0';
				}else{
					gridDone[i][l] = '1';
				}
			}
		}

		return gridDone;
	}

	public int numIslands(Terrain[][] gridRaw) {
		char[][] grid = terConvert(gridRaw);
		if(grid==null || grid.length==0){
			return 0;
		}
		int numIslands=0;
		for(int i=0;i<grid.length;i++){
			for(int j=0;j<grid[i].length;j++){
				if(grid[i][j]=='1'){
					numIslands+=dfs(grid,i,j);
				}
			}
		}
		System.out.println("ISLAND " + numIslands);
		return numIslands;
	}

	public int dfs(char[][] grid,int i,int j){
		if(i<0 || i>=grid.length || j<0 || j>=grid[i].length || grid[i][j]=='0'){
			return 0;
		}
		grid[i][j]='0';
		dfs(grid,i+1,j); //up
		dfs(grid,i-1,j); //down
		dfs(grid,i,j+1); //right
		dfs(grid,i,j-1); //left
		return 1;
	}
	public Terrain[][] get_half(){
		return table;
	}

}


