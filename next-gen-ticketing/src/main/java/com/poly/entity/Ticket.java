package com.poly.entity;

import java.io.Serializable;
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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Tickets")
public class Ticket implements Serializable{
	@Id
	@Column(name = "TicketID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "TicketName")
	private String name;
	@ManyToOne()
	@JoinColumn(name = "PublisherID")
	private Publisher publisher;
	@Column(name = "TicketImage")
	private String image;
	private Float price;
	@ManyToOne()
	@JoinColumn(name = "TypeID")
	private Type type;
	private Integer shelftime;
	private String description;
	private Boolean isActive = true;
	@JsonIgnore
	@OneToMany(mappedBy = "ticket")
	private List<Nft> nfts;
}
