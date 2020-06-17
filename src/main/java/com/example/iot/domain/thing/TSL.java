package com.example.iot.domain.thing;

import com.alibaba.fastjson.JSONObject;
import com.example.iot.util.FileUtil;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.Date;

/**
 * @author: lxc
 * @email 171250576@smail.nju.edu.cn
 * @date: 2020/6/14
 * @description: 每一个模板有一个TSL
 */
@Document(collection = "tsl")
@Data
public class TSL {

    @Id
    private String id;
    private int templateId;
    private Date createTime;
    private Date updateTime;
    //一整个物模型
    private JSONObject content;

    private static String TSLTemplate = "";

    static {
        TSLTemplate = FileUtil.readTxt("src/main/resources/file/tsl_template.txt");
    }

    public TSL(){
        //do nothing
    }

    public TSL(int templateId){
        this.templateId = templateId;
        this.createTime = new Date();
        this.updateTime = this.createTime;
        initialContent();
    }

    /**
     * 初始化TSL模型
     */
    private void initialContent(){

        JSONObject jsonObject = JSONObject.parseObject(TSLTemplate);
        jsonObject.put("templateId", this.templateId);
        this.content = jsonObject;
    }

    /**
     * 使用json字符串
     * 设置自身的json对象
     * @param
     */
    public void setJSONObjWithStr(String jsonStr){
        this.content = JSONObject.parseObject(jsonStr);
    }

}
