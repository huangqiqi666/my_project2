package com.hikvision.pbg.sitecodeprj.utils.humanId;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenerateUtils {
    private static final Logger log = LoggerFactory.getLogger(GenerateUtils.class);

    public GenerateUtils() {
    }

    public static String cardToHumanId(Integer certificateType, String certificateNumber) {
        String typeAndNum = "_" + IdentifySecurityTools.encode(certificateType + "_" + certificateNumber);
        String hashVale = String.format("%04d", Math.abs(typeAndNum.hashCode() % 999) + 1);
        return hashVale + typeAndNum;
    }

    public static CardInfo humanIdToCard(String humanId) {
        CardInfo cardInfo = new CardInfo();

        try {
            if (StringUtils.isNotEmpty(humanId) && humanId.length() > 5) {
                String card = IdentifySecurityTools.decode(humanId.substring(5));
                String[] arr = card.split("_");
                cardInfo.setCertificateType(arr[0]);
                cardInfo.setCertificateNumber(arr[1]);
            }
        } catch (Exception var4) {
            log.error("humanIdToCard-error:humanId={},e:{}", humanId, var4);
        }

        return cardInfo;
    }

    public static String humanId2Card(String humanId) {
        CardInfo cardInfo = humanIdToCard(humanId);
        return cardInfo.getCertificateNumber() + "##" + cardInfo.getCertificateType();
    }

    public static void main(String[] args) {
        System.out.println(humanIdToCard("0890_8GaMSoe1S8X6XQN8CQ8QNoa1oaQ_Ga68ENGS"));
        System.out.println(humanIdToCard("0890_8GaMSoe1S8X6XQN8CQ8QNoa1oaQ_Ga68ENGS"));
    }
}
