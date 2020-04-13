package com.wjyxsy.service.impl;

import com.wjyxsy.dao.ResumeDao;
import com.wjyxsy.pojo.Resume;
import com.wjyxsy.service.IResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author 继洋
 */
@Service("resumeService")
public class ResumeServiceImpl implements IResumeService {

    @Autowired
    private ResumeDao resumeDao;

    @Override
    public List<Resume> queryAllResume() {
        return resumeDao.findAll();
    }

    @Override
    public Resume queryResumeById(Long id) {
        Optional<Resume> optionalResume = resumeDao.findById(id);
        return optionalResume.get();
    }

    @Override
    public void removeResumeById(Long id) {
        resumeDao.deleteById(id);
    }

    @Override
    public Resume createResume(Resume resume) {
        return resumeDao.save(resume);
    }
}
