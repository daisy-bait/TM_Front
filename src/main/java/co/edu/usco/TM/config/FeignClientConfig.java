package co.edu.usco.TM.config;

import feign.Request;
import feign.RequestInterceptor;
import feign.Retryer;
import feign.codec.Encoder;
import feign.form.ContentType;
import feign.form.MultipartFormContentProcessor;
import feign.form.spring.SpringFormEncoder;
import feign.form.spring.SpringManyMultipartFilesWriter;
import feign.form.spring.SpringSingleMultipartFileWriter;
import java.lang.System.Logger;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.support.JsonFormWriter;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Configuration
@EnableFeignClients(basePackages = "co.edu.usco.TM.client")
@Import(JsonFormWriter.class)
public class FeignClientConfig {

    @Bean
    public JwtRequestInterceptor jwtRequestInterceptor() {
        return new JwtRequestInterceptor();
    }

    // Registrar el interceptor de Feign para añadir el token JWT automáticamente
    @Bean
    public RequestInterceptor requestInterceptor(JwtRequestInterceptor jwtRequestInterceptor) {
        return jwtRequestInterceptor;
    }

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.ALL;
    }

    @Bean
    Encoder feignEncoder(ObjectFactory<HttpMessageConverters> messageConverters, JsonFormWriter jsonFormWriter) {
        return new SpringFormEncoder(new SpringEncoder(messageConverters)) {
            {
                var processor = (MultipartFormContentProcessor) getContentProcessor(ContentType.MULTIPART);
                processor.addFirstWriter(jsonFormWriter);
                processor.addFirstWriter(new SpringSingleMultipartFileWriter());
                processor.addFirstWriter(new SpringManyMultipartFilesWriter());
            }
        };
    }
    
    @Bean
    public HttpMessageConverters messageConverters() {
        return new HttpMessageConverters(new MappingJackson2HttpMessageConverter());
    }
    
    @Bean
    public Request.Options options() {
        return new Request.Options(
            5000, TimeUnit.MILLISECONDS,  // connectTimeout
            5000, TimeUnit.MILLISECONDS,  // readTimeout
            true   // followRedirects
        );
    }
    
    @Bean
    public Retryer retryer() {
        return new Retryer.Default(100, 2000, 3);
    }
}
