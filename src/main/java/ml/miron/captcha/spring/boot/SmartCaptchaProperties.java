package ml.miron.captcha.spring.boot;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = SmartCaptchaProperties.PREFIX)
public class SmartCaptchaProperties {

	public static final String PREFIX = "smart-captcha";

	/** 是否启用验证码自动装配。 */
	private boolean enabled = true;

	/** 图片验证码访问路径 **/
	private String imagePattern = "/captcha.jpg";
	/** 图片验证码刷新路径 **/
	private String refreshPattern = "/captcha.html";
	/** 语音验证码访问路径 **/
	private String audioPattern = "/audio.wav";
	/** 语音存储的路径 */
	private String audioPath;
	private int width = 200;
	private int height = 50;

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getImagePattern() {
		return imagePattern;
	}

	public void setImagePattern(String imagePattern) {
		this.imagePattern = imagePattern;
	}
	
	public String getRefreshPattern() {
		return refreshPattern;
	}

	public void setRefreshPattern(String refreshPattern) {
		this.refreshPattern = refreshPattern;
	}

	public String getAudioPattern() {
		return audioPattern;
	}

	public void setAudioPattern(String audioPattern) {
		this.audioPattern = audioPattern;
	}

	public String getAudioPath() {
		return audioPath;
	}

	public void setAudioPath(String audioPath) {
		this.audioPath = audioPath;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

}
