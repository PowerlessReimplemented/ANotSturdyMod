package powerlessri.anotsturdymod.varia.reflection;

public class AnnotationSearcher {

//    public static List<Class<?>> getAllAnnotatedClasses(ASMDataTable table, Class<? extends Annotation> annotation) {
//        List<Class<?>> classes = new ArrayList<>();
//        for (Class<?> clazz : getRawClassesFromASMData(table, annotation)) {
//            if (clazz.isAnnotationPresent(annotation)) {
//                classes.add(clazz);
//            }
//        }
//        return classes;
//    }
//
//    public static List<Field> getAllAnnotatedFields(ASMDataTable table, Class<? extends Annotation> annotation) {
//        List<Field> fields = new ArrayList<>();
//        for (Class<?> clazz : getRawClassesFromASMData(table, annotation)) {
//            for (Field field : clazz.getDeclaredFields()) {
//                field.setAccessible(true);
//                if (field.isAnnotationPresent(annotation)) {
//                    fields.add(field);
//                }
//            }
//        }
//        return fields;
//    }
//
//    public static List<Method> getAllAnnotatedMethods(ASMDataTable table, Class<? extends Annotation> annotation) {
//        List<Method> methods = new ArrayList<>();
//        for (Class<?> clazz : getRawClassesFromASMData(table, annotation)) {
//            for (Method method : clazz.getDeclaredMethods()) {
//                method.setAccessible(true);
//                if (method.isAnnotationPresent(annotation)) {
//                    methods.add(method);
//                }
//            }
//        }
//        return methods;
//    }
//
//
//    public static List<Class<?>> getRawClassesFromASMData(ASMDataTable table, Class<? extends Annotation> annotation) {
//        return getRawClassesFromASMData(table.getAll(annotation.getName()));
//    }
//
//    private static List<Class<?>> getRawClassesFromASMData(Set<ASMData> dataSet) {
//        List<Class<?>> classes = new ArrayList<>();
//        for (ASMData data : dataSet) {
//            String className = data.getClassName();
//            Class<?> clazz;
//            try {
//                clazz = Class.forName(className);
//            } catch (ClassNotFoundException e) {
//                // This should never happen
//                Utils.report("The class " + className + " was found when FML building mods, but can't be found anymore during ANotSturdyMod template system's start up.", e);
//                continue;
//            }
//
//            classes.add(clazz);
//        }
//        return classes;
//    }

}
