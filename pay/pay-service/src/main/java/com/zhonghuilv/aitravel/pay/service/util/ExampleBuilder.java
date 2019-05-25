package com.zhonghuilv.aitravel.pay.service.util;

import tk.mybatis.mapper.entity.Example;

/**
    * @Description: TODO
    * @Author:      chenmin
    * @CreateDate:  2019-05-22 2019-05-22
    * @Version:     1.0
    * @JDK:         10
    */
public class ExampleBuilder {

    public static Example buildSimpleExample(Class entityClass, String pro, Object val) {
        Example example = new Example(entityClass);
        example.createCriteria().andEqualTo(pro, val);
        return example;
    }

    private Example example;

    private Example.Criteria criteria;

    public ExampleBuilder(Example example, Example.Criteria criteria) {
        this.example = example;
        this.criteria = criteria;
    }

    public static ExampleBuilder getBuilder(Class entityClass) {
        Example example = new Example(entityClass);
        return new ExampleBuilder(example, example.createCriteria());
    }

    public ExampleBuilder equalTo(String property, Object value) {
        criteria.andEqualTo(property, value);
        return this;
    }

    public Example create(){
        return example;
    }

}
