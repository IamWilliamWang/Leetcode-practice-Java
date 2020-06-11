class LongestDupSubstring {
    public String longestDupSubstring(String S) {
        String dupSubString = "";
        // 从长到短每个长度都是一遍
        for (int substrLength = S.length() - 1; substrLength > 0; substrLength--) {
            // 暴力比较，i和j指向长度为substrLength的子串
            for (int i = 0; i < S.length() - substrLength + 1; i++) {// i和j的条件消除了子串长度不为substrLength的情况
                for (int j = i + 1; j < S.length() - substrLength + 1; j++) {
                    if (S.substring(i, i + substrLength).equals(S.substring(j, j + substrLength))) {// 如果子串相等，判断是否为更大的子串
                        String subString = S.substring(i, i + substrLength);
                        if (dupSubString.length() < subString.length()) {
                            dupSubString = subString;
                        }
                    }
                }
            }
        }
        return dupSubString;
    }
}