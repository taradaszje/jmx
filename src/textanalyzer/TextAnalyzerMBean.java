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
public interface TextAnalyzerMBean {
    public void setThreadCount(int liczba);
    public int getThreadCount();
    public void setMemorySize(int liczba);
    public int getMemorySize();
    public String doInformation();
    
}
