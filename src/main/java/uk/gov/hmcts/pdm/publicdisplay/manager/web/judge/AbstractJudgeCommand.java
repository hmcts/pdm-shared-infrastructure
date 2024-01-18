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

package uk.gov.hmcts.pdm.publicdisplay.manager.web.judge;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

/**
 * The Class AbstractJudgeCommand.
 * 
 * @author toftn
 *
 */
public class AbstractJudgeCommand {

    /**
     * Maximum length for surname field.
     */
    private static final int SURNAME_MAX_LENGTH = 35;
    
    /**
     * Maximum length for first name field.
     */
    private static final int FIRST_NAME_MAX_LENGTH = 35;

    /**
     * Maximum length for middle name field.
     */
    private static final int MIDDLE_NAME_MAX_LENGTH = 35;

    /**
     * Maximum length for title field.
     */
    private static final int TITLE_MAX_LENGTH = 25;

    /**
     * Maximum length for full list title field.
     */
    private static final int FULL_LIST_TITLE_MAX_LENGTH = 80;
    
    /** The surname field. */
    @Length(max = SURNAME_MAX_LENGTH, message = "{judgeAmendCommand.surname.tooLong}")
    @NotBlank(message = "{judgeAmendCommand.surname.notBlank}")
    private String surname;
    
    /** The first name field. */
    @Length(max = FIRST_NAME_MAX_LENGTH, message = "{judgeAmendCommand.firstName.tooLong}")
    @NotBlank(message = "{judgeAmendCommand.firstName.notBlank}")
    private String firstName;

    /** The middle name field. */
    @Length(max = MIDDLE_NAME_MAX_LENGTH, message = "{judgeAmendCommand.middleName.tooLong}")
    private String middleName;

    /** The title field. */
    @Length(max = TITLE_MAX_LENGTH, message = "{judgeAmendCommand.title.tooLong}")
    @NotBlank(message = "{judgeAmendCommand.title.notBlank}")
    private String title;

    /** The full list title field. */
    @Length(max = FULL_LIST_TITLE_MAX_LENGTH, message = "{judgeAmendCommand.fullListTitle.tooLong}")
    @NotBlank(message = "{judgeAmendCommand.fullListTitle1.notBlank}")
    private String fullListTitle1;
    
    /** The judge type field. */
    @NotBlank(message = "{judgeAmendCommand.judgeType.notBlank}")
    private String judgeType;
    
    /**
     * Gets the surname.
     *
     * @return the surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the surname.
     *
     * @param surname the surname to set
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }
    
    /**
     * Gets the first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name.
     *
     * @param firstName the first name to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the middle name.
     *
     * @return the middle name
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * Sets the middle name.
     *
     * @param middleName the middle name to set
     */
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    /**
     * Gets the title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title.
     *
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the full list title.
     *
     * @return the full list title
     */
    public String getFullListTitle1() {
        return fullListTitle1;
    }

    /**
     * Sets the full list title.
     *
     * @param fullListTitle1 the full list title to set
     */
    public void setFullListTitle1(String fullListTitle1) {
        this.fullListTitle1 = fullListTitle1;
    }
    
    /**
     * Gets the judge type.
     *
     * @return the judge type
     */
    public String getJudgeType() {
        return judgeType;
    }

    /**
     * Sets the judge type.
     *
     * @param judgeType the judge type to set
     */
    public void setJudgeType(String judgeType) {
        this.judgeType = judgeType;
    }
}
