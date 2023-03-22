package ru.vkabanov.currencytelegrambot.caching;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
public class CachedJaxbContextCreator {

    private final LoadingCache<Class<?>, JAXBContext> jaxbContextCache;

    public CachedJaxbContextCreator() {
        jaxbContextCache = CacheBuilder.newBuilder()
                .build(new CacheLoader<>() {
                    @Override
                    public JAXBContext load(@NonNull Class clazz) throws JAXBException {
                        return JAXBContext.newInstance(clazz);
                    }
                });
    }

    @NonNull
    public JAXBContext getJaxbContext(@NonNull Class<?> clazz) throws ExecutionException {
        return jaxbContextCache.get(clazz);
    }
}
