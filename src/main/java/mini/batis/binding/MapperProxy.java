package mini.batis.binding;

import mini.batis.session.SqlSession;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MapperProxy<T> implements InvocationHandler, Serializable {
    private static final long serialVersionUID = -6424540398559729838L;

    private SqlSession sqlSession;
    private final Class<T> mapperInterface;

    public MapperProxy(SqlSession sqlSession, Class<T> mapperInterface) {
        this.sqlSession = sqlSession;
        this.mapperInterface = mapperInterface;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (Object.class.equals(method.getDeclaringClass())) {
            // 如果是 Object 提供的 toString、hashCode 等方法, 不需要代理执行
            return method.invoke(this, args);
        } else {
//            String key = mapperInterface.getName() + "." + method.getName();
            return sqlSession.selectOne(method.getName(), args);
        }
    }
}
