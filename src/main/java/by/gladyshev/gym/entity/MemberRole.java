package by.gladyshev.gym.entity;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum MemberRole {
    GUEST(Set.of(Permission.READ_PUBLIC)),
    MEMBER(Set.of(Permission.READ_PUBLIC)),
    TRAINER(Set.of(Permission.READ_PUBLIC)),
    ADMIN(Set.of(Permission.READ_PUBLIC, Permission.READ_PRIVATE, Permission.POST));

    MemberRole(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    private final Set<Permission> permissions;

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}
