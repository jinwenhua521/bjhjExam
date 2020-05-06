package com.nebulae.license;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nebulae.utils.AESUtil;

import de.schlichtherle.license.LicenseContent;
import de.schlichtherle.license.LicenseContentException;
import de.schlichtherle.license.LicenseManager;
import de.schlichtherle.license.LicenseNotary;
import de.schlichtherle.license.LicenseParam;
import de.schlichtherle.license.NoLicenseInstalledException;
import de.schlichtherle.xml.GenericCertificate;
import org.apache.commons.lang3.StringUtils;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;


public class CustomLicenseManager extends LicenseManager{
    private static final Logger log = LoggerFactory.getLogger(CustomLicenseManager.class);
    
    private static final String XML_CHARSET = "UTF-8";
    private static final int DEFAULT_BUFSIZE = 8 * 1024;

    public CustomLicenseManager() {

    }
    
    public CustomLicenseManager(LicenseParam param) {
        super(param);
    }
    
    @Override
    protected synchronized byte[] create(
            LicenseContent content,
            LicenseNotary notary)
            throws Exception {
        initialize(content);
        this.validateCreate(content);
        final GenericCertificate certificate = notary.sign(content);
        return getPrivacyGuard().cert2key(certificate);
    }
    
    @Override
    protected synchronized LicenseContent install(
            final byte[] key,
            final LicenseNotary notary)
            throws Exception {
        final GenericCertificate certificate = getPrivacyGuard().key2cert(key);

        notary.verify(certificate);
        final LicenseContent content = (LicenseContent)this.load(certificate.getEncoded());
        this.validate(content);
        setLicenseKey(key);
        setCertificate(certificate);

        return content;
    }
    
    @Override
    protected synchronized LicenseContent verify(final LicenseNotary notary)
            throws Exception {
        GenericCertificate certificate = getCertificate();
        final byte[] key = getLicenseKey();
        if (null == key){
            throw new NoLicenseInstalledException(getLicenseParam().getSubject());
        }
        certificate = getPrivacyGuard().key2cert(key);
        notary.verify(certificate);
        final LicenseContent content = (LicenseContent)this.load(certificate.getEncoded());
        this.validate(content);
        setCertificate(certificate);

        return content;
    }
    
    protected synchronized void validateCreate(final LicenseContent content)
            throws LicenseContentException {
        final LicenseParam param = getLicenseParam();

        final Date now = new Date();
        final Date notBefore = content.getNotBefore();
        final Date notAfter = content.getNotAfter();
        if (null != notAfter && now.after(notAfter)){
            throw new LicenseContentException(AESUtil.AESDecode("rMD677f+hIfu08ounYNKrZMVFS2A+c5JBms4iQXojaXXIgOVxbgiZlLFbFInyN82"));
        }
        if (null != notBefore && null != notAfter && notAfter.before(notBefore)){
            throw new LicenseContentException(AESUtil.AESDecode("khxGcQuUKfXZpa4HwEXlKzlQXbIHA5G/oFqTmFjUIIEFfCRQz2eGUjenaZR9DhnYbCKxauwwf6nGfsQmXNN94Q=="));
        }
        final String consumerType = content.getConsumerType();
        if (null == consumerType){
            throw new LicenseContentException(AESUtil.AESDecode("dkXKt5q5qIejymbQjwlpsJ8kXwk+A25KNHAwdVaByTU="));
        }
    }
    
    @Override
    protected synchronized void validate(final LicenseContent content)
            throws LicenseContentException {
        super.validate(content);
        LicenseCheckModel expectedCheckModel = (LicenseCheckModel) content.getExtra();
        LicenseCheckModel serverCheckModel = getServerInfos();

        if(expectedCheckModel != null && serverCheckModel != null){
            if(!checkIpAddress(expectedCheckModel.getIpAddress(),serverCheckModel.getIpAddress())){
                throw new LicenseContentException(AESUtil.AESDecode("0YFgOGN7HM4kjLPM+vUERSkJDMTKdTp7b1dQaVZNn0IkIzd6wH9kAom1lwitcFTS"));
            }
            if(!checkIpAddress(expectedCheckModel.getMacAddress(),serverCheckModel.getMacAddress())){
                throw new LicenseContentException(AESUtil.AESDecode("0YFgOGN7HM4kjLPM+vUERTeoTsw7x1Zc/9ladzUKYGrwNdMz7dYMzy6ZkLiDYuvkbCKxauwwf6nGfsQmXNN94Q=="));
            }
            if(!checkSerial(expectedCheckModel.getMainBoardSerial(),serverCheckModel.getMainBoardSerial())){
                throw new LicenseContentException(AESUtil.AESDecode("0YFgOGN7HM4kjLPM+vUERbxzsQ2ZQRbtyKgD+0T0IPt9MLDCDfRKEChw+gpTqi6Lrk5puqRO7aYGhyYvhB17Ew=="));
            }
            if(!checkSerial(expectedCheckModel.getCpuSerial(),serverCheckModel.getCpuSerial())){
                throw new LicenseContentException(AESUtil.AESDecode("0YFgOGN7HM4kjLPM+vUERQh3Qw1N+uP5E090VuKzZJXGzdvEUEtgT9W05UWYUISs6wREZUffl7G37RmkYdmK3w=="));
            }
        }else{
            throw new LicenseContentException(AESUtil.AESDecode("vLWG4OxkZ9QJvQpDBYY/D9k4HXaHrYrX22FMkv7KG+0+qRJLapfsTXOeLpjqHdJY"));
        }
    }
    private Object load(String encoded){
        BufferedInputStream inputStream = null;
        XMLDecoder decoder = null;
        try {
            inputStream = new BufferedInputStream(new ByteArrayInputStream(encoded.getBytes(XML_CHARSET)));

            decoder = new XMLDecoder(new BufferedInputStream(inputStream, DEFAULT_BUFSIZE),null,null);

            return decoder.readObject();
        } catch (UnsupportedEncodingException e) {
           log.error(e.getMessage());
        } finally {
            try {
                if(decoder != null){
                    decoder.close();
                }
                if(inputStream != null){
                    inputStream.close();
                }
            } catch (Exception e) {
                log.error("XMLDecoder解析XML失败",e);
            }
        }

        return null;
    }
    
    private LicenseCheckModel getServerInfos(){
        String osName = System.getProperty("os.name").toLowerCase();
        AbstractServerInfos abstractServerInfos = null;

        if (osName.startsWith("windows")) {
            abstractServerInfos = new WindowsServerInfos();
        } else if (osName.startsWith("linux")) {
            abstractServerInfos = new LinuxServerInfos();
        }else{
            abstractServerInfos = new LinuxServerInfos();
        }

        return abstractServerInfos.getServerInfos();
    }
    
   
    private boolean checkIpAddress(List<String> expectedList,List<String> serverList){
        if(expectedList != null && expectedList.size() > 0){
            if(serverList != null && serverList.size() > 0){
                for(String expected : expectedList){
                    if(serverList.contains(expected.trim())){
                        return true;
                    }
                }
            }

            return false;
        }else {
            return true;
        }
    }
    
    
    private boolean checkSerial(String expectedSerial,String serverSerial){
        if(StringUtils.isNotBlank(expectedSerial)){
            if(StringUtils.isNotBlank(serverSerial)){
                if(expectedSerial.equals(serverSerial)){
                    return true;
                }
            }

            return false;
        }else{
            return true;
        }
    }


}
