package org.example.common;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 定义网络请求的数据，远程调用需要的信息：哪个类，哪个方法，什么参数
 */
@Data
@AllArgsConstructor
public class Invocation implements Serializable {

    private String className;

    private String methodName;

    private Class<?>[] paramTypes;

    private Object[] args;
}
