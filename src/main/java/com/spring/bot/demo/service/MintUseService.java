package com.spring.bot.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.bot.demo.dao.MintUserDao;
import com.spring.bot.demo.entity.MintUser;

@Service
public class MintUseService {

    @Autowired
    private MintUserDao uDao;

    public MintUser findUserById(Integer id) {
        return uDao.selectUserById(id);
    }

    public List<MintUser> findUserByName(String name) {
        return uDao.selectUserByName(name);
    }

    public int saveUser(MintUser mUser) {
        return uDao.insertUser(mUser);
    }

    public void removeUserById(Integer id) {
        uDao.deleteUserById(id);
    }

    public void modifyUserById(MintUser mUser) {
        uDao.updateUserById(mUser);
    }
}
