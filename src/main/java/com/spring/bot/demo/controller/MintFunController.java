package com.spring.bot.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableMap;
import com.spring.bot.demo.dto.MintNftDto;
import com.spring.bot.demo.dto.MintUserDto;
import com.spring.bot.demo.entity.MintNft;
import com.spring.bot.demo.entity.MintUser;
import com.spring.bot.demo.exception.ErrorCode;
import com.spring.bot.demo.exception.MintFunException;
import com.spring.bot.demo.service.MintNftService;
import com.spring.bot.demo.service.MintUseService;
import com.spring.bot.demo.utils.MintNftBeanMapper;
import com.spring.bot.demo.utils.MintUserBeanMapper;
import com.spring.bot.demo.utils.PageHelperUtil;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Validated
@RestController
@RequestMapping("/api/mint")
public class MintFunController {

    @Autowired
    private MintUseService useService;

    @Autowired
    private MintNftService nftService;

    @Autowired
    private MintNftBeanMapper nMapper;

    @Autowired
    private MintUserBeanMapper uMapper;

    @GetMapping("/user/{id}")
    public ResponseEntity<MintUserDto> getUserById(@PathVariable("id") @Valid @Min(1) Integer id) {
        MintUser uMintUser = useService.findUserById(id);
        if (ObjectUtils.isEmpty(uMintUser)) {
            throw new MintFunException(ImmutableMap.of("user id:", id), ErrorCode.MINT_USER_NOT_FOUND);
        }
        MintUserDto uMintUserDto = uMapper.tDto(uMintUser);
        return ResponseEntity.ok(uMintUserDto);
    }

    @GetMapping("/user/detail/{id}")
    public ResponseEntity<MintUserDto> getUserDetailById(@PathVariable("id") @Valid @Min(1) Integer id) {
        MintUser uMintUser = useService.findUserDetailById(id);
        if (ObjectUtils.isEmpty(uMintUser)) {
            throw new MintFunException(ImmutableMap.of("user id:", id), ErrorCode.MINT_USER_DETAIL_NOT_FOUND);
        }
        MintUserDto uMintUserDto = uMapper.tDto(uMintUser);
        return ResponseEntity.ok(uMintUserDto);
    }

    @GetMapping("/user")
    public ResponseEntity<List<MintUserDto>> getUserByName(@RequestParam("name") @Valid @NotBlank String name) {
        List<MintUser> users = useService.findUserByName(name);
        if (ObjectUtils.isEmpty(users)) {
            throw new MintFunException(ImmutableMap.of("user name:", name), ErrorCode.MINT_USER_NOT_FOUND);
        }
        List<MintUserDto> userDtos = users.stream().map(user -> uMapper.tDto(user)).collect(Collectors.toList());
        return ResponseEntity.ok(userDtos);
    }

    @GetMapping("/user/all")
    public ResponseEntity<PageHelperUtil> getUserByPage(@RequestParam("page") Integer page,
            @RequestParam("rows") Integer rows) {
        PageHelperUtil helperUtil = useService.findUserByPage(page, rows);
        List<MintUser> users = (List<MintUser>) helperUtil.getDataTs();
        List<MintUserDto> userDtos = users.stream().map(user -> uMapper.tDto(user)).collect(Collectors.toList());
        helperUtil.setDataTs(userDtos);
        return ResponseEntity.ok(helperUtil);
    }

    @PostMapping("/user/add")
    public ResponseEntity<?> addUser(@RequestBody @Valid MintUserDto userDto) {
        MintUser user = uMapper.toSource(userDto);
        useService.saveUser(user);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> removeUserById(@PathVariable("id") @Valid @Min(1) Integer id) {
        useService.removeUserById(id);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/user/update")
    public ResponseEntity<?> updateUser(@RequestBody @Valid MintUserDto userDto) {
        MintUser user = uMapper.toSource(userDto);
        useService.modifyUserById(user);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/nft/{id}")
    public ResponseEntity<MintNftDto> getNftById(@PathVariable("id") @Valid @Min(1) Integer id) {
        MintNft nMintNft = nftService.findNftById(id);
        if (ObjectUtils.isEmpty(nMintNft)) {
            throw new MintFunException(ImmutableMap.of("nft id:", id), ErrorCode.MINT_NFT_NOT_FOUND);
        }
        MintNftDto nftDto = nMapper.tDto(nMintNft);
        return ResponseEntity.ok(nftDto);
    }

    @GetMapping("/nft")
    public ResponseEntity<List<MintNftDto>> getNftByName(@RequestParam("name") @Valid @NotBlank String name) {
        List<MintNft> nfts = nftService.findNftByName(name);
        if (ObjectUtils.isEmpty(nfts)) {
            throw new MintFunException(ImmutableMap.of("nft name:", name), ErrorCode.MINT_NFT_NOT_FOUND);
        }
        List<MintNftDto> nftDtos = nfts.stream().map(nft -> nMapper.tDto(nft)).collect(Collectors.toList());
        return ResponseEntity.ok(nftDtos);
    }

    @PostMapping("/nft/add")
    public ResponseEntity<?> addNft(@RequestBody @Valid MintNftDto nftDto) {
        MintNft nft = nMapper.toSource(nftDto);
        nftService.asyncSaveNft(nft);
        return ResponseEntity.accepted().build();
    }
}
