package com.zhonghuilv.aitravel.pay.service.mapper;

import com.zhonghuilv.aitravel.pay.intf.pojo.WechatMch;
import com.zhonghuilv.aitravel.service.mapper.CommonMapper;
import org.apache.ibatis.annotations.*;


@Mapper
public interface WechatMchMapper extends CommonMapper<WechatMch> {

    @ResultType(WechatMch.class)
    @Select("select wm.* " +
            "from wechat_mch wm" +
            "  inner join wechat_app_mch wam" +
            "    on wam.mch_id = wm.id" +
            " where wam.appid = #{appid} ")
    WechatMch selectByAppid(@Param("appid") String appid);

    /**
     * 查询app绑定商务号数量
     *
     * @param appid
     * @return
     */
    @Select("select count(1) from  wechat_app_mch where appid=#{appid}")
    int appidBindCount(@Param("appid") String appid);

    @Insert("insert into wechat_app_mch (appid, mch_id) values (#{appid},#{id})")
    int bindApp(@Param("id") Long id, @Param("appid") String appid);

    @Update("update wechat_app_mch set appid=#{appid} where mch_id=#{id}")
    int updateApp(@Param("id") Long id, @Param("appid") String appid);

    @Delete("delete from wechat_app_mch where mch_id=#{id} and appid =#{appid}")
    int unbindApp(@Param("id") Long id, @Param("appid") String appid);

    @ResultType(String.class)
    @Select("select appid from wechat_app_mch where mch_id = #{id}")
    String selectServerAppid(@Param("id") Long id);
}