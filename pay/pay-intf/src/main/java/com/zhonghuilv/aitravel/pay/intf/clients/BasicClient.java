package com.zhonghuilv.aitravel.pay.intf.clients;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.zhonghuilv.aitravel.pay.intf.common.pojo.dto.BatchUpdateDTO;
import com.zhonghuilv.aitravel.pay.intf.common.pojo.dto.PageQuery;
import com.zhonghuilv.aitravel.pay.intf.common.pojo.dto.QueryExample;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
    * @Description: TODO
    * @Author:      chenmin
    * @CreateDate:  2019-05-22 2019-05-22
    * @Version:     1.0
    * @JDK:         10
    */
public interface BasicClient<T> {

    /**
     * 新增
     *
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    T save(@RequestBody T model);

    /**
     * 全量修改
     *
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    T update(@RequestBody T model);

    /**
     * 局部修改
     *
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.PATCH)
    T updateSelective(@RequestBody T model);

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    boolean delete(@PathVariable("id") Long id);

    //查询
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    T loadById(@PathVariable("id") Long id);

    /**
     * 单行查询
     *
     * @return
     */
    @RequestMapping(value = "/_search_one", method = RequestMethod.POST)
    T loadOne(@RequestBody T model);

    @RequestMapping(value = "/_search", method = RequestMethod.POST)
    List<T> loadList(@RequestBody T model);

    @ApiOperation("分页查询")
    @RequestMapping(value = "/_search_page", method = RequestMethod.POST)
    PageInfo<T> loadPage(PageQuery<T> query);

    @ApiOperation("加载行数")
    @RequestMapping(value = "/_load_count", method = RequestMethod.POST)
    int loadCount(T model);

    @RequestMapping(value = "/_search_by_example", method = RequestMethod.POST)
    Page<T> searchByExample(@RequestBody QueryExample<T> pageQuery);

    @RequestMapping(value = "/_search_by_ids", method = RequestMethod.POST)
    List<T> searchByIds(@RequestBody List<Long> ids);

    @RequestMapping(value = "/_batch_update", method = RequestMethod.POST)
    int batchUpdate(@RequestBody BatchUpdateDTO<T> dto);

    @RequestMapping(value = "/_search_by_column_likely",method = RequestMethod.POST)
    PageInfo<T> searchByColumnNameLikely(@RequestBody PageQuery<T> pageQuery, @RequestParam("columnNames") String columnNames);
}
