package com.usa.his.gov.elg.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
@Data
@Entity
@Table(name="CD_TRIGGERS")
public class CdTriggersEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "cd_id_gen")
	@SequenceGenerator(name = "cd_id_gen",allocationSize = 1,initialValue = 1,sequenceName = "TRG_Id_SEQ")
	@Column(name="TRG_ID",length = 19)
	private Integer TrgId;
	@Column(name="CASE_NUM",length = 19)
	private Integer caseNum;
	@Column(name="NOTICE",length = 10)
	private String notice;
	@Temporal(TemporalType.DATE)
	@CreationTimestamp
	@Column(name="CREATE_DT",updatable = false,insertable = true)
	private Date createdDate;
	@Column(name = "TRG_STATUS",length = 255)
	private String trgStatus;
	@UpdateTimestamp
	@Temporal(TemporalType.DATE)
	@Column(name="UPDATE_DT",insertable = false,updatable = true)
	private Date updateDate;
	@Column(name = "BATCH_NAME",length = 255)
	private String batchName;
}
