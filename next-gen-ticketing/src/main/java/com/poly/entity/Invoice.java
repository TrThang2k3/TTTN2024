package com.poly.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Invoices")
public class Invoice implements Serializable{
	@Id
	@Column(name = "InvoiceID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne()
	@JoinColumn(name = "SellerID")
	private Account seller;
	@ManyToOne()
	@JoinColumn(name = "BuyerID")
	private Account buyer;
	@Temporal(TemporalType.DATE)
	private Date invoiceDate;
	@ManyToOne
	@JoinColumn(name = "NFTID")
	private Nft nft;
	private Float amount;
	private String payment;
}
