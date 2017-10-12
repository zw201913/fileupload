package com.github.zw201913.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface FileUpload {
	/**
	 * 是否做摘要
	 * 
	 * @return
	 */
	public boolean digest() default false;

	/**
	 * 是否自动保存
	 * 
	 * @return
	 */
	public boolean autoSave() default true;
}
