package com.poly.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "Nfts", uniqueConstraints = {@UniqueConstraint(columnNames = "nftaddress")})
public class Nft implements Serializable{
	@Id
	@Column(name = "nftid")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "nftaddress")
	private String nftAddress;
	@ManyToOne()
	@JoinColumn(name = "accountid")
	private Account account;
	@ManyToOne()
	@JoinColumn(name = "ticketid")
	private Ticket ticket;
	@Temporal(TemporalType.DATE)
	@Column(name = "createdate")
	private Date createDate;
	@JsonIgnore
	@OneToMany(mappedBy = "nft")
	private List<Invoice> invoices;
}
