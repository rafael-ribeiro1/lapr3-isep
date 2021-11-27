package lapr.project.utils;

/**
 * Algorithm to generate random password with specified length
 */
public class PasswordGenerator {
    /**
     * Upper case letters
     */
    final private static String LETTERS_UPPER_CASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    /**
     * Lower case letters
     */
    final private static String LETTERS_LOWER_CASE = LETTERS_UPPER_CASE.toLowerCase();

    /**
     * Construtor para impedir a instanciação da classe.
     */
    private PasswordGenerator(){

    }
    /**
     * Generates and returns a random password with the specified length
     * @param length size of the password
     * @return the random password
     */
    public static String generatePassword(int length){
        String password ="";
        for(int x=0; x<length;x++ ){
            int p = generateRandomInt(1,3);
            switch(p){
                case 1 :
                    password+= LETTERS_UPPER_CASE.charAt(generateRandomInt(0, LETTERS_LOWER_CASE.length()-1));
                    break;
                case 2:
                    password+= LETTERS_LOWER_CASE.charAt(generateRandomInt(0, LETTERS_LOWER_CASE.length()-1));
                    break;
                case 3 :
                    password+=generateRandomInt(0,9);
                    break;
                default:
                    break;
            }
        }
        return password;
    }

    /**
     * Generates a random integer number between two numbers
     * @param min minimum value to random number
     * @param max maximum value to random number
     * @return the randomized integer
     */
    private static int generateRandomInt(int min, int max){
        return (int)(Math.random() * ((max - min) + 1)) + min;
    }

}
