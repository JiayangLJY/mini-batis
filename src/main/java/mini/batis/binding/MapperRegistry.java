package mini.batis.binding;

import cn.hutool.core.lang.ClassScanner;
import mini.batis.session.SqlSession;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MapperRegistry {
    private final Map<Class<?>, MapperProxyFactory<?>> knownMappers = new HashMap<>();

    /**
     * add mapper into knownMappers by clazz
     * @param type
     * @param <T>
     */
    public <T> void addMapper(Class<T> type) {
        if (type.isInterface() && !hasMapper(type)) {
            knownMappers.put(type, new MapperProxyFactory<>(type));
        } else {
            throw new RuntimeException(type + " is not a Interface or is duplicated loaded by MapperRegistry");
        }
    }

    public <T> boolean hasMapper(Class<T> type) {
        return knownMappers.containsKey(type);
    }

    /**
     * add mappers by package
     * @param packageName
     */
    public void addMappers(String packageName) {
        final Set<Class<?>> classSet = ClassScanner.scanPackage(packageName);
        for (Class<?> c : classSet) {
            addMapper(c);
        }
    }

    public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
        final MapperProxyFactory<T> mapperProxyFactory = (MapperProxyFactory<T>) knownMappers.get(type);
        if (mapperProxyFactory == null) {
            throw new RuntimeException("Type " + type + " is not known to the MapperRegistry.");
        }
        try {
            return mapperProxyFactory.newInstance(sqlSession);
        } catch (Exception e) {
            throw new RuntimeException("Error getting mapper instance. Cause: " + e, e);
        }
    }
}
