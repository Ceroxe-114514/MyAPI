package api.ceroxe.security.Password;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class PasswordGeneratorUtil {
    //密码能包含的特殊字符
    public static final char[] allowedSpecialCharactors = {
            '`', '~', '@', '#', '$', '%', '^', '&',
            '*', '(', ')', '-', '_', '=', '+', '[',
            '{', '}', ']', '\\', '|', ';', ':', '"',
            '\'', ',', '<', '.', '>', '/', '?'};

    private static final int letterRange = 26;

    private static final int numberRange = 10;

    private static final int spCharactorRange = allowedSpecialCharactors.length;

    private static final Random random = new Random();

    /**
     * 密码的长度
     */
    private final int passwordLength;

    /**
     * 密码包含字符的最少种类
     */
    private final int minVariousType;

    /**
     * <构造函数>
     *
     * @param passwordLength 密码的长度
     * @param minVariousType 密码包含字符的最少种类
     */
    public PasswordGeneratorUtil(int passwordLength, int minVariousType) {
        if (minVariousType > CharactorType.values().length)
            minVariousType = CharactorType.values().length;
        if (minVariousType > passwordLength)
            minVariousType = passwordLength;
        this.passwordLength = passwordLength;
        this.minVariousType = minVariousType;
    }

    public static void main(String[] args) {
        System.out.println(new PasswordGeneratorUtil(8, 3).generateRandomPassword());
    }

    /**
     * <生成随机密码>
     *
     * @return 密码
     * @throws
     */
    public String generateRandomPassword() {
        char[] password = new char[passwordLength];
        List<Integer> pwCharsIndex = new ArrayList();
        for (int i = 0; i < password.length; i++) {
            pwCharsIndex.add(i);
        }
        List<CharactorType> takeTypes = new ArrayList(Arrays.asList(CharactorType.values()));
        List<CharactorType> fixedTypes = Arrays.asList(CharactorType.values());
        int typeCount = 0;
        while (pwCharsIndex.size() > 0) {
            int pwIndex = pwCharsIndex.remove(random.nextInt(pwCharsIndex.size()));//随机填充一位密码
            Character c;
            if (typeCount < minVariousType) {//生成不同种类字符
                c = generateCharacter(takeTypes.remove(random.nextInt(takeTypes.size())));
                typeCount++;
            } else {//随机生成所有种类密码
                c = generateCharacter(fixedTypes.get(random.nextInt(fixedTypes.size())));
            }
            password[pwIndex] = c.charValue();
        }
        return String.valueOf(password);
    }

    /**
     * <获取字符>
     *
     * @param type 字符类型
     * @return 字符
     * @throws
     */
    private Character generateCharacter(CharactorType type) {
        Character c = null;
        int rand;
        switch (type) {
            case LOWERCASE://随机小写字母
                rand = random.nextInt(letterRange);
                rand += 97;
                c = (char) rand;
                break;
            case UPPERCASE://随机大写字母
                rand = random.nextInt(letterRange);
                rand += 65;
                c = (char) rand;
                break;
            case NUMBER://随机数字
                rand = random.nextInt(numberRange);
                rand += 48;
                c = (char) rand;
                break;
            case SPECIAL_CHARACTOR://随机特殊字符
                rand = random.nextInt(spCharactorRange);
                c = allowedSpecialCharactors[rand];
                break;
        }
        return c;
    }
}

