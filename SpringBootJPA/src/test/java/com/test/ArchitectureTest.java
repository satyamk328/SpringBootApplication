package com.test;


import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import org.junit.BeforeClass;
import org.junit.Test;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchRule;

public abstract class ArchitectureTest {
	
    static final String DOMAIN_LAYER_PACKAGES = "com.satyam.model..";
    static final String SERVICE_LAYER_PACKAGES = "com.satyam.service..";
    static final String DAO_LAYER_CLASSES = "com.satyam.dao..";
    static final String WEB_LAYER_CLASSES = "com.satyam.controller..";
    static final String CONFIG_LAYER_PACKAGES = "com.satyam.config..";

    static JavaClasses classes;

    @BeforeClass
    public static void setUp() {
        classes = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_ARCHIVES)
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_JARS)
            .importPackages("com.satyam");
    }
    
    @Test
    public void archUnitTest() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("com.satyam");
        ArchRule rule = classes().should().bePublic();
        rule.check(importedClasses);
    }
    
}