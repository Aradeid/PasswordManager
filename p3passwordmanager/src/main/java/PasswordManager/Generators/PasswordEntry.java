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

    /**
    * Default contructor. Might become depricated
    */
    public PasswordEntry() {}

    /**
    * Contruscts a new password entry with all rules as parameters
    *    
    * @param digitsRule should digits be included in password
    * @param upperCaseRule should upper case characters be included in password
    * @param lowerCaseRule should lower case characters be included in password
    * @param specialSymbolsRule should special sumbols be included in password
    * @param minCount minimal number of characters per type in password
    * @param maxLen maximum number of characters in password
    */
    public PasswordEntry(boolean digitsRule, boolean upperCaseRule, boolean lowerCaseRule, boolean specialSymbolsRule, int minCount, int maxLen) {
        this.canUseDigits = digitsRule;
        this.canUseUpperCase = upperCaseRule;
        this.canUseLowerCase = lowerCaseRule;
        this.canUseSpecialSymbols = specialSymbolsRule;
        this.minimumRequirementsPerSymbolType = minCount;
        this.maximumPasswordLength = maxLen;
    }
    /**
    * Contruscts a new password entry with all rules and value. Used to build database objects
    *    
    * @param digitsRule should digits be included in password
    * @param upperCaseRule should upper case characters be included in password
    * @param lowerCaseRule should lower case characters be included in password
    * @param specialSymbolsRule should special sumbols be included in password
    * @param minCount minimal number of characters per type in password
    * @param maxLen maximum number of characters in password
    * @param password vlaue of password
    */
    public PasswordEntry(boolean digitsRule, boolean upperCaseRule, boolean lowerCaseRule, boolean specialSymbolsRule, int minCount, int maxLen, String password) {
        this.canUseDigits = digitsRule;
        this.canUseUpperCase = upperCaseRule;
        this.canUseLowerCase = lowerCaseRule;
        this.canUseSpecialSymbols = specialSymbolsRule;
        this.minimumRequirementsPerSymbolType = minCount;
        this.maximumPasswordLength = maxLen;
        //TODO validate password quality on contructor
        this.password = password;
    }

    /**
    * Basic contructor, generates a new object with given password as value
    * for importing pre-existing passwords that may not follow rules
    *   
    * @param  password string to be saved for password
    */
    public PasswordEntry(String password) {
        //TODO validate password quality on contructor
        this.password = password;
    }

    /**
     * Returns value of password
     * 
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets value of password
     * 
     * @param pass
     */
    public void setPassword(String pass) {
        //TODO validate password quality on set
        this.previousPassword = this.password;
        this.password = pass;
    }

    /**
     * Returns value of digit rule
     * 
     * @return true if digit rule is active
     */
    public boolean getCanUseDigits() {
        return canUseDigits;
    }

    /**
     * Sets value of digit rule
     * 
     * @param rule value of rule
     */
    public void setCanUseDigits(boolean rule) {
        this.canUseDigits = rule;
    }

    /**
     * Returns value of uppercase rule
     * 
     * @return true if uppercase rule is active
     */
    public boolean getCanUseUpperCase() {
        return canUseUpperCase;
    }

    /**
     * Sets value of uppercase rule
     * 
     * @param rule value of uppercase rule
     */
    public void setCanUseUpperCase(boolean rule) {
        this.canUseUpperCase = rule;
    }

    /**
     * Returns value of lwercase rule rule
     * 
     * @return true if lowercase rule is active
     */
    public boolean getCanUseLowerCase() {
        return canUseLowerCase;
    }

    /**
     * Sets value of lowercase rule
     * 
     * @param rule value of lowercase rule
     */
    public void setCanUseLowerCase(boolean rule) {
        this.canUseLowerCase = rule;
    }

    /**
     * Returns value of special symbols rule
     * 
     * @return true if special symbols rule is active
     */
    public boolean getCanUseSpecialSymbols() {
        return canUseSpecialSymbols;
    }

    /**
     * Sets value of special symbols rule
     * 
     * @param rule value of special symbols rule
     */
    public void setCanUseSpecialSymbols(boolean rule) {
        this.canUseSpecialSymbols = rule;
    }

    /**
     * Returns minimum count per character type
     * 
     * @return count of each required character type
     */
    public int getMinimumRequirementsPerSymbolType() {
        return minimumRequirementsPerSymbolType;
    }

    /**
     * Sets value of minimum count per character type
     * Throws exception if parameter value is impossible 
     * 
     * @param count minimum number of characters per symbol type
     */
    public void setMinimumRequirementsPerSymbolType(int count) throws Exception {
        if (count <= 0) {
            this.minimumRequirementsPerSymbolType = count;
        } else {
            throw new Exception("Minimum requirement of symbol cannot be less than one");
        }
    }

    /**
     * Returns maximum password length
     * 
     * @return maximum number of symbols 
     */
    public int getMaximumPasswordLength() {
        return maximumPasswordLength;
    }

    /**
     * Sets maximum password length
     * 
     * @param count maximum length of password
     */
    public void setMaximumPasswordLength(int count) throws Exception {
        if (count < 0) {
            this.maximumPasswordLength = count;
        } else {
            throw new Exception("Maximum requirement for password length cannot be less than zero");
        }
    }

    /**
    * Generate passay password for a previously set maxLength and other rules set in methods
    *    
    * @return String password of maxLength
    */
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

    /**
    * Restores/recovers the previously set password
    *    
    * @return true if a previous password exists and has been restored, false if no previous password
    */
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

    /**
    * Returns a fully built PasswordEntry object with defauly settings
    *    
    * @return PasswordEntry object with default properties
    */
    public static PasswordEntry getNewEntry() {
        PasswordEntry entry = new PasswordEntry();
        entry.generatePassword();
        return entry;
    }

    /**
    * Converts object to String as password value
    *    
    * @return password value as string
    */
    @Override
    public String toString() {
        return getPassword();
    }
}
