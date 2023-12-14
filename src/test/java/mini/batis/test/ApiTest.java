package mini.batis.test;

import static org.junit.Assert.*;

import mini.batis.binding.MapperProxyFactory;
import mini.batis.binding.MapperRegistry;
import mini.batis.session.SqlSession;
import mini.batis.session.defaults.DefaultSqlSessionFactory;
import mini.batis.test.dao.IUserDao;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class ApiTest {
    private Logger logger = LoggerFactory.getLogger(ApiTest.class);

    @Test
    public void test_register_and_getMapper() {
        // 注册 mappers
        MapperRegistry registry = new MapperRegistry();
        registry.addMappers("mini.batis.test.dao");

        // 创建 SqlSessionFactory 并得到 SqlSession
        final DefaultSqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(registry);
        final SqlSession sqlSession = sqlSessionFactory.openSession();

        // 通过 SqlSession 获取 Mapper(代理类)
        final IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        // 代理mapper 调用 SqlSession 统一提供的方法进行查询
        final String s = userDao.queryUserName("001");
        System.out.println(s);
    }
}
