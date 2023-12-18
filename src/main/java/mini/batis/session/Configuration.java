package mini.batis.session;

import mini.batis.binding.MapperRegistry;
import mini.batis.datasource.druid.DruidDataSourceFactory;
import mini.batis.datasource.pooled.PooledDataSourceFactory;
import mini.batis.datasource.unpooled.UnpooledDataSourceFactory;
import mini.batis.executor.Executor;
import mini.batis.executor.SimpleExecutor;
import mini.batis.executor.resultset.DefaultResultSetHandler;
import mini.batis.executor.resultset.ResultSetHandler;
import mini.batis.executor.statement.PreparedStatementHandler;
import mini.batis.executor.statement.StatementHandler;
import mini.batis.mapping.BoundSql;
import mini.batis.mapping.Environment;
import mini.batis.mapping.MappedStatement;
import mini.batis.transaction.Transaction;
import mini.batis.transaction.jdbc.JdbcTransactionFactory;
import mini.batis.type.TypeAliasRegistry;

import java.util.HashMap;
import java.util.Map;

public class Configuration {

    /**
     * environment: id, TransactionFactory, DataSource
     */
    protected Environment environment;

    /**
     * MapperRegistry
     */
    protected MapperRegistry mapperRegistry = new MapperRegistry(this);

    /**
     * 映射的SQL语句，存在Map里
     */
    protected final Map<String, MappedStatement> mappedStatements = new HashMap<>();

    /**
     * type alias registry
     */
    protected final TypeAliasRegistry typeAliasRegistry = new TypeAliasRegistry();

    public Configuration() {
        typeAliasRegistry.registerAlias("JDBC", JdbcTransactionFactory.class);
        typeAliasRegistry.registerAlias("DRUID", DruidDataSourceFactory.class);
        typeAliasRegistry.registerAlias("UNPOOLED", UnpooledDataSourceFactory.class);
        typeAliasRegistry.registerAlias("POOLED", PooledDataSourceFactory.class);
    }

    public void addMappers(String packageName) {
        mapperRegistry.addMappers(packageName);
    }

    public <T> void addMapper(Class<T> type) {
        mapperRegistry.addMapper(type);
    }

    public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
        return mapperRegistry.getMapper(type, sqlSession);
    }

    public boolean hasMapper(Class<?> type) {
        return mapperRegistry.hasMapper(type);
    }

    public void addMappedStatement(MappedStatement ms) {
        mappedStatements.put(ms.getId(), ms);
    }

    public MappedStatement getMappedStatement(String id) {
        return mappedStatements.get(id);
    }

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public TypeAliasRegistry getTypeAliasRegistry() {
        return typeAliasRegistry;
    }

    /**
     * 创建结果集处理器
     */
    public ResultSetHandler newResultSetHandler(Executor executor, MappedStatement mappedStatement, BoundSql boundSql) {
        return new DefaultResultSetHandler(executor, mappedStatement, boundSql);
    }

    /**
     * 生产执行器
     */
    public Executor newExecutor(Transaction transaction) {
        return new SimpleExecutor(this, transaction);
    }

    /**
     * 创建语句处理器
     */
    public StatementHandler newStatementHandler(Executor executor, MappedStatement mappedStatement, Object parameter, ResultHandler resultHandler, BoundSql boundSql) {
        return new PreparedStatementHandler(executor, mappedStatement, parameter, resultHandler, boundSql);
    }
}
