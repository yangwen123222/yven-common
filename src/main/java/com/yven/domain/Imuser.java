/**
 *
 */
package com.yven.domain;

import org.springframework.jdbc.core.RowMapper;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 */
public class Imuser implements RowMapper<Imuser>, Serializable {
    private static final long serialVersionUID = -1L;
    /**
     *
     */
    private String userId;

    /**
     *
     */
    private String mobilePhone;

    private Boolean isTclAccount;

    public void setTclAccount(Boolean tclAccount) {
        isTclAccount = tclAccount;
    }

    public Boolean getTclAccount() {
        return isTclAccount;
    }

    /**
     * 账户类型：1-智迅用户  2-集团用户
     */
    private Integer accountType;

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    @Override
    public Imuser mapRow(ResultSet rs, int rowNum) throws SQLException {
        Imuser user = new Imuser();
        user.setUserId(rs.getString("userId"));
        user.setMobilePhone(rs.getNString("mobilePhone"));
        user.setAccountType(rs.getInt("account_type"));
        return user;
    }

    @Override
    public String toString() {
        return "Imuser{" +
                "userId='" + userId + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                ", isTclAccount=" + isTclAccount +
                ", accountType=" + accountType +
                '}';
    }
}
