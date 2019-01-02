package powerlessri.anotsturdymod.items.transmutations;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.BlockStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import powerlessri.anotsturdymod.varia.general.MathUtils;

import java.util.ArrayList;
import java.util.List;

public class WorldTransmutation {

    public static void init(FMLPostInitializationEvent event) {
        transmutations.add(new WorldTransmutation("plant_infusion", Blocks.DIRT.getDefaultState(), Blocks.GRASS.getDefaultState()));
        transmutations.add(new WorldTransmutation("hell_of_fires", Blocks.NETHERRACK.getDefaultState(), Blocks.SOUL_SAND.getDefaultState()));
        transmutations.add(new WorldTransmutation("particles_party", Blocks.SAND.getDefaultState(), Blocks.GLASS.getDefaultState(), Blocks.GRAVEL.getDefaultState()));

        transmutations.add(new WorldTransmutation("igneous_reform", Blocks.STONE.getDefaultState(), Blocks.COBBLESTONE.getDefaultState(),
                Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.GRANITE),
                Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.DIORITE),
                Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.ANDESITE)));
    }


    public static WorldTransmutation EMPTY_TRANSMUTATION = new WorldTransmutation("empty", Blocks.AIR.getDefaultState());
    public static List<WorldTransmutation> transmutations = new ArrayList<>(ImmutableList.of(EMPTY_TRANSMUTATION));

    public static WorldTransmutation findTransmutationContains(IBlockState includes) {
        for (WorldTransmutation transmutation : transmutations) {
            if (transmutation.getBlockStates().contains(includes)) {
                return transmutation;
            }
        }
        return EMPTY_TRANSMUTATION;
    }




    private String name;

    private ImmutableList<IBlockState> blockStates;
    public WorldTransmutation(String id, IBlockState... states) {
        this.name = id;
        this.blockStates = ImmutableList.copyOf(states);
    }


    public String getName() {
        return this.name;
    }

    public ImmutableList<IBlockState> getBlockStates() {
        return blockStates;
    }

    public IBlockState findNextBlockState(IBlockState current, int offset) {
        ImmutableList<IBlockState> state = getBlockStates();
        int currentIndex = state.indexOf(current);
        int next = MathUtils.loopIndexAround(currentIndex + offset, state.size());
        return state.get(next);
    }

    public IBlockState findNextBlockState(IBlockState current) {
        return this.findNextBlockState(current, 1);
    }

}
