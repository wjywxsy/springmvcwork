package com.wjyxsy.service;

import com.wjyxsy.pojo.Resume;

import java.util.List;

/**
 * @author 继洋
 */
public interface IResumeService {

    List<Resume> queryAllResume();

    Resume queryResumeById(Long id);

    void removeResumeById(Long id);

    Resume createResume(Resume resume);
}
