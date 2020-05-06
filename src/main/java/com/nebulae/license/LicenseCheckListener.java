package com.nebulae.license;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.nebulae.utils.AESUtil;


public class LicenseCheckListener implements ApplicationListener<ContextRefreshedEvent> {

private static final Logger log = LoggerFactory.getLogger(LicenseCheckListener.class);

@Value("${license.subject}")
private String subject;

@Value("${license.publicAlias}")
private String publicAlias;

@Value("${license.storePass}")
private String storePass;

@Value("${license.licensePath}")
private String licensePath;

@Value("${license.publicKeysStorePath}")
private String publicKeysStorePath;

@Override
public void onApplicationEvent(ContextRefreshedEvent event) {
    //root application context 没有parent
    ApplicationContext context = event.getApplicationContext().getParent();
    if(context == null){
        if(StringUtils.isNotBlank(licensePath)){
        	log.info(AESUtil.AESDecode("X5PFGXPOEAHj3ecGnN3Z4EX+TG2xGVcPULpLUNZYxl2lEr/5OLFQDogNA7/wWU1JFwFNzYIRR0XfvHkXMGI+6g=="));
            LicenseVerifyParam param = new LicenseVerifyParam();
            param.setSubject(subject);
            param.setPublicAlias(publicAlias);
            param.setStorePass(storePass);
            param.setLicensePath(licensePath);
            param.setPublicKeysStorePath(publicKeysStorePath);
            LicenseVerify licenseVerify = new LicenseVerify();
            licenseVerify.install(param);
            log.info(AESUtil.AESDecode("X5PFGXPOEAHj3ecGnN3Z4LB3LpESPkCjkKja+fOsMOl4EzB3dBA1jEKnOEtZzsvSFwFNzYIRR0XfvHkXMGI+6g=="));
        }
    }
}
}
