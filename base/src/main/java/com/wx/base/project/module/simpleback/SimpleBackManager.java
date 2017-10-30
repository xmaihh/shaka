package com.wx.base.project.module.simpleback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex on 16-11-21.
 */

public class SimpleBackManager {

    List<SimpleBackPage> pages = new ArrayList<>();

    protected static SimpleBackManager instance;

    public static SimpleBackManager getInstance(){
        if(instance == null){
            instance = new SimpleBackManager();
        }

        return instance;
    }


    public SimpleBackManager(){

    }

    public void addPage(SimpleBackPage page){
        pages.add(page);
    }

    public SimpleBackPage getPageByValue(int val) {
    	for (SimpleBackPage p : pages) {
    		if (p.getValue() == val)
    			return p;
    	}
    	return null;
    }


}
