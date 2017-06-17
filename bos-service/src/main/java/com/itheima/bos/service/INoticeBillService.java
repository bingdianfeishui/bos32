package com.itheima.bos.service;

import java.util.List;

import com.itheima.bos.domain.NoticeBill;

public interface INoticeBillService {

    int save(NoticeBill model);

	List<NoticeBill> findDetachedToWorkBill();

}
