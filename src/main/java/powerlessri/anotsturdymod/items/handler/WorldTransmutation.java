package powerlessri.anotsturdymod.items.handler;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class WorldTransmutation {

    public static void init(FMLPostInitializationEvent event) {
        registerTransmutation("plant_infusion", Blocks.DIRT, Blocks.GRASS);
        registerTransmutation("hell_of_fires", Blocks.NETHERRACK, Blocks.SOUL_SAND);
        registerTransmutation("particles_party", Blocks.SAND, Blocks.GLASS, Blocks.GRAVEL);

        registerTransmutation("igneous_reform", Blocks.STONE.getDefaultState(), Blocks.COBBLESTONE.getDefaultState(),
                Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.GRANITE),
                Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.DIORITE),
                Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.ANDESITE));
    }

    public static List<WorldTransmutation> transmutations = new ArrayList<WorldTransmutation>();

    public IBlockState[] members;
    private int nextInsertion = 0;

    private String id;

    public WorldTransmutation(String id, int length) {
        this.id = id;
        this.members = new IBlockState[length];
    }

    public String getId() {
        return this.id;
    }

    public int indexOf(IBlockState b) {
        for (int i = 0; i < this.members.length; i++) {
            if (members[i] == b) {
                return i;
            }
        }

        return -1;
    }

    public void addMember(IBlockState block) {
        // Idiot input protection
        if (this.nextInsertion == this.members.length) {
            return;
        }

        members[this.nextInsertion] = block;
        this.nextInsertion++;
    }

    public IBlockState at(int i) {
        return this.members[i];
    }

    /**
     * Register a series of block transmutation, with an <b>unique</b> id. <br />
     * <s>Unfortunately too lazy to check if it is unique</s>
     */
    public static void registerTransmutation(String id, Block... blocks) {
        WorldTransmutation transm = new WorldTransmutation(id, blocks.length);
        Stream.of(blocks).forEach((b) -> {
            transm.addMember(b.getDefaultState());
        });

        transmutations.add(transm);
    }

    public static void registerTransmutation(String id, IBlockState... states) {
        WorldTransmutation transm = new WorldTransmutation(id, states.length);
        Stream.of(states).forEach((s) -> {
            transm.addMember(s);
        });

        transmutations.add(transm);
    }

    /**
     * Get the series of transmutation that includes the targeting block.
     */
    @Nullable
    public static WorldTransmutation getTransmutation(IBlockState includes) {
        for (WorldTransmutation transm : transmutations) {
            if (transm.indexOf(includes) != -1) {
                return transm;
            }
        }

        return null;
    }

    public static IBlockState getTransmutationNext(World world, BlockPos pointer, IBlockState current) {
        return getTransmutationNext(world, pointer, current, 1);
    }

    @Nullable
    public static IBlockState getTransmutationNext(World world, BlockPos pointer, IBlockState current, int offset) {
        WorldTransmutation targetTransm = getTransmutation(current);
        if (targetTransm != null) {
            int next = targetTransm.indexOf(current) + offset;

            // For positive offset
            if (next >= targetTransm.members.length)
                return targetTransm.at(0);

            // For negative offset
            if (next < 0)
                return targetTransm.at(targetTransm.members.length);

            return targetTransm.at(next);
        }

        return null;
    }

}
