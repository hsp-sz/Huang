# Issue #5 技术方案 — 这个项目是什么技术栈

## 1. 背景与问题

Issue #5 提出的问题是：“这个项目是什么技术栈”。

这是一个“信息问答 / 文档类” issue，而非功能需求。其本质是要求对仓库现有代码做技术栈盘点，并给出一份清晰、可追溯的技术栈说明，使项目的技术组成一目了然。

本步骤（`plan.md`）只产出技术方案；后续实现步骤将依据本方案补充一份面向读者的技术栈说明文档。

## 2. 项目概述（基于代码盘点）

- 项目名称：`bye-dwg-detail`（`groupId=cn.keyi`，`version=0.0.1-SNAPSHOT`，`packaging=jar`）
- 应用类型：Spring Boot 单体 Web 应用，面向“工件 / 产品明细（CAD 图纸明细 / BOM 物料清单）”的管理系统
- 入口：`src/main/java/cn/keyi/bye/ByeCadApplication.java`（`@SpringBootApplication`）
- 分层结构：`controller` → `service` → `dao`（JpaRepository）→ `model`（JPA 实体），并配 `config`（Shiro 配置）

## 3. 技术栈盘点结果

### 3.1 构建与运行
- 构建工具：Maven（`pom.xml`），打 jar 包
- 运行方式：Spring Boot 内嵌容器（`spring-boot-starter-web` 自带 Tomcat）

### 3.2 后端
- 语言：Java
- 核心框架：Spring Boot `2.3.1.RELEASE`（作为 parent POM）
  - `spring-boot-starter-web`：Web / REST
  - `spring-boot-starter-data-jpa`：数据访问 / ORM
  - `spring-boot-starter-thymeleaf`：服务端模板渲染
  - `spring-boot-devtools`（optional）：热重载
- 持久层：Spring Data JPA / Hibernate
  - `jpa.hibernate.ddl-auto=update`，方言 `MySQL5Dialect`
  - 实体：`Artifact`、`ArtifactDetail`、`SysUser`、`SysRole`、`SysPermission`（JPA 注解，含 `@OneToMany` 关联）
  - DAO 接口继承 `JpaRepository`，使用派生查询方法
- 数据库：MySQL（`mysql-connector-java`，runtime）
  - 库名：`dbcaddetail`（见 `application.properties`）
- 安全（认证 / 鉴权）：Apache Shiro `1.4.0`（`shiro-spring`）
  - 自定义 Realm：`MyShiroRealm`；过滤器链在 `ShiroConfig` 中以 `authc`/`anon` 控制路由
  - 凭证匹配：MD5 + 盐值 + 2 次哈希（`HashedCredentialsMatcher`）
  - 方法级权限：`@RequiresPermissions`
  - Thymeleaf 集成：`thymeleaf-extras-shiro 2.0.0`
- 模板引擎兼容：`nekohtml`（支持 Thymeleaf 的 `HTML` 宽松模式）
- 测试：JUnit（test 作用域）

### 3.3 前端
- 模板：Thymeleaf（`thymeleaf.mode=HTML`，`cache=false`），页面位于 `src/main/resources/templates`，含 `common/` 公共片段
- UI 框架：AdminLTE（`static/css/adminlte.min.css`、`static/js/adminlte.min.js`）
- 基础样式 / 组件：Bootstrap 4、Font Awesome（free）、iCheck-Bootstrap、Bootstrap Switch、Bootstrap Slider、SweetAlert2（+ bootstrap-4 主题）
- 脚本库：jQuery
- 数据表格：DataTables（含 Buttons、Responsive、bs4 适配，语言包 `language-zh.json`），并使用 JSZip 支持 Excel 导出
- 图表：Chart.js

### 3.4 架构与数据流
- 请求经 Shiro 过滤器链 → `@Controller`（返回视图名）/ `@RestController`（返回 JSON，供 DataTables 分页接口使用）→ Service → JpaRepository → MySQL
- BOM 递归展开由 `ArtifactController#findRecursiveDetail` 通过 `ArtifactDetailService` 递归实现

## 4. 方案 / 实现思路

1. 依据本方案在仓库根目录新增一份技术栈说明文档（如 `TECH_STACK.md`，中文为主），系统化列出上述后端、前端、构建运行、安全、持久层等组成，并标注关键版本与对应源文件/配置位置，便于读者核对。
2. 文档以表格 + 分类的形式呈现，结论与代码一一对应（如 Spring Boot 版本取自 `pom.xml` 的 parent、数据库名取自 `application.properties`），确保可追溯。
3. 不改动任何源代码与依赖版本，保持最小改动、仅新增文档。

> 注：本步骤仅交付 `plan.md`。上述实现（新增 `TECH_STACK.md`）将在后续提交中按本方案执行。

## 5. 受影响的文件 / 模块

- 本次（方案）：新增 `specs/issue-5/plan.md`（本文件）。
- 后续实现：新增 `TECH_STACK.md`（仓库根目录，纯文档，不影响构建与运行）。
- 盘点依据（只读，不修改）：
  - `pom.xml`（依赖与版本）
  - `src/main/java/cn/keyi/bye/**`（`ByeCadApplication`、`controller`、`service`、`dao`、`model`、`config`）
  - `src/main/resources/application.properties`（数据源、JPA、Thymeleaf 配置）
  - `src/main/resources/templates/**`、`src/main/resources/static/**`（前端框架与组件）
- 不涉及：编译产物、依赖升级、配置变更。

## 6. 关键决策

- 将该 issue 定性为“文档 / 知识类”任务，采用“仅新增文档、不改源码”的最小改动策略，降低风险。
- 选择新增独立 `TECH_STACK.md` 而非改写 `pom.xml` 注释或 README，避免与既有项目结构耦合，且便于后续维护。
- 文档以代码事实为准并标注出处（`pom.xml`、`application.properties`、对应源文件），避免主观描述偏离实际版本。
- 版本号严格取自 `pom.xml` 与 `application.properties`，不臆测未在依赖中显式声明的传递依赖版本（如具体 Hibernate 版本）。

## 7. 风险

- **`bin/` 目录疑似重复项目副本**：`bin/pom.xml` 与根 `pom.xml` 几乎一致（同 `groupId/artifactId/version`），且 `bin/` 内含 `.gitignore`、`pom.xml`、`src`，疑似误提交的项目副本。盘点时需以根 `pom.xml` 为准，并在文档中说明忽略 `bin/`，避免读者误以为是多模块结构。
- **传递依赖版本未显式声明**：Hibernate、Tomcat 等由 Spring Boot parent BOM 托管，文档中宜表述为“由 Spring Boot 2.3.1 BOM 托管”，不写死具体小版本。
- **安全配置为示例强度**：MD5 双哈希、`ddl-auto=update`、明文密码出现在 `application.properties` 均为示例项目特征；技术栈文档只客观陈述，不在此 issue 内做安全加固（超出范围）。
- **前端静态资源体积大**：`static/plugins/**` 含大量第三方库源文件，文档仅归类说明，不做迁移或清理。
- **问答类 issue 验收标准偏主观**：以“技术栈文档覆盖后端/前端/构建/安全/持久层且与代码一致”作为可验收标准。
