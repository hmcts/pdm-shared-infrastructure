/*
 * Copyrights and Licenses
 * 
 * Copyright (c) 2015-2016 by the Ministry of Justice. All rights reserved. Redistribution and use
 * in source and binary forms, with or without modification, are permitted provided that the
 * following conditions are met: - Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer. - Redistributions in binary form
 * must reproduce the above copyright notice, this list of conditions and the following disclaimer
 * in the documentation and/or other materials provided with the distribution. - Products derived
 * from this software may not be called "XHIBIT Public Display Manager" nor may
 * "XHIBIT Public Display Manager" appear in their names without prior written permission of the
 * Ministry of Justice. - Redistributions of any form whatsoever must retain the following
 * acknowledgment: "This product includes XHIBIT Public Display Manager." This software is provided
 * "as is" and any expressed or implied warranties, including, but not limited to, the implied
 * warranties of merchantability and fitness for a particular purpose are disclaimed. In no event
 * shall the Ministry of Justice or its contributors be liable for any direct, indirect, incidental,
 * special, exemplary, or consequential damages (including, but not limited to, procurement of
 * substitute goods or services; loss of use, data, or profits; or business interruption). However
 * caused any on any theory of liability, whether in contract, strict liability, or tort (including
 * negligence or otherwise) arising in any way out of the use of this software, even if advised of
 * the possibility of such damage.
 */

package uk.gov.hmcts.pdm.publicdisplay.manager.dto;

import uk.gov.hmcts.pdm.publicdisplay.manager.security.EncryptedFormat;

import java.io.Serializable;

/**
 * Simple transfer object used to move judge details around.
 * 
 * @author toftn
 *
 */
@SuppressWarnings("PMD.TooManyFields")
public class RefJudgeDto implements Serializable {
    
    /**
     * Default serialVersionUID.
     */
    private static final long serialVersionUID = 1L;

    @EncryptedFormat
    private Integer refJudgeId;
    
    private String judgeType;
    
    @EncryptedFormat
    private Integer crestJudgeId;
    
    private String title;
    
    private String firstName;
    
    private String middleName;
    
    private String surname;
    
    private String fullListTitle1;
    
    private String fullListTitle2;
    
    private String fullListTitle3;
    
    private String statsCode;
    
    private String initials;
    
    private String honours;
    
    private String judVers;
    
    private String obsInd;
    
    private String sourceTable;
    
    private String judgeTypeDeCode;
    
    @EncryptedFormat
    private Integer courtId;

    public Integer getRefJudgeId() {
        return refJudgeId;
    }

    public void setRefJudgeId(Integer refJudgeId) {
        this.refJudgeId = refJudgeId;
    }

    public String getJudgeType() {
        return judgeType;
    }

    public void setJudgeType(String judgeType) {
        this.judgeType = judgeType;
    }

    public Integer getCrestJudgeId() {
        return crestJudgeId;
    }

    public void setCrestJudgeId(Integer crestJudgeId) {
        this.crestJudgeId = crestJudgeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFullListTitle1() {
        return fullListTitle1;
    }

    public void setFullListTitle1(String fullListTitle1) {
        this.fullListTitle1 = fullListTitle1;
    }

    public String getFullListTitle2() {
        return fullListTitle2;
    }

    public void setFullListTitle2(String fullListTitle2) {
        this.fullListTitle2 = fullListTitle2;
    }

    public String getFullListTitle3() {
        return fullListTitle3;
    }

    public void setFullListTitle3(String fullListTitle3) {
        this.fullListTitle3 = fullListTitle3;
    }

    public String getStatsCode() {
        return statsCode;
    }

    public void setStatsCode(String statsCode) {
        this.statsCode = statsCode;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getHonours() {
        return honours;
    }

    public void setHonours(String honours) {
        this.honours = honours;
    }

    public String getJudVers() {
        return judVers;
    }

    public void setJudVers(String judVers) {
        this.judVers = judVers;
    }

    public String getObsInd() {
        return obsInd;
    }

    public void setObsInd(String obsInd) {
        this.obsInd = obsInd;
    }

    public String getSourceTable() {
        return sourceTable;
    }

    public void setSourceTable(String sourceTable) {
        this.sourceTable = sourceTable;
    }

    public Integer getCourtId() {
        return courtId;
    }

    public void setCourtId(Integer courtId) {
        this.courtId = courtId;
    }

    public String getJudgeTypeDeCode() {
        return judgeTypeDeCode;
    }

    public void setJudgeTypeDeCode(String judgeTypeDeCode) {
        this.judgeTypeDeCode = judgeTypeDeCode;
    }

}
