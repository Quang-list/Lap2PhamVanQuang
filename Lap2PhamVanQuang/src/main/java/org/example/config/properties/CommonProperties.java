package org.example.config.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonProperties {
    @Value("${appConfig.default-page-size}")
    private int defaultPageSize;
    @Value("${appConfig.default-page-number}")
    private int defaultPageNumber;

    public int getDefaultPageSize() {
        return defaultPageSize;
    }

    public void setDefaultPageSize(int defaultPageSize) {
        this.defaultPageSize = defaultPageSize;
    }

    public int getDefaultPageNumber() {
        return defaultPageNumber;
    }

    public void setDefaultPageNumber(int defaultPageNumber) {
        this.defaultPageNumber = defaultPageNumber;
    }
}
