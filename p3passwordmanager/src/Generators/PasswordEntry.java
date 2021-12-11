package p3passwordmanager.Generators;

import java.io.Serializable;
import org.passay.CharacterData;

public class PasswordEntry implements Serializable {
    private String password;
    private String previousPassword;
    private boolean canUseDigits = true;
    private boolean canUseUpperCase = true;
    private boolean canUseLowerCase = true;
    private boolean canUseSpecialSymbols = true;
    private int minimumRequirementsPerSymbolType = 2;

    public PasswordEntry() {}

    //Constructor used to generate new passwords
    public PasswordEntry(boolean digitsRule, boolean upperCaseRule, boolean lowerCaseRule, boolean specialSymbolsRule, int minCount) {
        this.canUseDigits = digitsRule;
        this.canUseUpperCase = upperCaseRule;
        this.canUseLowerCase = lowerCaseRule;
        this.canUseSpecialSymbols = specialSymbolsRule;
        this.minimumRequirementsPerSymbolType = minCount;
    }
    //Constructor used to build existing password objects from file or database
    public PasswordEntry(boolean digitsRule, boolean upperCaseRule, boolean lowerCaseRule, boolean specialSymbolsRule, int minCount, String password) {
        this.canUseDigits = digitsRule;
        this.canUseUpperCase = upperCaseRule;
        this.canUseLowerCase = lowerCaseRule;
        this.canUseSpecialSymbols = specialSymbolsRule;
        this.minimumRequirementsPerSymbolType = minCount;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public boolean getCanUseDigits() {
        return canUseDigits;
    }
    public void setCanUseDigits(boolean rule) {
        this.canUseDigits = rule;
    }

    public boolean getCanUseUpperCase() {
        return canUseUpperCase;
    }
    public void setCanUseUpperCase(boolean rule) {
        this.canUseUpperCase = rule;
    }

    public boolean getCanUseLowerCase() {
        return canUseLowerCase;
    }
    public void setCanUseLowerCase(boolean rule) {
        this.canUseLowerCase = rule;
    }

    public boolean getCanUseSpecialSymbols() {
        return canUseSpecialSymbols;
    }
    public void setCanUseSpecialSymbols(boolean rule) {
        this.canUseSpecialSymbols = rule;
    }

    public int getMinimumRequirementsPerSymbolType() {
        return minimumRequirementsPerSymbolType;
    }
    public void setMinimumRequirementsPerSymbolType(int count) {
        if (count <= 0) {
            this.minimumRequirementsPerSymbolType = count;
        } else {
            throw new Exception("Minimum requirement of symbol cannot be less than one");
        }
    }

    public String generatePassword() {
        PasswordGenerator gen = new PasswordGenerator();

        CharacterData lowerCaseChars = EnglishCharacterData.LowerCase;
        CharacterRule lowerCaseRule = new CharacterRule(lowerCaseChars);
        lowerCaseRule.setNumberOfCharacters(
            getCanUseLowerCase() ? minimumRequirementsPerSymbolType : 0
        );

        CharacterData upperCaseChars = EnglishCharacterData.UpperCase;
        CharacterRule upperCaseRule = new CharacterRule(upperCaseChars);
        upperCaseRule.setNumberOfCharacters(
            getCanUseUpperCase() ? minimumRequirementsPerSymbolType : 0
        );

        CharacterData digitChars = EnglishCharacterData.Digit;
        CharacterRule digitRule = new CharacterRule(digitChars);
        digitRule.setNumberOfCharacters(
            getCanUseDigits() ? minimumRequirementsPerSymbolType : 0
        );

        CharacterData specialChars = new CharacterData() {
            public String getErrorCode() {
                return ERROR_CODE;
            }

            public String getCharacters() {
                return "!@#$%^&*()_+";
            }
        };

        CharacterRule splCharRule = new CharacterRule(specialChars);
        splCharRule.setNumberOfCharacters(
            getCanUseSpecialSymbols() ? minimumRequirementsPerSymbolType : 0
        );

        String password = gen.generatePassword(12, splCharRule, lowerCaseRule, 
        upperCaseRule, digitRule);

        this.previousPassword = this.password;
        this.password = password;
        return password;
    }

    public boolean restorePassword() {
        if (previousPassword) {
            this.password = previousPassword;
            return true;
        }
        return false;
    }
}
