package com.hinmu.lims.util.generator;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CodeGenerator {

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        final String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("zhaohao");//设置作者
        gc.setOpen(false);//是否打开输出目录
        gc.setEnableCache(false);//是否在xml中添加二级缓存配置
        gc.setBaseResultMap(true);//开启 BaseResultMap
        gc.setBaseColumnList(true);//开启 baseColumnList
        gc.setIdType(IdType.AUTO);//指定生成的主键的ID类型
        gc.setFileOverride(true);//是否覆盖已有文件
        gc.setEntityName("%sEntity");//实体命名方式
        // gc.setSwagger2(true); 实体属性 Swagger2 注解
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/db_ai_case?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=UTC");//characterEncoding=utf8&useSSL=true
        // dsc.setSchemaName("public");//例如 PostgreSQL 可指定为 public
       // dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        //pc.setModuleName(scanner("模块名"));//父包模块名
        pc.setParent("com.hinmu.lims");//父包名。如果为空，将下面子包名必须写全部， 否则就只需写子包名
        pc.setEntity("model.entity");
        pc.setService("service");
        pc.setServiceImpl("service.impl");
        pc.setMapper("dao");
       // pc.setXml("mapper.xml");
        pc.setController("controller.manager");
        mpg.setPackageInfo(pc);


        // 策略配置(数据库表配置)
        StrategyConfig strategy = new StrategyConfig();
        strategy.setTablePrefix(new String[]{"tb_"});// 此处可以修改为您的表前缀
        strategy.setNaming(NamingStrategy.underline_to_camel);
       // strategy.setColumnNaming(NamingStrategy.underline_to_camel);//数据库表字段映射到实体的命名策略, 未指定按照 naming 执行
        strategy.setSuperEntityClass("com.hinmu.lims.model.BaseEntity");
        strategy.setSuperEntityColumns("id","when_created","when_modified","who_created" ,"who_modified");  // 写于父类中的公共字段
        strategy.setSuperControllerClass("com.hinmu.lims.controller.BaseController"); // 公共父类
       // strategy.setSuperServiceClass("com.hinmu.courtdsis.service");
       // strategy.setSuperServiceImplClass("com.hinmu.courtdsis.service.impl");
        //strategy.setSuperMapperClass("com.hinmu.courtdsis.mapper");
        //strategy.setInclude("com.hinmu.courtdsis.mapper");
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        // Boolean类型字段是否移除is前缀处理
        strategy.setEntityBooleanColumnRemoveIsPrefix(false);
        // 自定义需要填充的字段
        //如 每张表都有一个创建时间、修改时间
        //而且这基本上就是通用的了，新增时，创建时间和修改时间同时修改
        //修改时，修改时间会修改，
        //虽然像Mysql数据库有自动更新几只，但像ORACLE的数据库就没有了，
        //使用公共字段填充功能，就可以实现，自动按场景更新了。
        //如下是配置
        List<TableFill> tableFillList = new ArrayList<>();
        TableFill createField = new TableFill("when_created", FieldFill.INSERT);
        TableFill modifiedField = new TableFill("when_modified", FieldFill.UPDATE);
        tableFillList.add(createField);
        tableFillList.add(modifiedField);
        strategy.setTableFillList(tableFillList);

       strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
        strategy.setControllerMappingHyphenStyle(true);//驼峰转连字符
        strategy.setEntityTableFieldAnnotationEnable(true);//是否生成实体时，生成字段注解
        //strategy.setTablePrefix(pc.getModuleName() + "_");
        mpg.setStrategy(strategy);




        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        // String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/resources/mybatis/mapper/" //+ pc.getModuleName()+ "/"
                         + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });

//        cfg.setFileCreate(new IFileCreate() {
//            @Override
//            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
//                // 判断自定义文件夹是否需要创建
//                checkDir("调用默认方法创建的目录");
//                return false;
//            }
//        });


        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // templateConfig.setEntity("templates/entity2.java");
        // templateConfig.setService();
        // templateConfig.setController();

        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);


        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }
}
