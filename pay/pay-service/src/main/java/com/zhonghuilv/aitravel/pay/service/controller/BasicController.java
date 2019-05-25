package com.zhonghuilv.aitravel.pay.service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhonghuilv.aitravel.common.enums.EnumQueryMatch;
import com.zhonghuilv.aitravel.common.enums.EnumQueryOrder;
import com.zhonghuilv.aitravel.common.excption.ParameterNotValidException;
import com.zhonghuilv.aitravel.common.excption.ServiceLogicException;
import com.zhonghuilv.aitravel.pay.intf.common.pojo.dto.BatchUpdateDTO;
import com.zhonghuilv.aitravel.pay.intf.common.pojo.dto.PageQuery;
import com.zhonghuilv.aitravel.pay.intf.common.pojo.dto.QueryExample;
import com.zhonghuilv.aitravel.service.mapper.CommonMapper;
import com.zhonghuilv.aitravel.service.util.ExampleBuiler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import javax.persistence.Transient;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
    * @Description: TODO
    * @Author:      chenmin
    * @CreateDate:  2019-05-22 2019-05-22
    * @Version:     1.0
    * @JDK:         10
    */
public abstract class BasicController<T> /*implements BasicClient<T>*/ {

    private CommonMapper<T> commonMapper;

    private Class<T> clazz;

    @Autowired
    ObjectMapper objectMapper;

    public BasicController(CommonMapper<T> commonMapper) {

        this.commonMapper = commonMapper;

        Type genericSuperclass = this.getClass().getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            clazz = (Class<T>) ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
        }
    }

    /**
     * 新增
     *
     * @return
     */
//    @Override
    @RequestMapping(value = "", method = RequestMethod.POST)
    public T save(@RequestBody T model) {
        commonMapper.insertUseGeneratedKeys(model);

        return model;
    }

    @RequestMapping(value = "/_batch_add", method = RequestMethod.POST)
    public List<T> saveList(@RequestBody List<T> model) {
        commonMapper.insertList(model);
        return model;
    }

    @RequestMapping(value = "/_batch_update", method = RequestMethod.POST)
    public int batchUpdate(@RequestBody BatchUpdateDTO<T> dto) {

        Example example = new Example(dto.getModel().getClass());
        example.createCriteria().andIn("id", dto.getIds());
        int rows = commonMapper.updateByExampleSelective(dto.getModel(), example);
        if (rows != dto.getIds().size()) {
            throw new ServiceLogicException("批处理修改成功行数与id集size不等");
        }
        return rows;
    }

    /**
     * 全量修改
     *
     * @param mode
     * @return
     */
//    @Override
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public T update(@RequestBody T mode) {
        commonMapper.updateByPrimaryKey(mode);
        return mode;
    }

    /**
     * 局部修改
     *
     * @param model
     * @return
     */
//    @Override
    @RequestMapping(value = "", method = RequestMethod.PATCH)
    public T updateSelective(@RequestBody T model) {
        commonMapper.updateByPrimaryKeySelective(model);
        return model;
    }

    //    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public boolean delete(@PathVariable("id") Long id) {
        return commonMapper.deleteByPrimaryKey(id) == 1;
    }

    //    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public T loadById(@PathVariable("id") Long id) {
        return commonMapper.selectByPrimaryKey(id);
    }

    /**
     * 单行查询
     *
     * @param model
     * @return
     */
//    @Override
    @RequestMapping(value = "/_search_one", method = RequestMethod.POST)
    public T loadOne(@RequestBody T model) {
        return commonMapper.selectOne(model);
    }

    //    @Override
    @RequestMapping(value = "/_search", method = RequestMethod.POST)
    public List<T> loadList(@RequestBody T model) {
        return commonMapper.select(model);
    }

    //    @Override
    @RequestMapping(value = "/_search_page", method = RequestMethod.POST)
    public PageInfo<T> loadPage(@RequestBody PageQuery<T> query) {
        return PageHelper.startPage(query.getPageNum(), query.getPageSize()).doSelectPageInfo(
                () -> commonMapper.select(query.getQueryPo()));
    }

    /**
     * 自定义查询
     *
     * @param pageQuery
     * @return
     */
    @RequestMapping(value = "/_search_by_example", method = RequestMethod.POST)
    public Page<T> searchByExample(@RequestBody QueryExample<T> pageQuery) {

        Map<String, Object> map = objectMapper.convertValue(pageQuery.getQueryPo(), Map.class);
//        JSONObject map = (JSONObject) JSONObject.toJSON(pageQuery.getQueryPo());
        Example example = new Example(clazz);
        if (!map.isEmpty()) {
            Example.Criteria criteria = example.createCriteria();

            map.forEach((property, value) -> {

                if (Objects.isNull(value))
                    return;
                switch (queryMatch(pageQuery.getMatch(), property, value)) {
                    case EQ:
                        criteria.andEqualTo(property, value);
                        break;
                    case GT:
                        criteria.andGreaterThan(property, value);
                        break;
                    case LT:
                        criteria.andLessThan(property, value);
                        break;
                    case GTE:
                        criteria.andGreaterThanOrEqualTo(property, value);
                        break;
                    case LTE:
                        criteria.andLessThanOrEqualTo(property, value);
                        break;
                    case LIKE:
                        criteria.andLike(property, (String) value);
                        break;
                }
            });
        }

        Map<String, String> order = pageQuery.getOrder();

        if (!(Objects.isNull(order) || order.isEmpty())) {
            order.forEach((property, orderType) -> {
                switch (EnumQueryOrder.toEnum(orderType)) {
                    case ASC:
                        example.orderBy(property).asc();
                        break;
                    case DESC:
                        example.orderBy(property).desc();
                        break;
                }
            });
        }

        QueryExample.PageInfo pageInfo = pageQuery.getPageInfo();

        if (!CollectionUtils.isEmpty(pageQuery.getSelectProperties())) {

            example.selectProperties(pageQuery.getSelectProperties().toArray(new String[]{}));
        }

        if (!CollectionUtils.isEmpty(pageQuery.getExcludeProperties())) {
            example.excludeProperties(pageQuery.getExcludeProperties().toArray(new String[]{}));
        }
        if (Objects.nonNull(pageInfo)) {
            Page<T> result = PageHelper.startPage(pageInfo.getPageNum(), pageInfo.getPageSize())
                    .count(pageInfo.getCount()).doSelectPage(() -> commonMapper.selectByExample(example));
            if (Objects.nonNull(pageInfo.getTotal()))
                result.setTotal(pageInfo.getTotal());
            return result;
        }

        List<T> games = commonMapper.selectByExample(example);
        Page<T> page = new Page<>();
        page.addAll(games);
        return page;

    }

    private EnumQueryMatch queryMatch(Map<String, String> match, String property, Object value) {

        if (Objects.nonNull(value)) {
            if (Objects.nonNull(match)) {
                if (match.containsKey(property)) {
                    return EnumQueryMatch.toEnum(match.get(property));
                }
            }
        }
        return EnumQueryMatch.EQ;
    }

    /**
     * count 行数
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/_load_count", method = RequestMethod.POST)
    public int loadCount(@RequestBody T model) {
        return commonMapper.selectCount(model);
    }

    @RequestMapping(value = "/_search_by_ids", method = RequestMethod.POST)
    public List<T> searchByIds(@RequestBody List<Long> ids){
        if (CollectionUtils.isEmpty(ids)){
            return new ArrayList<>();
        }
        Example example = new Example(clazz);
        example.createCriteria().andIn("id",ids);
        return commonMapper.selectByExample(example);
    }

    /**
     * 依据字段的统一模糊查找
     *
     * @param columnNames   需要模糊查询的字段
     * @param pageQuery     分页查询参数
     * @return PageInfo<T>
     * @throws ParameterNotValidException 当columnName不是该类的属性时，会抛出此异常
     */
    @RequestMapping(value = "/_search_by_column_likely",method = RequestMethod.POST)
    public PageInfo<T> searchByColumnNameLikely(@RequestBody PageQuery<T> pageQuery, @RequestParam("columnNames") String columnNames){
        //该方法必须加验证，验证该columnName是否为该实体类的属性
        String[] split = columnNames.split(",");
        List<String> columnList = Arrays.asList(split);

        Field[] fields = clazz.getDeclaredFields();
        List<Field> fieldList = Arrays.asList(fields);

        columnList.forEach(c-> {
            try {
                clazz.getDeclaredField(c);
            } catch (NoSuchFieldException e) {
                throw new ParameterNotValidException("不存在此columnName:"+c);
            }
        });
        Example example = ExampleBuiler.cls(clazz);
        Example.Criteria criteria = example.createCriteria();
        for (Field temp:fieldList){
            if (temp.isAnnotationPresent(Transient.class)) {
                continue;
            }
            try {
                temp.setAccessible(true);
                Object object = temp.get(pageQuery.getQueryPo());
                if (Objects.isNull(object)) {
                    continue;
                }
                if(columnList.contains(temp.getName())) {
                    criteria.andLike(temp.getName(),"%"+object+"%");
                }else{
                    criteria.andEqualTo(temp.getName(), object);
                }
            } catch (IllegalAccessException e) {
                throw new ParameterNotValidException("没有权限访问此字段！");
            }
        }

        return PageHelper.startPage(pageQuery.getPageNum(), pageQuery.getPageSize()).doSelectPageInfo(
                () -> commonMapper.selectByExample(example));

    }
}
