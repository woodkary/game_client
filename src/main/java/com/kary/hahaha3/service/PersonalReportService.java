package com.kary.hahaha3.service;

import com.kary.hahaha3.exceptions.errorInput.MatchTypeErrorException;
import com.kary.hahaha3.exceptions.errorInput.UsernameErrorException;
import com.kary.hahaha3.pojo.vo.PersonalReport;

/**
 * @author:123
 */
public interface PersonalReportService {
    PersonalReport getPersonalReport(String username,int type) throws MatchTypeErrorException, UsernameErrorException;
}
