package ml.miron.captcha.spring.boot;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AudioCaptchaServletTest {

    @Test
    void getStoresAnswerAndWritesAudioWithNoCacheHeaders() throws IOException, jakarta.servlet.ServletException {
        AudioCaptchaServlet servlet = new AudioCaptchaServlet();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        ServletOutputStream stream = new ServletOutputStream() {
            @Override
            public boolean isReady() { return true; }

            @Override
            public void setWriteListener(WriteListener listener) { }

            @Override
            public void write(int value) { bytes.write(value); }
        };
        when(request.getSession()).thenReturn(session);
        when(response.getOutputStream()).thenReturn(stream);

        servlet.doGet(request, response);

        verify(session).setAttribute(eq("captcha"), anyString());
        verify(response).setContentType("audio/wav");
        verify(response).setHeader("Cache-Control", "no-cache, no-store");
        verify(response).setHeader("Pragma", "no-cache");
        verify(response).setDateHeader("Expires", 0);
        assertThat(bytes.size()).isGreaterThan(0);
    }
}
