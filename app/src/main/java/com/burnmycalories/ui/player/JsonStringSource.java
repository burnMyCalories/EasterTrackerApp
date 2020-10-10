package com.burnmycalories.ui.player;

import master.flame.danmaku.danmaku.parser.IDataSource;

public class JsonStringSource implements IDataSource<String> {

    private String jsonString;

    public JsonStringSource(String json){
        jsonString=json;
    }

    @Override
    public String data() {
        return jsonString;
    }

    @Override
    public void release() {
        jsonString=null;
    }
}
