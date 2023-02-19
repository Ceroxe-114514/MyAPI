package api.ceroxe.utils;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public final class RandomUtils {
    private Random randomInstance = new Random();

    public RandomUtils(long seed) {
        this.randomInstance = new Random(seed);
    }

    public RandomUtils() {
    }

    public static int getRInt(int biggerAndEqualsNum, int smallerAndEqualsNum) {
        return new Random().nextInt(biggerAndEqualsNum, smallerAndEqualsNum + 1);
    }

    public static double getRDouble(double biggerAndEqualsNum, double smallerAndEqualsNum) {
        double result = new Random().nextDouble(biggerAndEqualsNum, smallerAndEqualsNum + 1);
        return Math.min(result, smallerAndEqualsNum);
    }

    public static Object getRObject(Object... objects) {
        int index = new Random().nextInt(objects.length);
        return objects[index];
    }

    public static String getLCRLetters(int len) {
        CopyOnWriteArrayList<String> engLetters = new CopyOnWriteArrayList<>();
        // a-z
        for (int i = 0; i < 26; i++) {
            engLetters.add(String.valueOf((char) (97 + i)));
        }
        if (len < 2) {
            return engLetters.get(new Random().nextInt(engLetters.size()));
        } else {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < len; i++) {
                sb.append(engLetters.get(new Random().nextInt(engLetters.size())));
            }
            return sb.toString();
        }
    }

    public static String getUCRLetters(int len) {
        CopyOnWriteArrayList<String> engLetters = new CopyOnWriteArrayList<>();
        // A-Z
        for (int i = 0; i < 26; i++) {
            engLetters.add(String.valueOf((char) (65 + i)));
        }
        if (len < 2) {
            return engLetters.get(new Random().nextInt(engLetters.size()));
        } else {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < len; i++) {
                sb.append(engLetters.get(new Random().nextInt(engLetters.size())));
            }
            return sb.toString();
        }
    }

    public static String getRLetters(int len) {
        CopyOnWriteArrayList<String> engLetters = new CopyOnWriteArrayList<>();
        // A-Z
        for (int i = 0; i < 26; i++) {
            engLetters.add(String.valueOf((char) (65 + i)));
        }
        // a-z
        for (int i = 0; i < 26; i++) {
            engLetters.add(String.valueOf((char) (97 + i)));
        }
        if (len < 2) {
            return engLetters.get(new Random().nextInt(engLetters.size()));
        } else {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < len; i++) {
                sb.append(engLetters.get(new Random().nextInt(engLetters.size())));
            }
            return sb.toString();
        }
    }

    public void setRandomInstance(Random randomInstance) {
        this.randomInstance = Objects.requireNonNullElseGet(randomInstance, Random::new);
    }

    public int getRandomInt(int biggerAndEqualsNum, int smallerAndEqualsNum) {
        return randomInstance.nextInt(biggerAndEqualsNum, smallerAndEqualsNum + 1);
    }

    public double getRandomDouble(double biggerAndEqualsNum, double smallerAndEqualsNum) {
        double result = randomInstance.nextDouble(biggerAndEqualsNum, smallerAndEqualsNum + 1);
        return Math.min(result, smallerAndEqualsNum);
    }

    public boolean getRandomBoolean() {
        return this.randomInstance.nextBoolean();
    }

    public Object getRandomObject(Object... objects) {
        int index = this.randomInstance.nextInt(objects.length);
        return objects[index];
    }

    public String getLowercaseRandomLetters(int len) {
        CopyOnWriteArrayList<String> engLetters = new CopyOnWriteArrayList<>();
        // a-z
        for (int i = 0; i < 26; i++) {
            engLetters.add(String.valueOf((char) (97 + i)));
        }
        if (len < 2) {
            return engLetters.get(this.randomInstance.nextInt(engLetters.size()));
        } else {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < len; i++) {
                sb.append(engLetters.get(this.randomInstance.nextInt(engLetters.size())));
            }
            return sb.toString();
        }
    }

    public String getUppercaseRandomLetters(int len) {
        CopyOnWriteArrayList<String> engLetters = new CopyOnWriteArrayList<>();
        // A-Z
        for (int i = 0; i < 26; i++) {
            engLetters.add(String.valueOf((char) (65 + i)));
        }
        if (len < 2) {
            return engLetters.get(this.randomInstance.nextInt(engLetters.size()));
        } else {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < len; i++) {
                sb.append(engLetters.get(this.randomInstance.nextInt(engLetters.size())));
            }
            return sb.toString();
        }
    }

    public String getRandomLetters(int len) {
        CopyOnWriteArrayList<String> engLetters = new CopyOnWriteArrayList<>();
        // A-Z
        for (int i = 0; i < 26; i++) {
            engLetters.add(String.valueOf((char) (65 + i)));
        }
        // a-z
        for (int i = 0; i < 26; i++) {
            engLetters.add(String.valueOf((char) (97 + i)));
        }
        if (len < 2) {
            return engLetters.get(this.randomInstance.nextInt(engLetters.size()));
        } else {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < len; i++) {
                sb.append(engLetters.get(this.randomInstance.nextInt(engLetters.size())));
            }
            return sb.toString();
        }
    }
}
