package com.yven.dao;

import com.yven.domain.Imuser;
import com.yven.domain.Spool;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class ControllerDaoJdbc {

    private static Logger logger = Logger.getLogger(ControllerDaoJdbc.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final static String  THIRD_CTRLOR_SQL = "SELECT t.devtid FROM iotroster t WHERE "
            + "  EXISTS  (SELECT usertid FROM iotroster t0 WHERE t.usertid=t0.usertid AND  devtid= ? )"
            + " AND t.type='1' AND t.auth_token IS NOT NULL";


    private final static String  THIRD_CTRLOR_SQL2 = "SELECT t.devtid FROM iotroster t " +
            "INNER JOIN iotroster t0 ON t.usertid=t0.usertid AND t0.devtid= ? " +
            "WHERE 1=1 AND t.type='1' AND t.auth_token IS NOT NULL";


    public List<String> getThirdCtrlorList(Long devTid) {
        return this.jdbcTemplate.queryForList(THIRD_CTRLOR_SQL,new Object[] { devTid }, String.class);
    }

    public List<String> getThirdCtrlorList2(Long devTid) {
        return this.jdbcTemplate.queryForList(THIRD_CTRLOR_SQL2,new Object[] { devTid }, String.class);
    }


    /**
     * 插入离线message
     * @param spool
     * @return
     * @throws Exception
     */
    public boolean addOffLineMessage( Spool spool) throws Exception {
        String addSql = "INSERT INTO spool (username, msgid, xml, type,created_at,groupid,fromid) VALUES (?,?,?,?,?,?,?)";
        logger.info(spool.toString());
        int insert = 0;
        try {
            insert = jdbcTemplate.update(addSql, new PreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement pstmt) throws SQLException {
                    pstmt.setString(1, spool.getUsername());
                    pstmt.setString(2, spool.getMsgid());
                    pstmt.setString(3, spool.getXml());
                    pstmt.setInt(4, spool.getType());
                    pstmt.setTimestamp(5, new Timestamp(spool.getCreateAt().getTime()));
                    pstmt.setString(6, spool.getGroupId());
                    pstmt.setString(7, spool.getFromid());
                }
            });
        } catch (Exception e) {
            logger.error("addOffLineMessage error{}{}",e);
//			logger.error("addOffLineMessage error{}{}" + e.getMessage());
            throw e;
        }
        return insert>0?true:false;
    }

    private static final String GET_INFO_IMUSER ="SELECT userId,mobilePhone,account_type FROM imuser i WHERE i.`mobilePhone` IS NOT NULL AND i.`mobilePhone` != '';" ;

    public List<Imuser> getInfoFromImuser() {
        List<Imuser> imusers = jdbcTemplate.query(GET_INFO_IMUSER, new Object[]{}, new Imuser());
        return imusers;
    }
}
