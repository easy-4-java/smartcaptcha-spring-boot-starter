/*
 * Copyright (c) 2018, hiwepy (https://github.com/hiwepy).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package ml.miron.captcha.spring.boot;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {{ @link SmartCaptchaAutoConfiguration }}.
 *
 * <p>Verifies the auto-configuration activates under the expected conditions
 * and exposes its declared beans.</p>
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
@DisplayName("SmartCaptchaAutoConfiguration Tests")
class SmartCaptchaAutoConfigurationTest {

    private final ApplicationContextRunner runner = new ApplicationContextRunner();

    @Test
    @DisplayName("Auto-configuration class can be instantiated")
    void testInstantiation() {
        SmartCaptchaAutoConfiguration configuration = new SmartCaptchaAutoConfiguration();
        assertThat(configuration).isNotNull();
    }

    @Test
    @DisplayName("Auto-configuration loads when 'spring.boot.enabled=true'")
    void testLoadsWhenEnabledPropertySet() {
        runner.withUserConfiguration(SmartCaptchaAutoConfiguration.class)
                .withPropertyValues("spring.boot.enabled=true")
                .run(context -> assertThat(context).hasSingleBean(SmartCaptchaAutoConfiguration.class));
    }

    @Test
    @DisplayName("Auto-configuration registers captcha endpoints with default settings")
    void testLoadsWithDefaults() {
        runner.withUserConfiguration(SmartCaptchaAutoConfiguration.class)
                .run(context -> {
                    assertThat(context).hasSingleBean(SmartCaptchaAutoConfiguration.class);
                    assertThat(context).hasBean("imageCaptchaServlet");
                    assertThat(context).hasBean("audioCaptchaServlet");
                    assertThat(context).hasBean("refreshCaptchaFilter");
                });
    }

    @Test
    @DisplayName("Custom endpoint paths and dimensions bind from configuration")
    void testCustomSettingsBind() {
        runner.withUserConfiguration(SmartCaptchaAutoConfiguration.class)
                .withPropertyValues(
                        "smart-captcha.image-pattern=/image.jpg",
                        "smart-captcha.refresh-pattern=/refresh",
                        "smart-captcha.audio-pattern=/audio.wav",
                        "smart-captcha.audio-path=/voice",
                        "smart-captcha.width=180",
                        "smart-captcha.height=60")
                .run(context -> {
                    SmartCaptchaProperties properties = context.getBean(SmartCaptchaProperties.class);
                    assertThat(properties.getImagePattern()).isEqualTo("/image.jpg");
                    assertThat(properties.getRefreshPattern()).isEqualTo("/refresh");
                    assertThat(properties.getAudioPattern()).isEqualTo("/audio.wav");
                    assertThat(properties.getAudioPath()).isEqualTo("/voice");
                    assertThat(properties.getWidth()).isEqualTo(180);
                    assertThat(properties.getHeight()).isEqualTo(60);
                });
    }
}
