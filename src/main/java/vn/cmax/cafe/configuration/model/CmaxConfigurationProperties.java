package vn.cmax.cafe.configuration.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CmaxConfigurationProperties {
    private String fileDirectory;
    private String domainName;
    private CmaxPublicInformation info;
}
