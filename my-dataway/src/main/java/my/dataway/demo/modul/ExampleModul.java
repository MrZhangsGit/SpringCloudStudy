package my.dataway.demo.modul;

import net.hasor.core.ApiBinder;
import net.hasor.core.DimModule;
import net.hasor.db.JdbcModule;
import net.hasor.db.Level;
import net.hasor.spring.SpringModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * @author zhangs
 * @Description
 *  Spring Boot 和 Hasor 本是两个独立的容器框架，做整合之后为了使用 Dataway 的能力需要把 Spring 中的数据源设置到 Hasor 中。
 *  首先新建一个 Hasor 的 模块，并且将其交给 Spring 管理。然后把数据源通过 Spring 注入进来。
 *  Hasor 启动的时候会调用 loadModule 方法，在这里再把 DataSource 设置到 Hasor 中。
 * @createDate 2020/5/18
 */
@DimModule
@Component
public class ExampleModul implements SpringModule {

    @Autowired
    private DataSource dataSource = null;

    @Override
    public void loadModule(ApiBinder apiBinder) throws Throwable {
        apiBinder.installModule(new JdbcModule(Level.Full, this.dataSource));
    }
}
