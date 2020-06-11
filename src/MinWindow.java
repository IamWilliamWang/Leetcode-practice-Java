import java.util.HashMap;

public class MinWindow {
    public String minWindow(String s, String t) {
        HashMap<Character, Integer> targetCounter = new HashMap<>();
        HashMap<Character, Integer> nowCounter = new HashMap<>();
        for (char ch : t.toCharArray())
            targetCounter.put(ch, targetCounter.getOrDefault(ch, 0) + 1);
        int windowStart = 0, windowEnd = 0;
        int valid = 0;
        String minStr = "";
        int minLen = Integer.MAX_VALUE;
        while (windowEnd < s.length()) {
            char endChar = s.charAt(windowEnd++);
            if (targetCounter.containsKey(endChar)) {
                nowCounter.put(endChar, nowCounter.getOrDefault(endChar, 0) + 1);
                if (nowCounter.get(endChar).equals(targetCounter.get(endChar)))
                    valid++;
            }

            while (valid == targetCounter.size()) {
                if (windowEnd - windowStart < minLen) {
                    minLen = windowEnd - windowStart;
                    minStr = s.substring(windowStart, windowEnd);
                }
                char startChar = s.charAt(windowStart++);
                if (targetCounter.containsKey(startChar)) {
                    if (nowCounter.get(startChar).equals(targetCounter.get(startChar)))
                        valid--;
                    nowCounter.put(startChar, nowCounter.getOrDefault(startChar, 0) - 1);
                }
            }
        }
        return minStr;
    }

    public static void main(String[] args) {
        System.out.println(new MinWindow().minWindow("sshcxyfgecymhhpmjrfjlmiwkaunetxwleajdfrjhrxrymdkdltoxbmjdhwhatfoafzfkqquhnlhcqrfdmwdnkmtrlaueiignjlazdwirhrtzladxygnjugcfiymqgsgpggqjcaclsxmdarcyzpjuxobimnytigkqodzsdedxbscblfclwlhuzkcmychiltyzwzscdxbhpekdlmojaxdbhhphmwpxsnwqposumwbdcognawycvcefltmxqcukrraihtdvrgztuhivggxbkdgwxvtpxozqhzzoueklklgfuocaxbehfkdehztepsxwtymocybojiveyzexbkfixkmelhjabiyuinkmloavywbyvhwysbipnwtzdebgocbwpniadjxbhyaegwdaznpokkppptwdvzckokksvkteivjqtoqubfjnqadhtzmoaoaobngwxabfxmwramlduurmxutqvfhvwhjxusttuwzrixikluqfqhtndmeaulvsugprakkuhjmriueuqubhdvwgjagfndxklmbmzlgixuzhpfbhzfqccnknnqtdvsphhqvgdboaypipvlwwsnzualipebuz", "tjiazd"));
    }
}
