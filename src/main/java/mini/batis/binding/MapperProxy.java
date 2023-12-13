package mini.batis.binding;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

public class MapperProxy<T> implements InvocationHandler, Serializable {
    private static final long serialVersionUID = -6424540398559729838L;

    private Map<String, String> sqlSession;
    private final Class<T> mapperInterface;

    public MapperProxy(Map<String, String> sqlSession, Class<T> mapperInterface) {
        this.sqlSession = sqlSession;
        this.mapperInterface = mapperInterface;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (Object.class.equals(method.getDeclaringClass())) {
            // 如果是 Object 提供的 toString、hashCode 等方法, 不需要代理执行
            return method.invoke(this, args);
        } else {
            String key = mapperInterface.getName() + "." + method.getName();
            return "MapperProxy 代理了: " + sqlSession.get(key);
        }
    }
}
