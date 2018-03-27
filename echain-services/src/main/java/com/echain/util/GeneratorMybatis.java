package com.echain.util;
 
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;  
 

public class GeneratorMybatis {
 
    public static void main(String[] args) throws IOException {  
        List<String> warnings = new ArrayList<String>();  
        ConfigurationParser cp = new ConfigurationParser(warnings);  
   
        boolean overwrite = true;   
        //staticTableConfig.xml,dynamicTableConfig.xml  
                    
   	    InputStream inputstream  = null;
		try {  
			inputstream =  GeneratorMybatis.class.getClassLoader().getResourceAsStream("./generateConfig.xml");  
        	   
            Configuration config = cp.parseConfiguration(inputstream);  
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);  
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config,  
                    callback, warnings);  
            myBatisGenerator.generate(null);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }finally{
        	if(inputstream!=null){
        		inputstream.close();
        	}
        }  
    }  
	  
}
