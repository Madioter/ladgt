package com.madiot.poke.server.base;

import com.madiot.common.reflect.MetaClass;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by julian on 2017/8/29.
 */
public class ProcessExecuteContext {

    private Map<String, Class<IProcessExecute>> services = new HashMap<>();

    public IProcessExecute getService(String commandType) {
        return MetaClass.newInstance(services.get(commandType)).getInstance();
    }

    public void setServices(Map<String, Class<IProcessExecute>> services) {
        this.services = services;
    }
}
