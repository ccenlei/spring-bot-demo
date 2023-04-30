package com.spring.bot.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.bot.demo.dao.MintUserMapper;
import com.spring.bot.demo.entity.MintUser;
import com.spring.bot.demo.entity.PageHelperUtil;

@Service
public class MintUseService {

    @Autowired
    private MintUserMapper uMapper;

    public MintUser findUserById(Integer id) {
        return uMapper.selectUserById(id);
    }

    public List<MintUser> findUserByName(String name) {
        return uMapper.selectUserByName(name);
    }

    public PageHelperUtil findUserByPage(int page, int offset) {
        PageHelper.startPage(page, offset);
        List<MintUser> mUsers = uMapper.selectAll();
        PageInfo<MintUser> pageInfo = new PageInfo<>(mUsers);
        PageHelperUtil helperUtil = PageHelperUtil.builder()
                .total(pageInfo.getTotal())
                .pageTotal(pageInfo.getPages())
                .page(pageInfo.getPageNum())
                .pageRows(pageInfo.getPageSize())
                .dataTs(pageInfo.getList())
                .build();
        return helperUtil;
    }

    public int saveUser(MintUser mUser) {
        return uMapper.insertUser(mUser);
    }

    public void removeUserById(Integer id) {
        uMapper.deleteUserById(id);
    }

    public void modifyUserById(MintUser mUser) {
        uMapper.updateUserById(mUser);
    }
}
