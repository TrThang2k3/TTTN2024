package com.poly.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Accounts", uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
public class Account implements Serializable{
	@Id
	@Column(name = "AccountID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	@Temporal(TemporalType.DATE)
	private Date dayOfBirth;
	private String walletAddress;
	private Float balance = 0f;
	private Boolean isPublisher = false;
	private Boolean isActive = true;
	@JsonIgnore
	@OneToMany(mappedBy = "account")
	private List<Nft> nfts;
	@JsonIgnore
	@OneToMany(mappedBy = "seller")
	private List<Invoice> sellInvoices;
	@JsonIgnore
	@OneToMany(mappedBy = "buyer")
	private List<Invoice> buyInvoices;
	@JsonIgnore
	@OneToOne(mappedBy = "account")
	private Publisher publisher;
	@JsonIgnore
	@OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
	private List<Authority> authorities;
	
}
