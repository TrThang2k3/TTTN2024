package com.poly.DTO;

import com.poly.entity.Nft;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NftDTO {
	@Id
	private Nft nft;
	private Integer remain; // số ngày đến hạn sử dụng
	private Boolean isTrading; // có đang đăng chuyển nhượng không

}
