//package com.test.Insomnia.controller.daoTesting;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.format.datetime.DateFormatter;
//
//@Configuration
//public class AppConfig {
//    @Bean
//    public DateFormatter dateFormatter() {
////     Spring框架提供了多種方式來自動將輸入的字符串轉換為目標數據類型。其中一種方式是使用Formatter接口的實現類，如您所示的DateFormatter。
////     當您在Spring中使用DateFormatter將字符串轉換為日期時，Spring會嘗試使用您指定的格式將輸入的字符串解析為日期對象。如果解析成功，Spring會將該日期對象綁定到目標方法的參數上，並在處理請求時使用它。
////     因此，在您的代碼中，當Spring接收到日期字符串時，它將使用您指定的格式進行解析，然後將日期對象綁定到目標方法的參數上，這樣您就可以在方法中使用日期對象進行進一步處理了。
//        DateFormatter dateFormatter = new DateFormatter();
//        dateFormatter.setPattern("yyyy-MM-dd");
//        return dateFormatter;
//    }
//}
