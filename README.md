
# NUTZ-ONEKEY
NUTZ一键脚手架

## 概述

### github地址 
 [https://github.com/Kerbores/NUTZ-ONEKEY][1]
### 仓库
```
<repositories>
		<repository>
			<id>dgj nexus</id>
			<name>Team Nexus Repository</name>
			<url>http://maven.kerbores.com/nexus/content/groups/public</url>
		</repository>
	</repositories>
```
## 目标
- 提供开箱即用的企业级开发平台
- 提供企业开发中常见的组件和交互示例
- 提供高度复用的业务基类
- 提供各种超高体验的前端交互组件
- ...

## 功能

### 6表ACL,标准用户角色权限体系
- 6表标准用户角色权限体系
- 多类型用户系统可继承用户进行扩展,同时可复用ACL鉴权体系
- shiro权限控制,可精确到按钮的权限控制
### 轻量化配置管理
- 轻量级的参数配置管理
- 配置参数即刻生效
### 完善的日志系统
- 用户登录日志
- 核心业务操作日志
### APM
- 基于sigar的应用性能监控
- 应用性能监控提供单机控制台和json数据流(便于集中化监控)
- 集成druid sql性能监控
- 核心业务运行性能监控
### 无限级树状组织架构管理
- 实现无限级的树状组织结构管理功能
- 提供树状组织架构选择器
### 高度业务封装
- 基本零代码的业务层
- 省略数据访问层
- 高灵活度的分页组件
### 清爽易用的前端组件
- 使用bootstrap-ace最新版界面组件
- 高度定制化的表单验证组件
### 拒绝硬编码的sql管理
- 文件化的sql管理器
- 单表使用orm,跨表使用配置化的自定义sql语句
  + 保证最小化编码
  + 保证sql的集中化,语义化管理
  + 保证非单表sql语句的配置化和优化
  
## 关于我
key  | Value
------------- | -------------
email | kerbores@gmail.com
QQ | 19754300

## 捐赠
码字不容易,如果本项目的源码在你的项目中使用或者本项目的源码赐予你编码的洪荒之力,那么你可以扫一扫以下的二维码给我捐赠:
![微信二维码][2]
![支付宝二维码][3]


  [1]: http://git.oschina.net/uploads/qrcode/qrcode_wechat_14675223541030518.png
  [2]: http://git.oschina.net/uploads/qrcode/qrcode_wechat_14675223541030518.png
  [3]: http://git.oschina.net/uploads/qrcode/qrcode_alipay_14675225071030518.png