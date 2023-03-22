package ru.vkabanov.currencytelegrambot.service;

import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.vkabanov.currencytelegrambot.caching.CachedJaxbContextCreator;

import java.io.StringWriter;

import static java.nio.charset.StandardCharsets.UTF_8;

@Service
@RequiredArgsConstructor
public class XmlMessageService {

    private final CachedJaxbContextCreator cachedJaxbContextCreator;

    @NonNull
    public <T> String marshal(@Nullable T javaMessage) throws RuntimeException {
        if (javaMessage == null) {
            throw new RuntimeException("Java message is null");
        }

        try {
            final Marshaller marshaller = cachedJaxbContextCreator.getJaxbContext(javaMessage.getClass()).createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING, UTF_8.name());
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            StringWriter stringWriter = new StringWriter();
            marshaller.marshal(javaMessage, stringWriter);
            return stringWriter.toString();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @NonNull
    public <T> T unmarshal(@NonNull Class<T> clazz, @Nullable String xmlMessage) throws RuntimeException {
        if (!StringUtils.hasLength(xmlMessage)) {
            throw new RuntimeException("Xml message is null");
        }

        try {
            final Unmarshaller unmarshaller = cachedJaxbContextCreator.getJaxbContext(clazz).createUnmarshaller();
            //noinspection unchecked
            return (T) unmarshaller.unmarshal(IOUtils.toInputStream(xmlMessage, UTF_8.name()));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
