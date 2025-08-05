package com.example.order.spi;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.common.Constants;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.shaded.com.google.common.base.Preconditions;
import org.apache.shardingsphere.infra.url.spi.ShardingSphereURLLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class CustomNacosURLLoader implements ShardingSphereURLLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomNacosURLLoader.class);

    /**
     * 定义jdbc:shardingsphere:后的类型为nacos:
     */
    private static final String NACOS_TYPE = "nacos:";

    /**
     * 接收nacos:后的参数sharding.yaml?serverAddr=${nacos.service-address}&namespace=${nacos.namespace}&group=${nacos.group}&username=${nacos.username}&password=${nacos.password}
     * @param configurationSubject configuration dataId
     * @param queryProps url参数，已经解析成为Properties
     * @return config 配置
     */
    @Override
    public String load(String configurationSubject, Properties queryProps) {
        String config = "";
        try {
            ConfigService configService = NacosFactory.createConfigService(queryProps);
            //获取nacos配置
            config = configService.getConfig(configurationSubject, queryProps.getProperty(Constants.GROUP, Constants.DEFAULT_GROUP), 500);
            Preconditions.checkArgument(config != null, "Nacos config [" + configurationSubject + "] is Empty.");
        } catch (NacosException ex) {
            LOGGER.error("shardingsphere load Nacos config error: ", ex);
        }
        return config;
    }

    @Override
    public Object getType() {
        return NACOS_TYPE;
    }
}
