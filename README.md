##项目架构简介：
     完全是采用Springboot为基础框架实现的单体工程架构，集成了各种当前主流插件，使得框架更加的健壮和简洁，具体集成的插件如下.
     
##集成插件：（可以根据需要再添加）
（springboot+redis+mybatis+mybatis-plus+lombok+liquibase+shiro+jwt+swagger）

1.mybatis-plus: 
      几乎实现涵盖了所有的单表操作和分页操作
      
2.liquibase：
      数据库迁移工具：初始化表和初始化数据，修改表字段和修改数据的管理工具

3.shiro+jwt:
      权限认证系统：基于方法级别的权限校验，实现token代替session进行鉴权

4.swagger-ui：
      实现前后端动态可视化数据交互及接口对接

5.lombok：
      简化bean对象的生成

6.逆向工程：
      实现表结构到实体，service，mapper，xml等文件的生成。