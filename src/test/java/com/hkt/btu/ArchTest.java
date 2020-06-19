package com.hkt.btu;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.hkt.btu");

        noClasses()
            .that()
                .resideInAnyPackage("com.hkt.btu.service..")
            .or()
                .resideInAnyPackage("com.hkt.btu.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..com.hkt.btu.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
