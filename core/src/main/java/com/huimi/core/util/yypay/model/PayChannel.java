package com.huimi.core.util.yypay.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Data
public class PayChannel {
    String bankName;
    String bankID;
    String otherBankID;
    String cardType;


    public PayChannel() {
    }

    public PayChannel(Consumer<PayChannel> consumer) {
        consumer.accept(this);
    }

    public static List getList(String xmlStr) {
        List<PayChannel> list = new ArrayList<>();
        String rowsStr = xmlStr.split("<bankList>")[1].split("</bankList>")[0];
        String[] rows = rowsStr.replace("<bankRow>", "").split("</bankRow>");
        if (rows == null || rows.length <= 0) {
            return list;
        }
        for (String item : rows) {
            list.add(new PayChannel(t -> {
                t.setBankName(item.split("<bankName>")[1].split("</bankName>")[0]);
                t.setBankID(item.split("<bankID>")[1].split("</bankID>")[0]);
                t.setOtherBankID(item.split("<otherBankID>")[1].split("</otherBankID>")[0]);
                t.setCardType(item.split("<cardType>")[1].split("</cardType>")[0]);
            }));
        }
        return list;
    }
}
