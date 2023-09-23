package vn.cmax.cafe.utils;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import vn.cmax.cafe.movie.MovieEntity;
import vn.cmax.cafe.user.UserEntity;
import vn.cmax.cafe.user.UserRole;

public class QuerySpecifications {
  public static Specification<UserEntity> searchUsers(
      UserRole role, String userName, String email, String phoneNumber) {
    return (Root<UserEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
      List<Predicate> predicates = new ArrayList<>();
      if (role != null) {
        predicates.add(criteriaBuilder.equal(root.get("roles"), role));
      }
      if (StringUtils.isNotBlank(userName)) {
        predicates.add(
            criteriaBuilder.like(
                criteriaBuilder.lower(root.get("username")), "%" + userName.toLowerCase() + "%"));
      }
      if (StringUtils.isNotBlank(email)) {
        predicates.add(
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("email")), "%" + email.toLowerCase() + "%"));
      }
      if (StringUtils.isNotBlank(phoneNumber)) {
        predicates.add(criteriaBuilder.like(root.get("phoneNumber"), "%" + phoneNumber + "%"));
      }
      return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    };
  }

  public static Specification<MovieEntity> searchMovie(Long category, String keyword) {
    return (Root<MovieEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
      List<Predicate> predicates = new ArrayList<>();
      if (StringUtils.isNotBlank(keyword)) {
        predicates.add(
            criteriaBuilder.like(
                criteriaBuilder.lower(root.get("movieName")), "%" + keyword.toLowerCase() + "%"));
      }
      if (category != null) {
        predicates.add(criteriaBuilder.equal(root.get("category_id"), category));
      }
      return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    };
  }
}
