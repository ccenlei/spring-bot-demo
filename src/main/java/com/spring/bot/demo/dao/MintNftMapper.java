package com.spring.bot.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.spring.bot.demo.entity.MintNft;

@Mapper
public interface MintNftMapper {

    int insertNft(MintNft mNft);

    MintNft selectNftById(Integer id);

    List<MintNft> selecNftByName(String name);
}
