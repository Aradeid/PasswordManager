package PasswordManager.Generators;

import java.io.Serializable;
import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;

import PasswordManager.Generators.PassayGenerators.SpecialCharacterData;

public class PasswordEntry implements Serializable {
    private String password;
    private String previousPassword;
    private boolean canUseDigits = true;
    private boolean canUseUpperCase = true;
    private boolean canUseLowerCase = true;
    private boolean canUseSpecialSymbols = true;
    private int minimumRequirementsPerSymbolType = 2;
    private int maximumPasswordLength = 12;

    public PasswordEntry() {}

    //Constructor used to generate new passwords
    public PasswordEntry(boolean digitsRule, boolean upperCaseRule, boolean lowerCaseRule, boolean specialSymbolsRule, int minCount, int maxLen) {
        this.canUseDigits = digitsRule;
        this.canUseUpperCase = upperCaseRule;
        this.canUseLowerCase = lowerCaseRule;
        this.canUseSpecialSymbols = specialSymbolsRule;
        this.minimumRequirementsPerSymbolType = minCount;
        this.maximumPasswordLength = maxLen;
    }
    //Constructor used to build existing password objects from file or database
    public PasswordEntry(boolean digitsRule, boolean upperCaseRule, boolean lowerCaseRule, boolean specialSymbolsRule, int minCount, int maxLen, String password) {
        this.canUseDigits = digitsRule;
        this.canUseUpperCase = upperCaseRule;
        this.canUseLowerCase = lowerCaseRule;
        this.canUseSpecialSymbols = specialSymbolsRule;
        this.minimumRequirementsPerSymbolType = minCount;
        this.maximumPasswordLength = maxLen;
        this.password = password;
    }

    //for importing existing passwords that may not follow rules
    public PasswordEntry(String password) {
        //TODO validate password quality on contructor
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String pass) {
        //TODO validate password quality on set
        this.previousPassword = this.password;
        this.password = pass;
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
    public void setMinimumRequirementsPerSymbolType(int count) throws Exception {
        if (count <= 0) {
            this.minimumRequirementsPerSymbolType = count;
        } else {
            throw new Exception("Minimum requirement of symbol cannot be less than one");
        }
    }

    public int getMaximumPasswordLength() {
        return maximumPasswordLength;
    }
    public void setMaximumPasswordLength(int count) throws Exception {
        if (count < 0) {
            this.maximumPasswordLength = count;
        } else {
            throw new Exception("Maximum requirement for password length cannot be less than zero");
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

        CharacterData specialChars = new SpecialCharacterData();
        CharacterRule splCharRule = new CharacterRule(specialChars);
        splCharRule.setNumberOfCharacters(
            getCanUseSpecialSymbols() ? minimumRequirementsPerSymbolType : 0
        );

        //TODO test if combined length is greater than max length
        String password = gen.generatePassword(maximumPasswordLength, splCharRule, lowerCaseRule, 
        upperCaseRule, digitRule);

        this.previousPassword = this.password;
        this.password = password;
        return password;
    }

    public boolean restorePassword() {
        if (this.previousPassword.isEmpty()) {
            //existing password is saved, in case call was an accident
            String temp;
            temp = this.password;
            this.password = this.previousPassword;
            this.previousPassword = temp;

            return true;
        }
        return false;
    }

    public static PasswordEntry getNewEntry() {
        PasswordEntry entry = new PasswordEntry();
        entry.generatePassword();
        return entry;
    }
}
