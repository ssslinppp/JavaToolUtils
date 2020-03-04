package com.ssslinppp.runner;

import com.ssslinppp.model.MyConfig;
import com.ssslinppp.xmlUtils.XmlToConfigV;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class XMLTestRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        MyConfig myConfig = XmlToConfigV.getLogSystemConfigVO("c:\\myConfig.xml");
        System.out.println("########################");
        System.out.println(myConfig);
        System.out.println("########################");
    }
}
