package be.kuleuven.candycrush.model;

import javafx.scene.paint.Color;

public enum SnoepjeEnum {

    TYPE_EEN(Color.LIGHTCORAL),
    TYPE_TWEE(Color.SANDYBROWN),
    TYPE_DRIE(Color.KHAKI),
    TYPE_VIEW(Color.MEDIUMAQUAMARINE),
    TYPE_VIJF(Color.STEELBLUE);

    public Color javafxColor;

    private SnoepjeEnum(Color javafxColor){
        this.javafxColor = javafxColor;
    }


}
