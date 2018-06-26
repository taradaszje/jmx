/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textanalyzer;

import java.lang.management.ManagementFactory;
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
public class SystemConfigManagment {
    private static final int DEFAULT_NO_THREADS=3;
    private static final int DEFAULT_MEMORY_SIZE=5;
    
    public static void main(String[] args)throws MalformedObjectNameException, InstanceAlreadyExistsException, MBeanRegistrationException, NotCompliantMBeanException, InterruptedException{
        //get the MBean server
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        //register the MBEann
        SystemConfig mBean = new SystemConfig(DEFAULT_NO_THREADS,DEFAULT_MEMORY_SIZE);
        ObjectName name = new ObjectName("com.journaldev.jmx:type=SystemConfig");
        mbs.registerMBean(mBean,name);
        int temp = 5;
        do{
           
            Thread.sleep(3000);
            System.out.println("Thread Count="+mBean.getThreadCount()+":::Memory Size="+mBean.getMemorySize());
            
        }while(mBean.getThreadCount() !=0);
        
    }
}
