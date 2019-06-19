package com.finedo.lyz.controller;

import com.finedo.lyz.model.User;
import com.finedo.lyz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/getUserById/{id}")
    @ResponseBody
    public ModelAndView getUserById(@PathVariable Integer id){
        User user = userService.selectByPrimaryKey(id);
        System.out.println(user);
        ModelAndView mv = new ModelAndView();
        mv.addObject("user",user);
        mv.setViewName("list");
        return mv;
    }

    public String getStr(List<String> strs){
        StringBuffer buf = new StringBuffer();
        if(!ObjectUtils.isEmpty(strs)){
            for(int i = 0 ; i < strs.size() ;i++){
                if(buf.length()>0){
                    buf.append(",");
                }
                buf.append(strs.get(i));
            }
        }
        return buf.toString();


    }


    @RequestMapping("/getView")
    public String testView(){

        return "view";
    }
}
