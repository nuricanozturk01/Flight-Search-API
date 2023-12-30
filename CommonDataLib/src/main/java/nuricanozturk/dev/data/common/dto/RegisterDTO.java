package nuricanozturk.dev.data.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class RegisterDTO
{
    @JsonProperty(required = true)
    private final String username;
    @JsonProperty(value = "first_name", required = true)
    private final String firstName;
    @JsonProperty(value = "middle_name", required = true)
    private final String middleName;
    @JsonProperty(value = "last_name", required = true)
    private final String lastName;
    @JsonProperty(required = true)
    private final String email;
    @JsonProperty(required = true)
    private String password;

    public RegisterDTO(String username, String firstName, String middleName, String lastName, String email, String password)
    {
        this.username = username;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public String getUsername()
    {
        return username;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getMiddleName()
    {
        return middleName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public String getEmail()
    {
        return email;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (RegisterDTO) obj;
        return Objects.equals(this.username, that.username) &&
                Objects.equals(this.firstName, that.firstName) &&
                Objects.equals(this.middleName, that.middleName) &&
                Objects.equals(this.lastName, that.lastName) &&
                Objects.equals(this.email, that.email) &&
                Objects.equals(this.password, that.password);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(username, firstName, middleName, lastName, email, password);
    }

    @Override
    public String toString()
    {
        return "RegisterDTO[" +
                "username=" + username + ", " +
                "firstName=" + firstName + ", " +
                "middleName=" + middleName + ", " +
                "lastName=" + lastName + ", " +
                "email=" + email + ", " +
                "password=" + password + ']';
    }

}