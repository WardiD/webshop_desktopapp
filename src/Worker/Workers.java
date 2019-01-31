package Worker;

public class Workers {
    private int id_worker;
    private String name;
    private int phone;
    private String email;
    private int id_log;
    private boolean superAdmin;

    public Workers(int id_worker, String name, int phone, String email, int id_log, boolean superAdmin) {
        this.id_worker = id_worker;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.id_log = id_log;
        this.superAdmin = superAdmin;
    }

    @Override
    public String toString() {
        return "Workers{" +
                "id_worker=" + id_worker +
                ", name='" + name + '\'' +
                ", phone=" + phone +
                ", email='" + email + '\'' +
                ", id_log=" + id_log +
                ", superAdmin=" + superAdmin +
                '}';
    }

    public int getId_worker() {
        return id_worker;
    }

    public String getName() {
        return name;
    }

    public int getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public int getId_log() {
        return id_log;
    }

    public boolean isSuperAdmin() {
        return superAdmin;
    }
}
