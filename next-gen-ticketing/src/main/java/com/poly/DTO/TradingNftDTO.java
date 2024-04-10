package com.poly.DTO;

import com.poly.entity.Account;
import com.poly.entity.Nft;
import com.poly.entity.TradingNft;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TradingNftDTO {
	@Id
	private Integer id;
	private Account seller;
	private Nft nft;
	private Float price;
	private Integer remain;
}
