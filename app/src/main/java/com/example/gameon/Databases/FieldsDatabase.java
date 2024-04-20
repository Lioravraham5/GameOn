package com.example.gameon.Databases;

import com.example.gameon.Models.Address;
import com.example.gameon.Models.Field;

import java.util.ArrayList;
import java.util.Arrays;

public class FieldsDatabase {

    private static FieldsDatabase fieldsDatabaseInstance;

    private ArrayList<Field> allFields = new ArrayList<> (Arrays.asList(
            new Field()
                    .setFieldId("1")
                    .setFieldName("Omer middile school field")
                    .setFieldType(Field.FieldType.general)
                    .setAddress( new Address()
                            .setCountry("Israel")
                            .setCity("Ramat Hasharon")
                            .setStreet("Yigal Alon")
                            .setLatitude(32.13028220268648)
                            .setLongitude(34.858870963803426)
                    ),

            new Field()
                    .setFieldId("2")
                    .setFieldName("small Synthetic Morasha")
                    .setFieldType(Field.FieldType.soccer)
                    .setAddress( new Address()
                            .setCountry("Israel")
                            .setCity("Ramat Hasharon")
                            .setStreet("Dudu Dotan road 2")
                            .setLatitude(32.12933025032668)
                            .setLongitude(34.862715528311426)
                    ),

            new Field()
                    .setFieldId("3")
                    .setFieldName("soccer field Herzelia sportek")
                    .setFieldType(Field.FieldType.soccer)
                    .setAddress( new Address()
                            .setCountry("Israel")
                            .setCity("Herzelia")
                            .setStreet("Jabotinsky")
                            .setLatitude(32.17070146912905)
                            .setLongitude(34.82767167476159)
                    ),

            new Field()
                    .setFieldId("4")
                    .setFieldName("basketball field Herzelia sportek")
                    .setFieldType(Field.FieldType.basketball)
                    .setAddress( new Address()
                            .setCountry("Israel")
                            .setCity("Herzelia")
                            .setStreet("Jabotinsky")
                            .setLatitude(32.170038510654905)
                            .setLongitude(34.82669535069459)
                    ),

            new Field()
                    .setFieldId("5")
                    .setFieldName("Ramat Hasharon Tennis Center")
                    .setFieldType(Field.FieldType.tennis)
                    .setAddress( new Address()
                        .setCountry("Israel")
                        .setCity("Herzelia")
                        .setStreet("derech Hatennis 6")
                        .setLatitude(32.170038510654905)
                        .setLongitude(34.82669535069459)
                     ),

            new Field()
                    .setFieldId("6")
                    .setFieldName("soccer field Tel Aviv sportek")
                    .setFieldType(Field.FieldType.soccer)
                    .setAddress( new Address()
                            .setCountry("Israel")
                            .setCity("Tel Aviv yafo")
                            .setStreet("Sderot Rokah 40")
                            .setLatitude(32.09864698220323)
                            .setLongitude(34.78885481709377)
                    ),

            new Field()
                    .setFieldId("7")
                    .setFieldName("basketball field Tel Aviv sportek")
                    .setFieldType(Field.FieldType.basketball)
                    .setAddress( new Address()
                            .setCountry("Israel")
                            .setCity("Tel Aviv yafo")
                            .setStreet("Sderot Rokah 40")
                            .setLatitude(32.09849659233342)
                            .setLongitude(34.79007237270037)
                    ),

            new Field()
                    .setFieldId("8")
                    .setFieldName("tennis field Tel Aviv sportek")
                    .setFieldType(Field.FieldType.tennis)
                    .setAddress( new Address()
                            .setCountry("Israel")
                            .setCity("Tel Aviv yafo")
                            .setStreet("Sderot Rokah 40")
                            .setLatitude(32.098514769894905)
                            .setLongitude(34.79007237270037)
                    ),

            new Field()
                    .setFieldId("9")
                    .setFieldName("basketball central Netanya")
                    .setFieldType(Field.FieldType.basketball)
                    .setAddress( new Address()
                            .setCountry("Israel")
                            .setCity("Netanya")
                            .setStreet("Sderot Tom Lentos")
                            .setLatitude(32.30491686193757)
                            .setLongitude(34.86925501971638)
                    ),

            new Field()
                    .setFieldId("10")
                    .setFieldName("Hamarakana")
                    .setFieldType(Field.FieldType.soccer)
                    .setAddress( new Address()
                            .setCountry("Israel")
                            .setCity("Haifa")
                            .setStreet("Kadoshi Yassi, 2")
                            .setLatitude(32.79143606602666)
                            .setLongitude(34.966114862744064)
                    ),

            new Field()
                    .setFieldId("11")
                    .setFieldName("Sami Ofer")
                    .setFieldType(Field.FieldType.soccer)
                    .setAddress( new Address()
                            .setCountry("Israel")
                            .setCity("Haifa")
                            .setStreet("Pinchas and Abraham Rotenberg, 2")
                            .setLatitude(32.784509415334604)
                            .setLongitude(34.96503386020351)
                    ),

            new Field()
                    .setFieldId("12")
                    .setFieldName("Naot Lon tennis courts")
                    .setFieldType(Field.FieldType.tennis)
                    .setAddress( new Address()
                            .setCountry("Israel")
                            .setCity("Beer Sheva")
                            .setStreet("Jabotinsky")
                            .setLatitude(31.246450624992683)
                            .setLongitude(34.763092712162596)
                    ),

            new Field()
                    .setFieldId("13")
                    .setFieldName("Athletics Stadium")
                    .setFieldType(Field.FieldType.general)
                    .setAddress( new Address()
                            .setCountry("Israel")
                            .setCity("Rishon Lezion")
                            .setStreet("Rishon Lezion central park")
                            .setLatitude(32.170038510654905)
                            .setLongitude(34.82669535069459)
                    ),

            new Field()
                    .setFieldId("14")
                    .setFieldName("Football Field Binyamina")
                    .setFieldType(Field.FieldType.soccer)
                    .setAddress( new Address()
                            .setCountry("Israel")
                            .setCity("Binyamina")
                            .setStreet("Derech haatzmaut, 34")
                            .setLatitude(32.52216196166568)
                            .setLongitude(34.93904353294733)
                    ),

            new Field()
                    .setFieldId("15")
                    .setFieldName("Bloomfield Stadium")
                    .setFieldType(Field.FieldType.soccer)
                    .setAddress( new Address()
                            .setCountry("Israel")
                            .setCity("Tel Aviv yafo")
                            .setStreet("Hatkuma")
                            .setLatitude(32.051860247792455)
                            .setLongitude(34.761570554116695)
                    ),

            new Field()
                    .setFieldId("16")
                    .setFieldName("Golda Sports Hall Petah Tikva")
                    .setFieldType(Field.FieldType.tennis)
                    .setAddress( new Address()
                            .setCountry("Israel")
                            .setCity("Petah Tikva")
                            .setStreet("Rishon Lezion, 8")
                            .setLatitude(32.10232356400419)
                            .setLongitude(34.87704000013317)
                    )
    ));

    private FieldsDatabase(){

    }

    public static FieldsDatabase getFieldsDatabase(){
        if(fieldsDatabaseInstance == null){
            fieldsDatabaseInstance = new FieldsDatabase();
        }
        return fieldsDatabaseInstance;
    }

    public ArrayList<Field> getAllFields() {
        return allFields;
    }
}
