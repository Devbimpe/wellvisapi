//package com.medviser.services;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.thymeleaf.TemplateEngine;
//import org.thymeleaf.context.Context;
//
///**
// * Created by Longbridge on 04/01/2018.
// */
//@Service
//public class MailContentBuilder {
//    private TemplateEngine templateEngine;
//
//    @Autowired
//    public MailContentBuilder(TemplateEngine templateEngine) {
//        this.templateEngine = templateEngine;
//    }
//
//    public String build(String message) {
//        Context context = new Context();
//        context.setVariable("message", message);
//        return templateEngine.process("emailtemplate", context);
//    }
//}
