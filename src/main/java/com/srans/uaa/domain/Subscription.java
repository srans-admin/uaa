/**
 * 
 */
package com.srans.uaa.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;

import com.srans.uaa.utils.UAAUtils;

/**
 * @author ram
 *
 */
@Entity
public class Subscription {
	
	@Transient
	 @Autowired
	 private UAAUtils UAAUtils;
	 
	 @Id
	 @GeneratedValue(strategy= GenerationType.AUTO)
	 private int id;
	 @Column private String userName;
	 @Column private Date validFrom;
	 @Column private Date validTo;
	 @Column private String type;
	 @Column private String comments;
	 @Column private Date createdDate;
	 @Column private Date updatedDate;
	 
	 
	 
	public Subscription() {
		super();
		this.id = -1;
		this.userName = UAAUtils.DEFAULT_STRING;
		this.validFrom = UAAUtils.DEFAULT_DATE;
		this.validTo = UAAUtils.DEFAULT_DATE;
		this.type = UAAUtils.DEFAULT_STRING;
		this.comments = UAAUtils.DEFAULT_STRING;
		this.createdDate = UAAUtils.DEFAULT_DATE;
		this.updatedDate = UAAUtils.DEFAULT_DATE;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	 
	public Date getValidFrom() {
		return validFrom;
	}
	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}
	public Date getValidTo() {
		return validTo;
	}
	public void setValidTo(Date validTo) {
		this.validTo = validTo;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Subscription [UAAUtils=");
		builder.append(UAAUtils);
		builder.append(", id=");
		builder.append(id);
		builder.append(", userId=");
		builder.append(userName);
		builder.append(", validFrom=");
		builder.append(validFrom);
		builder.append(", validTo=");
		builder.append(validTo);
		builder.append(", type=");
		builder.append(type);
		builder.append(", comments=");
		builder.append(comments);
		builder.append(", createdDate=");
		builder.append(createdDate);
		builder.append(", updatedDate=");
		builder.append(updatedDate);
		builder.append("]");
		return builder.toString();
	} 
	
	
	

}
