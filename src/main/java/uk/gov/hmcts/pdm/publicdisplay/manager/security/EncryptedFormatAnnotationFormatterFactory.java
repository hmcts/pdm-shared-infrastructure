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

package uk.gov.hmcts.pdm.publicdisplay.manager.security;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Formatter;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.util.HashSet;
import java.util.Set;


/**
 * Binds the EncryptedFormat annotation to the encrypted formatters.
 * 
 * @author uphillj
 *
 */
// CHECKSTYLE:OFF Turn off class name warning
public class EncryptedFormatAnnotationFormatterFactory
    implements AnnotationFormatterFactory<EncryptedFormat> { // CHECKSTYLE:ON


    /** The field types supported by the EncryptedFormat annotation. */
    private final Set<Class<?>> fieldTypes = new HashSet<>();

    /**
     * Default constructor.
     */
    public EncryptedFormatAnnotationFormatterFactory() {
        fieldTypes.add(Integer.class);
        fieldTypes.add(Long.class);
        fieldTypes.add(String.class);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.format.AnnotationFormatterFactory#getFieldTypes()
     */
    @Override
    public Set<Class<?>> getFieldTypes() {
        return fieldTypes;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.format.AnnotationFormatterFactory#getParser(java.lang.annotation.
     * Annotation, java.lang.Class)
     */
    @Override
    public Parser<?> getParser(final EncryptedFormat annotation, final Class<?> fieldType) {
        return getFormatter(fieldType);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.format.AnnotationFormatterFactory#getPrinter(java.lang.annotation.
     * Annotation, java.lang.Class)
     */
    @Override
    public Printer<?> getPrinter(final EncryptedFormat annotation, final Class<?> fieldType) {
        return getFormatter(fieldType);
    }

    /**
     * Gets the formatter for the field type.
     *
     * @param fieldType the field type
     * @return the formatter
     */
    private Formatter<?> getFormatter(final Class<?> fieldType) {
        if (Integer.class.isAssignableFrom(fieldType)) {
            return new EncryptedIntegerFormatter();
        } else if (Long.class.isAssignableFrom(fieldType)) {
            return new EncryptedLongFormatter();
        } else if (String.class.isAssignableFrom(fieldType)) {
            return new EncryptedStringFormatter();
        } else {
            throw new IllegalArgumentException("Invalid field type annotated for encryption");
        }
    }
}
