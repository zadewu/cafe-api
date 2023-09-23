package vn.cmax.cafe.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import vn.cmax.cafe.user.UserEntity;
import vn.cmax.cafe.user.UserRole;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Keywords {
    private static final Pattern DIGIT_ONLY_PATTERN = Pattern.compile("^\\d+$");
  private static final Pattern EMAIL_PATTERN =
      Pattern.compile("^[\\w\\.-]+@[a-zA-Z\\d\\.-]+\\.[a-zA-Z]{2,}$");

    public static Specification<UserEntity> fromUserKeyword(UserRole role, String keyword) {
        if (isDigitOnly(keyword)) {
            return QuerySpecifications.searchUsers(role, null, null, keyword);
        } else if (isEmailPattern(keyword)){
            return QuerySpecifications.searchUsers(role, null, keyword, null);
        } else {
            return QuerySpecifications.searchUsers(role, keyword, null, null);
        }
    }

    private static boolean isDigitOnly(String keyword) {
        if ( StringUtils.isBlank(keyword)) {
            return false;
        }
        Matcher matcher = DIGIT_ONLY_PATTERN.matcher(keyword);
        return matcher.matches();
    }

    private static boolean isEmailPattern(String keyword) {
        if ( StringUtils.isBlank(keyword)) {
            return false;
        }
        Matcher matcher = EMAIL_PATTERN.matcher(keyword);
        return matcher.matches();
    }
}
