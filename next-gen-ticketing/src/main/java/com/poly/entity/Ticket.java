
package com.poly.entity;

import java.io.Serializable;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

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
import jakarta.persistence.Transient;
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
	@Column(name = "ticketid")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "ticketname")
	private String name;
	@ManyToOne()
	@JoinColumn(name = "publisherid")
	private Publisher publisher;
	@Column(name = "ticketimage")
	private String image;
	@Transient
	private MultipartFile imageFile;
	private Float price;
	@ManyToOne()
	@JoinColumn(name = "typeid")
	private Type type;
	private Integer shelftime;
	private String description;
	@Column(name = "isactive")
	private Boolean isActive = true;
	@JsonIgnore
	@OneToMany(mappedBy = "ticket")
	private List<Nft> nfts;
}
