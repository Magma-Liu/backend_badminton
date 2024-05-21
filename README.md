# 羽毛球比赛管理系统 - 后端

这是羽毛球比赛管理系统的后端部分，使用 Spring Boot 框架构建，提供 RESTful API 服务以支持前端的各项功能。系统包括用户管理、活动管理、比赛管理等功能。

## 目录

- [项目简介](#项目简介)
- [功能特点](#功能特点)
- [技术栈](#技术栈)
- [安装与运行](#安装与运行)
- [API 文档](#api-文档)
- [配置说明](#配置说明)
- [常见问题](#常见问题)
- [贡献指南](#贡献指南)
- [许可证](#许可证)

## 项目简介

本项目是羽毛球比赛管理系统的后端部分，主要提供数据存储、业务逻辑处理和 API 接口，以支持前端的小程序功能。

## 功能特点

- 用户注册和登录
- 活动和比赛的创建与管理
- 参赛队伍管理
- 比赛结果记录与查询
- 角色权限管理（超级管理员、裁判长、普通用户等）

## 技术栈

- **后端框架**：Spring Boot 2.7.18
- **数据库**：MySQL
- **持久层框架**：MyBatis
- **安全**：JWT（JSON Web Token）用于用户认证
- **依赖管理**：Maven

## 安装与运行

### 前置条件

确保你的开发环境中已经安装了以下工具：
- [JDK 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- [Maven](https://maven.apache.org/)
- [MySQL](https://www.mysql.com/) 数据库

### 克隆项目

```bash
git clone https://github.com/Magma-Liu/backend_badminton.git
cd backend_badminton
```

### 配置数据库

数据库基本信息（含测试数据）在`badminton_management_system.sql`中。在 MySQL 中配置数据库：
```sql
source yourPathToFile/badminton_management_system.sql;
```

### 配置应用

在 `src/main/resources` 目录下创建 `application.properties` 文件，并添加以下内容（注意需要填写自己的用户名和密码）：
```properties
spring.application.name=backend_badminton
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3336/ymq
spring.datasource.username=YourName
spring.datasource.password=YourPassword
spring.mvc.static-path-pattern=/**
```

！！！注意！！！要看自己的数据库端口，不要填错。

### 构建和运行项目

```bash
mvn clean install
```

注意，你当前使用的jdk需要`pom.xml`中描述的版本匹配。

打包jar，可参见：[IDEA中打JAR包](https://www.cnblogs.com/acm-bingzi/p/6625303.html)

## API 文档

后端 API 文档可以使用 Swagger 生成，配置如下（需要在maven中添加Swagger）

在 `src/main/java/com/example/SpringBoot` 目录下创建 Swagger 配置类：
```java
package com.example.SpringBoot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.SpringBoot"))
                .paths(PathSelectors.any())
                .build();
    }
}
```

访问 `http://localhost:8080/swagger-ui.html` 查看 API 文档。

可参见教程[Springboot整合swagger](https://www.cnblogs.com/progor/p/13297904.html)

## 配置说明

在 `src/main/resources/application.properties` 文件中可以配置数据库连接信息和其他应用设置，如服务器端口、JWT 秘钥等。

JWT秘钥可以在`backend_badminton/src/main/java/com/example/backend_badminton/utils/JwtUtil.java`第13行进行配置

## 常见问题

### 1. 如何解决数据库连接失败的问题？

确保 MySQL 服务已启动，并且数据库连接信息在 `application.properties` 文件中配置正确。

### 2. 如何查看 API 文档？

启动项目后，在浏览器中访问 `http://localhost:8080/swagger-ui.html` 查看自动生成的 API 文档。

### 3. 如何生成和解析 JWT？

JWT（JSON Web Token）用于用户身份认证，在用户登录成功后生成，并在每次请求时验证。可以使用 `java-jwt` 库生成和解析 JWT。

## 贡献指南

### 提交代码

1. Fork 本仓库
2. 创建新分支 (`git checkout -b feature-branch`)
3. 提交代码 (`git commit -am 'Add some feature'`)
4. 推送到分支 (`git push origin feature-branch`)
5. 创建 Pull Request

## 许可证

本项目使用 MIT 许可证，详情请参见 [LICENSE](LICENSE)。
