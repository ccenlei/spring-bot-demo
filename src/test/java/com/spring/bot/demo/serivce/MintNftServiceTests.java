package com.spring.bot.demo.serivce;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.bot.demo.entity.MintNft;
import com.spring.bot.demo.service.MintNftService;

@SpringBootTest
public class MintNftServiceTests {

    @Autowired
    private MintNftService nftService;

    @Test
    void save_nft_test() throws Exception {
        MintNft nft = new MintNft();
        nft.setName("PsychoNauts");
        nft.setTokenId("#2676");
        nft.setAddr("0xe05590833120f8a671d43aa3e9870fed9361b4ca");
        nft.setCreator("0x75af3607db30bbdf3c51aa59c8a346dab9b2f7b0");
        nft.setTotal(7777);
        nft.setOwnerId(3);

        ObjectMapper oMapper = new ObjectMapper();
        String json = oMapper.writeValueAsString(nft);
        System.out.println(json);

        nftService.saveNft(nft);
    }

    @Test
    void find_nft_by_id_test() throws Exception {
        MintNft nft = nftService.findNftById(2);
    }
}
