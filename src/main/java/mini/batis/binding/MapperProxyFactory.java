package mini.batis.binding;

import mini.batis.session.SqlSession;

import java.lang.reflect.Proxy;
import java.util.Map;

public class MapperProxyFactory<T> {
    private final Class<T> mapperInterface;

    public MapperProxyFactory(Class<T> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    public T newInstance(SqlSession sqlSession) {
        final MapperProxy<T> mapperProxy = new MapperProxy<>(sqlSession, mapperInterface);
        return (T) Proxy.newProxyInstance(
                mapperInterface.getClassLoader(), // The class loader to define the proxy class
                new Class[]{mapperInterface}, // interfaces that the proxy class should implement
                mapperProxy); // InvocationHandler
    }
}
