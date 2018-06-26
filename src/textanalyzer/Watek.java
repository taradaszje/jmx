/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package textanalyzer;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author jarek
 */
public class Watek implements Runnable{
    
    List<Boolean> goOn;
    int threadName;
    String nazwaPliku;
    ConcurrentHashMap<String,String> mapa ;
    int memSize;
    int fileLoadCount[] = new int[20];
    int fileAnalyzeCount[] = new int[20];
    int fileNumber;
    CacheData cData = new CacheData();
    
    void ininitilizeTable(){
        for(int i : fileLoadCount)
            i=0;
        for(int i : fileAnalyzeCount)
            i=0;
    }
    synchronized void threadLive(ConcurrentHashMap<String,String> map){
       drawFileName();
       loadToMemory(map);
       drawAnalizeMethod(map);
       this.cacheMemoryError();
    }
    Watek(ConcurrentHashMap<String, String> map, int i, int memSize, List<Boolean> gO,CacheData d){
        this.threadName = i;
        this.mapa = map;
        this.memSize = memSize;
        this.goOn = gO;
        this.cData = d;
    }
    
    synchronized void drawFileName(){
        Random rand = new Random();
        this.fileNumber = rand.nextInt(20);
        this.setNazwaPliku(this.fileNumber+".txt");
        
    
    }
    synchronized String drawFile(){
        
        try{
        File file = new File("data\\"+this.getNazwaPliku());
        Scanner in = new Scanner(file);
        String text = in.nextLine();
        return text;
        }catch(IOException e){
        return null;}
    }

    

    public String getNazwaPliku() {
        return nazwaPliku;
    }

    public void setNazwaPliku(String nazwaPliku) {
        this.nazwaPliku = nazwaPliku;
    }
   synchronized boolean checkInMemory(ConcurrentHashMap<String,String> map, String nazwaPliku){
        
        if(map.containsKey(nazwaPliku))
            return true;
        else 
            return false;
    }
    synchronized void loadToMemory(ConcurrentHashMap<String,String> map){
        
        if(checkInMemory(map,nazwaPliku)){
           // System.out.println("w pamieci, wielkosc mapy"+map.size());
           this.fileAnalyzeCount[this.fileNumber]+=1;
            
        }
        if(map.size()<this.memSize){
            map.put(this.nazwaPliku,drawFile() );
            this.fileLoadCount[this.fileNumber]+=1;
           // System.out.println("wrzucono do pamieci, wielkosc mapy:"+map.size());
        }
        else{
            int licznik=0;
            for(int i =0 ;i<20;i++){
                if(map.containsKey(i+".txt")){
                    map.remove(i+".txt");
                    licznik++;
               // System.out.println("wymieniono"+map.size());
                if(licznik==2)
                break;
                }
                
                }
            map.put(this.nazwaPliku, drawFile());
            this.fileLoadCount[this.fileNumber]+=1;
            
        }
    }
    int cacheMemoryError(){
        int fileA = 0;
        int fileB = 0;
        for(int i : this.fileAnalyzeCount)
            fileA+=i;
        for(int i : this.fileLoadCount)
            fileB+=i;
        if(fileA != 0 )
        fileA=fileB/fileA*100;
        
        this.cData.setWynik(fileA);
        return fileA;
    }    

    public int getThreadName() {
        return threadName;
    }

    public void setThreadName(int name) {
        this.threadName = name;
    }

    private void drawAnalizeMethod(ConcurrentHashMap<String, String>map) {
       Random rand = new Random();
       int wynik = rand.nextInt(3);
       if(wynik==0)
       {
           countA(map.get(this.getNazwaPliku()),"Wystąpienia litery A");
       }
       else if(wynik==1)
       {
           consonantsAndVowel(map.get(this.getNazwaPliku()),"Spółgloski i samogłoski");
       }
       else
       {
           countVowel(map.get(this.getNazwaPliku()),"Ile samogłosek");
       }
    }
    void countA(String a,String nazwaAlgorytmu){
        int counter=0;
        char znak='a';
        for(int i =0;i<a.length();i++){
            if(znak == a.charAt(i))
                counter++;
        }
        System.out.println(this.getThreadName()+",plik: "+this.getNazwaPliku()+" ,rodzaj: "+nazwaAlgorytmu+", wynik: "+counter);
    }
    void consonantsAndVowel(String a, String nazwaAlgorytmu){
        int counterC =0;
        int counterV =0;
        String samogl = "aeiouy";
        String spolgl = "bcdfghjklmnprstwxz";
        for(int i=0;i<a.length();i++){
            for(int j=0;j<samogl.length();j++){
                if(a.charAt(i)==samogl.charAt(j))
                    counterV++;
            }
        }
        for(int i=0;i<a.length();i++){
            for(int j=0;j<spolgl.length();j++){
                if(a.charAt(i)==spolgl.charAt(j))
                    counterC++;
            }
        }
        if(counterC > counterV)
            System.out.println(this.getThreadName()+",plik: "+this.getNazwaPliku()+" ,rodzaj: "+nazwaAlgorytmu+", wynik: wiecej spolglosek");
        else if(counterC < counterV)
            System.out.println(this.getThreadName()+",plik: "+this.getNazwaPliku()+" ,rodzaj: "+nazwaAlgorytmu+", wynik: wiecej samoglosek");
        else
            System.out.println(this.getThreadName()+",plik: "+this.getNazwaPliku()+" ,rodzaj: "+nazwaAlgorytmu+", wynik: równa ilosc");
    }
    
    void countVowel(String a, String nazwaAlgorytmu){
        int[] counterV ={0,0,0,0,0,0};
        String samogl = "aeiouy";
        for(int i=0;i<a.length();i++){
            for(int j=0;j<samogl.length();j++){
                if(a.charAt(i)==samogl.charAt(j))
                    counterV[j]++;
            }
        }
        System.out.println(this.getThreadName()+",plik: "+this.getNazwaPliku()+" ,rodzaj: "+nazwaAlgorytmu+",a="+counterV[0]+",e="+counterV[1]
                            +",i="+counterV[2]
                            +",o="+counterV[3]
                            +",u="+counterV[4]
                            +",y="+counterV[5]);
    }

    @Override
    public void run() {
        if(this.threadName<0)
            this.threadName=0;
        while(goOn.get(threadName)){
            synchronized(mapa){
        this.threadLive(mapa);}
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Watek.class.getName()).log(Level.SEVERE, null, ex);
            }
    
        }
    
    }
    
}
