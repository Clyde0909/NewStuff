
package com.newsbig.sinmunmul.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 파라미터가 없는 기본 생성자를 생성한다. 접근 권한을 설정하여 어느 곳에서나 객체를 생성할 수 있는 상황을 막는다.
@Getter
@Setter
@ToString
@AllArgsConstructor
public class NewsRecommend {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "recom_seq")
	private long recomSeq;

	@Column(name = "code")
	private int code;
	
	@Column(name = "news_seq")
	private long newsSeq;
	
	@Column(name = "keyword")
	private String keyword;

	@Column(name = "del_yn")
	private String delYn;

	@Column(name = "reg_dt")
	private String regDt;

	@Column(name = "reg_id")
	private String regId;

	@Column(name = "mod_dt")
	private String modDt;

	@Column(name = "mod_id")
	private String modId;
}
