package team.isaz.framework;

import com.google.common.reflect.ClassPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings({"UnstableApiUsage", "rawtypes"})
class PackageAnalyzer {
    Logger logger = LoggerFactory.getLogger(PackageAnalyzer.class);
    private String testingPackage;

    protected PackageAnalyzer(String testingPackage) {
        this.testingPackage = testingPackage;
    }

    /**
     * <b>Получение классов из пакета</b>
     *
     * @return @code{List<Class>}, содержащий все классы из пакета testingPackage или null, в случае ошибок чтения.
     */
    protected List<Class> getClasses() {
        ClassPath classpath = getClassPath();
        if (classpath == null) return null;
        List<Class> classes;
        classes = classpath.getTopLevelClassesRecursive(testingPackage)
                .stream()
                .map(this::getClassByClassInfo)
                .collect(Collectors.toList());
        return classes;
    }

    /**
     * <b>Получить класс по Guava ClassInfo</b>
     *
     * @param classInfo информация о классе по Google Guava Reflection
     * @return Class-объект соответствующий classInfo, или null в случае ошибок.
     */
    private Class<?> getClassByClassInfo(ClassPath.ClassInfo classInfo) {
        try {
            return Class.forName(classInfo.getName());
        } catch (Exception e) {
            logger.error("Can't load class!\n" + e.getMessage());
        }
        return null;
    }

    /**
     * <b>Получить classpath</b>
     *
     * @return classpath из текущего SystemClassLoader
     */
    private ClassPath getClassPath() {
        final ClassLoader loader = ClassLoader.getSystemClassLoader();
        ClassPath classpath;
        try {
            classpath = ClassPath.from(loader); // scans the class path used by classloader
        } catch (Exception e) {
            return null;
        }
        return classpath;
    }
}
