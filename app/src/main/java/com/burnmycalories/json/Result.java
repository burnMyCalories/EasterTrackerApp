/**
 * Copyright (C), 2019-2020, 天骄山仔
 * FileName: Result
 * Author:   lenovo
 * Date:     2020/5/19 22:03
 * Description: 前后端json交互格式
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.burnmycalories.json;

/**
 * 〈一句话功能简述〉<br> 
 * 〈前后端json交互格式〉
 *
 * @author lenovo
 * @create 2020/5/19
 * @since 1.0.0
 */
public class Result<T> {
    public int code;
    public String message;
    public T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
