package powerlessri.anotsturdymod.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import powerlessri.anotsturdymod.container.ContainerRemoteEnergyCell;
import powerlessri.anotsturdymod.library.TriFunction;

public class ModGuiHandler implements IGuiHandler {
    
    public static int REMOTE_ENERGY_CELL = 0;
    
    private List<TriFunction<World, EntityPlayer, BlockPos, ? extends GuiScreen>> guiFactories;
    
    public ModGuiHandler() {
        this.guiFactories = new ArrayList<>();
        
        this.guiFactories.add((world, player, pos) -> new GuiRemoteEnergyCell(
                this.getServerGuiElement(REMOTE_ENERGY_CELL, player, world, pos.getX(), pos.getY(), pos.getZ()),
                player.inventory));
    }

    @Override
    public Container getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        switch(id) {
        case 0: return new ContainerRemoteEnergyCell();
        }
        
        return null;
    }

    @Override
    public GuiScreen getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        if(id < this.guiFactories.size()) {
            return this.guiFactories.get(id).apply(world, player, new BlockPos(x, y, z));
        }
        
        throw new IllegalArgumentException("The given GUI id \'" + id + "\' does not exist!");
    }

}
