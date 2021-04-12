package com.nnamdi.account.swagger;

import com.fasterxml.classmate.TypeResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import static com.google.common.collect.Lists.newArrayList;

import java.util.List;


@Configuration
@EnableSwagger2
public class SwaggerConfig {

    //prefix for api version 1
    private static final String V1 = "v1.0";

    @Autowired
    private TypeResolver typeResolver;


    /**
     * Initialize group for API ver 1
     */

    @Bean
    public Docket v1Api() {
        List<ResponseMessage> res = buildGlobalResponses();
        ApiInfo info = buildApiInfo(V1);
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(true)
                .globalResponseMessage(RequestMethod.GET, res)
                .globalResponseMessage(RequestMethod.POST,res)
                .globalResponseMessage(RequestMethod.PUT, res)
                .globalResponseMessage(RequestMethod.DELETE, res)
                .apiInfo(info)
                .groupName(V1)
                .useDefaultResponseMessages(false)
                .tags(new Tag("Account Microservice", "Create Account"))
                .select()
                .paths(PathSelectors.regex(".*/"+V1+".*"))
                .build();
    }

    private ApiInfo buildApiInfo(String ver) {
        return new ApiInfoBuilder()
                .title("Account Microservice")
                .description("This micro service is in charge of managing the accounts")
                .version(ver)
                .contact(new Contact(null,null, "nwabuokeinnamdi19@gmail.com"))
                .build();
    }


    public List<ResponseMessage> buildGlobalResponses() {
        return newArrayList(new ResponseMessageBuilder()
                .code(500)
                .message("Unexpected error during execution")
                .responseModel(new ModelRef("Error"))
                .build()

        );
    }
}
