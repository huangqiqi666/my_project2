package com.test.thread.service;

import cn.hutool.core.thread.ThreadUtil;
import com.test.thread.utils.NamingThreadFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Classname CompletableFutureService
 * @Description CompletableFuture的使用
 * 参考博客：https://www.jianshu.com/p/94fda1648fac
 * TODO:强烈建议指定自定义的线程池，不指定默认使用ForkJoin的线程池，避免其他线程长IO造成其他线程等待(测试15)
 *
 * @Date 2022/10/11 14:30
 * @Created by huangqiqi
 */
public class CompletableFutureService {
    //TODO:设置线程池
    public static final ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(3, 6, 3000, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(),
            new NamingThreadFactory(Executors.defaultThreadFactory(), "测试线程池"));//自定义线程池每次

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //1.new实例化
//        CompletableFuture<List<Integer>> result = new CompletableFuture<>();
        //2.supplyAsync方法（有返回结果）
        CompletableFuture<List<Integer>> result = CompletableFuture.supplyAsync(() -> {
            List<Integer> ids = new ArrayList<Integer>(Arrays.asList(1, 2, 3));
            return ids;
        });
        System.out.println(result.get());
        //3.runAsync方法(没有返回结果)
        CompletableFuture<Void> noResult = CompletableFuture.runAsync(() -> {
            System.out.println("这个没有返回值");
        });
        System.out.println(noResult.get());

        //4.连续流式执行
        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
            System.out.println("这个没有返回值");
        }).thenRun(() -> System.out.println("执行通知逻辑")).thenRun(() -> System.out.println("执行附加逻辑"));
        System.out.println(completableFuture.isDone());


        //5.异常处理exceptionally()方法
        System.out.println(CompletableFuture.supplyAsync(()->{
            int i=1/0;//TODO:会异常
            return "返回结果"+i;
        }).exceptionally(ex->"异常了").get());

        System.out.println(CompletableFuture.supplyAsync(()->{
            int i=1/1;
            return "返回结果"+i;
        }).exceptionally(ex->"异常了").get());

        //6.completeExceptionally()：如果想要CompletableFuture的结果就是异常的话，可以使用completeExceptionally()方法为其赋值
        //这个比较麻烦的一点是不能直接流式编程，要分开写
//        CompletableFuture<String> completableFuture1 = CompletableFuture.supplyAsync(() -> {
//            return "返回结果";
//        });
//        completableFuture1.completeExceptionally(new RuntimeException("手动添加的异常！"));
//        System.out.println(completableFuture1.get());


        //7.组合CompletableFuture:
        // thenCompose(用来连接两个有依赖关系的任务，结果由第二个任务返回)、
        // thenCombine(会将两个任务的执行结果作为方法入参)按顺序连接两个CompletableFuture对象
        //TODO:实际开发中这个方法其实还挺有用的，比如说我们要先获取用户信息然后再利用用户信息去做别的事。
        CompletableFuture<String> combineCompletableFuture = CompletableFuture.supplyAsync(() -> "任务一执行完成")
                .thenCompose(x -> CompletableFuture.supplyAsync(() -> x +"\n" +"任务二执行完成"))
                //TODO:thenCombine的使用
                .thenCombine(CompletableFuture.supplyAsync(()->"任务三执行完成"),(s,s2)->s+"\n"+s2);
        System.out.println(combineCompletableFuture.get());
        //那么thenCombine()和thenCompose()有什么区别呢？
        //thenCompose可以两个CompletableFuture对象，并将前一个任务返回的结果作为下一个任务的参数他们之间存在着先后顺序。可以说第一个任务没完成没办法开始第二个任务。
        //thenCombine会两个任务都执行完成后，把两个任务的结果进行处理。两个任务是并行执行的，它们之间没有依赖顺序。


        //8.并行运行多个CompletableFuture
        CompletableFuture<String> task1 = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "执行任务1";
        });
        CompletableFuture task2 = CompletableFuture.supplyAsync(() -> "执行任务2");
        CompletableFuture task3 = CompletableFuture.supplyAsync(() -> "执行任务3");
        CompletableFuture task4 = CompletableFuture.supplyAsync(() -> "执行任务4");
        CompletableFuture task5 = CompletableFuture.supplyAsync(() -> "执行任务5");

        System.out.println(LocalDateTime.now());
        System.out.println(CompletableFuture.allOf(task1, task2, task3, task4, task5).get()+"所有完成返回"+new Date().toString());
        System.out.println(CompletableFuture.anyOf(task1, task2, task3, task4, task5).get()+"任一完成返回"+new Date().toString());



        //9.测试thenApply、allOf
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            int num = 200;
            ThreadUtil.sleep(1);
            System.out.println(Thread.currentThread().getName()+"第一次运算：" + num);
            return num;
        }).thenApply(num -> {
            int sum = num + 1000;
            ThreadUtil.sleep(2);
            System.out.println(Thread.currentThread().getName()+"第二次运算：" + sum);
            return sum;
        });
        //异常
        future.exceptionally(ex->{
            System.out.println("运算异常了");
            throw new RuntimeException("运算异常");
        });

        //TODO:主线程等待子线程完成（join或future.get()），获取结果
        //方式1
//        future.join();
//        Integer sum = future.get();
//        System.out.println("主线程："+Thread.currentThread().getName()+"执行完毕"+sum);

        //方式2:CompletableFuture.allOf().join
        CompletableFuture<String> allFuture = CompletableFuture.allOf(future).thenApply(x -> {
            String msg = "最终结果:";
            try {
                System.out.println(Thread.currentThread().getName()+"执行allOf");
                ThreadUtil.sleep(2);
                msg += future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            return msg;
        });
        String finalResult = allFuture.get();//等待子线程
        System.out.println("主线程：" + Thread.currentThread().getName() + "执行完毕!" + finalResult);


        //10.测试thenCombine
        CompletableFuture<Integer>f1=  CompletableFuture.supplyAsync(()->{
            ThreadUtil.sleep(2);
            return 2;
        });
        CompletableFuture<Integer>f2=  CompletableFuture.supplyAsync(()->{
            ThreadUtil.sleep(3);
            return 5;
        });
        CompletableFuture<Integer> combineFuture = f1.thenCombine(f2, (a, b) -> {
            System.out.println(a);
            System.out.println(b);
            return a + b;
        });
        //阻塞
        System.out.println("combine的最终结果："+ combineFuture.get());

        //11.thenAcceptBoth(接收两个Future,无返回值)
        CompletableFuture<Void> acceptBothFulture = f1.thenAcceptBoth(f2, (a, b) -> {
            ThreadUtil.sleep(1);
            System.out.println(Thread.currentThread().getName()+"计算两个的乘积:" + a * b);
        });
        //阻塞
        acceptBothFulture.get();

        System.out.println("主线程执行...");

        //11.applyToEither(有返回结果如:x):两个线程任务相比较，先获得执行结果的，就对该结果进行下一步的转化操作
        System.out.println("f1和f2执行最快的返回结果："+f1.applyToEither(f2, x -> x));
        //12.runAfterEither(无返回值)：两个线程任务比较，先获得执行结果的，就对该结果进行消费
        f1.runAfterEither(f2, () -> System.out.println("f1、f2两个线程有一个已经执行完成了!"));

        //13.anyOf(返回类型Object):针对多个Future,任意完成就执行
        CompletableFuture<Integer>f3=  CompletableFuture.supplyAsync(()->{
            ThreadUtil.sleep(1);
            return 10;
        });
        CompletableFuture<Object> anyOfFuture = CompletableFuture.anyOf(f1, f2, f3).thenApply(a -> a);
        System.out.println("f1,f2,f3三个线程执行最快的返回值"+anyOfFuture.get());

        //14.allOf(返回类型Void，即无返回值，不能接thenApply):针对多个Future,全部完成才执行
        CompletableFuture<Void> allOf = CompletableFuture.allOf(f1, f2, f3);
        //阻塞
        allOf.join();
//        allOf.get();
        System.out.println("f1,f2,f3三个线程全部执行完成，执行主线程后续逻辑...");

        //15.CompletableFuture结合自定义线程池(TODO:强烈建议指定自定义的线程池，不指定默认使用ForkJoin的线程池，避免其他线程长IO造成其他线程等待)
        CompletableFuture.runAsync(()->{
            ThreadUtil.sleep(2);
            System.out.println(Thread.currentThread().getName()+"执行线程逻辑");//日志打印可以看出线程编号是自定义的线程池编号
//        }, ThreadManager.getThreadPollProxy().initExecutor());//传入自定义的线程池ThreadPoolExecutor
        }, poolExecutor);//传入自定义的线程池ThreadPoolExecutor



        System.out.println(Thread.currentThread().getName()+"主线程继续执行...");




    }
}
