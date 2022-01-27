package PasswordManager.Generators.PassayGenerators;

import org.passay.CharacterData;

// created based on the passay password generation tutorial
public class SpecialCharacterData implements CharacterData {
    public static String ERROR_CODE = "ERRONEOUS_SPECIAL_CHARS";
    
    public String getErrorCode() {
        return ERROR_CODE;
    }

    /**
     * Set of charas used in generation
     */
    public String getCharacters() {
        return "!@#$%^&*()_+";
    }
}
