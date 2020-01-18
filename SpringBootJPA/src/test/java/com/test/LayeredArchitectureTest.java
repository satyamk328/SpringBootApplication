package com.test;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.Architectures;
import org.junit.Test;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import javax.persistence.Entity;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

public class LayeredArchitectureTest extends ArchitectureTest {

	private static final String DOMAIN_LAYER = "model layer";
	private static final String APPLICATION_LAYER = "controller layer";
	private static final String ADAPTERS_LAYER = "service layer";

	private static Architectures.LayeredArchitecture portsAndAdaptersArchitecture = Architectures.layeredArchitecture()
			.layer(DOMAIN_LAYER).definedBy(DOMAIN_LAYER_PACKAGES).layer(APPLICATION_LAYER).definedBy(WEB_LAYER_CLASSES)
			.layer(ADAPTERS_LAYER).definedBy(SERVICE_LAYER_PACKAGES);

	@Test
	public void domainLayerShouldOnlyBeAccessedByApplicationLayer() {
		ArchRule rule = portsAndAdaptersArchitecture.whereLayer(DOMAIN_LAYER)
				.mayOnlyBeAccessedByLayers(APPLICATION_LAYER);
		rule.check(classes);
	}

	@Test
	public void applicationLayerMayOnlyBeAccessedByAdaptersLayer() {
		ArchRule rule = portsAndAdaptersArchitecture.whereLayer(APPLICATION_LAYER)
				.mayOnlyBeAccessedByLayers(ADAPTERS_LAYER);
		rule.check(classes);
	}

	@Test
	public void adaptersLayerShouldNotBeAccessedByAnyLayer() {
		ArchRule rule = portsAndAdaptersArchitecture.whereLayer(ADAPTERS_LAYER).mayNotBeAccessedByAnyLayer();
		rule.check(classes);
	}

	@Test
	public void implResidesInTheBackend_orIsLegacy() {
		JavaClasses importedClasses = new ClassFileImporter().importPackages("com.satyam");

		ArchRule rule = classes().that().haveNameMatching(".*Impl").should().resideInAPackage("..backend..").orShould()
				.beAnnotatedWith(Deprecated.class);

		rule.check(importedClasses);
	}

	@Test
	public void entities_reside_in_model_packages() {

		JavaClasses importedClasses = new ClassFileImporter().importPackages("com.satyam");

		ArchRule rule = classes().that().areAnnotatedWith(Entity.class).should().resideInAPackage("..model..");

		rule.check(importedClasses);
	}

	@Test
	public void repositories_are_only_used_by_services() {
		JavaClasses importedClasses = new ClassFileImporter().importPackages("com.satyam");
		// ArchRule rule = classes()
		// .that().haveSimpleNameContaining("Dao")
		// .should().onlyBeAccessed().byClassesThat().haveSimpleNameContaining("Service");

		ArchRule rule = classes().that().haveSimpleNameContaining("Dao").should().onlyHaveDependentClassesThat()
				.haveSimpleNameContaining("Service");

		rule.check(importedClasses);
	}

	@Test
	public void adhere_to_layered_architecture() {
		JavaClasses importedClasses = new ClassFileImporter().importPackages("com.satyam");
		ArchRule rule = layeredArchitecture().layer("Frontend").definedBy("..frontend..").layer("API")
				.definedBy("..api..").layer("Backend").definedBy("..backend..").whereLayer("Frontend")
				.mayNotBeAccessedByAnyLayer().whereLayer("API").mayOnlyBeAccessedByLayers("Frontend", "Backend")
				.whereLayer("Backend").mayNotBeAccessedByAnyLayer();

		rule.check(importedClasses);
	}

	@Test
	public void no_cycles_in_slices() {
		JavaClasses importedClasses = new ClassFileImporter().importPackages("com.satyam");
		ArchRule rule = slices().matching("com.satyam.dao.(*)..").should().beFreeOfCycles();

		rule.check(importedClasses);
	}

	@Test
	public void no_dependencies_between_slices() {
		JavaClasses importedClasses = new ClassFileImporter().importPackages("com.satyam");
		ArchRule rule = slices().matching("com.satyam.dao.(*)..").should().notDependOnEachOther();

		rule.check(importedClasses);
	}

}