package jp.ac.kagawalab.mynah.core.oauth2.security;

import jp.ac.kagawalab.mynah.core.dto.model.RoleDto;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.io.Serializable;
import java.util.Map;

public final class MynahOidcUser extends MynahOAuth2User implements OidcUser, Serializable {
    private static final long serialVersionUID = -7860492139819186208L;
    private OidcUser oidcUser;

    public MynahOidcUser(int id, String provider, String providerId, RoleDto role, OidcUser oidcUser) {
        super(id, provider, providerId, role, oidcUser);
        this.oidcUser = oidcUser;
    }

    @Override
    public Map<String, Object> getClaims() {
        return oidcUser.getClaims();
    }

    @Override
    public OidcUserInfo getUserInfo() {
        return new OidcUserInfo(oidcUser.getClaims());
    }

    @Override
    public OidcIdToken getIdToken() {
        return oidcUser.getIdToken();
    }
}
