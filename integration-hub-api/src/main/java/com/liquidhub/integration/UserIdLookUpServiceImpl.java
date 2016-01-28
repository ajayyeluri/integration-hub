package com.liquidhub.integration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;


@Service ("UserIdLookUpService")

public class UserIdLookUpServiceImpl implements UserIdLookUpService {

    Log logger = LogFactory.getLog(this.getClass());

    @Value("${appdb.instance.identified:default}")
    String instanceName;

    @Autowired
    DataSource dataSource;


    JdbcTemplate jdbcTemplate = null ;


    public void init_jdbcTemplate(){
        if ( jdbcTemplate == null)
        jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public String getUserEid(String appName, String appId) {
        init_jdbcTemplate();
        String sql_count = "select count(1) from app_id_lookup  where app_name = '" +appName +
                "' and app_id ='" + appId +
                "' and instance_name='" + instanceName+
                "'";

        if ( jdbcTemplate.queryForObject(sql_count, Long.class).intValue() == 0 ){
            return "";
        }

        String sql = "select eid from app_id_lookup  where app_name = '" +appName +
                "' and app_id ='" + appId +
                "' and instance_name='" + instanceName+
                "';";



        logger.info("Execute SQL - " + sql );
        String result =  jdbcTemplate.queryForObject(sql, String.class);
        if( result != null ) {
            logger.info("SQL Result Value [EID] = " + result);
            return result;
        } else {
            return "";
        }
    }

    @Override
    public String getUserAppId(String appName, String eid) {
        init_jdbcTemplate();

        String sql_count = "select count(1) from app_id_lookup  where app_name = '" +appName +
                "' and eid  ='" + eid +
                "' and instance_name='" + instanceName+
                "'";

        if ( jdbcTemplate.queryForObject(sql_count, Long.class).intValue() == 0 ){
            return "";
        }


        String sql = "select eid from app_id_lookup  where app_name = '" +appName +
                "' and eid  ='" + eid +
                "' and instance_name='" + instanceName+
                "'";

        logger.info("Execute SQL - " + sql );
        String result =  jdbcTemplate.queryForObject(sql, String.class);
        if( result != null ) {
            logger.info("SQL Result Value [App_ID] = " + result);
            return result;
        }else {
            return "";
        }
    }

    public void  setUserEid(String appName, String appId) {
        init_jdbcTemplate();
        String countSQL = "select count(1) from app_id_lookup  where app_name = '" +appName +
                "' and app_id ='" + appId +
                "' and instance_name='" + instanceName+
                "'";
       long count =  jdbcTemplate.queryForObject(countSQL, Long.class);
        // if not present then add a new UUID and application ID
        if ( count == 0  ) {
            String eid = getUUID();
            String sql = "insert into app_id_lookup (eid, app_name, app_id, instance_name) " +
                    "values ( '" + eid +
                    "', '" +appName +
                    "', '" + appId + "' " +
                    "'" + instanceName+ "')";
            logger.info("Execute SQL - " + sql );
            jdbcTemplate.execute(sql);
            logger.warn("app_name:[" +appName +
                    "] and app_id:["  + appId +
                    "] Mapped to EID:[" + eid +
                    "]. ");
        } else {
            logger.warn("app_name:[" +appName +
                    "] and app_id:["  + appId +
                    "] already Mapped to EID. Skipping this action ");
        }

        return ;
    }


    @Override
    public void  addUserAppID(String appName, String appId, String eid) {
        init_jdbcTemplate();
        String sql = "insert into app_id_lookup (eid, app_name, app_id, instance_name) " +
                "values ( '" + eid +
                "', '" +appName +
                "', '" + appId + "' , " +
                "'" + instanceName+ "');";

        logger.info("Execute SQL - " + sql );
        jdbcTemplate.execute(sql);

        return ;
    }

    @Override
    public void removeUserAppID(String appName, String appId, String eid) {

        init_jdbcTemplate();

        String sql = "delete from app_id_lookup where app_name = '" +appName +
        "' and eid  ='" + eid +
                "' and app_id  ='" + appId+
                "' and instance_name='" + instanceName+
                "' ;";
        logger.info("Execute SQL - " + sql );
        jdbcTemplate.execute(sql);

        return ;
    }

    @Override
    public String getUUID() {
        init_jdbcTemplate();
        String uuid = jdbcTemplate.queryForObject("SELECT uuid_in(md5(random()::text || now()::text)::cstring)", String.class);
        logger.info("Getting UUID - " + uuid );
        return uuid;
    }



}
