package com.jasonli.homework.controller;

import com.jasonli.homework.entity.Auth;
import com.jasonli.homework.entity.ResponseResult;
import com.jasonli.homework.entity.Roles;
import com.jasonli.homework.entity.Users;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import static com.jasonli.homework.HomeworkApplication.*;

@RestController
public class AuthController {
    @PostMapping("api/v1/auth")
    public ResponseResult authenticate(@RequestBody Auth auth) throws NoSuchAlgorithmException {
        ResponseResult result = new ResponseResult();

        // Encrypt the userPassword using MD5
        MessageDigest digest = MessageDigest.getInstance("MD5");
        byte[] bytes = digest.digest(auth.getUserPassword().getBytes(StandardCharsets.UTF_8));
        String encryptStr = new BigInteger(1, bytes).toString(16);
        auth.setUserPassword(encryptStr);

        Auth targetAuth = null;
        for (var a:authList) {
            if(a.getUserName().equals(auth.getUserName()) && a.getUserPassword().equals(auth.getUserPassword())) {
                targetAuth = a;
                break;
            }
        }

        if(targetAuth != null) {
            result.setStatus("Success");
            result.setMsg("Your authKey is " + targetAuth.getAuthKey() + ", will be expired on " + targetAuth.getExpiredDateTime().toLocalDate() + " " +targetAuth.getExpiredDateTime().toLocalTime());
            ArrayList<String> strList = new ArrayList<String>();
            strList.add(targetAuth.getAuthKey());
            result.setData(strList);
        } else {
            result.setStatus("Fail");
            result.setMsg("Your authKey not existed");
        }

        return result;
    }

    @DeleteMapping("api/v1/auth/{authKey}")
    public ResponseResult invalidate(@PathVariable String authKey) {
        ResponseResult result = new ResponseResult();
        Auth targetAuth = null;
        int index = 0;

        for (var a:authList) {
            if(a.getAuthKey().equals(authKey)) {
                targetAuth = a;
                index = authList.indexOf(a);
                break;
            }
        }

        if(targetAuth != null) {
            authList.remove(index);
            result.setStatus("Success");
            result.setMsg("Your authKey " + authKey + " have been invalidated");
        } else {
            result.setStatus("Fail");
            result.setMsg("Your authKey " + authKey + " not found");
        }

        return result;
    }

    @GetMapping("api/v1/auth/{authKey}/{roleName}")
    public ResponseResult checkRole(@PathVariable String authKey,
                                    @PathVariable String roleName) {
        ResponseResult result = new ResponseResult();
        Auth targetAuth = null;
        Users targetUser = null;
        Roles targetRole = null;
        ArrayList<String> strList = new ArrayList<String>();

        for (var a:authList) {
            if(a.getAuthKey().equals(authKey)) {
                targetAuth = a;
                break;
            }
        }

        if(targetAuth != null) {
            for (var u:usersList) {
                if(u.getUserName().equals(targetAuth.getUserName())) {
                    targetUser = u;
                    break;
                }
            }

            if(targetUser != null) {
                ArrayList<Roles> targetuserRoles = targetUser.getUserRoles();

                if(targetuserRoles != null) {
                    for (var r:targetuserRoles) {
                        if(r.getRoleName().equals(roleName)) {
                            targetRole = r;
                            break;
                        }
                    }

                    if(targetRole != null) {
                        result.setStatus("Success");
                        result.setMsg("Your got role:" + roleName);
                        strList.add("True");
                        result.setData(strList);
                    } else {
                        result.setStatus("Fail");
                        result.setMsg("Your did not get role:" + roleName);
                        strList.add("False");
                        result.setData(strList);
                    }
                } else {
                    result.setStatus("Fail");
                    result.setMsg("Your did not get role:" + roleName);
                    strList.add("False");
                    result.setData(strList);
                }
            } else {
                result.setStatus("Fail");
                result.setMsg("No user match authKey " + authKey);
                strList.add("Error");
                result.setData(strList);
            }
        } else {
            result.setStatus("Fail");
            result.setMsg("Your authKey " + authKey + " not found");
            strList.add("Error");
            result.setData(strList);
        }

        return result;
    }

    @GetMapping("api/v1/auth/{authKey}")
    public ResponseResult allRoles(@PathVariable String authKey) {
        ResponseResult result = new ResponseResult();
        Auth targetAuth = null;
        Users targetUser = null;
        ArrayList<String> strList = new ArrayList<String>();

        for (var a:authList) {
            if(a.getAuthKey().equals(authKey)) {
                targetAuth = a;
                break;
            }
        }

        if(targetAuth != null) {
            for (var u:usersList) {
                if(u.getUserName().equals(targetAuth.getUserName())) {
                    targetUser = u;
                    break;
                }
            }

            if(targetUser != null) {
                ArrayList<Roles> targetuserRoles = targetUser.getUserRoles();

                if(targetuserRoles != null) {
                    result.setStatus("Success");
                    result.setMsg("Here are your all roles");
                    for (var r:targetuserRoles) {
                        strList.add(r.getRoleName());
                    }
                    result.setData(strList);
                } else {
                    result.setStatus("Fail");
                    result.setMsg("Your don't have any roles");
                }
            } else {
                result.setStatus("Fail");
                result.setMsg("No user match authKey " + authKey);
                strList.add("Error");
                result.setData(strList);
            }
        } else {
            result.setStatus("Fail");
            result.setMsg("Your authKey " + authKey + " not found");
            strList.add("Error");
            result.setData(strList);
        }

        return result;
    }
}
