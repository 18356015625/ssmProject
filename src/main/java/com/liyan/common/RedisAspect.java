package com.liyan.common;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;

@Aspect
public class RedisAspect {
	
	@Autowired
	private RedisCache redisCache;

	@Pointcut("@annotation(com.liyan.common.CacheList)")
	public void myPointCut() {

	}

	@Pointcut("@annotation(com.liyan.common.MyCacheRedis)")
	public void redisPointCut() {

	}

	//@Around("redisPointCut()")
	public void redisaround(ProceedingJoinPoint joinPoint) throws Throwable {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
		Method realMethod = joinPoint.getTarget().getClass().getDeclaredMethod(signature.getName(),
				method.getParameterTypes());
		// 此处realMethod是目标对象（原始的）的方法
		MyCacheRedis annotation = realMethod.getAnnotation(MyCacheRedis.class);
		String key = annotation.key();
		String newKey = null;
		if(key.startsWith("{") && key.endsWith("}")) {
			String temp = key.substring(1, key.length() - 1);
			String[] keys = temp.split("\\.");
			//说明是动态传参的
			Object[] args = joinPoint.getArgs();
			Parameter[] parameterNames = realMethod.getParameters(); 
			if(args.length > 0) {
				int index = ArrayUtils.indexOf(parameterNames, keys[0]);
				Class<? extends Object> object = args[index].getClass();
				Field[] fields = object.getFields();
				for(Field f : fields) {
					System.out.println(f);
				}
			}
			
		}
		System.out.println("需要删除的key"+key);
		//从redis中删除数据
		redisCache.removeKey(key);
		joinPoint.proceed();
	}

	//@Around("myPointCut()")
	public Object around(ProceedingJoinPoint joinPoint) throws NoSuchMethodException, SecurityException {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
		Method realMethod = joinPoint.getTarget().getClass().getDeclaredMethod(signature.getName(),
				method.getParameterTypes());
		// 此处realMethod是目标对象（原始的）的方法
		CacheList annotation = realMethod.getAnnotation(CacheList.class);
		String redisKey = annotation.key();
		// 获取从redis中查询到的对象
		Object objectFromRedis = redisCache.getDataFromRedis(redisKey);
		// 如果查询到了
		if (null != objectFromRedis) {
			System.out.println("从redis中查询到了数据...不需要查询数据库");
			return objectFromRedis;
		}
		System.out.println("没有从redis中查到数据...");
		// 没有查到，那么查询数据库
		Object object = null;
		try {
			object = joinPoint.proceed();
		} catch (Throwable e) {
			e.printStackTrace();
		}
		// 后置：将数据库中查询的数据放到redis中
		redisCache.setDataToRedis(redisKey, object);
		// 将查询到的数据返回
		return object;
	}
}
