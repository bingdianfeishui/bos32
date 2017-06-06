package com.itheima.bos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.IDecidedZoneDao;


@Service
@Transactional
public class DecidedZoneService implements IDecidedZoneService {

    @Autowired
    private IDecidedZoneDao decidedZoneDao;
}
