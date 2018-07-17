package powerlessri.anotsturdymod.items.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import powerlessri.anotsturdymod.utils.Utils;

public class WorldTransmutation {
	
	public static void init(FMLInitializationEvent event) {
		registerTransmutations("stone_reform", Blocks.STONE, Blocks.COBBLESTONE, Blocks.GRASS);
		registerTransmutations("hell_of_fires", Blocks.NETHERRACK, Blocks.SOUL_SAND);
		registerTransmutations("particles_party", Blocks.SAND, Blocks.GRAVEL);
	}
	
	
	
	public static List<WorldTransmutation> transmutations = new ArrayList<WorldTransmutation>();
	
	
	public Block[] members;
	
	private int nextInserstion = 0;
	private String id;
	
	public WorldTransmutation(String id, int length) {
		this.id = id;
		this.members = new Block[length];
	}
	
	
	public String getId() {
		return this.id;
	}
	
	public int indexOf(Block b) {
		Utils.getLogger().debug("WorldTransmutation.prototype.indexOf: running...");
		for(int i = 0; i < this.members.length; i++) {
			if(members[i] == b) {
				//Utils.getLogger().debug("WorldTransmutation.prototype.indexOf: " + true);
				return i;
			}
		}
		
		return -1;
	}
	
	
	public void addMember(Block block) {
		// Idiot input protection
		if(this.nextInserstion == this.members.length) {
			return;
		}
		
		members[ this.nextInserstion ] = block;
		this.nextInserstion++;
	}
	
	// ======== Writing Optimizations STARTS ======== //
	
	public Block at(int i) {
		return this.members[i];
	}
	
	// ========= Writing Optimizations ENDS ========= //
	
	
	
	/**
	 * Register a series of block transmutation, with an <b>unique</b> id. <br />
	 * <s>Unfortunately too lazy to check if it is unique</s>
	 * 
	 * @param id An unique id
	 * @param members The blocks that the transmutation will include
	 */
	public static void registerTransmutations(String id, Block... members) {
		WorldTransmutation transm = new WorldTransmutation(id, members.length);
		
		for(int i = 0; i < members.length; i++) { 
			transm.addMember(members[i]);
		}
		
		transmutations.add(transm);
	}
	
	/**
	 * Get the series of transmutation that includes the targeting block.
	 * 
	 * @param includes Targeting block
	 * @return The transmutation that includes the targeting block
	 */
	public static WorldTransmutation getTransmutation(Block includes) {
		Utils.getLogger().debug("WorldTransmutation.getTransmutation: running... ");
		for(WorldTransmutation transm : transmutations) {
			for(int i = 0; i < transm.members.length; i++) {
				if(transm.at(i) == includes) {
					//Utils.getLogger().debug("WorldTransmutation.getTransmutation: " + true);
					return transm;
				}
			}
		}
		
		return transmutations.get(0);
	}
	
}
