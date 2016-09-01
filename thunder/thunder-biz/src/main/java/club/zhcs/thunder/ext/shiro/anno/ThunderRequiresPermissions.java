package club.zhcs.thunder.ext.shiro.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.shiro.authz.annotation.Logical;

import club.zhcs.thunder.vo.InstallPermission;

/**
 * 
 * @author 王贵源
 *
 * @email kerbores@kerbores.com
 *
 * @description
 * 
 * @copyright copyright©2016 zhcs.club
 *
 * @createTime 2016年8月23日 下午12:59:26
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ThunderRequiresPermissions {
	InstallPermission[] value();

	Logical logical() default Logical.AND;
}
