package in.dudeapp.common.util;

import com.google.gson.Gson;

/**
 * Created by mohan on 16/07/22
 */
public class GsonUtil {
    private static Gson gson;

    private GsonUtil(){}

    public static Gson getInstance(){
        if(gson == null){
            gson = new Gson();
        }
        return gson;
    }
}
