package mini.batis.builder;

import mini.batis.session.Configuration;

/**
 * base builder abstract class
 */
public abstract class BaseBuilder {
    protected final Configuration configuration;

    public BaseBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    public Configuration getConfiguration() {
        return configuration;
    }
}
