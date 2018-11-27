package security;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAccount;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleRole;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.SimpleByteSource;

import dao.UserDao;
import dao.factory.UserDaoFactory;
import entity.User;
import exception.DBConnectionException;

public class SecureRealm extends AuthorizingRealm {

	private UserDao dao;
	private final String name = "SecureRealm";
	protected final Map<String, SimpleRole> roles; //roleName-to-SimpleRole
	private static final String[] roleNames = new String[] {"Admin", "High", "PS", "Normal"};

    public SecureRealm() {
        this((UserDao)null);
    }
    
    public SecureRealm(UserDao dao) {
        //SimpleAccountRealms are memory-only realms - no need for an additional cache mechanism since we're
        //already as memory-efficient as we can be:
    	this.roles = new LinkedHashMap<String, SimpleRole>();
        setCachingEnabled(false);
        if (dao == null) {
        	try {
    			dao = UserDaoFactory.getInstance();
    		} catch (DBConnectionException e) {
    			e.printStackTrace();
    		}
        }
        else {
        	this.dao = dao;
        }      
        setName(name);
    }

    public SecureRealm(String name) {
        this();
        setName(name);
    }

    protected SimpleAccount getUser(String username) {
        User currentUser = dao.findUserByUni(username);
        if (currentUser == null) {
        	throw new UnknownAccountException(); 
        }
        String[] saltAndHash = PasswordHashing.getSaltAndHashedPassword(currentUser.getPassword());
        SimpleAccount acc = new SimpleAccount((Object)username, (Object)(saltAndHash[1]), new SimpleByteSource(Base64.decodeBase64(saltAndHash[0])), name);
        int currentUserGroup = currentUser.getUserGroup();
        for (int i = currentUserGroup; i<4; i++) {
        	acc.addRole(roleNames[i]);
        }      
        return acc;
    }

    public boolean accountExists(String username) {
        return getUser(username) != null;
    }

    protected String getUsername(SimpleAccount account) {
        return getUsername(account.getPrincipals());
    }

    protected String getUsername(PrincipalCollection principals) {
        return getAvailablePrincipal(principals).toString();
    }

    protected SimpleRole getRole(String rolename) {
        return roles.get(rolename);
        
    }

    public boolean roleExists(String name) {
        return getRole(name) != null;
    }

    public void addRole(String name) {
        add(new SimpleRole(name));
    }

    protected void add(SimpleRole role) {
       roles.put(role.getName(), role);
       
    }

    protected static Set<String> toSet(String delimited, String delimiter) {
        if (delimited == null || delimited.trim().equals("")) {
            return null;
        }

        Set<String> values = new HashSet<String>();
        String[] rolenamesArray = delimited.split(delimiter);
        for (String s : rolenamesArray) {
            String trimmed = s.trim();
            if (trimmed.length() > 0) {
                values.add(trimmed);
            }
        }

        return values;
    }

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        SimpleAccount account = getUser(upToken.getUsername());

        if (account != null) {

            if (account.isLocked()) {
                throw new LockedAccountException("Account [" + account + "] is locked.");
            }
            if (account.isCredentialsExpired()) {
                String msg = "The credentials for account [" + account + "] are expired";
                throw new ExpiredCredentialsException(msg);
            }

        }

        return account;
    }

    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = getUsername(principals);
        return getUser(username);
        
    }
}
