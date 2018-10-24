package com.yven.mongodb.entity;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity(value = "client_info", noClassnameStored = true)
public class ClientInfoEntity implements Serializable {

	private static final long serialVersionUID = -3268130126346462202L;

	@Id
	private String jid;

	private String userId;

	private String clientType;

	private String ip;

	private String country;

	private String province;

	private String city;

	private Date loginTime;

	private String nsName;

	private String deviceType;

	private String category;

	private String wifiSoftversion;

	private String macAddress;

	private String companyCode; // 公司编号

	private String masterTID;// 主人TID(如果是子设备话是网关TID)

	private String snCode;
	
	private String productSource; //WIFI设备来源产线还是其他 1:产线 0：其他
	
	private int platType; //平台类型，0-智讯平台设备 1-openfire平台设备 2-古北平台设备
	
	private String joinid;
	
	private String bindBySSIDFuzzyMatch;	// 是否曾被云端通过ssid模糊匹配进行绑定：1：是，0：否

	private List<String> contronList;

	public List<String> getContronList() {
		return contronList;
	}

	public void setContronList(List<String> contronList) {
		this.contronList = contronList;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public ClientInfoEntity() {
	}

	public String getJid() {
		return jid;
	}

	public void setJid(String jid) {
		this.jid = jid;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getClientType() {
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public String getNsName() {
		return nsName;
	}

	public void setNsName(String nsName) {
		this.nsName = nsName;
	}

	public boolean isOnline() {
		return nsName != null && nsName.trim().length() > 0;
	}

	public String getWifiSoftversion() {
		return wifiSoftversion;
	}

	public void setWifiSoftversion(String wifiSoftversion) {
		this.wifiSoftversion = wifiSoftversion;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getMasterTID() {
		return masterTID;
	}

	public void setMasterTID(String masterTID) {
		this.masterTID = masterTID;
	}

	public String getSnCode() {
		return snCode;
	}

	public void setSnCode(String snCode) {
		this.snCode = snCode;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[jid=").append(jid).append(",userId=").append(userId).append(",clienttype=").append(clientType)
				.append(",ip=").append(ip).append(",country=").append(country).append(",province=").append(province)
				.append(",city=").append(city).append("]");
		return sb.toString();
	}

	public String getProductSource() {
		return productSource;
	}

	public void setProductSource(String productSource) {
		this.productSource = productSource;
	}

	public int getPlatType() {
		return platType;
	}

	public void setPlatType(int platType) {
		this.platType = platType;
	}

	public String getJoinid() {
		return joinid;
	}

	public void setJoinid(String joinid) {
		this.joinid = joinid;
	}

	public String getIsBindBySSIDFuzzyMatch() {
		return bindBySSIDFuzzyMatch;
	}

	public void setIsBindBySSIDFuzzyMatch(String bindBySSIDFuzzyMatch) {
		this.bindBySSIDFuzzyMatch = bindBySSIDFuzzyMatch;
	}

}
