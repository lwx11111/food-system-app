package org.example.config;

/**
 * @Author 刘文轩
 * @Date 2024/1/30 16:13
 */
public class DatabaseContextHolder {

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    public static final String FOOD_DS = "food_datasource";
    public static final String AUTH_DS = "auth_datasource";

    /**
     * 放入
     * @param type
     */
    public static void setDataBase(String type){
        contextHolder.set(type);
    }

    /**
     * 获取
     */
    public static String getDataBase(){
        return contextHolder.get();
    }

    /**
     * 清空
     */
    public static void chearDataSource(){
        contextHolder.remove();
    }

}
