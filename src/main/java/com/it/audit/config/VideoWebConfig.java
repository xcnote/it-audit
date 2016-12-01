package com.it.audit.config;

import java.nio.charset.Charset;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.ServletRegistration.Dynamic;

import lombok.extern.slf4j.Slf4j;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.common.collect.Lists;
import com.ksyun.video.auth.interceptor.KscCookieInterceptor;
import com.ksyun.video.auth.interceptor.VideoUserAuthInterceptor;
import com.ksyun.video.common.config.DateTimeDeserializer;
import com.ksyun.video.common.config.DateTimeSerializer;
import com.ksyun.video.common.config.RestExceptionHandler;
import com.ksyun.video.common.filter.KsyunVideoFilter;

@Configuration
@ComponentScan(basePackages = { "com.ksyun.video" })
@EnableTransactionManagement
@Slf4j
public class VideoWebConfig extends WebMvcConfigurationSupport{

	@Autowired
	private KscCookieInterceptor kscCookieInterceptor;
	@Autowired
	private VideoUserAuthInterceptor videoUserAuthInterceptor;
	
	public static Class<?>[] getRootConfigClasses() {
		return new Class<?>[] {
		//
		};
	}

	public static Class<?>[] getServletConfigClasses() {
		return new Class<?>[] {
		//
		VideoWebConfig.class, //
		};
	}

	public static String[] getServletMappings() {
		final Collection<String> mappings = new LinkedHashSet<String>();
		mappings.add("/");

		final String[] result = mappings.toArray(new String[] {});

		if (log.isInfoEnabled()) {
			log.info("dispatcherServletMappings: {}", (Object) result);
		}

		return result;
	}
	
    public static Filter[] getServletFilters() {
        final KsyunVideoFilter filter = new KsyunVideoFilter();
        final Filter[] filters = new Filter[] { filter };

        log.info("getServletFilters: {}", (Object) filters);

        return filters;
    }

	public static void customizeRegistration(final Dynamic registration) {
		registration.setInitParameter("dispatchOptionsRequest", "true");
		registration.setAsyncSupported(true);
		registration.setLoadOnStartup(1);
	}

	@Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(kscCookieInterceptor);
        registry.addInterceptor(videoUserAuthInterceptor);
    }
	
	/*
	 * @Override public void configureDefaultServletHandling(final
	 * DefaultServletHandlerConfigurer configurer) { configurer.enable(); //
	 * same as <mvc:default-servlet-handler/> }
	 */
	@Override
	public void configureContentNegotiation(
			final ContentNegotiationConfigurer configurer) {
		configurer.favorPathExtension(true).favorParameter(false)
				.ignoreAcceptHeader(false).useJaf(false)
				.defaultContentType(MediaType.APPLICATION_JSON)
				.mediaType("json", MediaType.APPLICATION_JSON);
	}

	@Override
	public void configureMessageConverters(
			final List<HttpMessageConverter<?>> converters) {
		converters.add(this.stringHttpMessageConverter());
		converters.add(this.jsonHttpMessageConverter());
	}
	

	@Bean(name = "jsonMapper")
	public ObjectMapper jsonMapper() {
		final ObjectMapper bean = new ObjectMapper();
		bean.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		SimpleModule module = new SimpleModule();  
		
		module.addSerializer(DateTime.class,new DateTimeSerializer());
		module.addDeserializer(DateTime.class, new DateTimeDeserializer());
		
		bean.registerModule(module);
		return bean;
	}

	@Bean
	public MappingJackson2HttpMessageConverter jsonHttpMessageConverter() {
		final MappingJackson2HttpMessageConverter bean = new MappingJackson2HttpMessageConverter();
		bean.setObjectMapper(this.jsonMapper());
		bean.setSupportedMediaTypes(Lists.newArrayList(
				MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM));
		return bean;
	}

	@Bean
	public StringHttpMessageConverter stringHttpMessageConverter() {
		StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter(
				Charset.forName("UTF-8"));
		stringHttpMessageConverter.setWriteAcceptCharset(false);
		return stringHttpMessageConverter;
	}
	//exception handle
	@Override
    public final void configureHandlerExceptionResolvers(final List<HandlerExceptionResolver> exceptionResolvers) {
		exceptionResolvers.add(this.annotationExceptionHandlerExceptionResolver());
		exceptionResolvers.add(this.restExceptionResolver());
	}
	@Bean(name = "exceptionHandlerExceptionResolver")
    public ExceptionHandlerExceptionResolver annotationExceptionHandlerExceptionResolver() {
        final ExceptionHandlerExceptionResolver resolver = new ExceptionHandlerExceptionResolver();
        resolver.setOrder(0);
        return resolver;
    }
	@Bean(name = "restExceptionResolver")
    public RestExceptionHandler restExceptionResolver() {
        final RestExceptionHandler bean = new RestExceptionHandler();
        bean.setOrder(100);
        return bean;
    }
	
	/**
	 * filter默认配置的映射是：/*.
	 * 如果觉得控制力度不够灵活（例如你想修改filter的映射），
	 * spring boot还提供了 ServletRegistrationBean，FilterRegistrationBean，ServletListenerRegistrationBean这3个东西来进行配置
	 * @param ksyunVideoFilter
	 * @return
	 * @author CUICHENGRUI
	 */
	@Bean
	public FilterRegistrationBean filterRegistrationBean(KsyunVideoFilter ksyunVideoFilter){
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(ksyunVideoFilter);
		filterRegistrationBean.setEnabled(true);
		filterRegistrationBean.addUrlPatterns("/*");
		return filterRegistrationBean;
	}
}
