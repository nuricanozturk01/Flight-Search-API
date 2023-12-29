package nuricanozturk.dev.data.flight.entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "customer")
public class Customer implements UserDetails
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "customer_id")
    private UUID customerId;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @JoinTable(name = "authorities", joinColumns = @JoinColumn(name = "customer_id"))
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    public Customer()
    {
    }

    public Customer(String username, String password, String firstName, String middleName, String lastName, String email)
    {
        this.password = password;
        this.username = username;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.email = email;
        addRole(Role.ROLE_USER);
    }

    public Customer(String username, String password, String firstName, String middleName, String lastName, String email, Role role)
    {
        this.password = password;
        this.username = username;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.email = email;
        addRole(role);
    }

    public void addRole(Role role)
    {
        if (roles == null)
            roles = new HashSet<>();

        roles.add(role);
    }

    public Set<Role> getRoles()
    {
        return roles;
    }

    public void setRoles(Set<Role> roles)
    {
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return roles;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public UUID getCustomerId()
    {
        return customerId;
    }

    public void setCustomerId(UUID customerId)
    {
        this.customerId = customerId;
    }

    public String getUsername()
    {
        return username;
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return false;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return false;
    }

    @Override
    public boolean isEnabled()
    {
        return false;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getMiddleName()
    {
        return middleName;
    }

    public void setMiddleName(String middleName)
    {
        this.middleName = middleName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }
}