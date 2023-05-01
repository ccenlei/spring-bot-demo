package com.spring.bot.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableMap;
import com.spring.bot.demo.entity.MintNft;
import com.spring.bot.demo.entity.MintUser;
import com.spring.bot.demo.entity.PageHelperUtil;
import com.spring.bot.demo.exception.ErrorCode;
import com.spring.bot.demo.exception.MintFunException;
import com.spring.bot.demo.service.MintNftService;
import com.spring.bot.demo.service.MintUseService;

@RestController
@RequestMapping("/mint")
public class MintFunController {

    @Autowired
    private MintUseService useService;

    @Autowired
    private MintNftService nftService;

    @GetMapping("/user/{id}")
    public ResponseEntity<MintUser> getUserById(@PathVariable("id") Integer id) {
        MintUser uMintUser = useService.findUserById(id);
        if (ObjectUtils.isEmpty(uMintUser)) {
            throw new MintFunException(ImmutableMap.of("user id:", id), ErrorCode.MINT_USER_NOT_FOUND);
        }
        return ResponseEntity.ok(uMintUser);
    }

    @GetMapping("/user")
    public ResponseEntity<List<MintUser>> getUserByName(@RequestParam("name") String name) {
        List<MintUser> users = useService.findUserByName(name);
        if (ObjectUtils.isEmpty(users)) {
            throw new MintFunException(ImmutableMap.of("user name:", name), ErrorCode.MINT_USER_NOT_FOUND);
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("/user/all")
    public ResponseEntity<PageHelperUtil> getUserByPage(@RequestParam("page") Integer page,
            @RequestParam("rows") Integer rows) {
        PageHelperUtil helperUtil = useService.findUserByPage(page, rows);
        return ResponseEntity.ok(helperUtil);
    }

    @PostMapping("/user/add")
    public ResponseEntity<?> addUser(@RequestBody MintUser mUser) {
        useService.saveUser(mUser);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> removeUserById(@PathVariable("id") Integer id) {
        useService.removeUserById(id);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/user/update")
    public ResponseEntity<?> updateUser(@RequestBody MintUser mUser) {
        useService.modifyUserById(mUser);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/nft/{id}")
    public ResponseEntity<MintNft> getNftById(@PathVariable("id") Integer id) {
        MintNft nMintNft = nftService.findNftById(id);
        if (ObjectUtils.isEmpty(nMintNft)) {
            throw new MintFunException(ImmutableMap.of("nft id:", id), ErrorCode.MINT_NFT_NOT_FOUND);
        }
        return ResponseEntity.ok(nMintNft);
    }

    @GetMapping("/nft")
    public ResponseEntity<List<MintNft>> getNftByName(@RequestParam("name") String name) {
        List<MintNft> nfts = nftService.findNftByName(name);
        if (ObjectUtils.isEmpty(nfts)) {
            throw new MintFunException(ImmutableMap.of("nft name:", name), ErrorCode.MINT_NFT_NOT_FOUND);
        }
        return ResponseEntity.ok(nfts);
    }
}
