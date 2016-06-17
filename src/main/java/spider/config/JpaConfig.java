package spider.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * Created by hzbc on 2016/5/20.
 */
@Configuration
@EnableJpaRepositories("spider.repository")
public class JpaConfig {
    private static String DRIVER_NAME="com.mysql.jdbc.Driver";
    private static String URL="jdbc:mysql://localhost:3306/school?useUnicode=true&characterEncoding=UTF8&autoReconnect=true";
    private static String USERNAME="root";
    private static String PASSWORD="1234";//sa

    @Bean
    public DataSource dataSource(){
        BasicDataSource basicDataSource=new BasicDataSource();
        basicDataSource.setDriverClassName(DRIVER_NAME);
        basicDataSource.setUrl(URL);
        basicDataSource.setUsername(USERNAME);
        basicDataSource.setPassword(PASSWORD);
        return basicDataSource;
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory EntityManagerFactory){
        return new JpaTransactionManager(EntityManagerFactory);
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter(){
//        EclipseLinkJpaVendorAdapter eclipseLinkJpaVendorAdapter=new EclipseLinkJpaVendorAdapter();
//        eclipseLinkJpaVendorAdapter.setDatabase(Database.MYSQL);
//        eclipseLinkJpaVendorAdapter.setGenerateDdl(true);
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter=new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setDatabase(Database.MYSQL);
        hibernateJpaVendorAdapter.setGenerateDdl(true);
        return hibernateJpaVendorAdapter;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
        LocalContainerEntityManagerFactoryBean lcemf=new LocalContainerEntityManagerFactoryBean();
        lcemf.setDataSource(dataSource());
        lcemf.setJpaVendorAdapter(jpaVendorAdapter());

        //如果选择 EclipseLink 持久化，就必须加下面的配置
//        Map<String,Object> map=new HashMap<String, Object>();
//        map.put("eclipselink.weaving","false");
//        lcemf.setJpaPropertyMap(map);

        lcemf.setPackagesToScan("spider.entity");
        return lcemf;
    }

}
