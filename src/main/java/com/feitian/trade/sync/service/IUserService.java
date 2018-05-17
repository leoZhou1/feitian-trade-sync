package com.feitian.trade.sync.service;

import com.feitian.trade.sync.model.TbUser;

import java.util.Date;
import java.util.List;

public interface IUserService {
    void addUser(TbUser user);
    TbUser findUserById(Long userId);
    List<TbUser> findAllUsers();
    List<TbUser> findIncrementUsers(Long limit);
    void updateAuthorizeInfo(TbUser user);
    void updateLastUpdateTime(Long userId, Date lastUpdateTime);
}
