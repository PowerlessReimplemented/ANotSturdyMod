package powerlessri.anotsturdymod.items.handlers;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import powerlessri.anotsturdymod.items.handler.WorldTransmutations;

public class WorldTransmutation {
	
	static {
		
	}
	
	public static List<WorldTransmutation> transmutations = new ArrayList<WorldTransmutation>();
	
	
	public WorldTransmutation(String id, Block... members) {
        transmutations.add(this);
    }
	
	private boolean includes(Block targetBlock) {
        return false;
    }
	
	
	
	public static WorldTransmutation getTransmutation(Block targetBlock) {
		for(WorldTransmutation t : transmutations) {
			if(t.includes(targetBlock)) {
				return t;
			}
		}
		return transmutations.get(0);
	}
	
}
