# 短链项目介绍

## 背景

1. **什么是短链**

   短链即短链接，是将原始长网址压缩而成的短网址。

2. **短链原理**

   通过短链找到原始链接（长链接），然后重定向到原始链接地址即可。

3. **短链作用**

   方便传播；对链接点击情况做后续追踪

## 技术栈

- SpringBoot
- Mybatis plus
- Guava

## 项目需求

- 通过原始的长链接生成一个或多个短链地址。
- 用户访问短链接会被重定向到原始长链接。

## 表结构

```sql
-- surl: table
CREATE TABLE `surl` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `short_url` varchar(255) NOT NULL DEFAULT '',
  `long_url` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `surl_pk_2` (`short_url`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
```

## 项目关键手段

- **唯一短链生成**

  本例生成唯一短链的方法为**MurmurHash + 六十二进制表示法**

- **如何判断是否发生 HASH 冲突？**

  采用Guava布隆过滤器判断是否存在 HASH 冲突。

- **如何处理 HASH 冲突？**

  如果发生 HASH 冲突，就在生成的字符串后随机拼一个字符后继续校验，直到冲突解决。

- **如何解决布隆过滤器误判的情况**

  在布隆过滤器过滤后使用数据库校验。默认布隆过滤器中提示存在的短链地址，都要去数据库中再次检验一遍。

- **如果用在分布式场景呢**
  本例用的是Guava实现的布隆过滤器，如果想用在分布式场景，可以引入Redis实现的布隆过滤器
