package powerlessri.anotsturdymod.varia.reflection;

import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.discovery.ASMDataTable.ASMData;
import powerlessri.anotsturdymod.varia.general.Utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AnnotationRetentionUtils {

    public static List<Class<?>> getAllAnnotatedClasses(ASMDataTable table, Class<? extends Annotation> annotation) {
        List<Class<?>> classes = new ArrayList<>();
        for (Class<?> clazz : getClassesFromASMData(table, annotation)) {
            if (clazz.isAnnotationPresent(annotation)) {
                classes.add(clazz);
            }
        }
        return classes;
    }
    
    public static List<Field> getAllAnnotatedFields(ASMDataTable table, Class<? extends Annotation> annotation) {
        List<Field> fields = new ArrayList<>();
        for (Class<?> clazz : getClassesFromASMData(table, annotation)) {
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(annotation)) {
                    fields.add(field);
                }
            }
        }
        return fields;
    }

    public static List<Method> getAllAnnotatedMethods(ASMDataTable table, Class<? extends Annotation> annotation) {
        List<Method> methods = new ArrayList<>();
        for (Class<?> clazz : getClassesFromASMData(table, annotation)) {
            for (Method method : clazz.getDeclaredMethods()) {
                method.setAccessible(true);
                if (method.isAnnotationPresent(annotation)) {
                    methods.add(method);
                }
            }
        }
        return methods;
    }
    

    public static List<Class<?>> getClassesFromASMData(ASMDataTable table, Class<? extends Annotation> annotation) {
        return getClassesFromASMData(table.getAll(annotation.getName()));
    }

    private static List<Class<?>> getClassesFromASMData(Set<ASMData> dataSet) {
        List<Class<?>> classes = new ArrayList<>();
        for (ASMData data : dataSet) {
            String className = data.getClassName();
            Class<?> clazz;
            try {
                clazz = Class.forName(className);
            } catch (ClassNotFoundException e) {
                // This should never happen
                Utils.report("The class " + className + " was found when FML building mods, but can't be found anymore during ANotSturdyMod template system's start up.", e);
                continue;
            }

            classes.add(clazz);
        }
        return classes;
    }
    
}
