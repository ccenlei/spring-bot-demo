package com.spring.bot.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.spring.bot.demo.dao.MintNftMapper;
import com.spring.bot.demo.entity.MintNft;

@Service
public class MintNftService {

    @Autowired
    private MintNftMapper nftMapper;

    @Async("serviceExecutor")
    public void asyncSaveNft(MintNft mNft) {
        nftMapper.insertNft(mNft);
    }

    public MintNft findNftById(Integer id) {
        return nftMapper.selectNftById(id);
    }

    public List<MintNft> findNftByName(String name) {
        return nftMapper.selecNftByName(name);
    }
}
