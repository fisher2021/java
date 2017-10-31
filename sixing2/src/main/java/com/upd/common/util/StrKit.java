package com.upd.common.util;

import java.util.UUID;

public class StrKit {
    public StrKit() {
    }

    public static String firstCharToLowerCase(String str) {
        char firstChar = str.charAt(0);
        if(firstChar >= 65 && firstChar <= 90) {
            char[] arr = str.toCharArray();
            arr[0] = (char)(arr[0] + 32);
            return new String(arr);
        } else {
            return str;
        }
    }

    public static String firstCharToUpperCase(String str) {
        char firstChar = str.charAt(0);
        if(firstChar >= 97 && firstChar <= 122) {
            char[] arr = str.toCharArray();
            arr[0] = (char)(arr[0] - 32);
            return new String(arr);
        } else {
            return str;
        }
    }

    public static boolean isBlank(String str) {
        if(str == null) {
            return true;
        } else {
            int len = str.length();
            if(len == 0) {
                return true;
            } else {
                int i = 0;

                while(i < len) {
                    switch(str.charAt(i)) {
                        case '\t':
                        case '\n':
                        case '\r':
                        case ' ':
                            ++i;
                            break;
                        default:
                            return false;
                    }
                }

                return true;
            }
        }
    }

    public static boolean notBlank(String str) {
        return !isBlank(str);
    }

    public static boolean notBlank(String... strings) {
        if(strings == null) {
            return false;
        } else {
            String[] arr$ = strings;
            int len$ = strings.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                String str = arr$[i$];
                if(isBlank(str)) {
                    return false;
                }
            }

            return true;
        }
    }

    public static boolean notNull(Object... paras) {
        if(paras == null) {
            return false;
        } else {
            Object[] arr$ = paras;
            int len$ = paras.length;

            for(int i$ = 0; i$ < len$; ++i$) {
                Object obj = arr$[i$];
                if(obj == null) {
                    return false;
                }
            }

            return true;
        }
    }

    public static String toCamelCase(String stringWithUnderline) {
        if(stringWithUnderline.indexOf(95) == -1) {
            return stringWithUnderline;
        } else {
            stringWithUnderline = stringWithUnderline.toLowerCase();
            char[] fromArray = stringWithUnderline.toCharArray();
            char[] toArray = new char[fromArray.length];
            int j = 0;

            for(int i = 0; i < fromArray.length; ++i) {
                if(fromArray[i] == 95) {
                    ++i;
                    if(i < fromArray.length) {
                        toArray[j++] = Character.toUpperCase(fromArray[i]);
                    }
                } else {
                    toArray[j++] = fromArray[i];
                }
            }

            return new String(toArray, 0, j);
        }
    }

    public static String join(String[] stringArray) {
        StringBuilder sb = new StringBuilder();
        String[] arr$ = stringArray;
        int len$ = stringArray.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            String s = arr$[i$];
            sb.append(s);
        }

        return sb.toString();
    }

    public static String join(String[] stringArray, String separator) {
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < stringArray.length; ++i) {
            if(i > 0) {
                sb.append(separator);
            }

            sb.append(stringArray[i]);
        }

        return sb.toString();
    }

    public static boolean slowEquals(String a, String b) {
        byte[] aBytes = a != null?a.getBytes():null;
        byte[] bBytes = b != null?b.getBytes():null;
        return HashKit.slowEquals(aBytes, bBytes);
    }

    public static String getRandomUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}

