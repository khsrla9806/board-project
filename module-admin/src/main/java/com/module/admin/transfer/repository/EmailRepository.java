package com.module.admin.transfer.repository;

import com.module.admin.transfer.dto.EmailTransfer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface EmailRepository {

    // 관리자가 보낸 메일 정보 저장
    void save(@Param("emailTransfer") EmailTransfer emailTransfer);
}
