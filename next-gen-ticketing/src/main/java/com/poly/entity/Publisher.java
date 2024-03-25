
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Publishers", uniqueConstraints = {@UniqueConstraint(columnNames = "accountid")})
public class Publisher implements Serializable{
	@Id
	@Column(name = "publisherid")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@OneToOne()
	@JoinColumn(name = "accountid")
	private Account account;
	@Column(name = "publishername")
	private String name;
	private String description;
	private String address;
	@Column(name = "isactive")
	private Boolean isActive = true;
	@JsonIgnore
	@OneToMany(mappedBy = "publisher")
	private List<Ticket> tickets;
}
