package com.nebulae.license;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nebulae.utils.AESUtil;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public abstract class AbstractServerInfos {
	
	private static final Logger log = LoggerFactory.getLogger(AbstractServerInfos.class);
    
    public LicenseCheckModel getServerInfos(){
        LicenseCheckModel result = new LicenseCheckModel();
        try {
            result.setIpAddress(this.getIpAddress());
            result.setMacAddress(this.getMacAddress());
            result.setCpuSerial(this.getCPUSerial());
            result.setMainBoardSerial(this.getMainBoardSerial());
        }catch (Exception e){
        	log.error(AESUtil.AESDecode("kPwE+TjqtY38d6JiqHhOMoVQKN5BTXgQXczX07/BAuK4+BQzcwg0M2UOkW1B7Ep3"),e);
        }
        return result;
    }
    
    protected abstract List<String> getIpAddress() throws Exception;
    protected abstract List<String> getMacAddress() throws Exception;
    protected abstract String getCPUSerial() throws Exception;
    protected abstract String getMainBoardSerial() throws Exception;
    protected List<InetAddress> getLocalAllInetAddress() throws Exception {
        List<InetAddress> result = new ArrayList<InetAddress>(4);
        for (Enumeration networkInterfaces = NetworkInterface.getNetworkInterfaces(); networkInterfaces.hasMoreElements(); ) {
            NetworkInterface iface = (NetworkInterface) networkInterfaces.nextElement();
            for (Enumeration inetAddresses = iface.getInetAddresses(); inetAddresses.hasMoreElements(); ) {
                InetAddress inetAddr = (InetAddress) inetAddresses.nextElement();
                if(!inetAddr.isLoopbackAddress() 
                        && !inetAddr.isLinkLocalAddress() && !inetAddr.isMulticastAddress()){
                    result.add(inetAddr);
                }
            }
        }
        return result;
    }
    protected String getMacByInetAddress(InetAddress inetAddr){
        try {
            byte[] mac = NetworkInterface.getByInetAddress(inetAddr).getHardwareAddress();
            StringBuffer stringBuffer = new StringBuffer();

            for(int i=0;i<mac.length;i++){
                if(i != 0) {
                    stringBuffer.append("-");
                }
                String temp = Integer.toHexString(mac[i] & 0xff);
                if(temp.length() == 1){
                    stringBuffer.append("0" + temp);
                }else{
                    stringBuffer.append(temp);
                }
            }
            return stringBuffer.toString().toUpperCase();
        } catch (SocketException e) {
           log.error(e.getMessage());
        }

        return null;
    }

}
