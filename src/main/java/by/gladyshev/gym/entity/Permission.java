package by.gladyshev.gym.entity;

public enum Permission {
    READ_PUBLIC("public:read"),
    READ_PRIVATE("private:read"),
    POST("post");
    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
