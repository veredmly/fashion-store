import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdatePasswordAction {


    private final String employeeId;
    private final String password;

    @JsonCreator
    public UpdatePasswordAction(@JsonProperty("employeeId") String employeeId,
                                @JsonProperty("password") String password) {

        this.employeeId = employeeId;
        this.password = password;

    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UpdatePasswordAction that = (UpdatePasswordAction) o;

        if (employeeId != null ? !employeeId.equals(that.employeeId) : that.employeeId != null) return false;
        return password != null ? password.equals(that.password) : that.password == null;
    }

    @Override
    public int hashCode() {
        int result = employeeId != null ? employeeId.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UpdatePasswordAction{" +
                "employeeId='" + employeeId + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
