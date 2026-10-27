import java.math.BigInteger;
import java.util.Locale;
import java.util.Scanner;

public class RadixCalculator {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("=== RADIX CALCULATOR ===");
        System.out.println("Use +, -, *, /, or =");
        System.out.println("You can continue a calculation after seeing an answer.");
        System.out.println("Division shows whole-number answers only.");

        boolean running = true;

        while (running) {
            System.out.println("\nStart a new calculation.");
            int firstBase = chooseBase(input);
            BigInteger answer = readNumber(input, firstBase);

            String expression = formatNumber(answer, firstBase);
            boolean calculating = true;

            while (calculating) {
                System.out.println("\nCurrent expression:");
                System.out.println(expression);

                System.out.print("Choose operation (+, -, *, /, =, or X to exit): ");
                String operation = input.nextLine().trim();

                if (operation.equalsIgnoreCase("X")) {
                    running = false;
                    calculating = false;

                } else if (operation.equals("=")) {
                    System.out.println("\n=== ANSWER ===");
                    showAllNumberSystems(answer);

                    System.out.print("\nContinue this answer? (Y/N): ");
                    String continueAnswer = input.nextLine().trim();

                    if (continueAnswer.equalsIgnoreCase("Y")) {
                        expression = formatNumber(answer, 10);
                    } else {
                        calculating = false;
                    }

                } else if (operation.equals("+") || operation.equals("-")
                        || operation.equals("*") || operation.equals("/")) {

                    int nextBase = chooseBase(input);
                    BigInteger nextNumber = readNumber(input, nextBase);

                    if (operation.equals("+")) {
                        answer = answer.add(nextNumber);

                    } else if (operation.equals("-")) {
                        answer = answer.subtract(nextNumber);

                    } else if (operation.equals("*")) {
                        answer = answer.multiply(nextNumber);

                    } else {
                        if (nextNumber.equals(BigInteger.ZERO)) {
                            System.out.println("You cannot divide by zero.");
                            continue;
                        }

                        answer = answer.divide(nextNumber);
                    }

                    expression = expression + " " + operation + " "
                            + formatNumber(nextNumber, nextBase);

                } else {
                    System.out.println("Invalid operation. Choose +, -, *, /, =, or X.");
                }
            }
        }

        System.out.println("\nThank you for using the Radix Calculator!");
        input.close();
    }

    public static int chooseBase(Scanner input) {
        while (true) {
            System.out.println("\nChoose number system:");
            System.out.println("1 - Binary");
            System.out.println("2 - Octal");
            System.out.println("3 - Decimal");
            System.out.println("4 - Hexadecimal");
            System.out.print("Choice: ");

            String choice = input.nextLine().trim();

            if (choice.equals("1")) {
                return 2;
            } else if (choice.equals("2")) {
                return 8;
            } else if (choice.equals("3")) {
                return 10;
            } else if (choice.equals("4")) {
                return 16;
            } else {
                System.out.println("Invalid choice. Please enter 1 to 4.");
            }
        }
    }

    public static BigInteger readNumber(Scanner input, int base) {
        while (true) {
            System.out.print("Enter a " + baseName(base) + " number: ");
            String number = input.nextLine().trim();

            try {
                return new BigInteger(number, base);
            } catch (NumberFormatException e) {
                System.out.println("Invalid " + baseName(base) + " number.");
            }
        }
    }

    public static void showAllNumberSystems(BigInteger number) {
        System.out.println("Binary: " + formatNumber(number, 2));
        System.out.println("Octal: " + formatNumber(number, 8));
        System.out.println("Decimal: " + number);
        System.out.println("Hexadecimal: " + formatNumber(number, 16));
    }

    public static String formatNumber(BigInteger number, int base) {
    String value = number.abs().toString(base).toUpperCase(Locale.ROOT);

    if (base == 10) {
        return number.toString();
    }

    String sign = number.signum() < 0 ? "-" : "";

    return sign + "(" + value + ")" + baseSubscript(base);
}

public static String baseSubscript(int base) {
    if (base == 2) {
        return "\u2082";
    } else if (base == 8) {
        return "\u2088";
    } else if (base == 16) {
        return "\u2081\u2086";
    }

    return "";
}

    public static String baseName(int base) {
        if (base == 2) {
            return "Binary";
        } else if (base == 8) {
            return "Octal";
        } else if (base == 10) {
            return "Decimal";
        } else {
            return "Hexadecimal";
        }
    }
}