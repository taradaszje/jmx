/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textanalyzer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jarek
 */
public class TextAnalyzer implements TextAnalyzerMBean {

    /**
     * @param args the command line arguments
     */
    
    List<Thread> list;
    List<Boolean> listB;
    private int memorySize;
    private int threadCount;
    ConcurrentHashMap<String,String> map;
    CacheData d = new CacheData();
    
    
    public TextAnalyzer(int th, int memS, List<Thread> watki , List<Boolean> lB, ConcurrentHashMap<String,String> map){
        this.memorySize = memS;
        this.threadCount = th;
        this.list = watki;
        this.listB = lB;
        this.map = map;
        
    }
    

    @Override
    public void setThreadCount(int liczba) {
        //System.out.println("HERE");
        
       
        if(liczba<1)
        {   
            this.threadCount = 1;
            System.out.println(list.size());
            for( int i = 0; i < list.size(); ++i )
                try {
                    listB.set(i, Boolean.FALSE);
                    list.get(i).join();
                    
                } catch (InterruptedException ex) {
                    Logger.getLogger(TextAnalyzer.class.getName()).log(Level.SEVERE, null, ex);
                }
            
            list.clear();
            listB.clear();
        
            //System.out.println("HERE " + list.size() );
            
            listB.add( true );
            
            Watek watek = new Watek(map,1,this.memorySize, listB ,d );
            Thread t = new Thread(watek);
            list.add(t);

            t.start();
            
           // System.out.println("HERE " + list.size() );
            
                        
        }
        else {
            this.threadCount = liczba;
            for( int i = 0; i < list.size(); i++ )
                try {
                    listB.set(i, Boolean.FALSE);
                    list.get(i).join();
                    
                } catch (InterruptedException ex) {
                    Logger.getLogger(TextAnalyzer.class.getName()).log(Level.SEVERE, null, ex);
                }
            
            list.clear();
            listB.clear();
            for(int i=0;i<liczba;++i){
                Thread t = new Thread(new Watek(map,i,this.memorySize,listB, d));
                this.list.add(t);
                this.listB.add(true);
                t.start();
            }
            
        }
        
    }
    

    @Override
    public int getThreadCount() {
        return this.threadCount;
    }

    @Override
    public void setMemorySize(int liczba) {
        if(liczba<1)this.memorySize=1;
        else this.memorySize=liczba; 
    }

    @Override
    public int getMemorySize() {
        return this.memorySize;
    }

    @Override
    public String doInformation() {
        return "Number of threads="+this.getThreadCount()+
                ", Memory size="+this.getMemorySize()+", CacheMemoryError: "+d.getWynik()+"\n";
    }

    
    
}
