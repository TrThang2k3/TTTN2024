package com.poly.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Tradingnfts")
public class TradingNft implements Serializable{
	@Id
	@Column(name = "tradingid")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne
	@JoinColumn(name = "accountid")
	private Account account;
	@ManyToOne
	@JoinColumn(name = "nftid")
	private Nft nft;
	private Float price;
	@Column(name = "isavailable")
	private Boolean isAvailable = true;
}
