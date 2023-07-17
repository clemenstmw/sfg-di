package de.maibornwolff.sfgdi.config;

import de.maibornwolff.sfgdi.datasource.FakeDataSource;
import de.maibornwolff.sfgdi.repository.EnglishGreetingRepository;
import de.maibornwolff.sfgdi.repository.EnglishGreetingRepositoryImpl;
import de.maibornwolff.sfgdi.services.ConstructorGreetingService;
import de.maibornwolff.sfgdi.services.I18nEnglishGreetingService;
import de.maibornwolff.sfgdi.services.I18nSpanishGreetingService;
import de.maibornwolff.sfgdi.services.PrimaryGreetingService;
import de.maibornwolff.sfgdi.services.PropertyGreetingService;
import de.maibornwolff.sfgdi.services.SetterGreetingService;
import de.maibornwolff.sfgdi.services.pets.PetService;
import de.maibornwolff.sfgdi.services.pets.PetServiceFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@ImportResource("classpath:sfgdi-config.xml")
@Configuration
public class GreetingServiceConfiguration {

    @Bean
    FakeDataSource fakeDataSource(SfgConfiguration configuration) {
        FakeDataSource fakeDataSource = new FakeDataSource();
        fakeDataSource.setUsername(configuration.getUsername());
        fakeDataSource.setPassword(configuration.getPassword());
        fakeDataSource.setJdbcurl(configuration.getJdbcurl());
        return fakeDataSource;
    }

    @Bean
    PetServiceFactory petServiceFactory() {
        return new PetServiceFactory();
    }

    @Profile({"dog", "default"})
    @Bean
    PetService dogPetService(PetServiceFactory petServiceFactory) {
        return petServiceFactory.getPetService("dog");
    }

    @Profile("cat")
    @Bean
    PetService catPetService(PetServiceFactory petServiceFactory) {
        return petServiceFactory.getPetService("cat");
    }

    @Profile({"ES", "default"})
    @Bean("i18nService")
    I18nSpanishGreetingService i18nSpanishGreetingService() {
        return new I18nSpanishGreetingService();
    }

    @Bean
    EnglishGreetingRepository englishGreetingRepository() {
        return new EnglishGreetingRepositoryImpl();
    }

    @Profile("EN")
    @Bean
    I18nEnglishGreetingService i18nService(EnglishGreetingRepository englishGreetingRepository) {
        return new I18nEnglishGreetingService(englishGreetingRepository);
    }

    @Primary
    @Bean
    PrimaryGreetingService primaryGreetingService() {
        return new PrimaryGreetingService();
    }

    ConstructorGreetingService constructorGreetingService() {
        return new ConstructorGreetingService();
    }

    @Bean
    PropertyGreetingService propertyGreetingService() {
        return new PropertyGreetingService();
    }

    @Bean
    SetterGreetingService setterGreetingService() {
        return new SetterGreetingService();
    }
}
