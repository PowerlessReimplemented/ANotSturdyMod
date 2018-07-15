package powerlessri.anotsturdymod;

import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import powerlessri.anotsturdymod.init.ModItems;
import powerlessri.anotsturdymod.items.ItemExchanger;
import powerlessri.anotsturdymod.proxy.CommonProxy;
import powerlessri.anotsturdymod.utils.Reference;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class ANotSturdyMod {
	
	@Mod.Instance(Reference.MODID)
	public static ANotSturdyMod instance;
	
	@SidedProxy(serverSide = Reference.SERVER_PROXY_CLASS, clientSide = Reference.CLIENT_PROXY_CLASS)
	public static CommonProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		//Utils.getLogger().info("preinit...");
		
		ModItems.registerItems(new String[] {
			"null_item",
			"obsidian_ingot",
			"redstone_ingot",
			"glowstone_ingot",
		}, CreativeTabs.MISC);
		
		ModItems.ITEMS.add(new ItemExchanger("magical_exchanger", CreativeTabs.TOOLS) {
			@Override
			public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
				
				if(world.isRemote) return EnumActionResult.SUCCESS;
				
				//if(hand == EnumHand.OFF_HAND) return EnumActionResult.FAIL;
				
				IBlockState STONE = Blocks.STONE.getBlockState().getBaseState();
				IBlockState COBBLESTONE = Blocks.COBBLESTONE.getBlockState().getBaseState();
				
				
				for(BlockPos changingPos : getAffectedBlocks(world, pos, player.isSneaking())) {
					IBlockState target = world.getBlockState(changingPos);
					if(target == STONE) {
						world.setBlockState(changingPos, COBBLESTONE);
					} else if(target == COBBLESTONE) {
						world.setBlockState(changingPos, STONE);
					} else {
						return EnumActionResult.FAIL;
					}
				}
				
				return EnumActionResult.SUCCESS;
				
			}
			
			private Iterable<BlockPos> getAffectedBlocks(World world, BlockPos pos, boolean sneaking) {
				Iterable<BlockPos> iterator;
				
				if(sneaking) {
					iterator = BlockPos.getAllInBox(pos.add(1, 0, 1), pos.add(-1, 0, -1));
				} else {
					iterator = BlockPos.getAllInBox(pos, pos);
				}
				
				return iterator;
			}
		});
		
		ModItems.ITEMS.add(new ItemExchanger("exchanger", CreativeTabs.TOOLS));
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		//Utils.getLogger().info("init...");
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		//Utils.getLogger().info("postinit...");
	}
	
}
