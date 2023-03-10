package cn.jji8.floatingmarket.money;

import cn.jji8.floatingmarket.logger.Logger;

import cn.jji8.java.script.Invocable;
import cn.jji8.java.script.ScriptEngine;
import cn.jji8.java.script.ScriptEngineManager;
import cn.jji8.java.script.ScriptException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * 一个函数类，用于加载调用js函数
 * */
public class MoneyFunction {
    Invocable Invocable;
    File file;
    /**
     * 通过一个js文件创建一个js
     * */
    public MoneyFunction(File file){
        this.file = file;
        FileReader FileReader = null;
        try {
            FileReader = new FileReader(file);
        } catch (FileNotFoundException e) {
            Logger.putSevere("没有找到文件:"+file);
            e.printStackTrace();
        }
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine scriptEngine = manager.getEngineByName("js");
        if(scriptEngine==null){
            for(int i=0;i<10;i++){
                Logger.putSevere("本插件可能无法在你使用的java版本上运行，建议使用java8");
            }
        }
        try {
            scriptEngine.eval(FileReader);
        } catch (ScriptException e) {
            Logger.putSevere(file+":你的脚本初始化出错");
            e.printStackTrace();
        }
        try {
            FileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Invocable = (Invocable)scriptEngine;
    }
    /**
     * 调用js中的函数
     * */
    public Object function(String name, MoneyVariable value){
        Object sss = null;
        try {
            sss = Invocable.invokeFunction(name,value);
        } catch (ScriptException e) {
            Logger.putWarning(file+":你的脚本运行出错");
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            Logger.putWarning(file+":你的脚本中没有"+name+"方法");
            e.printStackTrace();
        }
        return sss;
    }
    /**
     * 调用js中的指定方法，返回一个double
     * */
    public double Doublefunction(String name, MoneyVariable value){
        Object sss = function(name,value);
        if(sss==null){
            return -1;
        }
        double qqq = -1;
        try {
            qqq = Double.parseDouble(sss.toString());
        }catch (NumberFormatException eee){
            Logger.putWarning(file+":你的脚本返回的”"+sss.toString()+"“不是一个数");
            eee.printStackTrace();
        }
        return qqq;
    }
}
