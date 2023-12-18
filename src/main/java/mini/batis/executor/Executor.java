package mini.batis.executor;

import mini.batis.mapping.BoundSql;
import mini.batis.mapping.MappedStatement;
import mini.batis.session.ResultHandler;
import mini.batis.transaction.Transaction;

import java.sql.SQLException;
import java.util.List;

public interface Executor {
    ResultHandler NO_RESULT_HANDLER = null;

    <E> List<E> query(MappedStatement ms, Object parameter, ResultHandler resultHandler, BoundSql boundSql);

    Transaction getTransaction();

    void commit(boolean required) throws SQLException;

    void rollback(boolean required) throws SQLException;

    void close(boolean forceRollback);
}
