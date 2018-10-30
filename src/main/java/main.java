import org.json.JSONObject;

import java.util.*;

public class main {
    public static int getMaxOccurrences(String s, int minLength, int maxLength, int maxUnique) {
        // Write your code here
        Map<String, Integer> map = new HashMap<>();
        for (int i=0; i<s.length(); i++) {
            for (int j=minLength; j<=Math.min(maxLength, s.length()-i); j++) {
                String sub = s.substring(i, i+j);
                Set<Character> set = new HashSet<>();
                for (int k = 0; k < sub.length(); k++) {
                    set.add(sub.charAt(k));
                }
                if (set.size() <= maxUnique) {
                    int old = map.getOrDefault(sub, 0);
                    map.put(sub, old+1);
                }
            }
        }
        int ans = 0;
        for (String str: map.keySet()) {
            ans = Math.max(ans, map.get(str));
        }
        return ans;
    }

    public static void dfs(int left, int day, String p, int x, List<String> ans) {
        if (x == p.length()) {
            if (left == 0) {
                ans.add(p);
            }
            return;
        }
        if (p.charAt(x) != '?') {
            dfs(left-(p.charAt(x)-'0'), day, p, x+1, ans);
            return;
        }
        char[] arr = p.toCharArray();
        for (int i=0; i<=day; i++) {
            arr[x] = (char)('0'+i);
            dfs(left-i, day, String.valueOf(arr), x+1, ans);
        }
    }

    public static void main(String[] args) {
        String str = "{'GoodForMeal': {'dessert': False, 'latenight': False, 'lunch': False, 'dinner': False, 'brunch': False, 'breakfast': False}, 'Alcohol': 'beer_and_wine', 'HasTV': False, 'GoodForKids': True, 'NoiseLevel': 'average', 'WiFi': 'free', 'RestaurantsAttire': 'casual', 'RestaurantsReservations': True, 'OutdoorSeating': False, 'RestaurantsPriceRange2': 2, 'BikeParking': True, 'RestaurantsTableService': True, 'RestaurantsDelivery': True, 'Ambience': {'romantic': False, 'intimate': False, 'classy': False, 'hipster': False, 'touristy': False, 'trendy': False, 'upscale': False, 'casual': False}, 'RestaurantsTakeOut': True, 'RestaurantsGoodForGroups': True, 'WheelchairAccessible': False, 'BusinessParking': {'garage': False, 'street': True, 'validated': False, 'lot': False, 'valet': False}}";
        JSONObject j = new JSONObject(str);
        System.out.println(j.getBoolean("BikeParking"));
//        for (String key: j.keySet()){
//            System.out.println(key+": "+j.get(key).toString());
//        }
    }
}
