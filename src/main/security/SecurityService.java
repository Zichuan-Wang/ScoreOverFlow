package security;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.Subject;

import dao.UserDao;

public class SecurityService {
	
	public static Subject currentUser; 
	private static final int ITERATIONS = 20000;
	
	public static void initialize(UserDao dao) {
		//Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
	    DefaultSecurityManager securityManager = new DefaultSecurityManager();
	    Realm realm;
	    if (dao == null) {
	    	realm = new SecureRealm();
	    }
	    else {
	    	realm = new SecureRealm(dao);
	    }
	    CredentialsMatcher matcher = new HashedCredentialsMatcher();
	    ((HashedCredentialsMatcher)matcher).setHashAlgorithmName("SHA-256");
	    ((HashedCredentialsMatcher)matcher).setStoredCredentialsHexEncoded(false);
	    ((HashedCredentialsMatcher)matcher).setHashIterations(ITERATIONS);
	    ((AuthenticatingRealm)realm).setCredentialsMatcher(matcher); 
	    securityManager.setRealm(realm);
	    SecurityUtils.setSecurityManager(securityManager);  
	}
	
	public static LoginStatus Login(String inputUserName, String inputPassword) {
		
		currentUser = SecurityUtils.getSubject();
		
		if (!currentUser.isAuthenticated()) {
			UsernamePasswordToken token = new UsernamePasswordToken(inputUserName, inputPassword);
	        try {
	            currentUser.login(token);
	            return new LoginStatus(true, "Success");
	        } catch (UnknownAccountException uae) {
	        	return new LoginStatus(false, "Unknown account");
	        } catch (IncorrectCredentialsException ice) {
	        	return new LoginStatus(false, "Wrong password");
	        } catch (Exception e) {
	        	return new LoginStatus(false, "Internal error");
	        }			
		}
		else {
			return new LoginStatus(false, "The user is already authenticated");
		}
	}
	
}
