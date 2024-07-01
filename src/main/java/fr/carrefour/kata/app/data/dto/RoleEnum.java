package fr.carrefour.kata.app.data.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public enum RoleEnum {

  USER(
          Set.of(
                  PermissionEnum.USER_READ,
                  PermissionEnum.USER_UPDATE,
                  PermissionEnum.USER_DELETE,
                  PermissionEnum.USER_CREATE
          )
  ),
  ADMIN(
          Set.of(
                  PermissionEnum.ADMIN_READ,
                  PermissionEnum.ADMIN_UPDATE,
                  PermissionEnum.ADMIN_DELETE,
                  PermissionEnum.ADMIN_CREATE,
                  PermissionEnum.USER_READ,
                  PermissionEnum.USER_UPDATE,
                  PermissionEnum.USER_DELETE,
                  PermissionEnum.USER_CREATE
          )
  );

  @Getter
  private final Set<PermissionEnum> permissions;

  public List<SimpleGrantedAuthority> getAuthorities() {
    var authorities = getPermissions()
            .stream()
            .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
            .collect(Collectors.toList());
    authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

    return authorities;
  }
}
