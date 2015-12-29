/*
 * Estructura de Datos
 * Arturo Iturriaga García
 * Daniela Becerra González
 * Proyecto de Buscaminas
 * 5/03/15
 */

public class Map {
	
	int[][] map;
	int size;

	public Map(int size, int mines){
		this.size = size;
		int j, 
			k;
		int[][] map = new int[this.size][this.size];
		this.map = map;
		for(int x = 0; x < this.size; x++){
			for(int y = 0; y < this.size; y++){
				this.map[x][y] = 0;
			}
		}
		for(int i = 0; i < mines; i++){
			do{
				j = (int)(Math.random() * this.size);
				k = (int)(Math.random() * this.size);
			}while(map[j][k] == 9);
			map[j][k] = 9;
		}
		
	}

	protected void fillMap(){
		for(int i = 0; i < this.size; i++){
			for(int j = 0; j < this.size; j++){
				this.map[i][j] = countMines(i, j);
			}
		}
	}

	protected int countMines(int i, int j){
		if(this.map[i][j] != 9){
			int minesCount = 0;
			for(int x = i -1; x <= i + 1; x++){
				for(int y = j -1; y <= j + 1; y++){
					if((x >= 0) && (x < this.size) && (y >= 0) && (y < this.size)){
						if(this.map[x][y] == 9)
							minesCount++;
					}
				}
			}
			return minesCount;
		}
		return 9;
	}
	
	public int[][] getMap(){
		return this.map;
	}
	
	public int getSize() {
		return size;
	}

	public static void main(String[] args){
		Map map = new Map(5, 5);
		map.fillMap();
		
		for(int i = 0; i < map.size; i++){
			for(int j = 0; j < map.size; j++){
				System.out.print("[" + map.map[i][j] + "] ");
			}
			System.out.println();
		}
	}
}