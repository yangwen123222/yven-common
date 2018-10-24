/**
 * 
 */
package com.yven.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: Spool
 * @Description: 离线消息类
 * @author zhail
 * @date 2016-1-18 下午3:33:35
 * 
 */

public class Spool implements Serializable {

	private static final long serialVersionUID = -1L;

	private String username;
	private String msgid;
	private String xml;
	private Long seq;
	private String fromid;
	private String groupId;
	// 0普通，1群
	private Integer type;
	private Date createAt;
    

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	

	public String getFromid() {
		return fromid;
	}

	public void setFromid(String fromid) {
		this.fromid = fromid;
	}

	public String getMsgid() {
		return msgid;
	}

	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	@Override
	public String toString() {
		return "Spool{" +
				"username='" + username + '\'' +
				", msgid='" + msgid + '\'' +
				", xml='" + xml + '\'' +
				", seq=" + seq +
				", fromid='" + fromid + '\'' +
				", groupId='" + groupId + '\'' +
				", type=" + type +
				", createAt=" + createAt +
				'}';
	}
}
