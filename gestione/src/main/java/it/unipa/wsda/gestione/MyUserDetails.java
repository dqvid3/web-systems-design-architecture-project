package it.unipa.wsda.gestione;

import java.util.Collection;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import it.unipa.wsda.gestione.entities.Ruolo;
import it.unipa.wsda.gestione.entities.Utente;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class MyUserDetails implements UserDetails {

    private Utente user;

    public MyUserDetails(Utente user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Ruolo> roles = user.getRuoli();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Ruolo role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getNome()));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPasswordHash();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }
}
