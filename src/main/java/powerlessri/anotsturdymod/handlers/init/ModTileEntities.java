package powerlessri.anotsturdymod.handlers.init;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.commons.lang3.tuple.Pair;
import powerlessri.anotsturdymod.varia.Reference;
import powerlessri.anotsturdymod.varia.general.Utils;
import powerlessri.anotsturdymod.varia.reflection.AnnotationRetentionUtils;

import java.util.ArrayList;
import java.util.List;

public class ModTileEntities {

    public static final List<Pair<Class<? extends TileEntity>, ResourceLocation>> TILE_ENTITIES = new ArrayList<>();
    

    public static void preInit(FMLPreInitializationEvent event) {
        loadTileEntities(event.getAsmData());
        registerTileEntities();
    }

    private static void registerTileEntities() {
        for (Pair<Class<? extends TileEntity>, ResourceLocation> tileData : TILE_ENTITIES) {
            Class<? extends TileEntity> clazz = tileData.getLeft();
            ResourceLocation location = tileData.getRight();
            GameRegistry.registerTileEntity(clazz, location);
        }
    }

    private static void loadTileEntities(ASMDataTable table) {
        List<Class<?>> tileCandidates = AnnotationRetentionUtils.getClassesFromASMData(table, RegistryTileEntity.class);
        for (Class<?> candidate : tileCandidates) {
            if (TileEntity.class.isAssignableFrom(candidate)) {
                Class<? extends TileEntity> clazz = (Class<? extends TileEntity>) candidate;
                RegistryTileEntity annotation = clazz.getAnnotation(RegistryTileEntity.class);
                String name = annotation.value();
                ResourceLocation location = new ResourceLocation(Reference.MODID, RegistryHandler.makeTileEntityID(name));

                Pair<Class<? extends TileEntity>, ResourceLocation> tileData = Pair.of(clazz, location);
                TILE_ENTITIES.add(tileData);
            }
        }
    }
    
}
