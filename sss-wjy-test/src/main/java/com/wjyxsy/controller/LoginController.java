package com.wjyxsy.controller;

import com.wjyxsy.pojo.SsoUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 继洋
 */
@Controller
@RequestMapping(value = "/login")
public class LoginController {

    @RequestMapping(value = "/loginIndex")
    public String loginIndex() {
        return "login";
    }

    @ResponseBody
    @RequestMapping(value = "/handleLogin")
    public SsoUser handleLogin(
            @RequestParam(value = "username") String username,
            @RequestParam(value = "password") String password,
            HttpServletRequest request
    ) {
        if ("admin".equals(username) && "admin".equals(password)) {
            // 通过
            SsoUser ssoUser = new SsoUser();
            ssoUser.setUsername(username);
            request.getSession().setAttribute("ssoUser", ssoUser);
            return ssoUser;
        }
        return null;
    }
}
