package powerlessri.anotsturdymod.handlers;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import powerlessri.anotsturdymod.mechanisms.remote_enetwork.gui.GuiENWirelessTransmitter;
import powerlessri.anotsturdymod.mechanisms.remote_enetwork.gui.GuiEnergyIOAccess;
import powerlessri.anotsturdymod.mechanisms.remote_enetwork.gui.ContainerEnergyIOAccess;
import powerlessri.anotsturdymod.mechanisms.remote_enetwork.tile.TileENComponentBase;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class VanillaGuiHandler implements IGuiHandler {

    public static final int ENERGY_ACCESS_PORT = 0;
    public static final int ENERGY_WIRELESS_TRANSMITTER = 1;


    private List<BiFunction<EntityPlayer, BlockPos, Container>> containerSupplier = new ArrayList<>();
    private List<Function<Container, GuiScreen>> guiSupplier = new ArrayList<>();

    public VanillaGuiHandler() {
        BiFunction<EntityPlayer, BlockPos, Container> containerEnergyIOAccess = (player, pos) -> {
            TileENComponentBase tile = (TileENComponentBase) player.world.getTileEntity(pos);
            ContainerEnergyIOAccess container = new ContainerEnergyIOAccess(player, tile);
            return container;
        };

        addGuiSupplier(containerEnergyIOAccess,
                (container) -> new GuiEnergyIOAccess((ContainerEnergyIOAccess) container));
        addGuiSupplier(containerEnergyIOAccess,
                (container) -> new GuiENWirelessTransmitter((ContainerEnergyIOAccess) container));
    }


    private void addGuiSupplier(Supplier<GuiScreen> gui) {
        addGuiSupplier(null, (container) -> gui.get());
    }

    private void addGuiSupplier(BiFunction<EntityPlayer, BlockPos, Container> container, Function<Container, GuiScreen> gui) {
        containerSupplier.add(container);
        guiSupplier.add(gui);
    }


    @Nullable
    @Override
    public Container getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        if (id < containerSupplier.size()) {
            return containerSupplier.get(id).apply(player, new BlockPos(x, y, z));
        }

        return null;
    }

    @Nullable
    @Override
    public GuiScreen getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        if (id < guiSupplier.size()) {
            Container container = getServerGuiElement(id, player, world, x, y, z);
            return guiSupplier.get(id).apply(container);
        }

        return null;
    }

}
