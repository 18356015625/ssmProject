package com.liyan.common;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 写入缓存至redis，
 * @author dell
 *
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME) 
@Target({ElementType.FIELD,ElementType.METHOD})
public @interface CacheList {
	 String key();  
	 String value() default "";
}
