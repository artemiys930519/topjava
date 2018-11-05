package ru.javawebinar.topjava;

import org.springframework.test.context.ActiveProfilesResolver;

public class ActiveRepositoryProfileResolver implements ActiveProfilesResolver {
    @Override
    public String[] resolve(Class<?> aClass) {
        return new String[]{Profiles.REPOSITORY_IMPLEMENTATION};
    }
}
