package ml.miron.captcha.spring.boot;

import javax.servlet.ServletException;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ml.miron.captcha.servlet.AudioCaptchaServlet;
import ml.miron.captcha.servlet.CaptchaServlet;
import ml.miron.captcha.servlet.RefreshCaptchaFilter;

@Configuration
@ConditionalOnClass({ CaptchaServlet.class })
@ConditionalOnProperty(prefix = SmartCaptchaProperties.PREFIX, name = "enabled", havingValue = "true", matchIfMissing = true)
@EnableConfigurationProperties(SmartCaptchaProperties.class)
public class SmartCaptchaAutoConfiguration {

	private static final String PARAM_HEIGHT = "captcha-height";
    private static final String PARAM_WIDTH = "captcha-width";
    private static final String PARAM_AUDIO_PATH = "audio-path";
    
    @Bean
	@ConditionalOnMissingBean(name = "imageCaptchaServlet")
	public ServletRegistrationBean<CaptchaServlet> imageCaptchaServlet(SmartCaptchaProperties properties) throws ServletException {

		ServletRegistrationBean<CaptchaServlet> registrationBean = new ServletRegistrationBean<CaptchaServlet>();
		
		CaptchaServlet captchaServlet = new CaptchaServlet();
		
		registrationBean.setServlet(captchaServlet);
		
		// 默认参数
		registrationBean.addInitParameter(PARAM_HEIGHT, Integer.toString(properties.getHeight()));
		registrationBean.addInitParameter(PARAM_WIDTH, Integer.toString(properties.getWidth()));
		registrationBean.addUrlMappings(properties.getImagePattern());

		return registrationBean;
	}
    
    @Bean
	@ConditionalOnMissingBean(name = "audioCaptchaServlet")
	public ServletRegistrationBean<AudioCaptchaServlet> audioCaptchaServlet(SmartCaptchaProperties properties) throws ServletException {

		ServletRegistrationBean<AudioCaptchaServlet> registrationBean = new ServletRegistrationBean<AudioCaptchaServlet>();
		
		AudioCaptchaServlet captchaServlet = new AudioCaptchaServlet();
		
		registrationBean.setServlet(captchaServlet);
		
		// 默认参数
		registrationBean.addInitParameter(PARAM_AUDIO_PATH, properties.getAudioPath());
		registrationBean.addUrlMappings(properties.getAudioPattern());

		return registrationBean;
	}
    
    @Bean
	@ConditionalOnMissingBean(name = "refreshCaptchaFilter")
	public FilterRegistrationBean<RefreshCaptchaFilter> refreshCaptchaFilter(SmartCaptchaProperties properties){
		
		FilterRegistrationBean<RefreshCaptchaFilter> registrationBean = new FilterRegistrationBean<RefreshCaptchaFilter>();
		registrationBean.setFilter(new RefreshCaptchaFilter());
		
		registrationBean.addUrlPatterns(properties.getRefreshPattern());
		registrationBean.setEnabled(true); 
	    return registrationBean;
	}
    
    
}
