package OptionalDemo;

import java.util.Optional;

/**
 * @author zhangs
 * @Description
 * @createDate 2019/6/3
 */
public class Address {
    private Country country;

    public Address() {
    }

    public Optional<Country> getCountry() {
        return Optional.ofNullable(country);
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
