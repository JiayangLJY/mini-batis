package mini.batis.session.defaults;

import mini.batis.binding.MapperRegistry;
import mini.batis.session.SqlSession;
import mini.batis.session.SqlSessionFactory;

public class DefaultSqlSessionFactory implements SqlSessionFactory {
    private final MapperRegistry mapperRegistry;

    public DefaultSqlSessionFactory(MapperRegistry mapperRegistry) {
        this.mapperRegistry = mapperRegistry;
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(mapperRegistry);
    }
}
