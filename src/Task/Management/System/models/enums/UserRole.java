package Task.Management.System.models.enums;

public enum UserRole {
    NORMAL,
    VIP,
    ADMIN;

    @Override
    public String toString() {
        switch (this) {
            case ADMIN:
                return "Admin";
            case NORMAL:
                return "Normal";
            case VIP:
                return "VIP";
            default:
                return "";
        }
    }
}
