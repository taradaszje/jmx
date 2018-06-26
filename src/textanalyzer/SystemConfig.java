/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textanalyzer;

/**
 *
 * @author jarek
 */
public class SystemConfig implements SystemConfigMBean{

    private int threadCount;
    private int memorySize;
    public SystemConfig(int numThe, int memS){
        this.memorySize = memS;
        this.threadCount = numThe;
    }
    @Override
    public void setThreadCount(int liczba) {
        this.threadCount = liczba;
    }

    @Override
    public int getThreadCount() {
        return this.threadCount;
    }

    @Override
    public void setMemorySize(int liczba) {
        this.memorySize = liczba;
    }

    @Override
    public int getMemorySize() {
        return this.memorySize;
    }

    @Override
    public String doInformation() {
        return "Number of threads="+this.getThreadCount()+
                ", Memory size="+this.getMemorySize();
    }
    
}
