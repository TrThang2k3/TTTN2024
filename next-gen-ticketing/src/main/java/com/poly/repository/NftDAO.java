package com.poly.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.entity.Nft;

public interface NftDAO extends JpaRepository<Nft, Integer>{

}
