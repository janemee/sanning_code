package com.huimi.common.tools;

/**
 * @description: 校验银行卡号
 * @author: zhangyong
 * @create: 2018-12-03 16:52
 **/
public class CheckBankCard {
    public static boolean IsBankCard(String bankCard) {
        if(bankCard.length() < 15 || bankCard.length() > 19){
            return false;
        }
        char bit = getBankCardCheckCode(bankCard.substring(0, bankCard.length() - 1));
        if(bit == 'N'){
            return false;
        }
        return bankCard.charAt(bankCard.length() - 1) == bit;
    }


    public static char getBankCardCheckCode(String nonCheckCodeBankCard){
         if(nonCheckCodeBankCard == null || nonCheckCodeBankCard.trim().length() == 0 || !nonCheckCodeBankCard.matches("\\d+")) {
             //如果传的不是数据返回N
             return 'N';
         }
         char[] chs = nonCheckCodeBankCard.trim().toCharArray();
         int luhmSum = 0;
         for(int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if(j % 2 == 0) {
                 k *= 2;
                 k = k / 10 + k % 10;
            }
             luhmSum += k;
         }
         return (luhmSum % 10 == 0) ? '0' : (char)((10 - luhmSum % 10) + '0');
    }
}
