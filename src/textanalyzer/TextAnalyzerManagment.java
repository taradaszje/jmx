/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textanalyzer;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;

import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

/**
 *
 * @author jarek
 */
public class TextAnalyzerManagment {
    
    private static final int DEFAULT_NO_THREADS = 3;
    private static final int DEFAULT_MEMORY_SIZE = 5;
    private static ConcurrentHashMap<String,String> map = new ConcurrentHashMap() ;
    private static ArrayList<Thread> t = new ArrayList<Thread>();
    private static ArrayList<Boolean> b = new ArrayList<Boolean>(); 
    private static CacheData d = new CacheData();
    

    
    public static void main(String[] args)throws MalformedObjectNameException, InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException, InterruptedException{
        //get the MBean server
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        //register the MBEann
        TextAnalyzer mBean = new TextAnalyzer(DEFAULT_NO_THREADS,DEFAULT_MEMORY_SIZE,t,b,map);
        ObjectName name = new ObjectName("com.journaldev.jmx:type=TextAnalyzer");
        
        mbs.registerMBean(mBean,name);
       
            b.add(true);
            b.add(true);
            b.add(true);
        
            Thread one = new Thread(new Watek(map,0,mBean.getMemorySize() , b , d));
            Thread two = new Thread(new Watek(map,1,mBean.getMemorySize() , b ,d));
            Thread three = new Thread(new Watek(map,2,mBean.getMemorySize() , b,d ));
                
            t.add(one);
            t.add(two);
            t.add(three);
            one.start();
            two.start();
            three.start();
            do{Thread.sleep(500);}while(true);
            
        
    }
}
