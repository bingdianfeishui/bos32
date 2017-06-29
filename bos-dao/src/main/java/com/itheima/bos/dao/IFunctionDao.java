package com.itheima.bos.dao;

import java.util.List;

import com.itheima.bos.domain.Function;
import com.itheima.bos.domain.User;

public interface IFunctionDao extends IBaseDao<Function>{

    List<Function> findFunctionsByUserId(String id);

    List<Function> findAllMenuFunction();

    List<Function> findMenuFuncitonByUser(User user);
    
}
