package com.zhonghuilv.aitravel.pay.service.mapper;

import com.zhonghuilv.aitravel.pay.intf.pojo.SeqBuild;
import com.zhonghuilv.aitravel.service.mapper.CommonMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
    * @Description: TODO
    * @Author:      chenmin
    * @CreateDate:  2019-05-22 2019-05-22
    * @Version:     1.0
    * @JDK:         10
    */
@Mapper
public interface BuildMapper extends CommonMapper<SeqBuild> {

    @Select(" select FUN_SEQ(#{seqName})")
    String getSeqNextValue(@Param("seqName") String seqName);
}
