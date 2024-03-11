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
@Table(name = "NFTs", uniqueConstraints = {@UniqueConstraint(columnNames = "NFTAddress")})
public class Nft implements Serializable{
	@Id
	@Column(name = "NFTID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nftAddress;
	@ManyToOne()
	@JoinColumn(name = "AccountID")
	private Account account;
	@ManyToOne()
	@JoinColumn(name = "TicketID")
	private Ticket ticket;
	@Temporal(TemporalType.DATE)
	private Date createDate;
	@JsonIgnore
	@OneToMany(mappedBy = "nft")
	private List<Invoice> invoices;
}
