package powerlessri.anotsturdymod.handlers;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.network.IGuiHandler;
import powerlessri.anotsturdymod.blocks.gui.api.template.ITemplate;
import powerlessri.anotsturdymod.blocks.gui.api.template.TemplateProvider;
import powerlessri.anotsturdymod.blocks.gui.base.ComponentizedGui;
import powerlessri.anotsturdymod.varia.general.Utils;

import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

public class ComponentizedGuiHandler implements IGuiHandler {
    
    private final Object mod;
    
    public ImmutableList<ITemplate> templates;
    public ImmutableMap<String, Integer> keys;

    public ComponentizedGuiHandler(Object mod) {
        this.mod = mod;
    }

    /**
     * Load templates by invoking methods annotated by {@link TemplateProvider}.
     */
    public void init(ASMDataTable table) {
        Utils.getLogger().info("Loading template providers");
        
        Set<ASMDataTable.ASMData> dataSet = table.getAll(TemplateProvider.class.getName());
        
        ImmutableList.Builder<ITemplate> templatesBuilder = ImmutableList.builder();
        ImmutableMap.Builder<String, Integer> keysBuilder = ImmutableMap.builder();
        int lastID = -1;
        
        for (ASMDataTable.ASMData data : dataSet) {
            String className = data.getClassName();
            Class<?> clazz;
            try {
                clazz = Class.forName(className);
            } catch (ClassNotFoundException e) {
                // This should never happen
                Utils.report("The class " + className + " was found when FML building mods, but can't be found anymore during ANotSturdyMod template system's start up.", e);
                continue;
            }
            
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                method.setAccessible(true);

                if (method.isAnnotationPresent(TemplateProvider.class)) {
                    TemplateProvider annotation = method.getAnnotation(TemplateProvider.class);
                    String id = annotation.id();
                    
                    // Make it null so that the compiler is happy when we check its existence later.
                    ITemplate template = null;
                    try {
                        template = (ITemplate) method.invoke(null);
                    } catch (ClassCastException e) {
                        Utils.getLogger().error("Method specified as a TemplateProvider, but returns a different type. ", e);
                        continue;
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        Utils.getLogger().error(e);
                        continue;
                    }

                    // Existence check, filter situations where the getter magically returns null
                    if (template != null) {
                        templatesBuilder.add(template);
                        keysBuilder.put(id, ++lastID);
                    }
                }
            }
        }
        
        templates = templatesBuilder.build();
        keys = keysBuilder.build();
    }


    @Nullable
    @Override
    public Container getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        if (id >= 0 && id < templates.size()) {
            checkParameters(id, player, world, x, y, z);
            return templates.get(id).getContainer();
        }

        return null;
    }

    @Nullable
    @Override
    public ComponentizedGui getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        if (id >= 0 && id < templates.size()) {
            checkParameters(id, player, world, x, y, z);
            return templates.get(id).getGui();
        }

        return null;
    }

    /**
     * Ensures parameter used to create the container and the GUI are the same.
     */
    private void checkParameters(int id, EntityPlayer player, World world, int x, int y, int z) {
        // TODO check if it applied same parameters before.
        templates.get(id).applyParameters(player, world, x, y, z);
    }


    public void openGui(String name, EntityPlayer player, BlockPos pos) {
        openGui(name, player, pos.getX(), pos.getY(), pos.getZ());
    }

    public void openGui(String name, EntityPlayer player, World world, BlockPos pos) {
        openGui(name, player, world, pos.getX(), pos.getY(), pos.getZ());
    }

    public void openGui(String name, EntityPlayer player, int x, int y, int z) {
        openGui(name, player, player.world, x, y, z);
    }

    public void openGui(String name, EntityPlayer player, World world, int x, int y, int z) {
        int id = keys.get(name);
        player.openGui(mod, id, world, x, y, z);
    }

}
