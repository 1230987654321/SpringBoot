package com.example.admin;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;

import java.util.Collections;

public class CodeGenerator {
    public static void main(String[] args) {
        FastAutoGenerator.create(
            /* 数据库配置 */
            new DataSourceConfig.Builder("jdbc:mysql://localhost:3306/test?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai","root","hyy835435")
        )
        /* 全局配置 */
        .globalConfig(builder -> {
            //禁止打开输出目录，默认打开
            builder.disableOpenDir()
            // 指定输出目录
            .outputDir(System.getProperty("user.dir") + "/admin/src/main/java")
            // 设置作者
            .author("贲玉柱")
            // 时间策略
            .dateType(DateType.TIME_PACK)
            //注释日期
            .commentDate("yyyy-MM-dd hh:mm:ss");
        })
        /* 包配置 */
        .packageConfig(builder -> {
            // 设置父包名
            builder.parent("com.example")
            // 设置父包模块名
            .moduleName("admin")
            //Entity 包名
            .entity("entity")
            //Service 包名
            .service("service")
            //Service Impl 包名
            .serviceImpl("service.impl")
            //Mapper 包名
            .mapper("mapper")
            //Mapper XML 包名
            .xml("mapper.xml")
            //Controller 包名
            .controller("controller")
            // 设置mapperXml生成路径
            .pathInfo(Collections.singletonMap(OutputFile.xml, System.getProperty("user.dir") + "/admin/src/main/resources/mapper" ) );
        })
        /* 策略配置 */
        .strategyConfig(builder -> {
            //开启跳过视图
            builder.enableSkipView()
            // 设置需要生成的表名(不写,默认为所有表)
            .addInclude()
            // 设置过滤表前缀
            .addTablePrefix("db_")
            /* Entity 策略配置 */
            .entityBuilder()
            //覆盖原有文件(原有fileOverride已废弃)
            .enableFileOverride()
            //禁用生成 serialVersionUID
            .disableSerialVersionUID()
            //开启lombok模型
            .enableLombok()
            //开启生成实体时生成字段注解
            .enableTableFieldAnnotation()
            //activeRecord模式，使用上来说就是可以直接在entity对象上执行insert、update等操作
            .enableActiveRecord()
            //逻辑删除字段名(数据库)
            .logicDeleteColumnName("deleted")
            //逻辑删除属性名(实体)
            .logicDeletePropertyName("deleted")
            //数据库表映射到实体的命名策略：下划线转驼峰命
            .naming(NamingStrategy.underline_to_camel)
            //数据库表字段映射到实体的命名策略：下划线转驼峰命
            .columnNaming(NamingStrategy.underline_to_camel)
            //添加表字段填充
            .addTableFills(new Column("created_at", FieldFill.INSERT))
            .addTableFills(new Column("updated_at", FieldFill.INSERT_UPDATE))
            //全局主键类型(自增)
            .idType(IdType.AUTO)
            /* Controller 策略配置 */
            .controllerBuilder()
            //覆盖原有文件(原有fileOverride已废弃)
            .enableFileOverride()
            //开启生成@RestController 控制器
            .enableRestStyle()
            //格式化文件名称%s进行匹配表名，如 UserController
            .formatFileName("%sController")
            /* Service 策略配置 */
            .serviceBuilder()
            //覆盖原有文件(原有fileOverride已废弃)
            .enableFileOverride()
            //设置 service 实现类父类
            .superServiceClass(IService.class)
            //设置 service 实现类父类
            .superServiceImplClass(ServiceImpl.class)
            //格式化 service 接口文件名称，%s进行匹配表名，如 UserService
            .formatServiceFileName("%sService")
            //格式化 service 实现类文件名称，%s进行匹配表名，如 UserServiceImpl
            .formatServiceImplFileName("%sServiceImpl")
            /* Mapper 策略配置 */
            .mapperBuilder()
            //覆盖原有文件(原有fileOverride已废弃)
            .enableFileOverride()
            //开启 @Mapper 注解(原有 enableMapperAnnotation 已废弃)
            .mapperAnnotation(org.apache.ibatis.annotations.Mapper.class)
            //设置父类
            .superClass(BaseMapper.class)
            //转换 xml 文件名称(最好设置,防止生成的XML文件未转换成驼峰)
            .convertXmlFileName(entityName -> entityName)
            //格式化 mapper 文件名称
            .formatMapperFileName("%sMapper")
            //格式化 xml 实现类文件名称
            .formatXmlFileName("%sXml");
        })
        /* 使用默认的Velocity引擎模板 */
        .templateEngine(new VelocityTemplateEngine())
        .execute();
    }
}
