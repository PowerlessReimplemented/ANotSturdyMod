package powerlessri.anotsturdymod.handlers;

import com.google.common.collect.ImmutableList;
import com.google.common.reflect.ClassPath;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import powerlessri.anotsturdymod.blocks.gui.api.template.ITemplate;
import powerlessri.anotsturdymod.blocks.gui.api.template.TemplateProvider;
import powerlessri.anotsturdymod.blocks.gui.base.ComponentizedGui;
import powerlessri.anotsturdymod.varia.Reference;
import powerlessri.anotsturdymod.varia.general.Utils;

import javax.annotation.Nullable;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ComponentizedGuiHandler implements IGuiHandler {

    /**
     * Last {@code ID} passed in by either {@link #getServerGuiElement(int, EntityPlayer, World, int, int, int)} or 
     * {@link #getClientGuiElement(int, EntityPlayer, World, int, int, int)}.
     * Used to update applied information (to the ITemplate) as needed.
     */
    private int lastRequestId;
    private ImmutableList<ITemplate> templates;
    
    public ComponentizedGuiHandler() {
        init();
    }

    /**
     * Load templates from all classes inside {@link Reference#MOD_PACKAGE_NAME}
     * Templates are provided by annotating any public method as {@link TemplateProvider}
     */
    private void init() {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        ClassPath classes;
        try {
            classes = ClassPath.from(classLoader);
        } catch (IOException e) {
            Utils.report("Failed to create ClassPath object!", e);
            return;
        }

        List<ITemplate> loaderTemplates = new ArrayList<>();
        for (ClassPath.ClassInfo info : classes.getTopLevelClassesRecursive(Reference.MOD_PACKAGE_NAME)) {
            Class<?> clazz = info.getClass();
            Method[] methods = clazz.getDeclaredMethods();
            
            for (int i = 0; i < methods.length; i++) {
                Method method = methods[i];

                if (method.isAnnotationPresent(TemplateProvider.class)) {
                    ITemplate template = null; // Make it null so that the compiler is happy when we check its existence later.
                    
                    try {
                        template = (ITemplate) method.invoke(null);
                    } catch (ClassCastException e) {
                        Utils.getLogger().error("Method specified as a TemplateProvider, but returns a different type. ", e);
                        continue;
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                        continue;
                    }

                    if (template != null) {
                        loaderTemplates.add(template);
                    }
                }
            }
        }
    
        templates = ImmutableList.copyOf(loaderTemplates);
    }


    @Nullable
    @Override
    public Container getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        if (id < templates.size()) {
            checkParameters(id, player, world, x, y, z);
            return templates.get(id).getContainer();
        }
        
        return null;
    }

    @SideOnly(Side.CLIENT)
    @Nullable
    @Override
    public ComponentizedGui getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        if (id < templates.size()) {
            checkParameters(id, player, world, x, y, z);
            return templates.get(id).getGui();
        }
        
        return null;
    }

    /**
     * Ensures parameter used to create the container and the GUI are the same.
     * Ensures 
     */
    private void checkParameters(int id, EntityPlayer player, World world, int x, int y, int z) {
        if (id != lastRequestId) {
            templates.get(id).applyParameters(player, world, x, y, z);
            lastRequestId = id;
        }
    }

}
