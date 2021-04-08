package com.huimi.core.webpowerSms;

/**
 * @author ccl
 * @time 2018-04-10 15:31
 * @name IOkHttpClient
 * @desc:
 */
public interface IOkHttpClient {
    IResponse get(IRequest request, boolean foreCache);
    IResponse post(IRequest request, boolean foreCache);
}
