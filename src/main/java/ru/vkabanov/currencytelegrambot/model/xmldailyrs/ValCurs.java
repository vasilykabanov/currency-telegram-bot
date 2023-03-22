package ru.vkabanov.currencytelegrambot.model.xmldailyrs;

import jakarta.xml.bind.annotation.*;
import lombok.Data;

import java.util.List;

@Data
@XmlRootElement(name = "ValCurs")
@XmlAccessorType(XmlAccessType.FIELD)
public class ValCurs {

    @XmlElement(name = "Valute")
    protected List<Valute> valute;

    @XmlAttribute(name = "Date", required = true)
    protected String date;

    @XmlAttribute(name = "name", required = true)
    protected String name;
}
