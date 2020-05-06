package com.nebulae.license;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nebulae.utils.AESUtil;

import de.schlichtherle.license.CipherParam;
import de.schlichtherle.license.DefaultCipherParam;
import de.schlichtherle.license.DefaultLicenseParam;
import de.schlichtherle.license.KeyStoreParam;
import de.schlichtherle.license.LicenseContent;
import de.schlichtherle.license.LicenseManager;
import de.schlichtherle.license.LicenseParam;

import java.io.File;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.prefs.Preferences;


public class LicenseVerify {
	
	private static final Logger log = LoggerFactory.getLogger(LicenseVerify.class);

    public synchronized LicenseContent install(LicenseVerifyParam param){
        LicenseContent result = null;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try{
            LicenseManager licenseManager = LicenseManagerHolder.getInstance(initLicenseParam(param));
            licenseManager.uninstall();
            result = licenseManager.install(new File(param.getLicensePath()));
            log.info(MessageFormat.format(AESUtil.AESDecode("cqSoS9IKHxQ+8SM2CvO+YIJYwt6ro3JbCXbwpO0oV80APXM9gIZ9tjCZw8Rfbg6z")+"{0} - {1}",format.format(result.getNotBefore()),format.format(result.getNotAfter())));
        }catch (Exception e){
        	log.error(AESUtil.AESDecode("SzjtJAu03XQwk5eUNhr3opiZL4Ax+q26yqK0SIu6snc=")+"！",e);
        }

        return result;
    }
    
    public boolean verify(){
        LicenseManager licenseManager = LicenseManagerHolder.getInstance(null);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            LicenseContent licenseContent = licenseManager.verify();
//            log.info(MessageFormat.format(AESUtil.AESDecode("XZV0LytU0ie/KW2mYuvh5b75kzsd7ZzedNU6K4hwXGwaMUq8l159oI/aIL2VIO/J")+"{0} - {1}",format.format(licenseContent.getNotBefore()),format.format(licenseContent.getNotAfter())));
            return true;
        }catch (Exception e){
        	log.error(AESUtil.AESDecode("SzjtJAu03XQwk5eUNhr3opiZL4Ax+q26yqK0SIu6snc=")+"！",e);
            return false;
        }
    }
    
    private LicenseParam initLicenseParam(LicenseVerifyParam param){
        Preferences preferences = Preferences.userNodeForPackage(LicenseVerify.class);

        CipherParam cipherParam = new DefaultCipherParam(param.getStorePass());

        KeyStoreParam publicStoreParam = new CustomKeyStoreParam(LicenseVerify.class
                ,param.getPublicKeysStorePath()
                ,param.getPublicAlias()
                ,param.getStorePass()
                ,null);

        return new DefaultLicenseParam(param.getSubject()
                ,preferences
                ,publicStoreParam
                ,cipherParam);
    }

}
