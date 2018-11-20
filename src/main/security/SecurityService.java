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

public class SecurityService {
	
	public static Subject currentUser; 
	private static final int ITERATIONS = 20000;
	
	public static void initialize() {
		//Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
	    DefaultSecurityManager securityManager = new DefaultSecurityManager();
	    Realm realm = new SecureRealm();
	    CredentialsMatcher matcher = new HashedCredentialsMatcher();
	    ((HashedCredentialsMatcher)matcher).setHashAlgorithmName("SHA-256");
	    ((HashedCredentialsMatcher)matcher).setStoredCredentialsHexEncoded(false);
	    ((HashedCredentialsMatcher)matcher).setHashIterations(ITERATIONS);
	    ((AuthenticatingRealm)realm).setCredentialsMatcher(matcher); 
	    securityManager.setRealm(realm);
	    SecurityUtils.setSecurityManager(securityManager);  
	}
	
	public static String Login(String inputUserName, String inputPassword) {
		
		currentUser = SecurityUtils.getSubject();
		
		if (!currentUser.isAuthenticated()) {
			UsernamePasswordToken token = new UsernamePasswordToken(inputUserName, inputPassword);
	        try {
	            currentUser.login(token);
	            return "Success";
	        } catch (UnknownAccountException uae) {
	        	return "Unknown account";
	        } catch (IncorrectCredentialsException ice) {
	        	return "Wrong password";
	        } catch (Exception e) {
	        	return "Internal error";
	        }			
		}
		else {
			return "The user is already authenticated";
		}
	}
		
	
	
}
