package nuricanozturk.dev.data.flight.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "airport")
public class Airport
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false, unique = true)
    private String city;

    public Airport()
    {
    }

    public Airport(String city)
    {
        this.city = city;
    }

    public UUID getId()
    {
        return id;
    }

    public void setId(UUID id)
    {
        this.id = id;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }
}
