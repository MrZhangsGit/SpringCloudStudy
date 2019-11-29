//package my.datasource.config;
//
//import com.alibaba.druid.pool.DruidDataSource;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import tk.mybatis.spring.annotation.MapperScan;
//
//import javax.sql.DataSource;
//
///**
// * @author zhangs
// * @Description
// * @createDate 2019/11/19
// */
//@Configuration
//@MapperScan(basePackages = ThirdDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "sqlSessionTemplateThird")
//public class ThirdDataSourceConfig {
//
//    static final String PACKAGE = "my.datasource.dao.third";
//    //static final String MAPPER_LOCATION = "classpath:mapper/master/*.xml";
//
//    @Value("${third.datasource.url}")
//    private String url;
//
//    @Value("${third.datasource.username}")
//    private String user;
//
//    @Value("${third.datasource.password}")
//    private String password;
//
//    @Value("${third.datasource.driverClassName}")
//    private String driverClass;
//
//    @Bean(name = "thirdDataSource")
//    public DataSource thirdDataSource() {
//        DruidDataSource dataSource = new DruidDataSource();
//        dataSource.setDriverClassName(driverClass);
//        dataSource.setUrl(url);
//        dataSource.setUsername(user);
//        dataSource.setPassword(password);
//        return dataSource;
//    }
//
//    @Bean(name = "thirdSqlSessionFactory")
//    public SqlSessionFactory thirdSqlSessionFactory(@Qualifier("thirdDataSource") DataSource thirdDataSource) throws Exception {
//        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
//        sessionFactory.setDataSource(thirdDataSource);
//        //sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(ThirdDataSourceConfig.MAPPER_LOCATION));
//        return sessionFactory.getObject();
//    }
//
//    @Bean(name = "thirdTransactionManager")
//    public DataSourceTransactionManager thirdTransactionManager() {
//        return new DataSourceTransactionManager(thirdDataSource());
//    }
//
//    @Bean(name = "sqlSessionTemplateThird")
//    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("thirdSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
//        return new SqlSessionTemplate(sqlSessionFactory);
//    }
//}
