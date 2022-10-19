package com.test.designpattern.strategy.Service.impl;

import com.test.designpattern.strategy.Service.GrantTypeSerive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @Classname QueryGrantTypeService
 * @Description
 * @Date 2022/10/18 17:30
 * @Created by huangqiqi
 */
@Service
public class QueryGrantTypeService {

    @Autowired
    private GrantTypeSerive grantTypeSerive;
    //value是个函数式接口
    private Map<String, Function<String,String>> grantTypeMap=new HashMap<>();

    /**
     *  初始化业务分派逻辑,代替了if-else部分
     *  key: 优惠券类型
     *  value: lambda表达式,最终会获得该优惠券的发放方式
     */
    @PostConstruct
    public void dispatcherInit(){
        grantTypeMap.put("红包",resourceId->grantTypeSerive.redPaper(resourceId));
        grantTypeMap.put("购物券",resourceId->grantTypeSerive.shopping(resourceId));
        grantTypeMap.put("qq会员",resourceId->grantTypeSerive.QQVip(resourceId));
    }

    public String getResult(String resourceType,String resourceId){
        //Controller根据 优惠券类型resourceType、编码resourceId 去查询 发放方式grantType
        Function<String,String> result=this.grantTypeMap.get(resourceType);
        if(result!=null){
            //传入resourceId 执行这段表达式获得String型的grantType
            return result.apply(resourceId);
        }
        return "查询不到该优惠券的发放方式";
    }

    //函数式接口方法
    public static void main(String[] args) {
        //Function（key：入参类型  value:结果类型）
        Function<String,String> fun = x -> x+"成功";
        System.out.println(fun.apply("测试"));

        //Consumer （key：入参类型）
        Consumer<Object> consumer = System.out::println;
//        consumer.accept("张三");
        consumer.accept(111);

        //Predicate （key:入参类型）
        Predicate<String> predicate = b -> Objects.equals("张三", b);//是否满足
        System.out.println(predicate.test("李四"));

    }
}
