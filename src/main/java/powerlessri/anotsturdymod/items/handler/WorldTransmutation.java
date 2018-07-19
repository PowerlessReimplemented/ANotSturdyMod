package powerlessri.anotsturdymod.items.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

public class WorldTransmutation {
	
	public static void init(FMLInitializationEvent event) {
		registerTransmutations("inter_smeltery", Blocks.STONE, Blocks.COBBLESTONE, Blocks.GRASS);
		registerTransmutations("hell_of_fires", Blocks.NETHERRACK, Blocks.SOUL_SAND);
		registerTransmutations("particles_party", Blocks.SAND, Blocks.GLASS);
		
//		registerTransmutations(EnumTransmutationType.TRANSFORMAL, "igneous_reform", (state) -> state.getActualState(blockAccess, pos));
	}
	
	
	
	public static List<WorldTransmutation> transmutations = new ArrayList<WorldTransmutation>();
	public static enum EnumTransmutationType {
		
	    /** Given a list of blocks, and cycle through them */
		EXCHANGE(),
		/** Given a single IBlockState and a condition, cycle through the metas */
		TRANSFORMAL();
		
	}
	
	
	public IBlockState[] members;
	private int nextInserstion = 0;
	
	private String id;
	private EnumTransmutationType type;
	
	/** EnumTransmutationType.EXCHANGE */
	public WorldTransmutation(EnumTransmutationType type, String id, int length) {
		this.type = type;
		this.id = id;
		this.members = new IBlockState[length];
	}
	/** EnumTransmutationType.TRANSFORMAL */
	public WorldTransmutation(EnumTransmutationType type, String id, IBlockState baseState) {
	    this.type = type;
	    this.id = id;
	}
	
	
	public String getId() {
		return this.id;
	}
	public EnumTransmutationType getType() {
		return this.type;
	}
	
	// ======== Type: EXCHANGE starts ======== //
	
	public int indexOf(IBlockState b) {
	    if(this.type != EnumTransmutationType.EXCHANGE)
	        return -1;
	    
		for(int i = 0; i < this.members.length; i++) {
			if(members[i] == b) {
				return i;
			}
		}
		
		return -1;
	}
	
	public void addMember(IBlockState block) {
	    if(this.type != EnumTransmutationType.EXCHANGE)
            return;
	    
		// Idiot input protection
		if(this.nextInserstion == this.members.length) {
			return;
		}
		
		members[ this.nextInserstion ] = block;
		this.nextInserstion++;
	}
	
	public IBlockState at(int i) {
	    if(this.type != EnumTransmutationType.EXCHANGE)
            return null;
	    
		return this.members[i];
	}
	
	// ======== Type: EXCHANGE ends ======== //
	
	
	
	/**
	 * Register a series of block transmutation, with an <b>unique</b> id. <br />
	 * <s>Unfortunately too lazy to check if it is unique</s>
	 * 
	 * @param id An unique id
	 * @param members The blocks that the transmutation will include
	 */
	public static void registerTransmutations(String id, Block... members) {
		WorldTransmutation transm = new WorldTransmutation(EnumTransmutationType.EXCHANGE, id, members.length);
		
		for(int i = 0; i < members.length; i++) { 
			transm.addMember(members[i].getDefaultState());
		}
		
		transmutations.add(transm);
	}
	
	public static void registerTransmutations(String id, IBlockState baseState, Predicate<IBlockState> cond) {
		WorldTransmutation transm = new WorldTransmutation(EnumTransmutationType.TRANSFORMAL, id, baseState);
		
		transmutations.add(transm);
	}
	
	
	
	/**
	 * Get the series of transmutation that includes the targeting block.
	 * 
	 * @param includes Targeting block
	 * @return The transmutation that includes the targeting block
	 */
	public static WorldTransmutation getTransmutation(IBlockState includes) {
		getTransmutationSuccessed = true;
		
		for(WorldTransmutation transm : transmutations) {
			for(int i = 0; i < transm.members.length; i++) {
				if(transm.at(i).getBlock() == includes.getBlock()) {
					return transm;
				}
			}
		}
		
		getTransmutationSuccessed = false;
		return transmutations.get(0);
	}
	
	public static IBlockState getTransmutationNext(World world, BlockPos pointer, IBlockState current) {
		WorldTransmutation targetTransm = getTransmutation(current);
		if(getTransmutationSuccessed) {
		    int next = targetTransm.indexOf(current) + 1;
		    if(next >= targetTransm.members.length)
		        return targetTransm.at(0);
			return targetTransm.at(next);
		} else {
			return null;
		}
		
		//return null;
	}
	private static boolean getTransmutationSuccessed;
	
	
}
