package com.huimi.nettySocket.service;


public interface IMessageService<String> {

    Object handle(String requestId, String message);

    String getCode();

}
