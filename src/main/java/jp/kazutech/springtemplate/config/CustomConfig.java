package jp.kazutech.springtemplate.config;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Configuration
@Getter
public class CustomConfig {

    // swagger表示設定(デフォルト非表示)
    @Value("${config.swagger.show:false}")
    private boolean showSwagger;

    @Value("${config.base-url}")
    private String baseUrl;

    public String getInviteAcceptEndpointTemplate() {
        return StringUtils.cleanPath(StringUtils.applyRelativePath(baseUrl, "/accept/%s"));
    }

    public URL getBaseUrl() {
        try {
            return URI.create(baseUrl).toURL();
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid base URL: %s".formatted(baseUrl), e);
        }
    }
}
