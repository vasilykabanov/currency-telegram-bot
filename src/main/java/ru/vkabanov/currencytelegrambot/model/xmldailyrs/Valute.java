package ru.vkabanov.currencytelegrambot.model.xmldailyrs;

import jakarta.xml.bind.annotation.*;
import lombok.Data;

@Data
@XmlRootElement(name = "Valute")
@XmlAccessorType(XmlAccessType.FIELD)
//@XmlType(name = "", propOrder = {"numCode", "charCode", "nominal", "name", "value"})
public class Valute {

    @XmlElement(name = "NumCode")
    @XmlSchemaType(name = "unsignedShort")
    protected int numCode;

    @XmlElement(name = "CharCode", required = true)
    protected String charCode;

    @XmlElement(name = "Nominal")
    @XmlSchemaType(name = "unsignedInt")
    protected long nominal;

    @XmlElement(name = "Name", required = true)
    protected String name;

    @XmlElement(name = "Value", required = true)
    protected String value;

    @XmlAttribute(name = "ID", required = true)
    protected String id;
}