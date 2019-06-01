package com.lzh.shiro;

import com.lzh.bean.User;
import com.lzh.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author lizhenhao
 */
public class CustomRealm  extends AuthorizingRealm {


    @Autowired
    private UserService userService;

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /*
    * 认证操作
    * */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        //获取用户名
        String username = (String) authenticationToken.getPrincipal();

        //根据用户名获取对象
        User user = userService.findUserByUsername(username);

        //

        if(user == null){
            return null;
        }

        //创建Info
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user.getUsername(),user.getPassword(),this.getName());


        //
        info.setCredentialsSalt(ByteSource.Util.bytes(user.getSalt()));


        return info;
    }
}
