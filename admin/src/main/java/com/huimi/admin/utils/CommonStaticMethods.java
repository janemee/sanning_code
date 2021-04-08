package com.huimi.admin.utils;

import org.springframework.web.servlet.ModelAndView;

public class CommonStaticMethods {
    public static ModelAndView errorModelAndView(ModelAndView modelAndView, String msg) {
        modelAndView.setViewName("errorMsg");
        modelAndView.addObject("msg", msg);
        return modelAndView;
    }

}
