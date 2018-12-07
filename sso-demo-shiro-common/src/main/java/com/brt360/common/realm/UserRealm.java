package com.brt360.common.realm;

import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.brt360.common.redisDB.RoleDB;
import com.brt360.common.redisDB.UserDB;

@Component
public class UserRealm extends AuthorizingRealm{
	
	@Autowired
	private UserDB userDB;
	
	@Autowired
	private RoleDB roleDB;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		List<String> roles = roleDB.get(principalCollection.getPrimaryPrincipal().toString());
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		roles.forEach(r -> authorizationInfo.addRole(r));
		return authorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
		if (!userDB.hasKey(usernamePasswordToken.getUsername())) {
			throw new AuthenticationException("用户不存在");
		}
		if (!userDB.get(usernamePasswordToken.getUsername()).equals(new String(usernamePasswordToken.getPassword()))) {
			throw new AuthenticationException("密码不正确");
		}
        return new SimpleAuthenticationInfo(usernamePasswordToken.getUsername(), usernamePasswordToken.getPassword(), getName());
	}

}
