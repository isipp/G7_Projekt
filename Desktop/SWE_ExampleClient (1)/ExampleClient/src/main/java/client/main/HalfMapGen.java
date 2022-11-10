package client.main;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

import org.springframework.core.codec.AbstractDataBufferDecoder;

public class HalfMapGen {
	//ArrayList[][] table = new ArrayList[3][7];
	Terrain[] half;
	
	public HalfMapGen() {
		half = firstRow();
	}
	
	private Terrain[][] halfgen(){
		Terrain[][] half = new Terrain[3][7];
		
		
		
		
		return half;
	}
	private Terrain[] firstRow() {
		
		Terrain[] buff =  new Terrain[8];

		int water = 0;
		
		for(int i = 0; i<buff.length;) {

			
			Terrain newTer = new Terrain();
			
			if(newTer.type==TerrainEnum.Wasser) {
				water++;
			}
			
			buff[i]=newTer;
			
			if(water>2) {
				i = -1;
				water = 0;
			}
			
			i++;
		}
		
		return buff;
	}
	
	public int sizeOfIt() {
		return half.length;
	}
	public Terrain getTer(int i) {
		
	
		
		return half[i];
	}
	
}


