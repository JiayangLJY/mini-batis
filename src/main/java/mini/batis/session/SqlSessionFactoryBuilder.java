package mini.batis.session;

import mini.batis.builder.xml.XMLConfigBuilder;
import mini.batis.session.defaults.DefaultSqlSessionFactory;

import java.io.Reader;

/**
 * 向外暴露的Builder，通过Configuration类初始化 SqlSessionFactory
 */
public class SqlSessionFactoryBuilder {
    public SqlSessionFactory build(Reader reader) {
        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder(reader);
        return build(xmlConfigBuilder.parse());
    }

    public SqlSessionFactory build(Configuration config) {
        return new DefaultSqlSessionFactory(config);
    }
}
