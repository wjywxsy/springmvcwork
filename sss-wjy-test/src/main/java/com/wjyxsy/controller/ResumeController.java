package com.wjyxsy.controller;

import com.wjyxsy.pojo.Resume;
import com.wjyxsy.service.IResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author 继洋
 */
@Controller
@RequestMapping(value = "/resume")
public class ResumeController {

    @Autowired
    @Qualifier("resumeService")
    private IResumeService resumeService;

    @RequestMapping(value = "/list")
    public ModelAndView list(
            @RequestParam(value = "id", required = false) Long id,
            @RequestParam(value = "name", required = false) String name
    ) {
        ModelAndView modelAndView = new ModelAndView("resumeList");
        modelAndView.addObject("resumeList", resumeService.queryAllResume());
        return modelAndView;
    }

    @RequestMapping(value = "/create")
    public ModelAndView create(
            @RequestParam(value = "id", required = false) Long id
    ) {
        ModelAndView modelAndView = new ModelAndView("resumeCreate");
        modelAndView.addObject("resume", null);
        if (id != null) {
            modelAndView.addObject("resume", resumeService.queryResumeById(id));
        }
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping(value = "/remove")
    public Resume remove(@RequestParam(value = "id") Long id) {
        Resume resume = resumeService.queryResumeById(id);
        if (resume != null) {
            resumeService.removeResumeById(id);
        }
        return resume;
    }

    @ResponseBody
    @RequestMapping(value = "/doCreate")
    public Resume doCreate(Resume resume) {

        return resumeService.createResume(resume);
    }
}
