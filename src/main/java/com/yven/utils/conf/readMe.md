#读取config.properties配置文件
##使用的jar
<dependency>
    <groupId>commons-configuration</groupId>
    <artifactId>commons-configuration</artifactId>
    <version>1.6</version>
</dependency>

##两个配置类
Config 工具类
ConfigManager 加载配置文件

```
public void loadConfig() {
        loadConfig("src/main/resources/conf/config.xml");
    }
```

[baidu](www.baidu.com)