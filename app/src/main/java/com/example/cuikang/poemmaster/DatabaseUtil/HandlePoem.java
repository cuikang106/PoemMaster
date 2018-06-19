package com.example.cuikang.poemmaster.DatabaseUtil;
import java.lang.String;
import java.util.List;

/**
 * Created by IDesign_Lab on 2018/6/6.
 */

public class HandlePoem {
    public static String splitPoem(String originalPoem) {
        String[] poemStrArray = originalPoem.split(",");
        String splitPoem = poemStrArray[0];
        for (int i = 1; i < poemStrArray.length; i++) {
            String strPart = poemStrArray[i];
            splitPoem += "\n";
            splitPoem += strPart;
        }
        return splitPoem;
    }

    public static String combinePoem(List<String> originalPoem) {
        String combinePoem = originalPoem.get(0);
        for (int i = 1; i < originalPoem.size(); i++) {
            String strPart = originalPoem.get(i);
            combinePoem += "\n";
            combinePoem += strPart;
        }
        return combinePoem;
    }
}
